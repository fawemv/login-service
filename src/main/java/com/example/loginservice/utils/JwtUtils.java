package com.example.loginservice.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "markerhub.jwt")
public class JwtUtils {

    private long expire;
    private String secret;
    private String header;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisTemplate redisTemplate;


    // 生成jwt
    public String generateToken(String username) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)// 7天過期
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        // 添加到redis缓存中map : token , value就是用户的唯一标识符，便于后面使用
        //redisUtil.sSet("token", token);
        redisTemplate.opsForHash().put("token", token, "userId");


        return token;
    }

    // 解析jwt
    public Claims getClaimByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // jwt是否过期
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    //参数：token   功能： 返回管理员账号或者学生学号
    public String parseTokenToUsername(String token) {
        Claims claimByToken = getClaimByToken(token);
        String username = claimByToken.getSubject();
        return username;
    }

}
