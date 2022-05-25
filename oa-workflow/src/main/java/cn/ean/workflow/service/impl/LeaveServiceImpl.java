package cn.ean.workflow.service.impl;

import cn.ean.workflow.mapper.LeaveMapper;
import cn.ean.workflow.model.bo.Result;
import cn.ean.workflow.model.dto.LeaveREQ;
import cn.ean.workflow.model.po.Leave;
import cn.ean.workflow.service.IBusinessStatusService;
import cn.ean.workflow.service.ILeaveService;
import cn.ean.workflow.utils.UserUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ean
 * @FileName LeaveService
 * @Date 2022/5/25 12:10
 **/
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements ILeaveService {

    @Autowired
    IBusinessStatusService businessStatusService;

    @Override
    public Result add(Leave leave) {
        // 1. 新增请假信息
        // 当前登录用户即为申请人
        leave.setUsername(UserUtils.getUsername());
        int size = baseMapper.insert(leave);
        // 2. 新增请假业务状态：待提交
        if (size == 1) {
            businessStatusService.add(leave.getId());
        }
        return Result.ok();
    }

    @Override
    public Result listPage(LeaveREQ req) {
        if(StringUtils.isEmpty(req.getUsername())) {
            req.setUsername(UserUtils.getUsername());
        }
        IPage<Leave> page = baseMapper.getLeaveAndStatusList(req.getPage(), req);
        return Result.ok(page);
    }

    @Override
    public Result update(Leave leave) {
        if(leave == null || StringUtils.isEmpty(leave.getId())) {
            return Result.error("数据不合法");
        }
        // 查询原数据
        Leave entity = baseMapper.selectById(leave.getId());
        // 拷贝新数据
        BeanUtils.copyProperties(leave, entity);
        entity.setUpdateDate(new Date());
        baseMapper.updateById(entity);
        return Result.ok();
    }

}

