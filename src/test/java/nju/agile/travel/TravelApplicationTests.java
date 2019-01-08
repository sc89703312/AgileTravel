package nju.agile.travel;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelApplicationTests {

    @Autowired
    MailService mailService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ActivityRepo activityRepo;

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
    public void testUserRepo(){
        System.out.println(userRepo.findAll());
    }

    @Test
    public void testActivityRepo(){
        System.out.println(activityRepo.findAll());
    }
}

