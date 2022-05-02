package com.example.loginservice.controller;

import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.map.MapUtil;
import com.example.loginservice.utils.JwtUtils;
import com.example.loginservice.utils.RedisUtil;
import com.example.loginservice.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class UtilsController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JwtUtils jwtUtils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //@PreAuthorize("hasRole('admin')")
    @GetMapping("/sumRequest")
    public Result sumRequest() {
        System.out.println("zhixing");
        Result result = new Result();
        //  请求添加到redis sumRequest
        redisUtil.incr("sumRequest", 1);
        Object sumRequest = redisUtil.get("sumRequest");
        Result succ = result.succ(sumRequest);

        return succ;
    }


    @GetMapping("/test/pass")
    public Result passEncode() {
        // 密码加密
        String pass = bCryptPasswordEncoder.encode("111111");

        // 密码验证
        boolean matches = bCryptPasswordEncoder.matches("111111", pass);

        return Result.succ(MapUtil.builder()
                .put("pass", pass)
                .put("marches", matches)
                .build()
        );
    }

    //   注销登录
    @GetMapping("/outLogin")
    public Result outLogin(HttpServletRequest request) {
        Result result = null;
        // 获取token 清除redis中的token
        String token = request.getHeader(jwtUtils.getHeader());

        try {
            redisUtil.del(token);
            result = Result.succ(200, "注销成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.succ(400, "注销失败", null);
        }

        return result;
    }

    // 处理文件上传
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
        Result result = null;
        //检查文件是否为空
        if (file.isEmpty()) {
            result = Result.fail("上传文件不能为空");
            return result;
        }
        //检查文件大小  2097152 =2M
        if (file.getSize() > 2097152) {
            result = Result.fail("文件大于2M");
            return result;
        }
        //检查是否是图片
        try {
            BufferedImage bi = ImageIO.read(file.getInputStream());
            if (bi == null) {
                result = Result.fail("不是图片");
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取文件存储路径（绝对路径），实际开发中，建议直接使用一个服务器的绝对地址
        //获得项目的类路径
        String path = System.getProperty("user.dir");
        path = path + "/target/classes/static/upload/img/";
        // String path1 = request.getServletContext().getRealPath("/WEB-INF/upload/img/");
        // String resourcePath= ResourceUtils.getURL("classpath:").getPath();
//        System.out.println("服务器中的相对/WEB-INF/upload/完整路径："+path);
        //获取上传文件后缀名
//        System.out.println("客户端上传的文件名:"+file.getOriginalFilename());
        String originalFilename = file.getOriginalFilename();
        String suff = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 获取文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suff;
        // 创建文件实例
        File filePath = new File(path, fileName);
        // 如果文件目录不存在，创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录" + filePath);
        }
        // 写入文件
        file.transferTo(filePath);

        result = Result.succ(200, "上传成功", "http://localhost:8081/upload/img/" + fileName);
        return result;
    }
}
