package cn.ean.emp.controller;

import cn.ean.emp.aspect.Log;
import cn.ean.emp.model.po.MenuPO;
import cn.ean.emp.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ean
 * @FileName MenuController
 * @Date 2022/5/24 14:11
 **/
@RestController
@RequestMapping("/menus")
public class MenuController {

    private IMenuService menuService;


    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @Log(title = "通过用户id查询菜单列表")
    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public List<MenuPO> getMenusByUserId() {
        return menuService.getMenusByUserId();
    }


}
