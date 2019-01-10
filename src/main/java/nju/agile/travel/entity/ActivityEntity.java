package nju.agile.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by echo on 2019/1/8.
 */
@Entity(name = "t_activity")
@Data
@EqualsAndHashCode(exclude = {"participants", "posts"})
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    String name;

    String description;

    String location;

    @Column(name = "start_time")
    Date startTime;

    @Column(name = "end_time")
    Date endTime;

    @Column(name = "banner_url")
    String bannerUrl;

    @Column(name = "[check]")
    int check;

    @Column(name = "access")
    int access;

    @Column(name = "invited_at")
    Date invitedAt;

    @ManyToOne
    @JoinColumn(name = "t_user_id")
    UserEntity creator;

    @ManyToMany(mappedBy = "joinedActivityList")
    Set<UserEntity> participants;

    @OneToMany(mappedBy = "belongedActivity")
    Set<PostEntity> posts;

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();

        buf.append(String.format(
                "Activity[id=%d, name='%s', description='%s', location='%s', start_time='%s', end_time='%s', banner_url='%s', check=%d]%n",
                id, name, description, location, startTime.toString(), endTime.toString(), bannerUrl, check));

        if (creator != null) {
            buf.append(String.format(
                    "Creator[id=%d, name='%s']%n",
                    creator.getId(), creator.getName()));
        }

        if (participants != null) {
            for(UserEntity participant : participants) {
                buf.append(String.format(
                        "Participant[id=%d, name='%s']%n",
                        participant.getId(), participant.getName()));
            }
        }

        if (posts != null) {
            for(PostEntity post : posts) {
                buf.append(String.format(
                        "Post[id=%d, content='%s']%n",
                        post.getId(), post.getContent()));
            }
        }

        return buf.toString();
    }

}
