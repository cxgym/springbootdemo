package com.wondersgroup.springbootsecuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity    //开启web安全
@SpringBootApplication
public class SpringbootsecuritydemoApplication extends WebSecurityConfigurerAdapter {

    /**
     * 添加安全授权规则
     * ctrl+o打开WebSecurityConfigurerAdapter可重写的方法
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //定制请求的授权规则
        //"/"请求的首页路径permitAll()全部允许
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        //开启自动配置的登录功能，如果访问的路径没有认证或者授权就会跳到登录页面（注意这个登录页面是springsecurity自带的，我们也可以自定义登录页面）
        //http.formLogin();
        //自定义登录页面，这里的user和pwd要和login.html中的<input name="">标签的属性name一一对应
        http.formLogin()
                .usernameParameter("user")
                .passwordParameter("pwd")
                .loginPage("/userlogin");
        //开启自动配置的注销功能，访问logout表示用户注销清空session，注销成功会返回/login?logout页面（这个相当于登录界面）
        //http.logout();
        //自定义注销成功后的返回路径，返回首页
        http.logout().logoutSuccessUrl("/");
        //开启记住我功能
        //这些功能的实现都是springsecurity框架帮我们做好的
        //原理就是登录成功后将cookie发给浏览器保存，以后访问页面带上这个cookie只要通过检查就可以免登录
        //点击注销会删除这个cookie
        //自定义登录页面记住我，这里的remember要和login.html中的<input name="">标签的属性name一一对应
        http.rememberMe().rememberMeParameter("remember");
    }

    /**
     * 添加安全认证规则
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        //这里的密码进行了bcrypt加密处理
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("chenxin").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1","VIP2")
                .and()
                .withUser("chenxin01").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP3");
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootsecuritydemoApplication.class, args);
    }

}
