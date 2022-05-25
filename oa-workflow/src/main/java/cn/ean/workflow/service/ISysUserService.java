package cn.ean.workflow.service;

import cn.ean.workflow.model.po.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ean
 * @FileName ISysUserService
 * @Date 2022/5/25 11:52
 **/
public interface ISysUserService extends IService<SysUser> {

    SysUser findByUsername(String username);


}
