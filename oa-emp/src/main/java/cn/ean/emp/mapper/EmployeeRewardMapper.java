package cn.ean.emp.mapper;

import cn.ean.emp.model.po.EmployeeRewardPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ean
 * @FileName EmployeeRewardMapper
 * @Date 2022/5/23 20:39
 **/
@Mapper
public interface EmployeeRewardMapper extends BaseMapper<EmployeeRewardPO> {


    /**
     * 获取所有员工账套信息
     * @param page
     * @return
     */
    IPage<EmployeeRewardPO> getAllInfo(Page<EmployeeRewardPO> page);

    /**
     * 插入时判断是否已经拥有对应的奖惩数据
     * @param eid
     * @return
     */
    Integer getEmpRewardByEid(@Param("eid") Integer eid);

}
