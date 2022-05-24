package cn.ean.emp.service.impl;

import cn.ean.emp.mapper.SalaryMapper;
import cn.ean.emp.model.po.SalaryPO;
import cn.ean.emp.service.ISalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ean
 * @FileName SalaryServiceImpl
 * @Date 2022/5/24 11:07
 **/
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, SalaryPO> implements ISalaryService {
}
