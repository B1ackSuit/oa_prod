package cn.ean.mail;

import cn.ean.emp.model.bo.MailBO;
import cn.ean.emp.model.po.EmployeePO;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import java.io.IOException;
import java.util.Date;

/**
 * @author ean
 * @FileName MailReceiver
 * @Date 2022/5/25 17:39
 **/
@Component
public class MailReceiver {

    public static final Logger LOGGER = LoggerFactory.getLogger(MailReceiver.class);

    private JavaMailSender javaMailSender;
    private MailProperties mailProperties;
    private TemplateEngine templateEngine;
    private RedisTemplate redisTemplate;


    @Autowired
    public MailReceiver(JavaMailSender javaMailSender,
                         MailProperties mailProperties,
                         TemplateEngine templateEngine,
                         RedisTemplate redisTemplate) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
        this.templateEngine = templateEngine;
        this.redisTemplate = redisTemplate;
    }

    public MailReceiver() {
    }

    @RabbitListener(queues = MailBO.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel) {

        EmployeePO employee = (EmployeePO) message.getPayload();

        // ??????????????????
        MessageHeaders headers = message.getHeaders();
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // ??????msgId
        String msgId = (String) headers.get("spring_returned_message_correlation");

        HashOperations hashOperations = redisTemplate.opsForHash();

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);

        try {

            if (hashOperations.entries("mail_log").containsKey(msgId)){
                LOGGER.error("?????????????????????=============>{}",msgId);
                /**
                 * ??????????????????
                 * tag:????????????
                 * multiple:??????????????????
                 */
                channel.basicAck(tag,false);
                return;
            }

            //?????????
            helper.setFrom(mailProperties.getUsername());

            //?????????
            helper.setTo(employee.getEmail());

            //??????
            helper.setSubject("??????????????????");

            //????????????
            helper.setSentDate(new Date());

            //????????????
            Context context = new Context();
            context.setVariable("name", employee.getName());
            context.setVariable("posName", employee.getPositionPO().getName());
            context.setVariable("jobLevelName", employee.getJobLevelPO().getName());
            context.setVariable("departmentName", employee.getDepartmentPO().getName());

            String mail = templateEngine.process("mail", context);
            helper.setText(mail, true);

            //????????????
            javaMailSender.send(msg);
            LOGGER.info("??????????????????");
            // ?????????ID??????redis
            assert msgId != null;
            hashOperations.put("mail_log", msgId, "OK");

            // ??????????????????
            channel.basicAck(tag, false);
        } catch (MessagingException | IOException e) {

            /**
             * ??????????????????
             * tag???????????????
             * multiple?????????????????????
             * requeue????????????????????????
             */
            try {
                channel.basicNack(tag,false,true);
            } catch (IOException ex) {
                LOGGER.error("??????????????????=========>{}", ex.getMessage());
            }

            LOGGER.error("?????????????????? =====>{}", e.getMessage());
        }

    }
}
