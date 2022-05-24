package cn.ean.emp.service.impl;

import cn.ean.emp.mapper.MailLogMapper;
import cn.ean.emp.model.po.MailLogPO;
import cn.ean.emp.service.IMailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ean
 * @FileName MailLogServiceImpl
 * @Date 2022/5/24 10:55
 **/
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLogPO> implements IMailLogService {
}
