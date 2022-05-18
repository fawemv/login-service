package com.example.loginservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class LoginServiceApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(LoginServiceApplication.class, args);
		/*for (String beanDefinitionName : run.getBeanDefinitionNames()) {
			System.out.println(beanDefinitionName);
		}*/
    }

}
