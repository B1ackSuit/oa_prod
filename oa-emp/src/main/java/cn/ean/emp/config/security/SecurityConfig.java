package cn.ean.emp.config.security;

import cn.ean.emp.model.dto.UserDTO;
import cn.ean.emp.model.po.UserPO;
import cn.ean.emp.service.IUserService;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author ean
 * @FileName SecurityConfig
 * @Date 2022/5/24 09:55
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 出循环注入问题需要加@Lazy注解
     */
    private IUserService userService;

    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    private RoleFilter roleFilter;

    private RoleUrlDecisionManager roleUrlDecisionManager;


    @Autowired
    public SecurityConfig(@Lazy IUserService userService,
                          RestfulAccessDeniedHandler restfulAccessDeniedHandler,
                          RestAuthorizationEntryPoint restAuthorizationEntryPoint,
                          RoleFilter roleFilter,
                          RoleUrlDecisionManager roleUrlDecisionManager) {

        this.userService = userService;
        this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
        this.restAuthorizationEntryPoint = restAuthorizationEntryPoint;
        this.roleFilter = roleFilter;
        this.roleUrlDecisionManager = roleUrlDecisionManager;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 放的路径不走拦截
        web.ignoring()
                .antMatchers(
                        "/login",
                        "/logout",
                        "/hello",
                        // 前端静态资源的放行
                        "/css/**",
                        "/js/**",
                        "/index.html",
                        "favicon.ico",
                        // swagger2的放行
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        // 验证码接口放行
                        "/captcha",
                        //WebSocket
                        "/ws/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用JWT，不需要csrf
        http.csrf().disable()
                // 基于token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 所有请求都要认证，除了登录访问
                .authorizeRequests()

                .anyRequest()
                .authenticated()
                //     .antMatchers("/login", "/logout")
                //    .permitAll()

                // 动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(roleUrlDecisionManager);
                        o.setSecurityMetadataSource(roleFilter);
                        return o;
                    }

                })
                .and()
                // 禁用缓存
                .headers()
                .cacheControl();

        // 添加JWT登录授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            UserPO userPO = userService.getUserByUserName(username);
            UserDTO userDTO = new UserDTO()
                    .setId(userPO.getId())
                    .setWorkId(userPO.getWorkId())
                    .setPassword(userPO.getPassword())
                    .setName(userPO.getName())
                    .setPhone(userPO.getPhone())
                    .setUserFace(userPO.getUserFace())
                    .setEnabled(userPO.getEnabled());
            if (null != userDTO) {
                userDTO.setRolePOs(userService.getRoles(userDTO.getId()));
                return userDTO;
            }
            // Spring Security提供的异常
            throw new UsernameNotFoundException("===Security debugger: UserDetailService: 用户名或密码不正确");
        };

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

}
