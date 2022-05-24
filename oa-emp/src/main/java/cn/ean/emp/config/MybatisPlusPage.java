package cn.ean.emp.config;


// import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ean
 * @FileName MybatisPlusPage
 * @Date 2022/5/24 10:03
 **/
@Configuration
public class MybatisPlusPage {

    /**
     * 3.4以前版本
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInnerInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 3.4以后版本
     */
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
//        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
//        return mybatisPlusInterceptor;
//    }
}
