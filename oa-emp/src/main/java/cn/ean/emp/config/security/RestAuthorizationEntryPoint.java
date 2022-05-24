package cn.ean.emp.config.security;

import cn.ean.emp.model.bo.ResponseBO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当未登录或者token失效时访问接口时，自定义的返回结果
 * @author ean
 * @FileName RestAuthorizationEntryPoint
 * @Date 2022/5/24 09:35
 **/
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {


        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

        PrintWriter out = httpServletResponse.getWriter();

        ResponseBO bo = ResponseBO.error("尚未登录，请重新登录");
        bo.setCode(401);

        out.write(new ObjectMapper().writeValueAsString(bo));
        out.flush();
        out.close();

    }

}
