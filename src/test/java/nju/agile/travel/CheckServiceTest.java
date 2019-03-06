package nju.agile.travel;

import nju.agile.travel.service.CheckService;
import nju.agile.travel.vo.ActivityCheckVO;
import nju.agile.travel.vo.UserCheckVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by echo on 2019/3/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckServiceTest {

    @Autowired
    CheckService checkService;

    @Test
    public void testQueryAllUser(){
        List<UserCheckVO> users = checkService.queryAllUsers();
        for (UserCheckVO user: users
             ) {
            System.out.println(user);
        }
    }

    @Test
    public void testQueryAllActivity(){
        List<ActivityCheckVO> activities = checkService.queryAllActivities();
        for (ActivityCheckVO activity: activities
             ) {
            System.out.println(activity);
        }
    }

//    @Test
//    public void testChangeUserCheck(){
//        System.out.println(checkService.userCheckChange(19));
//    }

    @Test(expected = RuntimeException.class)
    public void testChangeUserCheckInvalid(){
        System.out.println(checkService.userCheckChange(-1));
    }

//    @Test
//    public void testChangeActivityCheck(){
//        System.out.println(checkService.activityCheckChange(1));
//    }

    @Test(expected = RuntimeException.class)
    public void testChangeActivityCheckInvalid(){
        System.out.println(checkService.activityCheckChange(-1));
    }

}
