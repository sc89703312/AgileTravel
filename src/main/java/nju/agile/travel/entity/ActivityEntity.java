package nju.agile.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by echo on 2019/1/8.
 */
@Entity(name = "t_activity")
@Data
@EqualsAndHashCode(exclude = {"posts", "participants", "applicants", "userActivityRelations", "messages"})
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

    @Lob
    @Column(name = "image_urls", columnDefinition="text")
    String imageUrls;

    @Column(name = "[check]")
    int check;

    @Column(name = "access")
    int access;

    @Column(name = "invited_at")
    Date invitedAt;

    @Column(name = "post_num")
    int postNum;

    @ManyToOne
    @JoinColumn(name = "t_user_id")
    UserEntity creator;

    @OneToMany(mappedBy = "belongedActivity")
    Set<PostEntity> posts;

    @ManyToMany(mappedBy = "joinedActivityList")
    @WhereJoinTable(clause = "status = 1")
    Set<UserEntity> participants;

    @ManyToMany(mappedBy = "applyingActivityList")
    @WhereJoinTable(clause = "status = 0")
    Set<UserEntity> applicants;

    @OneToMany(mappedBy = "activity")
    Set<RUserActivityEntity> userActivityRelations;

    @OneToMany(mappedBy = "activity")
    Set<MessageEntity> messages;

    public Set<UserEntity> getCreatorAndParticipants() {
        Set<UserEntity> users = new HashSet<>(participants);
        users.add(creator);
        return users;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();

        buf.append(String.format(
                "Activity[id=%d, title='%s', description='%s', address='%s', start_time='%s', end_time='%s', image_urls='%s', check=%d]%n",
                id, name, description, location, startTime.toString(), endTime.toString(), imageUrls, check));

        if (creator != null) {
            buf.append(String.format(
                    "Creator[id=%d, title='%s']%n",
                    creator.getId(), creator.getName()));
        }

        if (participants != null) {
            for(UserEntity participant : participants) {
                buf.append(String.format(
                        "Participant[id=%d, title='%s']%n",
                        participant.getId(), participant.getName()));
            }
        }

        if (applicants != null) {
            for (UserEntity applicant: applicants) {
                buf.append(String.format(
                        "Applicant[id=%d, title='%s']%n",
                        applicant.getId(), applicant.getName()));
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
