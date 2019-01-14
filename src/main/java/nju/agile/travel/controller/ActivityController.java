package nju.agile.travel.controller;

import nju.agile.travel.service.ActivityCURDService;
import nju.agile.travel.vo.ActivityBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ShirokoSama on 2019/1/14.
 */
@RestController
public class ActivityController {

    @Autowired
    ActivityCURDService activityCURDService;

    @RequestMapping("/user/{userID}/activity/public/page/{pageIndex}")
    public List<ActivityBaseVO> getPublicActivityList(
            @PathVariable Integer userID,
            @PathVariable Integer pageIndex) {
        return activityCURDService.queryActivityPage(userID, pageIndex);
    }

    @RequestMapping("/user/{userID}/activity/created")
    public List<ActivityBaseVO> getCreatedActivityList(@PathVariable Integer userID) {
        return activityCURDService.queryCreatedActivity(userID);
    }

    @RequestMapping("/user/{userID}/activity/joined")
    public List<ActivityBaseVO> getJoinedActivityList(@PathVariable Integer userID) {
        return activityCURDService.queryJoinedActivity(userID);
    }

}
