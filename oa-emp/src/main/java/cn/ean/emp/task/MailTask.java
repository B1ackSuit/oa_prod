package cn.ean.emp.task;

import cn.ean.emp.model.bo.MailBO;
import cn.ean.emp.model.po.EmployeePO;
import cn.ean.emp.model.po.MailLogPO;
import cn.ean.emp.service.IEmployeeService;
import cn.ean.emp.service.IMailLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ean
 * @FileName MailTask
 * @Date 2022/5/24 14:39
 **/
@Component
public class MailTask {

    private IMailLogService mailLogService;

    private IEmployeeService employeeService;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailTask(IMailLogService mailLogService,
                     IEmployeeService employeeService,
                     RabbitTemplate rabbitTemplate) {
        this.mailLogService = mailLogService;
        this.employeeService = employeeService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public MailTask() {
    }

    /**
     * 邮件发送定时任务
     * 10秒执行一次
     */
//    @Scheduled(cron = "0/10 * * * * ?")
    // 已设置长时间，方便测试
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        List<MailLogPO> list = mailLogService.list(new QueryWrapper<MailLogPO>()
                .eq("status", 0)
                .lt("tryTime", LocalDateTime.now()) );

        list.forEach(mailLog -> {
            //如果重试次数超过3次，更新状态为投递失败，不再重试
            if (3 <= mailLog.getCount()) {
                mailLogService.update(new UpdateWrapper<MailLogPO>()
                        .set("status", 2).eq("msgId", mailLog.getMsgId()));
            }

            mailLogService.update(new UpdateWrapper<MailLogPO>()
                    .set("count",mailLog.getCount()+1)
                    .set("updateTime",LocalDateTime.now())
                    .set("tryTime",LocalDateTime.now().plusMinutes(MailBO.MSG_TIMEOUT))
                    .eq("msgId",mailLog.getMsgId()));

            EmployeePO emp = employeeService.getEmployee(mailLog.getEid()).get(0);

            //发送消息
            rabbitTemplate.convertAndSend(MailBO.MAIL_EXCHANGE_NAME,
                    MailBO.MAIL_ROUTING_KEY_NAME,
                    emp,
                    new CorrelationData(mailLog.getMsgId()));
        });
    }
}

