package nju.agile.travel.controller;

import nju.agile.travel.model.LoginParam;
import nju.agile.travel.model.RegisterParam;
import nju.agile.travel.service.AuthService;
import nju.agile.travel.vo.UserBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by echo on 2019/1/13.
 */
@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/user/register")
    public void register(@RequestBody @Valid RegisterParam registerParam){
        authService.register(registerParam);
    }

    @PostMapping("/user/login")
    public UserBaseVO login(@RequestBody @Valid LoginParam loginParam){
        return authService.login(loginParam);
    }

    @GetMapping("/user/{userId}/check/{checkStr}")
    public String check(@PathVariable Integer userId,
                     @PathVariable String checkStr){
        return authService.check(userId, checkStr);
    }
}
