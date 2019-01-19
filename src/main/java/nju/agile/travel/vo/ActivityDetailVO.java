package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.util.Constants;
import nju.agile.travel.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ShirokoSama on 2019/1/10.
 */
@Data
public class ActivityDetailVO {

    int id;

    String title;

    String description;

    String address;

    String startDateTime;

    String endDateTime;

    int comments;

    int isMember; // 0-applying, 1-participant, 2-organizer, 3-not in this activity

    boolean isPublic;

    List<String> images;

    UserBaseVO organizer;

    List<UserBaseVO> participants;

    public ActivityDetailVO(ActivityEntity activityEntity, int memberStatus) {
        this.id = activityEntity.getId();
        this.title = activityEntity.getName();
        this.description = activityEntity.getDescription();
        this.address = activityEntity.getLocation();
        this.startDateTime = DateUtil.dateToString(activityEntity.getStartTime());
        this.endDateTime = DateUtil.dateToString(activityEntity.getEndTime());
        this.comments = activityEntity.getPostNum();
        this.isMember = memberStatus;
        this.isPublic = activityEntity.getAccess() == Constants.ACTIVITY_PUBLIC;
        if (activityEntity.getImageUrls() != null && !activityEntity.getImageUrls().equals(""))
            this.images = Arrays.asList(activityEntity.getImageUrls().split("\\s"));
        else
            this.images = Collections.singletonList(Constants.defaultCoverUrl);
        this.organizer = new UserBaseVO(activityEntity.getCreator());
        this.participants = activityEntity.getParticipants()
                .stream()
                .map(UserBaseVO::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(String.format(
                "%nActivityDetailedVO[id=%d, title=%s, description=%s, address=%s, startDateTime=%s, endDateTime=%s, " +
                        "images=%s, organizerID=%d, isMember=%d, isPublic=%b]",
                id, title, description, address, startDateTime, endDateTime,
                images, organizer.getId(), isMember, isPublic));
        for (UserBaseVO user : participants)
            buf.append("\n").append(user.toString());
        return buf.toString();
    }

}
