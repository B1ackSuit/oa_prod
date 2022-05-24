package cn.ean.emp.service.impl;

import cn.ean.emp.mapper.JobLevelMapper;
import cn.ean.emp.model.po.JobLevelPO;
import cn.ean.emp.service.IJobLevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ean
 * @FileName JobLevelServiceImpl
 * @Date 2022/5/24 10:48
 **/
@Service
public class JobLevelServiceImpl extends ServiceImpl<JobLevelMapper, JobLevelPO> implements IJobLevelService {
}
