package com.chapter114.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**定义认证用户信息获取来源，密码校验规则等*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //inMemoryAuthentication 从内存中获取
        //auth.inMemoryAuthentication().withUser("demo").password("123456").roles("USER");
        //上面的语会报IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"，下面解决

        //Spring security 5.0中新增了多种加密方式,改变了密码的格式我们要将前端传过来的密码进行某种方式加密，spring security 官方推荐的是使用bcrypt加密方式
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("demo").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");

        //jdbcAuthentication从数据库中获取，但是默认是以security提供的表结构
        //usersByUsernameQuery 指定查询用户SQL
        //authoritiesByUsernameQuery 指定查询权限SQL
        //auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query).authoritiesByUsernameQuery(query);

        //注入userDetailsService，需要实现userDetailsService接口
        //auth.userDetailsService(userDetailsService);
        //Spring security 5.0
        // auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**定义安全策略*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//配置安全策略
                .antMatchers("/","/hello").permitAll()//定义/请求不需要验证
                .anyRequest().authenticated()//其余的所有请求都需要验证
                .and()
                .logout()
                .permitAll()//定义logout不需要验证
                .and()
                .formLogin();//使用form表单登录
    }


}
