package cn.ean.emp.controller;


import cn.ean.emp.aspect.BusinessTypeEnum;
import cn.ean.emp.aspect.Log;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.PositionPO;
import cn.ean.emp.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author ean
 * @FileName PositionController
 * @Date 2022/5/24 14:17
 **/
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    private IPositionService positionService;
    @Autowired
    public PositionController(IPositionService positionService) {
        this.positionService = positionService;
    }

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<PositionPO> getAllPositions() {
        return positionService.list();
    }

    @Log(title = "添加职位信息", businessType = BusinessTypeEnum.INSERT)
    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public ResponseBO addPosition(@RequestBody PositionPO position) {
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)) {
            return ResponseBO.success("职位添加成功");
        }

        return ResponseBO.error("职位添加失败");

    }

    @Log(title = "更新职位信息", businessType = BusinessTypeEnum.UPDATE)
    @ApiOperation(value = "更新职位信息")
    @PutMapping("/")
    public ResponseBO updatePosition(@RequestBody PositionPO position) {

        if (positionService.updateById(position)) {
            return ResponseBO.success("职位更新成功");
        }

        return ResponseBO.error("职位更新失败");

    }

    @Log(title = "删除职位信息", businessType = BusinessTypeEnum.DELETE)
    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public ResponseBO deletePosition(@PathVariable Integer id) {
        if (positionService.removeById(id)) {
            return ResponseBO.success("职位删除成功");
        }
        return ResponseBO.error("职位删除失败");
    }

    @Log(title = "批量删除职位信息", businessType = BusinessTypeEnum.DELETE)
    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public ResponseBO deletePositionsByIds(Integer[] ids) {
        // positionService.removeByIds() 此接口出错，改为removeBatchByIds()
        // MP3.3.1使用removeByIds()
        if (positionService.removeByIds(Arrays.asList(ids))) {
            return ResponseBO.success("批量删除职位成功");
        }
        return ResponseBO.error("批量删除职位失败");
    }
}
