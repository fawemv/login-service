package com.example.loginservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/**
 * @author Lang
 * @Desc
 * @data 2022/4/27 23:22
 **/

@Configuration
public class UploadConfig {


    @Bean(name = "multipartResolver")//将方法返回值添加到Spring容器
    public StandardServletMultipartResolver addResolver() {
        return new StandardServletMultipartResolver();
    }


}
