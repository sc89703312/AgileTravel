package nju.agile.travel.controller;

import nju.agile.travel.service.ActivityMemberService;
import nju.agile.travel.vo.ApplyMessageVO;
import nju.agile.travel.vo.UserBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ShirokoSama on 2019/1/14.
 */
@RestController
public class MemberController {

    @Autowired
    ActivityMemberService activityMemberService;

    @GetMapping("/user/{userID}/activity/{activityID}/invite/generate")
    public String generateInvitationCode(@PathVariable Integer userID, @PathVariable Integer activityID) {
        return activityMemberService.generateInvitationCode(userID, activityID);
    }

    @GetMapping("/user/{userID}/activity/{activityID}/invite/get")
    public String getInvitationCode(@PathVariable Integer userID, @PathVariable Integer activityID) {
        return activityMemberService.queryInvitationCode(userID, activityID);
    }

    @GetMapping("/user/{userID}/accept/{invitationCode}")
    public int acceptInvitation(@PathVariable Integer userID, @PathVariable String invitationCode) {
        return activityMemberService.checkInvitationCode(userID, invitationCode);
    }

    @GetMapping("/user/{userID}/message/applicants")
    public List<ApplyMessageVO> getActivityApplicants(@PathVariable Integer userID) {
        return activityMemberService.queryApplyMessages(userID);
    }

    @GetMapping("/user/{userID}/activity/{activityID}/applicants")
    public List<UserBaseVO> getActivityApplicants(@PathVariable Integer userID, @PathVariable Integer activityID) {
        return activityMemberService.queryApplicants(userID, activityID);
    }

    @GetMapping("/user/{userID}/activity/{activityID}/participants")
    public List<UserBaseVO> getActivityParticipants(@PathVariable Integer userID, @PathVariable Integer activityID) {
        return activityMemberService.queryParticipants(userID, activityID);
    }

    @PostMapping("/user/{userID}/activity/{activityID}/apply")
    public int applyJoinActivity(@PathVariable Integer userID, @PathVariable Integer activityID) {
        return activityMemberService.applyJoinActivity(userID, activityID);
    }

    @PostMapping("/user/{userID}/activity/{activityID}/exit")
    public int exitActivity(@PathVariable Integer userID, @PathVariable Integer activityID) {
        return activityMemberService.exitActivity(userID, activityID);
    }

    @PostMapping("/user/{userID}/activity/{activityID}/applicants/{applicantID}/approve")
    public int approve(
            @PathVariable Integer userID,
            @PathVariable Integer activityID,
            @PathVariable Integer applicantID) {
        return activityMemberService.approveApplicant(userID, activityID, applicantID);
    }

    @PostMapping("/user/{userID}/activity/{activityID}/applicants/{applicantID}/refuse")
    public int refuse(
            @PathVariable Integer userID,
            @PathVariable Integer activityID,
            @PathVariable Integer applicantID) {
        return activityMemberService.refuseApplicant(userID, activityID, applicantID);
    }

    @PostMapping("/user/{userID}/activity/{activityID}/participants/{participantID}/remove")
    public int remove(
            @PathVariable Integer userID,
            @PathVariable Integer activityID,
            @PathVariable Integer participantID) {
        return activityMemberService.removeParticipant(userID, activityID, participantID);
    }

}
