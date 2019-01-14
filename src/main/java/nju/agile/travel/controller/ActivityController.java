package nju.agile.travel.controller;

import nju.agile.travel.model.ActivityInfoParam;
import nju.agile.travel.service.ActivityCURDService;
import nju.agile.travel.vo.ActivityBaseVO;
import nju.agile.travel.vo.ActivityDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by ShirokoSama on 2019/1/14.
 */
@RestController
public class ActivityController {

    @Autowired
    ActivityCURDService activityCURDService;

    @GetMapping("/user/{userID}/activity/public/page/{pageIndex}")
    public List<ActivityBaseVO> getPublicActivityList(
            @PathVariable Integer userID,
            @PathVariable Integer pageIndex) {
        return activityCURDService.queryActivityPage(userID, pageIndex);
    }

    @GetMapping("/user/{userID}/activity/created")
    public List<ActivityBaseVO> getCreatedActivityList(@PathVariable Integer userID) {
        return activityCURDService.queryCreatedActivity(userID);
    }

    @GetMapping("/user/{userID}/activity/joined")
    public List<ActivityBaseVO> getJoinedActivityList(@PathVariable Integer userID) {
        return activityCURDService.queryJoinedActivity(userID);
    }

    @GetMapping("/user/{userID}/activity/{activityID}")
    public ActivityDetailVO getDetailedActivityInfo(
            @PathVariable Integer userID,
            @PathVariable Integer activityID) {
        return activityCURDService.queryDetailedActivity(userID, activityID);
    }

    @PostMapping("/user/{userID}/activity")
    public int postActivity(@PathVariable Integer userID, @RequestBody @Valid ActivityInfoParam param) {
        return activityCURDService.createActivity(userID, param);
    }

    @PostMapping("/user/{userID}/activity/{activityID}")
    public int editActivity(
            @PathVariable Integer userID,
            @PathVariable Integer activityID,
            @RequestBody @Valid ActivityInfoParam param) {
        return activityCURDService.editActivity(userID, activityID, param);
    }

}
