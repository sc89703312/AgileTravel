package nju.agile.travel.service;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.ShareRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.entity.ShareEntity;
import nju.agile.travel.entity.UserEntity;
import nju.agile.travel.model.ShareInfoParam;
import nju.agile.travel.util.Constants;
import nju.agile.travel.util.DateUtil;
import nju.agile.travel.vo.ShareBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by echo on 2019/1/12.
 */
@Component
public class ShareService {

    @Autowired
    ShareRepo shareRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ActivityRepo activityRepo;

    public List<ShareBaseVO> queryShareList(Integer userId){
        return shareRepo
                .findAllByOrderByStarNumDesc()
                .stream()
                .map(entity -> new ShareBaseVO(entity, userId))
                .collect(Collectors.toList());
    }

    @Transactional
    public int share(ShareInfoParam param){
        UserEntity author = userRepo
                .findByIdAndCheck(param.getUserId(), Constants.ACCOUNT_ON)
                .orElseThrow(() -> new RuntimeException("用户ID不存在或未过审"));
        ActivityEntity belongedActivity = activityRepo
                .findByIdAndCheckAndAccess(param.getActivityId(), Constants.ACTIVITY_ON, Constants.ACTIVITY_PUBLIC)
                .orElseThrow(() -> new RuntimeException("活动ID不存在或未过审或为私密活动"));

        if(!belongedActivity.getCreatorAndParticipants().contains(author)){
            throw new RuntimeException("用户未参加该活动，或尚在审核中");
        }

        return shareRepo.save(buildShareEntity(param, author, belongedActivity)).getId();
    }

    public void star(Integer userId, Integer shareId){

        userRepo.findByIdAndCheck(userId, Constants.ACCOUNT_ON).orElseThrow(() -> new RuntimeException("用户ID不存在或未过审"));

        shareRepo
                .findById(shareId)
                .map(shareEntity -> {
                    String starUserIds = shareEntity.getStarUserIds();
                    List<String> userIdList = Arrays.asList(starUserIds.split(" "));
                    if(!userIdList.contains(String.valueOf(userId))){
                        shareEntity.setStarUserIds(String.format("%s %d", starUserIds, userId));
                        shareEntity.setStarNum(shareEntity.getStarNum() + 1);
                    }
                    return shareRepo.save(shareEntity);
                })
                .orElseThrow(() -> new RuntimeException("分享ID不存在"));
    }

    private ShareEntity buildShareEntity(ShareInfoParam param, UserEntity author, ActivityEntity belongedActivity){
        ShareEntity shareEntity = new ShareEntity();
        shareEntity.setAuthor(author);
        shareEntity.setBelongedActivity(belongedActivity);
        shareEntity.setTimestamps(DateUtil.getCurrentRoundDate());
        shareEntity.setContent(param.getContents());
        shareEntity.setStarNum(0);
        shareEntity.setStarUserIds("");

        return shareEntity;
    }
}
