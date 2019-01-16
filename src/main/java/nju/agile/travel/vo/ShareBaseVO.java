package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ShareEntity;
import nju.agile.travel.util.DateUtil;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by echo on 2019/1/12.
 */
@Data
public class ShareBaseVO {

    int id;
    UserBaseVO user;
    String contents;
    ActivityBaseVO activity;
    int likeNum;
    Boolean isLike;
    String dateTime;

    public ShareBaseVO(final ShareEntity entity, Integer currentUserId){
        this.id = entity.getId();
        this.user = new UserBaseVO(entity.getAuthor());
        this.contents = entity.getContent();
        this.activity = new ActivityBaseVO(entity.getBelongedActivity());
        this.likeNum = entity.getStarNum();
        this.dateTime = DateUtil.dateToString(entity.getTimestamps());
        this.isLike = Arrays.asList(
                entity.getStarUserIds().split(" ")).contains(
                String.valueOf(currentUserId));
    }

    @Override
    public String toString(){
        return String.format(
                "%nShareBaseVO[id=%d, sharedTime='%s', contents='%s', authorId=%d, activityId=%d, likeNum=%d, isLike='%s']",
                id, dateTime, contents, user.getId(), activity.getId(), likeNum, String.valueOf(isLike)
        );
    }

}
