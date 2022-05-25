package cn.ean.workflow.service.impl;

import cn.ean.workflow.mapper.SysUserMapper;
import cn.ean.workflow.model.po.SysUser;
import cn.ean.workflow.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author ean
 * @FileName SysUserServiceImpl
 * @Date 2022/5/25 11:53
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser findByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);

        SysUser sysUser = baseMapper.selectOne(wrapper);
        return sysUser;
    }
}
