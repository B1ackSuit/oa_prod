package cn.ean.emp.config.security;

import cn.ean.emp.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ean
 * @FileName JwtAuthenticationTokenFilter
 * @Date 2022/5/24 09:34
 **/

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    public JwtAuthenticationTokenFilter(JwtTokenUtils jwtTokenUtils,
//                                         UserDetailsService userDetailsService) {
//        this.jwtTokenUtils = jwtTokenUtils;
//        this.userDetailsService = userDetailsService;
//    }

    public JwtAuthenticationTokenFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = httpServletRequest.getHeader(tokenHeader);
        // 存在token
        if (null != authHeader && authHeader.startsWith(tokenHead)) {

            // 将JWT的tokenHead截取掉，就是token
            String authToken = authHeader.substring(tokenHead.length());
            String userName = jwtTokenUtils.getUserNameFromToken(authToken);

            // token存在用户名，但是未登录
            if (null != userName && null == SecurityContextHolder.getContext().getAuthentication()) {

                // 自动登录(UserDetailsService已重写)
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                // 验证token是否有效，重新设置用户对象
                if (jwtTokenUtils.validateToken(authToken, userDetails)) {
                    // 更新security登录用户对象
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }
}
