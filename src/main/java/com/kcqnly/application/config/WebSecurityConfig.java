package com.kcqnly.application.config;


import cn.hutool.json.JSONUtil;
import com.kcqnly.application.common.Result;
import com.kcqnly.application.entity.User;
import com.kcqnly.application.filter.JwtTokenFilter;
import com.kcqnly.application.filter.LoginFilter;
import com.kcqnly.application.model.UserParam;
import com.kcqnly.application.service.UserService;
import com.kcqnly.application.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @www.codesheep.cn 20190312
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @Bean
    public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/verifyCode");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }


    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            User user = (User) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(user);
            UserParam userParam = new UserParam(user, token);
            Result result = Result.ok("登录成功", userParam);
            out.write(JSONUtil.toJsonStr(result));
            out.flush();
            out.close();
        });
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            Result result = Result.error(exception.getMessage());
            if (exception instanceof LockedException) {
                result.setMsg("账户被锁定，请联系管理员!");
            } else if (exception instanceof CredentialsExpiredException) {
                result.setMsg("密码过期，请联系管理员!");
            } else if (exception instanceof AccountExpiredException) {
                result.setMsg("账户过期，请联系管理员!");
            } else if (exception instanceof DisabledException) {
                result.setMsg("账户被禁用，请联系管理员!");
            } else if (exception instanceof BadCredentialsException) {
                result.setMsg("用户名或者密码输入错误，请重新输入!");
            }
            out.write(JSONUtil.toJsonStr(result));
            out.flush();
            out.close();
        });
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/login");
        return loginFilter;
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                //第3步：请求权限配置
                //放行注册API请求，其它任何请求都必须经过身份验证.
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST).authenticated()
                .antMatchers(HttpMethod.PUT).authenticated()
                .antMatchers(HttpMethod.DELETE).authenticated()
                .antMatchers(HttpMethod.GET).authenticated();
        //第2步：让Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().cacheControl();

        //没有认证时，在这里处理结果，不要重定向
        httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException authException) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                resp.setStatus(401);
                PrintWriter out = resp.getWriter();
                Result result = Result.error("访问失败!");
                if (authException instanceof InsufficientAuthenticationException) {
                    result.setMsg("请求失败，请联系管理员!");
                }
                out.write(JSONUtil.toJsonStr(result));
                out.flush();
                out.close();
            }
        });
        httpSecurity.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        //拦截token，并检测。在 UsernamePasswordAuthenticationFilter 之前添加 JwtAuthenticationTokenFilter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

}
