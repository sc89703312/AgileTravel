package nju.agile.travel.service;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.vo.ActivityBaseVO;
import nju.agile.travel.vo.ActivityCheckVO;
import nju.agile.travel.vo.UserBaseVO;
import nju.agile.travel.vo.UserCheckVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public int userCheckChange(int userId){
        return userRepo.findById(userId).map(
                userEntity -> {
                    userEntity.setCheck(1-userEntity.getCheck());
                    userRepo.save(userEntity);
                    return userEntity.getCheck();
                }
        ).orElseThrow(() -> new RuntimeException("用户ID不存在"));
    }

    public int activityCheckChange(int activityId){
        return activityRepo.findById(activityId).map(
                activityEntity -> {
                    activityEntity.setCheck(1-activityEntity.getCheck());
                    activityRepo.save(activityEntity);
                    return activityEntity.getCheck();
                }
        ).orElseThrow(() -> new RuntimeException("活动ID不存在"));
    }

}
