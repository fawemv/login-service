package com.example.loginservice.filter;

import cn.hutool.core.util.StrUtil;
import com.example.loginservice.service.serviceImpl.AccountUser;
import com.example.loginservice.service.serviceImpl.UserDetailsServiceImpl;
import com.example.loginservice.servicecode.entity.Sysuser;
import com.example.loginservice.servicecode.service.ISysuserService;
import com.example.loginservice.utils.JwtUtils;
import com.example.loginservice.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeSet;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ISysuserService sysUserService;


    @Resource
    UserDetailsServiceImpl userDetailsServiceImpl;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("jwt 校验 filter");
        String jwt = request.getHeader(jwtUtils.getHeader());
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }
        Claims claim = jwtUtils.getClaimByToken(jwt);
        if (claim == null) {
            throw new JwtException("token异常！");
        }
        if (jwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token已过期");
        }
        String username = claim.getSubject();
        log.info("用户-{}，正在访问:" + request.getRequestURL(), username);

        // 权限
        String userAuthority = null;
        userAuthority = (String) redisUtil.get(jwt);
        if (userAuthority == null || "".equals(userAuthority)) {
            userAuthority = userDetailsServiceImpl.getUserAuthority(username);
            if (userAuthority != null) {
                // 权限存入redis缓存 , 缓存时间小一点，还可以防止权限变更是吧
                redisUtil.set(jwt, userAuthority, 60 * 30);
            }
        }


        // 根据token从缓存中获取权限
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(userAuthority));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }
}
