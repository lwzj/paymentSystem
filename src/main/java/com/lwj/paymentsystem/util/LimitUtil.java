package com.lwj.paymentsystem.util;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName LimitUtil
 * @Description 限流工具类,分布式限流--Redis+lua
 * @Author junliang
 * @Date 2018/12/12 10:20 AM
 * @Version 1.0
 **/
public class LimitUtil {

    private static final AtomicLong ATOMIC_LONG = new AtomicLong(1000);
    private static final int BUCKET_NUM = 1000;
    private static Long TIMESPACE = 0L;
    private static final Long DURATION = 60*1000L;
    public static boolean tokenBucket(){
        if (ATOMIC_LONG.get()<=0){
            System.out.println("request limit...");
            return false;
        }
        if (ATOMIC_LONG.get()==BUCKET_NUM){
            ATOMIC_LONG.decrementAndGet();
            TIMESPACE = DateUtil.getTimestamp(LocalDateTime.now());
            System.out.println("first join..."+TIMESPACE);
        }else{
            if (DateUtil.getTimestamp(LocalDateTime.now())-TIMESPACE>=DURATION){
                System.out.println("reset...");
                ATOMIC_LONG.set(BUCKET_NUM-1);
                TIMESPACE = DateUtil.getTimestamp(LocalDateTime.now());
            }else{
                ATOMIC_LONG.decrementAndGet();
            }
        }
        System.out.println(ATOMIC_LONG.get());
        return true;
    }

    public static void leakyBucket(){

    }

    public static void main(String[] args) {
        //google 令牌桶的实现
        RateLimiter rateLimiter = RateLimiter.create(5);
        for (int i=0;i<100;i++){
            System.out.println(rateLimiter.tryAcquire());
        }
        /*for (int i=0;i<500;i++){
            tokenBucket();
        }
        try {
            Thread.sleep(1000*60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i=0;i<500;i++){
            tokenBucket();
        }*/
    }
}
