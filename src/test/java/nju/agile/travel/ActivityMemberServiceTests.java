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

    @Test
    public void testMemberOperation() {
        assert activityMemberService.applyJoinActivity(9, 1) > 0;
        System.out.println(activityMemberService.queryApplicants(1, 1));
        System.out.println(activityMemberService.queryParticipants(1, 1));
        assert activityMemberService.approveApplicant(1, 1, 9) > 0;
        System.out.println(activityMemberService.queryApplicants(1, 1));
        System.out.println(activityMemberService.queryParticipants(1, 1));
        assert activityMemberService.removeParticipant(1, 1, 9) > 0;
        System.out.println(activityMemberService.queryApplicants(1, 1));
        System.out.println(activityMemberService.queryParticipants(1, 1));
        assert activityMemberService.applyJoinActivity(9, 1) > 0;
        System.out.println(activityMemberService.queryApplicants(1, 1));
        System.out.println(activityMemberService.queryParticipants(1, 1));
        assert activityMemberService.refuseApplicant(1, 1, 9) > 0;
        System.out.println(activityMemberService.queryApplicants(1, 1));
        System.out.println(activityMemberService.queryParticipants(1, 1));
    }

}
