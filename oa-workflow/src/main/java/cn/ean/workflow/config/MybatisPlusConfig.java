package cn.ean.workflow.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ean
 * @FileName MybatisPlusConfig
 * @Date 2022/5/25 10:57
 **/
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页，MybatisPlus版本不同对应Bean不同，具体参考主项目MP配置
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
