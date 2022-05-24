package cn.ean.emp.service.impl;

import cn.ean.emp.mapper.EmployeeMapper;
import cn.ean.emp.mapper.EmployeeRewardMapper;
import cn.ean.emp.model.bo.PageBO;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.EmployeePO;
import cn.ean.emp.model.po.EmployeeRewardPO;
import cn.ean.emp.service.IEmployeeRewardService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author ean
 * @FileName EmployeeRewardServiceIMpl
 * @Date 2022/5/24 10:40
 **/


@Service
public class EmployeeRewardServiceImpl extends ServiceImpl<EmployeeRewardMapper, EmployeeRewardPO>
            implements IEmployeeRewardService {

    @Autowired
    private EmployeeRewardMapper employeeRewardMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public PageBO getRewardsByPage(Integer currentPage, Integer size) {
        // 开启分页
        Page<EmployeeRewardPO> page = new Page<>(currentPage, size);

        IPage<EmployeeRewardPO> IPage = employeeRewardMapper.getAllInfo(page);
        PageBO pageBo = new PageBO(IPage.getTotal(), IPage.getRecords());

        System.out.println("EmployeeRewardServiceImpl-getRewardsByPage PageBO.getSize: " + page.getSize());

        return pageBo;
    }

    @Override
    public ResponseBO updateInfo(EmployeeRewardPO employeeReward, String type) {


        EmployeePO employee = employeeMapper.getEmployee(employeeReward.getEid()).get(0);

        if (! employeeReward.getSid().equals(employee.getSalaryId())) {
            employee.setSalaryId(employeeReward.getSid());
            employeeMapper.updateById(employee);
        } else {
            return ResponseBO.error("请设置不同的员工账套id");
        }

        if ("insert".equals(type)) {
            Integer eid = employee.getId();
            Integer validateInt = employeeRewardMapper.getEmpRewardByEid(eid);
            if (validateInt > 0){
                return ResponseBO.error("此员工已存在奖惩数据");
            }
            employeeReward.setEname(employee.getName());
            if (1 == employeeRewardMapper.insert(employeeReward)) {
                return ResponseBO.success("此员工账套已添加");
            }

            return ResponseBO.error("此员工账套添加失败");
        } else if ("update".equals(type)){


            if (1 == employeeRewardMapper.updateById(employeeReward)) {
                return ResponseBO.success("此员工账套已更新");
            }

            return ResponseBO.error("此员工账套更新失败");
        }
        return ResponseBO.error("此员工账套设置出错");
    }

    @Override
    public ResponseBO updateEmployeeReward(EmployeeRewardPO employeeReward) {
        return updateInfo(employeeReward, "update");
    }

    @Override
    public ResponseBO deleteEmpById(EmployeeRewardPO employeeRewardPO) {

        employeeRewardMapper.deleteById(employeeRewardPO.getId());
        List<EmployeePO> employee = employeeMapper.getEmployee(employeeRewardPO.getEid());
        if (employee != null) {
            EmployeePO employeePO = employee.get(0);
            if (employeePO != null) {
                employeePO.setSalaryId(0);
                employeeMapper.updateById(employeePO);
                return ResponseBO.success("删除成功");
            }
        }
        return ResponseBO.success("删除失败");
    }


}



