package com.example.loginservice.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");     //是否设置边框
        properties.put("kaptcha.textproducer.font.color", "black");        // 文本的颜色
        properties.put("kaptcha.textproducer.char.space", "3");    // 每一个字符的空格
        properties.put("kaptcha.image.height", "40");
        properties.put("kaptcha.image.width", "120");
        properties.put("kaptcha.textproducer.font.size", "30");      // 字体的大小

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;


    }
}
