package nju.agile.travel.service;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.model.ActivityInfoParam;
import nju.agile.travel.util.Constants;
import nju.agile.travel.vo.ActivityBaseVO;
import nju.agile.travel.vo.ActivityDetailVO;
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

    @Transactional
    public ActivityDetailVO queryDetailedActivity(int userID, int activityID) {
        return userRepo
                .findById(userID)
                .map(userEntity -> activityRepo
                        .findById(activityID)
                        .map(activityEntity -> {
                            if (userEntity.getApplyingActivityList().contains(activityEntity))
                                return new ActivityDetailVO(activityEntity, Constants.MEMBER_APPLYING);
                            else if (userEntity.getJoinedActivityList().contains(activityEntity))
                                return new ActivityDetailVO(activityEntity, Constants.MEMBER_APPROVED);
                            else if (userEntity.getCreatedActivityList().contains(activityEntity))
                                return new ActivityDetailVO(activityEntity, Constants.MEMBER_CREATOR);
                            else
                                return new ActivityDetailVO(activityEntity, Constants.MEMBER_NONE);
                        })
                        .orElseThrow(() -> new RuntimeException("活动ID不存在"))
                )
                .orElseThrow(() -> new RuntimeException("用户ID不存在"));
    }

    @Transactional
    public List<ActivityBaseVO> queryActivityPage(int userID, int pageIndex) {
        return userRepo
                .findById(userID)
                .map(userEntity -> activityRepo
                        .findByCheckAndAccess(Constants.ACTIVITY_ON, Constants.ACTIVITY_PUBLIC, PageRequest.of(pageIndex, Constants.PAGE_SIZE))
                        .getContent()
                        .stream()
                        .map(activityEntity -> {
                            if (userEntity.getApplyingActivityList().contains(activityEntity))
                                return new ActivityBaseVO(activityEntity, Constants.MEMBER_APPLYING);
                            else if (userEntity.getJoinedActivityList().contains(activityEntity))
                                return new ActivityBaseVO(activityEntity, Constants.MEMBER_APPROVED);
                            else if (userEntity.getCreatedActivityList().contains(activityEntity))
                                return new ActivityBaseVO(activityEntity, Constants.MEMBER_CREATOR);
                            else
                                return new ActivityBaseVO(activityEntity, Constants.MEMBER_NONE);
                        })
                        .collect(Collectors.toList())
                )
                .orElseThrow(() -> new RuntimeException("用户ID不存在"));
    }

    @Transactional
    public List<ActivityBaseVO> queryCreatedActivity(int userID) {
        return userRepo
                .findById(userID)
                .map(userEntity -> userEntity
                        .getCreatedActivityList()
                        .stream()
                        .map(activityEntity -> new ActivityBaseVO(activityEntity, Constants.MEMBER_CREATOR))
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
                    .map(activityEntity -> new ActivityBaseVO(activityEntity, Constants.MEMBER_APPROVED))
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
                    entity.setPostNum(0);
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
        entity.setImageUrls(param.getImageUrls());
        entity.setAccess(param.isPublic() ? Constants.ACTIVITY_PUBLIC : Constants.ACTIVITY_PRIVATE);
        return entity;
    }

}
