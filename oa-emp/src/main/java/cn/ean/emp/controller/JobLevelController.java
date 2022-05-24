package cn.ean.emp.controller;

import cn.ean.emp.aspect.BusinessTypeEnum;
import cn.ean.emp.aspect.Log;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.JobLevelPO;
import cn.ean.emp.service.IJobLevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author ean
 * @FileName JobLevelController
 * @Date 2022/5/24 14:06
 **/
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    private IJobLevelService jobLevelService;

    @Autowired
    public JobLevelController(IJobLevelService jobLevelService) {
        this.jobLevelService = jobLevelService;
    }



    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public List<JobLevelPO> getAllJobLevel() {
        return jobLevelService.list();
    }

    @Log(title = "添加职称", businessType = BusinessTypeEnum.INSERT)
    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public ResponseBO addJobLevel(@RequestBody JobLevelPO jobLevel) {
        jobLevel.setCreateDate(LocalDateTime.now());
        if (jobLevelService.save(jobLevel)) {
            return ResponseBO.success("职称添加成功");
        }
        return ResponseBO.error("职称添加失败");
    }

    @Log(title = "更新职称信息", businessType = BusinessTypeEnum.UPDATE)
    @ApiOperation(value = "更新职称信息")
    @PutMapping("/")
    public ResponseBO updateJobLevel(@RequestBody JobLevelPO jobLevel) {

        if (jobLevelService.updateById(jobLevel)) {
            return ResponseBO.success("职称更新成功");
        }

        return ResponseBO.error("职称更新失败");

    }

    @Log(title = "删除职称信息", businessType = BusinessTypeEnum.DELETE)
    @ApiOperation(value = "删除职称信息")
    @DeleteMapping("/{id}")
    public ResponseBO deleteJobLevel(@PathVariable Integer id) {
        if (jobLevelService.removeById(id)) {
            return ResponseBO.success("职称删除成功");
        }
        return ResponseBO.error("职称删除失败");
    }

    @Log(title = "批量删除职称信息", businessType = BusinessTypeEnum.DELETE)
    @ApiOperation(value = "批量删除职称信息")
    @DeleteMapping("/")
    public ResponseBO deleteJobLevelsByIds(Integer[] ids) {
        // MP3.5.1 : joblevelService.removeByIds() 此接口出错，改为removeBatchByIds()
        // MP3.3.1使用removeByIds()
        if (jobLevelService.removeByIds(Arrays.asList(ids))) {
            return ResponseBO.success("批量删除职称成功");
        }
        return ResponseBO.error("批量删除职称失败");
    }

}
