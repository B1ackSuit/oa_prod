package cn.ean.workflow.mapper;

import cn.ean.workflow.model.dto.LeaveREQ;
import cn.ean.workflow.model.po.Leave;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ean
 * @FileName LeaveMapper
 * @Date 2022/5/25 10:22
 **/
@Mapper
public interface LeaveMapper extends BaseMapper<Leave> {

    /**
     * 分页条件查询请假申请列表数据
     * @param page 分页对象，mybatis-plus规定一定要作为第1个参数
     * @return
     */
    IPage<Leave> getLeaveAndStatusList(IPage page, @Param("req") LeaveREQ req);

}
