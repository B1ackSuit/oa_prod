package cn.ean.workflow.service.impl;


import cn.ean.workflow.mapper.ProcessConfigMapper;
import cn.ean.workflow.model.bo.Result;
import cn.ean.workflow.model.po.ProcessConfig;
import cn.ean.workflow.service.IProcessConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ProcessConfigServiceImpl extends ServiceImpl<ProcessConfigMapper, ProcessConfig>
        implements IProcessConfigService {

    @Override
    public ProcessConfig getByProcessKey(String processKey) {
        QueryWrapper<ProcessConfig> query = new QueryWrapper<>();
        query.eq("process_key", processKey);

        return baseMapper.selectOne(query);
    }

    @Override
    public Result deleteByProcessKey(String processKey) {
        QueryWrapper<ProcessConfig> query = new QueryWrapper<>();
        query.eq("process_key", processKey);
        baseMapper.delete(query);
        return Result.ok();
    }

    @Override
    public ProcessConfig getByBusinessRoute(String businessRoute) {
        QueryWrapper<ProcessConfig> query = new QueryWrapper<>();
        query.eq("upper(business_route)", businessRoute.toUpperCase());
        List<ProcessConfig> list = baseMapper.selectList(query);
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

}
