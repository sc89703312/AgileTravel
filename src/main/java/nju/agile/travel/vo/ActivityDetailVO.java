package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    Date startDateTime;

    Date endDateTime;

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
        this.startDateTime = activityEntity.getStartTime();
        this.endDateTime = activityEntity.getEndTime();
        this.comments = activityEntity.getPostNum();
        this.isMember = memberStatus;
        this.isPublic = activityEntity.getAccess() == Constants.ACTIVITY_PUBLIC;
        this.images = activityEntity.getImageUrls() == null ?
                new ArrayList<>() :
                Arrays.asList(activityEntity.getImageUrls().split("\\s"));
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
                id, title, description, address, startDateTime.toString(), endDateTime.toString(),
                images, organizer.getId(), isMember, isPublic));
        for (UserBaseVO user : participants)
            buf.append(user.toString());
        return buf.toString();
    }

}
