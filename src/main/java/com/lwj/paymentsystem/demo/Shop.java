package com.lwj.paymentsystem.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName Shop
 * @Description TODO
 * @Author junliang
 * @Date 2018/12/14 11:08 AM
 * @Version 1.0
 **/
public class Shop {
    private String name;

    public Shop(String name) {
        this.name = name;
    }
    static List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("liangjun"),new Shop("likaili"),
            new Shop("liangwei"),new Shop("liangjiatong"));
    private static final Executor executor =
            Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setDaemon(true);
                            return t;
                        }
                    });
    private static Random random = new Random();
    public double getPrice(String product){
        return calculatePrice(product);
    }
    public String getPrice_string(String product){
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()
                [random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s",name,price,code);
    }
    public Future<Double> getPriceAsync(String product){
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                future.complete(price);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        }).start();
        return future;
    }
    public Future<Double> getPriceAsync1(String product){
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }
    public static void delay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void randomDelay(){
        int delay = 500+random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private double calculatePrice(String product){
//        delay();
        randomDelay();
        return random.nextDouble()*product.charAt(0)+product.charAt(1);
    }
    public static List<String> findPrices(String product){
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f",shop.getName()
                ,shop.getPrice(product))).collect(Collectors.toList());
    }
    public static List<String> findPrices_parallel(String product){
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f",shop.getName()
                        ,shop.getPrice(product))).collect(Collectors.toList());
    }
    public static List<String> findPrices_async(String product){
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() ->
                shop.getName()+"price is "+shop.getPrice(product)))
                .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
    public static List<String> findPrices_async_2(String product){
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() ->
                                shop.getName()+"price is "+shop.getPrice(product),executor))
                        .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
    public static List<String> findPrices_disCount(String product){
        return shops.stream()
                .map(shop -> shop.getPrice_string(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }
    public static List<String> findPrices_disCount_async(String product){
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(() ->
                                shop.getPrice_string(product),executor))
                        .map(future -> future.thenApplyAsync(Quote::parse))
                        .map(future -> future.thenCompose(
                                quote -> CompletableFuture.supplyAsync(
                                        ()-> Discount.applyDiscount(quote),executor)))
                        .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
    public static Stream<CompletableFuture<String>> findPricesStream(String product){
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPrice_string(product),executor
                ))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                CompletableFuture.supplyAsync(
                        () -> Discount.applyDiscount(quote),executor
                )));
    }
    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
//        System.out.println(Runtime.getRuntime().availableProcessors());
//        System.out.println(findPrices("myphone27s"));//5117ms
//        System.out.println(findPrices_parallel("myphone27s"));//2098ms
//        System.out.println(findPrices_async("myphone27s"));//2073ms
//        System.out.println(findPrices_async_2("myphone27s"));//1078ms
//        System.out.println(findPrices_disCount("myphone27s"));//10158ms
//        System.out.println(findPrices_disCount_async("myphone27s"));//2122ms
        CompletableFuture[] futures = findPricesStream("myphone27s")
                .map(f -> f.thenAccept(string -> System.out.println(string
                + " (done in "+(System.nanoTime()-start)/1_000_000+" msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
//        Future<Double> future = shop.getPriceA、sync("my favorite product");
//        double price = shop.getPrice("my favorite product");
//        Future<Double> future = shop.getPriceAsync1("my favorite product");
//        System.out.println(shop.getPrice_string("my favorite product"));
        long invocationYime = (System.nanoTime()-start)/1_000_000;
        System.out.println(invocationYime);
//        delay();
//        try {
//            double price = future.get();
//            System.out.printf("price is %.2f%n",price);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        long retry = (System.nanoTime()-start)/1_000_000;
//        System.out.println(retry);
    }

    public String getName() {
        return name;
    }
}
