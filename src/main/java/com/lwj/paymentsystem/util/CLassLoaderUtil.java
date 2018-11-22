package com.lwj.paymentsystem.util;

import sun.misc.Launcher;

import java.net.URL;
import java.util.stream.Stream;

public class CLassLoaderUtil {
    public static void main(String[] args) {
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        Stream.of(urls).forEach((a) -> System.out.println(a.toExternalForm()));
        System.out.println(System.getProperty("sun.boot.class.path"));

        String rootUrl = "http://localhost:8080/httpweb/classes";
        UserDefinedClassLoader userDefinedClassLoader = new UserDefinedClassLoader(rootUrl);
        String className = "";
        Class clazz = null;
        try {
            clazz = userDefinedClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(clazz);
    }
}
