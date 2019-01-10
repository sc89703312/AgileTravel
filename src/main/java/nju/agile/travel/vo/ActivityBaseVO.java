package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ActivityEntity;

import java.util.Date;

/**
 * Created by ShirokoSama on 2019/1/10.
 */
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
        this.location = activityEntity.getLocation();
        this.startTime = activityEntity.getStartTime();
        this.endTime = activityEntity.getEndTime();
        this.bannerUrl = activityEntity.getBannerUrl();
        this.creator = new UserBaseVO(activityEntity.getCreator());
    }

    @Override
    public String toString() {
        return String.format(
                "%nActivityBaseVO[id=%d, name=%s, description=%s, location=%s, startTime=%s, endTime=%s, bannerUrl=%s, creatorID=%d]",
                id, name, description, location, startTime.toString(), endTime.toString(), bannerUrl, creator.getId());
    }

}
