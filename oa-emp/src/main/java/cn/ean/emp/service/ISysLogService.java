package cn.ean.emp.service;

import cn.ean.emp.model.bo.PageBO;
import cn.ean.emp.model.po.SysLogPO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ean
 * @FileName ISysLogService
 * @Date 2022/5/24 00:24
 **/

public interface ISysLogService extends IService<SysLogPO> {

    /**
     * 获取所有操作日志（分页）
     * @param currentPage
     * @param size
     * @param name
     * @return
     */
    PageBO getSysLogByPage(Integer currentPage, Integer size, String name);
}
