package com.example.loginservice.service.serviceImpl;

import com.example.loginservice.service.KaptchaService;
import com.example.loginservice.utils.RedisUtil;
import com.google.code.kaptcha.Producer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class KaptchaServiceImpl implements KaptchaService {

    @Resource
    private Producer producer;


    @Resource
    private RedisUtil redisUtil;


    @Override
    public Map<String, Object> getKaptcha() {
        // 验证码的key
        String key = UUID.randomUUID().toString();
        //生成验证码google
        String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray());

        // 将验证码存入redis
        //redisTemplate.opsForHash().put("captcha", key, code);
        redisUtil.set(key, code, 2 * 60);

        Map<String, Object> map = new HashMap<>();
        map.put("image", base64Img);
        map.put("key", key);

        return map;
    }
}
