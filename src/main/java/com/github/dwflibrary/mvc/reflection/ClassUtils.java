package com.github.dwflibrary.mvc.reflection;

public class ClassUtils {

    public static Class getClass(String name){
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ignored) {}
        return null;
    }

}
