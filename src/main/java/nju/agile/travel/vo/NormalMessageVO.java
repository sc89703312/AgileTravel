package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ActivityEntity;

/**
 * Created by ShirokoSama on 2019/3/5.
 */
@Data
public class NormalMessageVO {

    ActivityBaseVO activity;

    Integer type;

    public NormalMessageVO(ActivityEntity activityEntity, Integer type) {
        this.activity = new ActivityBaseVO(activityEntity);
        this.type = type;
    }

}
