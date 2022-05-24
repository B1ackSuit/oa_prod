package cn.ean.emp.config.security;

import cn.ean.emp.model.bo.ResponseBO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当访问接口没有权限时，自定义的返回结果
 * @author ean
 * @FileName RestfulAccessDeniedHandler
 * @Date 2022/5/24 09:37
 **/
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        ResponseBO bo = ResponseBO.error("权限不足，请联系管理员！");
        bo.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(bo));
        out.flush();
        out.close();

    }
}

