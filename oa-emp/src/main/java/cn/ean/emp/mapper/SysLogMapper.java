package cn.ean.emp.mapper;

import cn.ean.emp.model.po.SysLogPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ean
 * @FileName SysLogMapper
 * @Date 2022/5/23 21:15
 **/
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogPO> {


    /**
     * 获取系统操作日志
     * @param page
     * @param name
     * @return
     */
    IPage<SysLogPO> getSysLogByPage(Page<SysLogPO> page, @Param("name") String name);
}
