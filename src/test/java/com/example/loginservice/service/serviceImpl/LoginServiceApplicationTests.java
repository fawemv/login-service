package com.example.loginservice.service.serviceImpl;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.service.IStudentService;
import com.example.loginservice.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Collections;
import java.util.UUID;

@SpringBootTest
class LoginServiceApplicationTests {


    @Resource
    private IStudentService iStudentService;

    @Resource
    private RedisUtil redisUtil;

    //   基本代码生成
    @Test
    public void contextLoads() {
        //            表：
        /**
         * announcement
         * building
         * exchange
         * institute
         * repairs
         * room
         * student
         * sysuser
         * */
        //   mybatis plus代码生成工具
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/student_room?useSSL=false", "root", "password")
                .globalConfig(builder -> {
                    builder.author("陈浪") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            //.outputDir("F:\\毕设代码\\login-service\\src\\main\\java\\com\\example\\loginservice\\servicecode"); // 指定输出目录
                            .outputDir("F:\\毕设代码\\login-service");
                    //.outputDir("F:\\毕设代码"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("test") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            //  .pathInfo(Collections.singletonMap(OutputFile.entity, "F:\\毕设代码\\后端\\studentRoom\\login-service\\src\\main\\java\\com\\example\\loginservice\\servicecode\\pojo"))
                            .pathInfo(Collections.singletonMap(OutputFile.mapper, "F:\\毕设代码\\login-service\\src\\main\\java\\com\\example\\loginservice\\servicecode\\mapper"))
                            .pathInfo(Collections.singletonMap(OutputFile.service, "F:\\毕设代码\\后端\\studentRoom\\login-service\\src\\main\\java\\com\\example\\loginservice\\servicecode\\service"))
                            .pathInfo(Collections.singletonMap(OutputFile.serviceImpl, "F:\\毕设代码\\后端\\studentRoom\\login-service\\src\\main\\java\\com\\example\\loginservice\\service\\serviceImpl"))
                            .pathInfo(Collections.singletonMap(OutputFile.controller, "F:\\毕设代码\\后端\\studentRoom\\login-service\\src\\main\\java\\com\\example\\loginservice\\servicecode\\controller"))
                    // 设置mapperXml生成路径
                    ;
                })
                .strategyConfig(builder -> {
                    builder.addInclude("exchange") // 设置需要生成的表名
                            .addTablePrefix("u_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    @Test
    public void test() {
        //  测试生成的代码是否可用，使用的是否是逻辑删除

        /*Student student = new Student();
        student.setsId(new Long(1086034111));
        student.setPassword("password");
        student.setName("陈浪");
        student.setSex("男");
        student.setInstituteId(1);
        student.setGrade(18);

        System.out.println(iStudentService);
        boolean save = iStudentService.save(student);*/

        //iStudentService.removeById(new Long(1806034111));
        redisUtil.set("name", "陈浪");
    }

}
