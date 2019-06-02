package com.github.dwflibrary.util;

public class TokenGenerator {

    public static String generate(int len){
        return new RandomString(len).nextString();
    }

    public static String generate(){
        return generate(6);
    }
}
