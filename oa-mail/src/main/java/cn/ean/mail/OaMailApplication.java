package cn.ean.mail;

import cn.ean.emp.model.bo.MailBO;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OaMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaMailApplication.class, args);
    }

    @Bean
    public Queue queue() {
        return new Queue(MailBO.MAIL_QUEUE_NAME);
    }

}
