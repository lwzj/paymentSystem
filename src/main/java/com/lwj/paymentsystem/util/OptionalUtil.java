package com.lwj.paymentsystem.util;

import java.util.Optional;
import java.util.Properties;

public class OptionalUtil {
    public static Optional<Integer> stringToInt(String s){
        try{
            return Optional.of(Integer.parseInt(s));
        }catch (NumberFormatException e){
            return Optional.empty();
        }
    }
    public static int readDuration(Properties properties,String name){
        return Optional.ofNullable(properties.getProperty(name))
                .flatMap(OptionalUtil::stringToInt)
                .filter(i -> i>0)
                .orElse(0);
    }

    public static void main(String[] args) {
        Optional<Integer> optionalInteger = stringToInt("");
        System.out.println(optionalInteger.get());
    }
}
