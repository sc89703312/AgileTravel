package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ActivityEntity;

/**
 * Created by echo on 2019/3/6.
 */
@Data
public class ActivityCheckVO extends ActivityBaseVO {

    private int check;

    public ActivityCheckVO(ActivityEntity activityEntity) {
        super(activityEntity);
        this.check = activityEntity.getCheck();
    }

    @Override
    public String toString() {
        return String.format(
                "%nActivityCheckVO[id=%d, title=%s, description=%s, address=%s, startDateTime=%s, endDateTime=%s, " +
                        "cover=%s, organizerID=%d, isMember=%d, isPublic=%b, check=%d]",
                id, title, description, address, startDateTime, endDateTime,
                cover, organizer.getId(), isMember, isPublic, check);
    }
}
