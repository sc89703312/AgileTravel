package nju.agile.travel.service;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.model.ActivityInfoParam;
import nju.agile.travel.util.Constants;
import nju.agile.travel.vo.ActivityBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ShirokoSama on 2019/1/9.
 */
@Component
public class ActivityCURDService {

    @Autowired
    ActivityRepo activityRepo;

    @Autowired
    UserRepo userRepo;

    public List<ActivityBaseVO> queryActivityPage(int pageIndex) {
        return activityRepo
                .findByCheckAndAccess(Constants.ACTIVITY_ON, Constants.ACTIVITY_PUBLIC, PageRequest.of(pageIndex, Constants.PAGE_SIZE))
                .map(ActivityBaseVO::new)
                .getContent();
    }

    @Transactional
    public List<ActivityBaseVO> queryCreatedActivity(int userID) {
        return userRepo
                .findById(userID)
                .map(userEntity -> userEntity
                        .getCreatedActivityList()
                        .stream()
                        .map(ActivityBaseVO::new)
                        .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    @Transactional
    public List<ActivityBaseVO> queryJoinedActivity(int userID) {
        return userRepo
                .findById(userID)
                .map(userEntity -> userEntity
                    .getJoinedActivityList()
                    .stream()
                    .map(ActivityBaseVO::new)
                    .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    @Transactional
    public int createActivity(ActivityInfoParam param) {
        return userRepo.findById(param.getCreatorID())
                .map(userEntity -> {
                    ActivityEntity entity = new ActivityEntity();
                    entity.setCreator(userEntity);
                    entity.setCheck(Constants.ACTIVITY_ON);
                    activityRepo.save(buildActivityEntity(entity, param));
                    return entity.getId();
                })
                .orElseThrow(() -> new RuntimeException("用户ID不存在"));
    }

    @Transactional
    public void editActivity(int activityID, ActivityInfoParam param) {
        activityRepo
                .findById(activityID)
                .map(activityEntity -> activityRepo.save(buildActivityEntity(activityEntity, param)))
                .orElseThrow(() -> new RuntimeException("活动ID不存在"));
    }

    private ActivityEntity buildActivityEntity(ActivityEntity entity, ActivityInfoParam param) {
        entity.setName(param.getName());
        entity.setDescription(param.getDescription());
        entity.setLocation(param.getLocation());
        entity.setStartTime(param.getStartTime());
        entity.setEndTime(param.getEndTime());
        entity.setBannerUrl(param.getBannerUrl());
        entity.setAccess(param.isPublic() ? Constants.ACTIVITY_PUBLIC : Constants.ACTIVITY_PRIVATE);
        return entity;
    }

}
