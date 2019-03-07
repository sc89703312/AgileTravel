package nju.agile.travel.service;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.MessageRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.MessageEntity;
import nju.agile.travel.util.Constants;
import nju.agile.travel.vo.ActivityBaseVO;
import nju.agile.travel.vo.ActivityCheckVO;
import nju.agile.travel.vo.UserBaseVO;
import nju.agile.travel.vo.UserCheckVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by echo on 2019/3/6.
 */
@Component
public class CheckService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ActivityRepo activityRepo;

    @Autowired
    MessageRepo messageRepo;

    public List<UserCheckVO> queryAllUsers(){
        return userRepo.findAll()
                .stream()
                .map(UserCheckVO::new)
                .collect(Collectors.toList());
    }

    public List<ActivityCheckVO> queryAllActivities(){
        return activityRepo.findAll()
                .stream()
                .map(ActivityCheckVO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public int userCheckChange(int userId) {
        return userRepo.findById(userId).map(
                userEntity -> {
                    MessageEntity messageEntity = userEntity.getCheck() == Constants.ACCOUNT_ON ?
                            MessageEntity.of(userEntity, null, 0, Constants.USER_BANNED) :
                            MessageEntity.of(userEntity, null, 0, Constants.USER_CHECKED);
                    messageRepo.save(messageEntity);
                    userEntity.setCheck(1 - userEntity.getCheck());
                    userRepo.save(userEntity);
                    return userEntity.getCheck();
                }
        ).orElseThrow(() -> new RuntimeException("用户ID不存在"));
    }

    @Transactional
    public int activityCheckChange(int activityId) {
        return activityRepo.findById(activityId).map(
                activityEntity -> {
                    MessageEntity messageEntity = activityEntity.getCheck() == Constants.ACTIVITY_OFF ?
                            MessageEntity.of(activityEntity.getCreator(), activityEntity, 0, Constants.ACTIVITY_CHECKED) :
                            MessageEntity.of(activityEntity.getCreator(), activityEntity, 0, Constants.ACTIVITY_BANNED);
                    messageRepo.save(messageEntity);
                    activityEntity.setCheck(1 - activityEntity.getCheck());
                    activityRepo.save(activityEntity);
                    return activityEntity.getCheck();
                }
        ).orElseThrow(() -> new RuntimeException("活动ID不存在"));
    }

}
