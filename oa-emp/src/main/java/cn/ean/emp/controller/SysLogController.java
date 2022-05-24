package cn.ean.emp.controller;

import cn.ean.emp.model.bo.PageBO;
import cn.ean.emp.service.ISysLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ean
 * @FileName SysLogController
 * @Date 2022/5/24 14:29
 **/

@RestController
@RequestMapping("/system/log")
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;


    @ApiOperation(value = "获取所有操作日志（分页）")
    @GetMapping("/")
    public PageBO getSysLogByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                  @RequestParam(defaultValue = "10") Integer size,
                                  String name) {
        return sysLogService.getSysLogByPage(currentPage, size, name);
    }

}
