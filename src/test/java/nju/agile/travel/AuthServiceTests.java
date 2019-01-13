package nju.agile.travel;

import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.model.LoginParam;
import nju.agile.travel.model.RegisterParam;
import nju.agile.travel.service.AuthService;
import nju.agile.travel.service.MailService;
import nju.agile.travel.util.Base64Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by echo on 2019/1/11.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTests {

    @Autowired
    MailService mailService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthService authService;

    @Test
    public void testLogin(){
        LoginParam param = new LoginParam("sc89703312@qq.com", "123456");
        System.out.println(authService.login(param));
    }

    @Test
    public void testLoginWrongPwd(){
        LoginParam param = new LoginParam("sc89703312@qq.com", "1");
        System.out.println(authService.login(param));
    }

    @Test
    public void testLoginWrongMail(){
        LoginParam param = new LoginParam("?", "1");
        System.out.println(authService.login(param));
    }

    @Test
    public void testLoginUnchecked(){
        LoginParam param = new LoginParam("2271642660@qq.com", "123456");
        System.out.println(authService.login(param));
    }

}
