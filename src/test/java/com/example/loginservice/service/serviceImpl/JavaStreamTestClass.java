package com.example.loginservice.service.serviceImpl;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

public class JavaStreamTestClass {
    @Test
    public void test1(){
        Stream<String> stream = Arrays.stream(
                new String[]{"java", "c++", "c#", "python",
                        "jsoup", "jwt", "redis","zkshop"});
        // 过滤器和limit skip sorted测试
        stream.filter( x -> !x.equals("c++") )
                .skip(1)
                .limit(5)
                .sorted()
                .forEach(System.out::println);

    }
}
