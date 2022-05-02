package com.example.loginservice.config;

import com.example.loginservice.exception.JwtAuthenticationEntryPoint;
import com.example.loginservice.filter.CaptchaFilter;
import com.example.loginservice.filter.JWTAuthenticationFilter;
import com.example.loginservice.security.LoginFailureHandler;
import com.example.loginservice.security.LoginSuccessHandler;
import com.example.loginservice.service.serviceImpl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)       //
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {     // security的策略配置
    public static final String[] URL_WHITELIST = {
            "/webjars/**",
            "/favicon.ico",
            "/goLogin",
            "/captcha",
            "/sumRequest",
            //"/swagger-ui.html", // swagger的接口文档
            //"/login",
            "/logout",
            "/user/**",
            // 配置静态资源过滤
            "/upload/img/**"
    };
    @Resource
    LoginFailureHandler loginFailureHandler;
    @Resource
    CaptchaFilter captchaFilter;

    @Resource
    LoginSuccessHandler loginSuccessHandler;

    @Resource
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    UserDetailsServiceImpl userDetailsService;

    //        配置http相关安全的
    //    配置允许跨域cors
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //    配置允许跨域cors
        http.cors().and().csrf().disable()


                //异常处理器的配置

                // 登录配置
                .formLogin()   // 选用form表单的提交方式，因为使用原来的UsernamePasswordAuthtionFilter
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)

                .and()
                //    配置拦截规则
                .authorizeRequests()
                //   白名单，这些请求不会被拦截
                .antMatchers(URL_WHITELIST).permitAll()
                //   其他的都需要拦截
                .anyRequest().authenticated()
                // 不会创建 session
                .and()
                //   禁用session
                .sessionManagement()
                // 生成规则，不生成session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                .and()
                .addFilter(jwtAuthenticationFilter())
                //      配置自定义过滤器的执行规则 ： 验证码过滤器在UsernamePasswordAuthenticationFilter之前执行
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class) // 登录验证码校验过滤器

        ;
    }

    //认证过滤器和认证失败入口配置到SecurityConfig中
    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager());
        return filter;
    }

    /**首先来插入一条用户数据，但这里有个问题，就是我们的密码怎么生成？密码怎么来的？
     * 这里我们使用Security内置了的BCryptPasswordEncoder，里面就有生成和匹配密码是否正确的方法，
     * 也就是加密和验证策略。
     * */
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 因为security在认证用户身份的时候会调用UserDetailsService.loadUserByUsername()方法，
     * 因此我们重写了之后security就可以根据我们的流程去查库获取用户了。
     * 然后我们把UserDetailsServiceImpl配置到SecurityConfig中：*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
