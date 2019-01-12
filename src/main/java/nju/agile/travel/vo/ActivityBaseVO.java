package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.util.Constants;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by ShirokoSama on 2019/1/10.
 */
@Data
public class ActivityBaseVO {

    int id;

    String title;

    String description;

    String address;

    Date startDateTime;

    Date endDateTime;

    String cover;

    int comments;

    int isMember; // 0-applying, 1-participant, 2-organizer, 3-not in this activity

    boolean isPublic;

    UserBaseVO organizer;

    ActivityBaseVO(ActivityEntity activityEntity) {
        this.id = activityEntity.getId();
        this.title = activityEntity.getName();
        this.description = activityEntity.getDescription();
        this.address = activityEntity.getLocation();
        this.startDateTime = activityEntity.getStartTime();
        this.endDateTime = activityEntity.getEndTime();
        this.cover = activityEntity.getImageUrls() == null ?
                "" : Arrays.asList(activityEntity.getImageUrls().split("\\s")).get(0);
        this.comments = activityEntity.getPostNum();
        this.isPublic = activityEntity.getAccess() == Constants.ACTIVITY_PUBLIC;
        this.organizer = new UserBaseVO(activityEntity.getCreator());
    }

    public ActivityBaseVO(ActivityEntity activityEntity, int memberStatus) {
        this(activityEntity);
        this.isMember = memberStatus;
    }

    @Override
    public String toString() {
        return String.format(
                "%nActivityBaseVO[id=%d, title=%s, description=%s, address=%s, startDateTime=%s, endDateTime=%s, cover=%s, organizerID=%d]",
                id, title, description, address, startDateTime.toString(), endDateTime.toString(), cover, organizer.getId());
    }

}
