package cn.ean.emp.service.impl;

import cn.ean.emp.mapper.SysLogMapper;
import cn.ean.emp.model.bo.PageBO;
import cn.ean.emp.model.po.SysLogPO;
import cn.ean.emp.service.ISysLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ean
 * @FileName SysLogServiceImpl
 * @Date 2022/5/24 00:51
 **/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogPO> implements ISysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;


    /**
     * 获取所有操作日志（分页）
     *
     * @param currentPage
     * @param size
     * @param name
     * @return
     */
    @Override
    public PageBO getSysLogByPage(Integer currentPage, Integer size, String name) {
        // 开启分页
        Page<SysLogPO> page = new Page<>(currentPage, size);

        IPage<SysLogPO> sysLogIPage = sysLogMapper.getSysLogByPage(page, name);
        PageBO pageBO = new PageBO(sysLogIPage.getTotal(), sysLogIPage.getRecords());
        System.out.println("=====debugger SystemLogServiceImpl getSystemLogByPage(): Total:" + sysLogIPage.getTotal());


        return pageBO;
    }
}
