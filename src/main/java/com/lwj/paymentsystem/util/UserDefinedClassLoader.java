package com.lwj.paymentsystem.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UserDefinedClassLoader extends ClassLoader{
    private String rootUrl;
    public UserDefinedClassLoader(String rootUrl){
        this.rootUrl = rootUrl;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData==null){
            throw new ClassNotFoundException();
        }
        return defineClass(name,classData,0,classData.length);
    }
    private byte[] getClassData(String name){
        InputStream is = null;
        try {
            String path = classNameToPath(name);
            URL url = new URL(path);
            byte[] buff = new byte[1024*4];
            int len = -1;
            is = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = is.read(buff))!= -1){
                baos.write(buff,0,len);
            }
            return baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    private String classNameToPath(String name){
        return rootUrl+"/"+name.replace(".","/")+".class";
    }
}
