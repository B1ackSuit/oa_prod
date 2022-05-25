package cn.ean.workflow.service;


import cn.ean.workflow.model.bo.Result;
import cn.ean.workflow.model.dto.LoanREQ;
import cn.ean.workflow.model.po.Loan;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ILoanService extends IService<Loan> {

    Result add(Loan loan);

    /**
     * 条件分页查询借款列表
     * @param req
     * @return
     */
    Result listPage(LoanREQ req);

    Result update(Loan loan);
}
