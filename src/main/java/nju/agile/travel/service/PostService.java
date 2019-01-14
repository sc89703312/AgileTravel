package nju.agile.travel.service;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.PostRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.entity.PostEntity;
import nju.agile.travel.entity.UserEntity;
import nju.agile.travel.model.PostInfoParam;
import nju.agile.travel.util.Constants;
import nju.agile.travel.util.DateUtil;
import nju.agile.travel.vo.PostBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by echo on 2019/1/11.
 */
@Component
public class PostService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ActivityRepo activityRepo;

    @Transactional
    public List<PostBaseVO> queryPost(Integer activityId){
        return postRepo
                .findAllByActivityId(activityId)
                .stream()
                .map(PostBaseVO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public int post(PostInfoParam param){
        UserEntity author = userRepo
                .findByIdAndCheck(param.getAuthorId(), Constants.ACCOUNT_ON)
                .orElseThrow(() -> new RuntimeException("用户ID不存在或未过审"));
        ActivityEntity belongedActivity = activityRepo
                .findByIdAndCheck(param.getActivityId(), Constants.ACTIVITY_ON)
                .orElseThrow(() -> new RuntimeException("活动ID不存在或未过审"));

        if(!belongedActivity.getCreatorAndParticipants().contains(author)){
            throw new RuntimeException("用户未参加该活动，或尚在审核中");
        }

        return postRepo.save(buildPostEntity(param, author, belongedActivity)).getId();
    }

    private PostEntity buildPostEntity(PostInfoParam param, UserEntity author, ActivityEntity belongedActivity){
        PostEntity postEntity = new PostEntity();
        postEntity.setContent(param.getContent());
        postEntity.setTimestamps(DateUtil.getCurrentRoundDate());
        postEntity.setImageUrls(param.getImageUrls());
        postEntity.setAuthor(author);
        postEntity.setBelongedActivity(belongedActivity);

        return postEntity;
    }
}
