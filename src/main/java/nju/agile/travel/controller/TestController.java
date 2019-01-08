package nju.agile.travel.controller;

import nju.agile.travel.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by echo on 2019/1/8.
 */
@RestController
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/test")
    public int getTotalLength(){
        return testService.calculateLength();
    }
}
