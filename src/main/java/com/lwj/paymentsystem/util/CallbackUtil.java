package com.lwj.paymentsystem.util;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.BasicAsyncResponseConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName CallbackUtil
 * @Description TODO
 * @Author junliang
 * @Date 2018/12/12 4:48 PM
 * @Version 1.0
 **/
public class CallbackUtil {
    public static HttpAsyncClient httpAsyncClient;
    public static CompletableFuture<String> getHttpData(String url){
        CompletableFuture asyncFuture = new CompletableFuture();
        HttpAsyncRequestProducer producer =
                HttpAsyncMethods.create(new HttpPost(url));
        BasicAsyncResponseConsumer consumer =
                new BasicAsyncResponseConsumer();
        FutureCallback  callback = new FutureCallback() {
            @Override
            public void completed(Object o) {
                asyncFuture.complete(o);
            }

            @Override
            public void failed(Exception e) {
                asyncFuture.completeExceptionally(e);
            }

            @Override
            public void cancelled() {
                asyncFuture.cancel(true);
            }
        };
        httpAsyncClient.execute(producer,consumer,callback);
        return asyncFuture;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future =
                CallbackUtil.getHttpData("http://www.jd.com");
        String result = future.get();
    }
}
