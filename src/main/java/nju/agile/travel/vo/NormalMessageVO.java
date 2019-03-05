package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.entity.UserEntity;

/**
 * Created by ShirokoSama on 2019/3/5.
 */
@Data
public class NormalMessageVO {

    ActivityBaseVO activity;

    UserBaseVO user;

    Integer type;

    public NormalMessageVO(ActivityEntity activityEntity, UserEntity userEntity, Integer type) {
        this.activity = activityEntity == null ? null : new ActivityBaseVO(activityEntity);
        this.user = userEntity == null ? null : new UserBaseVO(userEntity);
        this.type = type;
    }

}
