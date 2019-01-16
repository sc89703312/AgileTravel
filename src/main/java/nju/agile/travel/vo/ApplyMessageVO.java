package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.entity.UserEntity;

/**
 * Created by ShirokoSama on 2019/1/16.
 */
@Data
public class ApplyMessageVO {

    UserBaseVO applicant;

    ActivityBaseVO activity;

    public ApplyMessageVO(UserEntity userEntity, ActivityEntity activityEntity) {
        this.applicant = new UserBaseVO(userEntity);
        this.activity = new ActivityBaseVO(activityEntity);
    }

}
