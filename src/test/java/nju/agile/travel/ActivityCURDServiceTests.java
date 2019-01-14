package nju.agile.travel;

import nju.agile.travel.model.ActivityInfoParam;
import nju.agile.travel.service.ActivityCURDService;
import nju.agile.travel.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityCURDServiceTests {

    @Autowired
    ActivityCURDService activityCURDService;

    @Test
    public void testActivityCURDService() {
        System.out.println(activityCURDService.queryDetailedActivity(1, 1));
//        ActivityInfoParam param =
//                new ActivityInfoParam("activity by test", "something", "nju",
//                        DateUtil.getCurrentRoundDate(), DateUtil.getCurrentRoundDate(),
//                        new ArrayList<>(Collections.singletonList("url")), true, 1);
//        ActivityInfoParam newParam =
//                new ActivityInfoParam("activity by test", "another thing", "nju",
//                        DateUtil.getCurrentRoundDate(), DateUtil.getCurrentRoundDate(),
//                        new ArrayList<>(Collections.singleton("another-url")), false, 1);
//        activityCURDService.createActivity(param);
//        int activityID = activityCURDService.createActivity(param);
//        activityCURDService.editActivity(activityID, newParam);
        System.out.println(activityCURDService.queryActivityPage(1, 0));
        System.out.println(activityCURDService.queryCreatedActivity(1));
        System.out.println(activityCURDService.queryJoinedActivity(1));
    }

}
