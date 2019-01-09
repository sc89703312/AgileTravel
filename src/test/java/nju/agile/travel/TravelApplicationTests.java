package nju.agile.travel;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.PostRepo;
import nju.agile.travel.dao.ShareRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.model.RegisterParam;
import nju.agile.travel.service.AuthService;
import nju.agile.travel.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSendMail() {
        String toAddress = "MF1832136@smail.nju.edu.cn";
        String content = "中文";
        mailService.sendEmail(toAddress, content);
    }

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
        RegisterParam param = new RegisterParam("2271642660@qq.com", "hehe", "qwer@1213d2_", "1");
        System.out.println(authService.register(param));
    }
}

