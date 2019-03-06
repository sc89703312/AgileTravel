package nju.agile.travel.controller;

import nju.agile.travel.service.CheckService;
import nju.agile.travel.vo.ActivityCheckVO;
import nju.agile.travel.vo.UserCheckVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by echo on 2019/3/6.
 */
@RestController
public class CheckController {

    @Autowired
    CheckService checkService;

    @GetMapping("/check/users")
    public List<UserCheckVO> queryUsers(){
        return checkService.queryAllUsers();
    }

    @GetMapping("/check/activities")
    public List<ActivityCheckVO> queryActivities(){
        return checkService.queryAllActivities();
    }

    @PostMapping("/check/user/{userId}")
    public int changeUserCheck(@PathVariable int userId){
        return checkService.userCheckChange(userId);
    }

    @PostMapping("/check/activity/{activityId}")
    public int changeActivityCheck(@PathVariable int activityId){
        return checkService.activityCheckChange(activityId);
    }

}
