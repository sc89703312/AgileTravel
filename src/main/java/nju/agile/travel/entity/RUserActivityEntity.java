package nju.agile.travel.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by ShirokoSama on 2019/1/13.
 */
@Entity(name = "r_user_activity")
@Data
public class RUserActivityEntity {

    @EmbeddedId
    RUserActivityID id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "t_user_id")
    UserEntity user;

    @ManyToOne
    @MapsId("activityID")
    @JoinColumn(name = "t_activity_id")
    ActivityEntity activity;

    @Column(name = "status")
    int status;

    public static RUserActivityEntity of(UserEntity user, ActivityEntity activity, int status) {
        RUserActivityEntity entity = new RUserActivityEntity();
        entity.setId(RUserActivityID.of(user.getId(), activity.getId()));
        entity.setUser(user);
        entity.setActivity(activity);
        entity.setStatus(status);
        return entity;
    }

    @Override
    public String toString() {
        return String.format(
                "RelationUserActivityEntity[uid=%d, aid=%d, status=%d]%n",
                this.getUser().getId(), this.getActivity().getId(), status
        );
    }

}
