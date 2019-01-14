package nju.agile.travel;

import nju.agile.travel.service.ActivityMemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityMemberServiceTests {

    @Autowired
    ActivityMemberService activityMemberService;

    @Test
    public void testInvitationCode() {
        String code = activityMemberService.generateInvitationCode(1, 1);
        System.out.println(activityMemberService.checkInvitationCode(7, code));
    }

}
