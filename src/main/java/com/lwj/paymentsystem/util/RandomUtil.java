package com.lwj.paymentsystem.util;

import java.text.DecimalFormat;
import java.util.Random;

public class RandomUtil {
    public static void main(String[] args) {
//        DecimalFormat df = new DecimalFormat("#.0");
//        System.out.println(df.format(Math.random()*2+5.8));
        Random random = new Random();
        int i = random.nextInt(3);
        System.out.println(i);
    }
}
