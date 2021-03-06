package nju.agile.travel.service;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.MessageRepo;
import nju.agile.travel.dao.RUserActivityRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.*;
import nju.agile.travel.util.Base64Util;
import nju.agile.travel.util.Constants;
import nju.agile.travel.util.DateUtil;
import nju.agile.travel.vo.ApplyMessageVO;
import nju.agile.travel.vo.UserBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by ShirokoSama on 2019/1/10.
 */
@Component
public class ActivityMemberService {

    @Autowired
    ActivityRepo activityRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RUserActivityRepo rUserActivityRepo;

    @Autowired
    MessageRepo messageRepo;

    private static final Pattern pattern = Pattern.compile("(\\d+?)\\s(.*)");

    @Transactional
    public String generateInvitationCode(int userID, int activityID) {
        ActivityEntity activityEntity = getValidCreatorAndActivity(userID, activityID).getSecond();
        Date invitedAt = DateUtil.getCurrentRoundDate();
        activityEntity.setInvitedAt(invitedAt);
        activityRepo.save(activityEntity);
        return Base64Util.encode(activityID, DateUtil.dateToString(invitedAt));
    }

    public String queryInvitationCode(int userID, int activityID) {
        ActivityEntity activityEntity = getValidCreatorAndActivity(userID, activityID).getSecond();
        if (activityEntity.getInvitedAt() == null)
            throw new RuntimeException("邀请码未生成");
        return Base64Util.encode(activityID, DateUtil.dateToString(activityEntity.getInvitedAt()));
    }

    // return activity id if success
    @Transactional
    public int checkInvitationCode(int userID, String invitationCode) {
        String originStr = Base64Util.decode(invitationCode);
        Matcher matcher = pattern.matcher(originStr);
        if (matcher.find()) {
            int activityID = Integer.parseInt(matcher.group(1));
            Pair<UserEntity, ActivityEntity> pair = getValidUserAndActivity(userID, activityID);
            UserEntity userEntity = pair.getFirst();
            ActivityEntity activityEntity = pair.getSecond();
            if (DateUtil.dateToString(activityEntity.getInvitedAt()).equals(matcher.group(2))) {
                RUserActivityEntity relationEntity =
                        RUserActivityEntity.of(userEntity, activityEntity, Constants.MEMBER_APPROVED);
                rUserActivityRepo.save(relationEntity);
                return activityID;
            }
            else
                throw new RuntimeException("邀请码已失效");
        }
        else
            throw new RuntimeException("邀请码不合法");
    }

