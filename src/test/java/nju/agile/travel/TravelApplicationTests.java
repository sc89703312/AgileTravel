package nju.agile.travel;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.PostRepo;
import nju.agile.travel.dao.ShareRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.model.ActivityInfoParam;
import nju.agile.travel.model.RegisterParam;
import nju.agile.travel.service.ActivityCURDService;
import nju.agile.travel.service.AuthService;
import nju.agile.travel.service.MailService;
import nju.agile.travel.util.Base64Util;
import nju.agile.travel.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelApplicationTests {

    @Autowired
    MailService mailService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ActivityRepo activityRepo;

    @Autowired
    PostRepo postRepo;

    @Autowired
    ShareRepo shareRepo;

    @Autowired
    AuthService authService;

    @Autowired
    ActivityCURDService activityCURDService;

    @Value("${check.host}")
    String serverHost;

    @Value("${check.port}")
    Integer serverPort;

    @Test
    public void contextLoads() {
        System.out.println(serverHost);
        System.out.println(serverPort);
    }

//    @Test
//    public void testSendMail() {
//        String toAddress = "MF1832136@smail.nju.edu.cn";
//        String content = "中文";
//        mailService.sendEmail(toAddress, content);
//    }

    @Test
    @Transactional
    public void testUserRepo(){
        System.out.println(userRepo.findAll());
    }

    @Test
    @Transactional
    public void testActivityRepo(){
        System.out.println(activityRepo.findAll());
    }

    @Test
    @Transactional
    public void testPostRepo(){
        System.out.println(postRepo.findAll());
    }

    @Test
    @Transactional
    public void testShareRepo(){
        System.out.println(shareRepo.findAll());
    }

    @Test
    public void testAuthService(){
        RegisterParam param = new RegisterParam("sc89703312@qq.com", "echo", "123456", "1");
        authService.register(param);
    }

    @Test
    public void testBase64(){
//        String encodedStr = Base64Util.encode(1, "2019-01-09 11:11:11");
        String encodedStr = "NSAyMDE5LTAxLTEwIDE0OjQwOjMz";
        System.out.println(encodedStr);

        String decodedStr = Base64Util.decode(encodedStr);
        System.out.println(decodedStr);
    }

    @Test
    public void testAccountCheck(){
       authService.check(9,"OSAyMDE5LTAxLTExIDE1OjA3OjM3");
    }

    @Test
    public void testActivityCURDService() {
        ActivityInfoParam param =
                new ActivityInfoParam("activity by test", "something", "nju",
                        DateUtil.getCurrentRoundDate(), DateUtil.getCurrentRoundDate(), "url",
                        true, 1);
        ActivityInfoParam newParam =
                new ActivityInfoParam("activity by test", "another thing", "nju",
                        DateUtil.getCurrentRoundDate(), DateUtil.getCurrentRoundDate(), "another url",
                        false, 1);
        activityCURDService.createActivity(param);
        int activityID = activityCURDService.createActivity(param);
        activityCURDService.editActivity(activityID, newParam);
        System.out.println(activityCURDService.queryActivityPage(0));
        System.out.println(activityCURDService.queryCreatedActivity(1));
    }
}

