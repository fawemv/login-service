package com.example.loginservice.service.serviceImpl;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class LoginServiceApplicationTests {


    @Test
    public void contextLoads() {
        //   mybatis plus代码生成工具
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/student_room?useSSL=false", "root", "password")
                .globalConfig(builder -> {
                    builder.author("陈浪") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("F:\\毕设代码\\login-service\\src\\main\\java\\com\\example\\loginservice"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            //.pathInfo(Collections.singletonMap(OutputFile.entity, "F:\\毕设代码\\后端\\studentRoom\\login-service\\src\\main\\java\\com\\example\\loginservice\\pojo"))
                            .pathInfo(Collections.singletonMap(OutputFile.mapper, "F:\\毕设代码\\login-service\\src\\main\\java\\com\\example\\loginservice\\mapper"))
                            //.pathInfo(Collections.singletonMap(OutputFile.service, "F:\\毕设代码\\后端\\studentRoom\\login-service\\src\\main\\java\\com\\example\\loginservice\\service"))
                            //.pathInfo(Collections.singletonMap(OutputFile.serviceImpl, "F:\\毕设代码\\后端\\studentRoom\\login-service\\src\\main\\java\\com\\example\\loginservice\\service\\serviceImpl"));
                    //.pathInfo(Collections.singletonMap(OutputFile.controller, "F:\\毕设代码\\后端\\studentRoom\\login-service\\src\\main\\java\\com\\example\\loginservice\\controller"))
                    // 设置mapperXml生成路径
                    ;
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sysUser") // 设置需要生成的表名
                            .addTablePrefix("u_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
