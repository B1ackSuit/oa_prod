package cn.ean.workflow;


import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc // 开启swagger接口文档
@SpringBootApplication
public class OaWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaWorkflowApplication.class, args);
    }

}
