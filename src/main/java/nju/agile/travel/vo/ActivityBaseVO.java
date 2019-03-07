package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.util.Constants;
import nju.agile.travel.util.DateUtil;

import java.util.Arrays;

/**
 * Created by ShirokoSama on 2019/1/10.
 */
@Data
public class ActivityBaseVO {

    int id;

    String title;

    String description;

    String address;

    String startDateTime;

    String endDateTime;

    String cover;

    int comments;

    int isMember; // 0-applying, 1-participant, 2-organizer, 3-not in this activity

    boolean isPublic;

    UserBaseVO organizer;

    public ActivityBaseVO(ActivityEntity activityEntity) {
        this.id = activityEntity.getId();
        this.title = activityEntity.getName();
        this.description = activityEntity.getDescription();
        this.address = activityEntity.getLocation();
        this.startDateTime = DateUtil.dateToString(activityEntity.getStartTime());
        this.endDateTime = DateUtil.dateToString(activityEntity.getEndTime());
        if (activityEntity.getImageUrls() != null && !activityEntity.getImageUrls().equals(""))
            this.cover = Arrays.asList(activityEntity.getImageUrls().split("\\s")).get(0);
        else
            this.cover = Constants.defaultCoverUrl;
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
                "%nActivityBaseVO[id=%d, title=%s, description=%s, address=%s, startDateTime=%s, endDateTime=%s, " +
                        "cover=%s, organizerID=%d, isMember=%d, isPublic=%b]",
                id, title, description, address, startDateTime, endDateTime,
                cover, organizer.getId(), isMember, isPublic);
    }

}
