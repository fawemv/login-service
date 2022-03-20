package com.example.loginservice.config;

import com.example.loginservice.filter.CaptchaFilter;
import com.example.loginservice.security.LoginFailureHandler;
import com.example.loginservice.security.LoginSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    };
    @Resource
    LoginFailureHandler loginFailureHandler;
    @Resource
    CaptchaFilter captchaFilter;

    @Resource
    LoginSuccessHandler loginSuccessHandler;

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
                //      配置自定义过滤器的执行规则 ： 验证码过滤器在UsernamePasswordAuthenticationFilter之前执行
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class) // 登录验证码校验过滤器
        ;
    }
}
