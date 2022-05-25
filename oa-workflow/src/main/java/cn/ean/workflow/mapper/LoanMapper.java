package cn.ean.workflow.mapper;


import cn.ean.workflow.model.dto.LoanREQ;
import cn.ean.workflow.model.po.Loan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoanMapper extends BaseMapper<Loan> {

    IPage<Loan> getLoanAndStatusList(IPage<Loan> page, @Param("req") LoanREQ req);

}
