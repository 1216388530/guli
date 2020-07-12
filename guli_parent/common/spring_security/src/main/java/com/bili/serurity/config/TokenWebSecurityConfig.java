package com.bili.serurity.config;


import com.bili.serurity.filter.TokenAuthenticationFilter;
import com.bili.serurity.filter.TokenLoginFilter;
import com.bili.serurity.security.DefaultPasswordEncoder;
import com.bili.serurity.security.TokenLogoutHandler;
import com.bili.serurity.security.TokenManager;
import com.bili.serurity.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * 核心配置类
 * </p>
 *
 * @author qy
 * @since 2019-11-18
 */
//一个配置类
@Configuration
/*
EnableWebSecurity注解有两个作用:
1: 加载了WebSecurityConfiguration配置类, 配置安全认证策略。
2: 加载了AuthenticationConfiguration, 配置了认证信息。
 */
@EnableWebSecurity
//开启注解模式，可以使用注解在框架中
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    //自己写的 ，查数据库的类
    private UserDetailsService userDetailsService;
    //生成token的工具
    private TokenManager tokenManager;
    //密码处理
    private DefaultPasswordEncoder defaultPasswordEncoder;
    //redis操作
    private RedisTemplate redisTemplate;

    //构造器注入
    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 实现对用户/角色的请求访问控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //AuthenticationEntryPoint() 用来解决匿名用户访问无权限资源时的异常
        //AccessDeineHandler() 用来解决认证过的用户访问无权限资源时的异常
        http.exceptionHandling()//处理异常
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and().csrf().disable()// CSRF问题
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/admin/acl/index/logout")//指定登出的地址
                /**
                 * LogoutHandler 即在程序执行logout时一起参与执行其中的处理逻辑，不能抛出异常，官方默认提供了几个实现。
                 *
                 * PersistentTokenBasedRememberMeServices
                 * TokenBasedRememberMeServices 移除Token
                 * CookieClearingLogoutHandler 清楚Cookie
                 * CsrfLogoutHandler 移除CSRF TOKEN
                 * SecurityContextLogoutHandler
                 * HeaderWriterLogoutHandler
                 * LogoutSuccessHandler
                 * 在调用完LogoutHandler之后，并且处理成功后调用，可以抛出异常，官方默认提供了两个
                 *
                 * SimpleUrlLogoutSuccessHandler 不需要直接指定，在指定logoutSuccessUrl()会自己使用
                 * HttpStatusReturningLogoutSuccessHandler 返回登出成功后的状态码
                 * 最后
                 * 在登出的时候区分是接口登出还是页面登出，针对不同的登出做不同的处理。
                 *
                 * 作者：Real_man
                 * 链接：https://www.jianshu.com/p/a061c28d8202
                 * 来源：简书
                 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
                 */
                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
                //在请求过滤器
                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
    }

    /**
     * 配置用户信息，表示从数据库中获取用户信息，并且配置密码处理方式
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截，还可以配置过滤器
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {//哪些可以不进行权限控制，直接访问
        web.ignoring().antMatchers("/api/**",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
               );
       // web.ignoring().antMatchers("/*/**"
       // );
    }
}