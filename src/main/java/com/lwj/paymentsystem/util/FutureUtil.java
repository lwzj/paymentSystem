package com.lwj.paymentsystem.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName FutureUtil
 * @Description TODO
 * @Author junliang
 * @Date 2018/12/12 4:12 PM
 * @Version 1.0
 **/
public class FutureUtil {
    final static ExecutorService EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        RpcService rpcService = new RpcService();
        HttpService httpService = new HttpService();
        Future<Map<String,String>> future1 = null;
        Future<Integer> future2 = null;
        try {
            future1 = EXECUTOR_SERVICE.submit(() -> rpcService.getRpcResult());
            future2 = EXECUTOR_SERVICE.submit(() -> httpService.getHttpResult());
            Map<String,String> result1 = future1.get(300, TimeUnit.MILLISECONDS);
            Integer result2 = future2.get(300,TimeUnit.MILLISECONDS);
        }catch (Exception e){
            if (future1!=null){
                future1.cancel(true);
            }
            if (future2!=null){
                future2.cancel(true);
            }
            throw new RuntimeException(e);
        }
    }
    static class RpcService{
        Map<String,String> getRpcResult() throws InterruptedException {
            Thread.sleep(10);
            return new HashMap<>();
        }
    }
    static class HttpService{
        Integer getHttpResult() throws InterruptedException {
            Thread.sleep(20);
            return 0;
        }
    }
}
