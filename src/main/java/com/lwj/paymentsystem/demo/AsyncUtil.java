package com.lwj.paymentsystem.demo;

import java.util.concurrent.*;

/**
 * @ClassName AsyncUtil
 * @Description TODO
 * @Author junliang
 * @Date 2018/12/14 10:37 AM
 * @Version 1.0
 **/
public class AsyncUtil {
    public static void test(){
        doSomeLongComputation();
        doSomethingElse();
    }
    public static void futureTest(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> doubleFuture = executorService.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomeLongComputation();
            }
        });
        doSomethingElse();
        try {
            Double d = doubleFuture.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static Double doSomeLongComputation(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0D;
    }
    public static void doSomethingElse(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Long begin = System.currentTimeMillis();
//        futureTest();
        test();
        Long end = System.currentTimeMillis();
        System.out.println(end-begin);
    }
}
