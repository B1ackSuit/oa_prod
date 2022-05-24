package cn.ean.emp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ean
 * @FileName Swagger2APITestController
 * @Date 2022/5/24 09:24
 **/
@RestController
public class Swagger2APITestController {

    @GetMapping("/employee/basic/SimpTest")
    public String testEmpBasic() {
        return "String result: success";
    }


}
