package nju.agile.travel.service;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.RUserActivityRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.entity.RUserActivityEntity;
import nju.agile.travel.entity.RUserActivityID;
import nju.agile.travel.entity.UserEntity;
import nju.agile.travel.util.Base64Util;
import nju.agile.travel.util.Constants;
import nju.agile.travel.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Pattern pattern = Pattern.compile("(\\d+?)\\s(.*)");

    @Transactional
    public String generateInvitationCode(int userID, int activityID) {
        Pair<UserEntity, ActivityEntity> pair = getValidUserAndActivity(userID, activityID);
        UserEntity userEntity = pair.getFirst();
        ActivityEntity activityEntity = pair.getSecond();
        // check if user is the creator of activity
        if (userEntity.getId() == activityEntity.getCreator().getId()) {
            Date invitedAt = DateUtil.getCurrentRoundDate();
            activityEntity.setInvitedAt(invitedAt);
            activityRepo.save(activityEntity);
            return Base64Util.encode(activityID, DateUtil.dateToString(invitedAt));
        }
        else
            throw new RuntimeException("用户不是活动组织者，无权生成邀请码");
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

}
