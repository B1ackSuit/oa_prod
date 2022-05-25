package cn.ean.workflow.service.impl;

import cn.ean.workflow.mapper.LoanMapper;
import cn.ean.workflow.model.bo.Result;
import cn.ean.workflow.model.dto.LoanREQ;
import cn.ean.workflow.model.po.Loan;
import cn.ean.workflow.service.IBusinessStatusService;
import cn.ean.workflow.service.ILoanService;
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
 * @FileName LoanServiceIMpl
 * @Date 2022/5/25 12:14
 **/
@Service
public class LoanServiceImpl extends ServiceImpl<LoanMapper, Loan> implements ILoanService {

    @Autowired
    IBusinessStatusService businessStatusService;

    @Override
    public Result add(Loan loan) {
        // 1. 新增借款信息
        // 当前登录用户即为申请人
        loan.setUserId(UserUtils.getUsername());
        int size = baseMapper.insert(loan);
        // 2. 新增借款业务状态：待提交
        if (size == 1) {
            businessStatusService.add(loan.getId());
        }
        return Result.ok();
    }

    @Override
    public Result listPage(LoanREQ req) {
        if(StringUtils.isEmpty(req.getUsername())) {
            req.setUsername(UserUtils.getUsername());
        }
        IPage<Loan> page = baseMapper.getLoanAndStatusList(req.getPage(), req);
        return Result.ok(page);
    }

    @Override
    public Result update(Loan loan) {
        if(loan == null || StringUtils.isEmpty(loan.getId())) {
            return Result.error("数据不合法");
        }
        // 查询原数据
        Loan entity = baseMapper.selectById(loan.getId());
        // 拷贝新数据
        BeanUtils.copyProperties(loan, entity);
        entity.setUpdateDate(new Date());
        baseMapper.updateById(entity);
        return Result.ok();
    }

}

