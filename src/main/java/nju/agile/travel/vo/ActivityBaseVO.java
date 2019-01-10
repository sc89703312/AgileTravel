package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ActivityEntity;

import java.util.Date;

@Data
public class ActivityBaseVO {

    int id;

    String name;

    String description;

    String location;

    Date startTime;

    Date endTime;

    String bannerUrl;

    UserBaseVO creator;

    public ActivityBaseVO(final ActivityEntity activityEntity) {
        this.id = activityEntity.getId();
        this.name = activityEntity.getName();
        this.description = activityEntity.getDescription();
        this.startTime = activityEntity.getStartTime();
        this.endTime = activityEntity.getEndTime();
        this.creator = new UserBaseVO(activityEntity.getCreator());
    }

}
