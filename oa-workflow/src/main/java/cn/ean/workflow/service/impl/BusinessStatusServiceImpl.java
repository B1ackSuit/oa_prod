package cn.ean.workflow.service.impl;

import cn.ean.workflow.enums.BusinessStatusEnum;
import cn.ean.workflow.mapper.BusinessStatusMapper;
import cn.ean.workflow.model.bo.Result;
import cn.ean.workflow.model.po.BusinessStatus;
import cn.ean.workflow.service.IBusinessStatusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ean
 * @FileName BusinessStatusServiceImpl
 * @Date 2022/5/25 12:08
 **/
@Service
public class BusinessStatusServiceImpl extends ServiceImpl<BusinessStatusMapper, BusinessStatus>
        implements IBusinessStatusService {


    @Override
    public int add(String businessKey) {
        BusinessStatus bs = new BusinessStatus();
        // 待提交
        bs.setStatus(BusinessStatusEnum.WAIT.getCode());
        bs.setBusinessKey(businessKey);
        return baseMapper.insert(bs);
    }

    @Override
    public Result updateState(String businessKey, BusinessStatusEnum statusEnum, String procInstId) {
        // 1. 查询当前数据
        BusinessStatus bs = baseMapper.selectById(businessKey);
        // 2. 设置状态值
        bs.setStatus(statusEnum.getCode());
        bs.setUpdateDate(new Date());

        // 只要判断不为null,就更新，因为后面有个地方传递“”
        if (procInstId != null) {
            bs.setProcessInstanceId(procInstId);
        }

        // 3. 更新操作
        baseMapper.updateById(bs);
        return Result.ok();
    }

    @Override
    public Result updateState(String businessKey, BusinessStatusEnum statusEnum) {
        return updateState(businessKey, statusEnum, null);
    }
}
