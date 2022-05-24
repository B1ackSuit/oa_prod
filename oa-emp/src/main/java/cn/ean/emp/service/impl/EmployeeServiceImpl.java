package cn.ean.emp.service.impl;

import cn.ean.emp.mapper.EmployeeMapper;
import cn.ean.emp.mapper.MailLogMapper;
import cn.ean.emp.model.bo.MailBO;
import cn.ean.emp.model.bo.PageBO;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.EmployeePO;
import cn.ean.emp.model.po.MailLogPO;
import cn.ean.emp.service.IEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author ean
 * @FileName EmployeeServiceImpl
 * @Date 2022/5/24 11:12
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeePO> implements IEmployeeService {

    private EmployeeMapper employeeMapper;
    private RabbitTemplate rabbitTemplate;
    private MailLogMapper mailLogMapper;

    @Autowired
    private EmployeeServiceImpl(EmployeeMapper employeeMapper,
                                RabbitTemplate rabbitTemplate,
                                MailLogMapper mailLogMapper) {
        this.employeeMapper = employeeMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.mailLogMapper = mailLogMapper;
    }

    public EmployeeServiceImpl() {
    }

    /**
     * 获取所有员工(分页)
     *
     * @param currentPage
     * @param size
     * @param employeePO
     * @param beginDateScope
     * @return
     */
    @Override
    public PageBO getEmployeeByPage(Integer currentPage, Integer size, EmployeePO employeePO, LocalDate[] beginDateScope) {

        // 开启分页
        Page<EmployeePO> page = new Page<>(currentPage, size);

        IPage<EmployeePO> employeeIPage = employeeMapper.getEmployeeByPage(page, employeePO, beginDateScope);
        PageBO PageBO = new PageBO(employeeIPage.getTotal(), employeeIPage.getRecords());
        System.out.println("=====debugger EmployeeServiceImpl getEmployeeByPage(): Total:" + employeeIPage.getTotal());

        return PageBO;
    }

    /**
     * 获取工号
     *
     * @return
     */
    @Override
    public ResponseBO maxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<EmployeePO>().select("max(workID)"));
        return ResponseBO.success(null, String.format("%08d",
                Integer.parseInt(maps.get(0).get("max(workID)").toString()) + 1));
    }

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    @Override
    public ResponseBO addEmp(EmployeePO employee) {

        // 处理合同期限，保留2位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long workDays = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");

        employee.setContractTerm(Double.parseDouble(decimalFormat.format(workDays / 365.00)));
        if (1 == employeeMapper.insert(employee)) {
            EmployeePO emp = employeeMapper.getEmployee(employee.getId()).get(0);
            //数据库记录发送的消息
            String msgId = UUID.randomUUID().toString();

            // 测试幂等性
//            String msgId = "123456";


            MailLogPO mailLog = new MailLogPO();
            mailLog.setMsgId(msgId);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailBO.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailBO.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailBO.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());

            mailLogMapper.insert(mailLog);

            rabbitTemplate.convertAndSend(MailBO.MAIL_EXCHANGE_NAME,
                    MailBO.MAIL_ROUTING_KEY_NAME,
                    emp, new CorrelationData(msgId));
            return ResponseBO.success("员工添加成功");

        }


        return ResponseBO.error("员工添加失败");
    }

    /**
     * 查询员工
     *
     * @param id
     * @return
     */
    @Override
    public List<EmployeePO> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }

    /**
     * 获取所有员工帐套
     *
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public PageBO getEmployeeWithSalary(Integer currentPage, Integer size) {
        // 开启分页
        Page<EmployeePO> page = new Page<>(currentPage, size);

        IPage<EmployeePO> employeeIPage = employeeMapper.getEmployeeWithSalary(page);

        PageBO PageBO = new PageBO(employeeIPage.getTotal(), employeeIPage.getRecords());

        return PageBO;
    }


}