    @Transactional
    public List<UserBaseVO> queryApplicants(int userID, int activityID) {
        ActivityEntity activityEntity = getValidCreatorAndActivity(userID, activityID).getSecond();
        return activityEntity
                .getApplicants()
                .stream()
                .map(UserBaseVO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ApplyMessageVO> queryApplyMessages(int userID) {
        List<ApplyMessageVO> messages = new ArrayList<>();
        userRepo
            .findByIdAndCheck(userID, Constants.ACCOUNT_ON)
            .map(userEntity -> userEntity
                    .getCreatedActivityList()
                    .stream()
                    .map(activityEntity -> activityEntity
                            .getApplicants()
                            .stream()
                            .map(applicantEntity -> messages.add(new ApplyMessageVO(applicantEntity, activityEntity)))
                            .collect(Collectors.toList())
                    )
                    .collect(Collectors.toList())
            )
            .orElseThrow(() -> new RuntimeException("用户ID不存在或未过审"));
        return messages;
    }

    @Transactional
    public List<UserBaseVO> queryParticipants(int userID, int activityID) {
        ActivityEntity activityEntity = getValidUserAndActivity(userID, activityID).getSecond();
        return activityEntity
                .getParticipants()
                .stream()
                .map(UserBaseVO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public int applyJoinActivity(int userID, int activityID) {
        Pair<UserEntity, ActivityEntity> pair = getValidUserAndActivity(userID, activityID);
        UserEntity userEntity = pair.getFirst();
        ActivityEntity activityEntity = pair.getSecond();
        if (activityEntity.getAccess() != Constants.ACTIVITY_PUBLIC)
            throw new RuntimeException("活动未公开");
        if (activityEntity.getCreator().getId() == userEntity.getId())
            throw new RuntimeException("用户已是活动组织者");
        RUserActivityEntity relationEntity = RUserActivityEntity.of(userEntity, activityEntity, Constants.MEMBER_APPLYING);
        rUserActivityRepo.save(relationEntity);
        return activityID;
    }

    @Transactional
    public int exitActivity(int userID, int activityID) {
        Pair<UserEntity, ActivityEntity> pair = getValidUserAndActivity(userID, activityID);
        UserEntity userEntity = pair.getFirst();
        ActivityEntity activityEntity = pair.getSecond();
        return rUserActivityRepo
                .findByIdAndStatus(RUserActivityID.of(userEntity.getId(), activityEntity.getId()), Constants.MEMBER_APPROVED)
                .map(relationEntity -> {
                    // update user-activity relationship table
                    rUserActivityRepo.delete(relationEntity);
                    // insert message to applicant's message table
                    MessageEntity message = new MessageEntity();
                    message.setUser(userEntity);
                    message.setActivity(activityEntity);
                    message.setContentUserID(relationEntity.getUser().getId());
                    message.setType(Constants.MEMBER_EXIT);
                    messageRepo.save(message);
                    return activityID;
                })
                .orElseThrow(() -> new RuntimeException("用户不是目标活动的参与者"));
    }

    @Transactional
    public int approveApplicant(int userID, int activityID, int applicantID) {
        Pair<UserEntity, ActivityEntity> pair = getValidCreatorAndActivity(userID, activityID);
        ActivityEntity activityEntity = pair.getSecond();
        return userRepo
                .findByIdAndCheck(applicantID, Constants.ACCOUNT_ON)
                .map(applicantEntity -> rUserActivityRepo
                        .findByIdAndStatus(RUserActivityID.of(applicantID, activityID), Constants.MEMBER_APPLYING)
                        .map(relationEntity -> {
                            // update user-activity relationship table
                            relationEntity.setStatus(Constants.MEMBER_APPROVED);
                            rUserActivityRepo.save(relationEntity);
                            // insert message to applicant's message table
                            MessageEntity message = new MessageEntity();
                            message.setUser(applicantEntity);
                            message.setActivity(activityEntity);
                            message.setType(Constants.APPLY_APPROVED);
                            messageRepo.save(message);
                            return applicantID;
                        })
                        .orElseThrow(() -> new RuntimeException("目标用户不在申请队列中"))
                )
                .orElseThrow(() -> new RuntimeException("申请者用户不存在或未过审"));
    }

    @Transactional
    public int refuseApplicant(int userID, int activityID, int applicantID) {
        Pair<UserEntity, ActivityEntity> pair = getValidCreatorAndActivity(userID, activityID);
        ActivityEntity activityEntity = pair.getSecond();
        return rUserActivityRepo
                .findByIdAndStatus(RUserActivityID.of(applicantID, activityID), Constants.MEMBER_APPLYING)
                .map(relationEntity -> {
                    // update user-activity relationship table
                    rUserActivityRepo.delete(relationEntity);
                    // insert message to applicant's message table
                    MessageEntity message = new MessageEntity();
                    message.setUser(relationEntity.getUser());
                    message.setActivity(activityEntity);
                    message.setType(Constants.APPLY_REFUSED);
                    messageRepo.save(message);
                    return applicantID;
                })
                .orElseThrow(() -> new RuntimeException("目标用户不在申请队列中"));
    }

    @Transactional
    public int removeParticipant(int userID, int activityID, int participantID) {
        getValidCreatorAndActivity(userID, activityID);
        return rUserActivityRepo
                .findByIdAndStatus(RUserActivityID.of(participantID, activityID), Constants.MEMBER_APPROVED)
                .map(relationEntity -> {
                    rUserActivityRepo.delete(relationEntity);
                    return participantID;
                })
                .orElseThrow(() -> new RuntimeException("目标用户不是活动成员"));
    }

    // check and get user and activity
    private Pair<UserEntity, ActivityEntity> getValidUserAndActivity(int userID, int activityID) {
        return userRepo
                .findByIdAndCheck(userID, Constants.ACCOUNT_ON)
                .map(userEntity -> activityRepo
                        .findByIdAndCheck(activityID, Constants.ACTIVITY_ON)
                        .map(activityEntity -> Pair.of(userEntity, activityEntity))
                        .orElseThrow(() -> new RuntimeException("活动ID不存在或未过审"))
                )
                .orElseThrow(() -> new RuntimeException("用户ID不存在或未过审"));
    }

    // check and get valid activity and its creator
    private Pair<UserEntity, ActivityEntity> getValidCreatorAndActivity(int userID, int activityID) {
        return userRepo
                .findByIdAndCheck(userID, Constants.ACCOUNT_ON)
                .map(userEntity -> activityRepo
                        .findByIdAndCheck(activityID, Constants.ACTIVITY_ON)
                        .map(activityEntity -> {
                            if (userEntity.getId() == activityEntity.getCreator().getId())
                                return Pair.of(userEntity, activityEntity);
                            else
                                throw new RuntimeException("用户不是活动组织者，无权进行成员管理操作");
                        })
                        .orElseThrow(() -> new RuntimeException("活动ID不存在或未过审"))
                )
                .orElseThrow(() -> new RuntimeException("用户ID不存在或未过审"));
    }

}
