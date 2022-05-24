package cn.ean.emp.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ean
 * @FileName MybatisPlusPage
 * @Date 2022/5/24 10:03
 **/
@Configuration
public class MybatisPlusPage {

    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }
}
