package nju.agile.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nju.agile.travel.util.Constants;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.WhereJoinTable;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by echo on 2019/1/8.
 */
@Entity(name = "t_user")
@Data
@EqualsAndHashCode(exclude = {"createdActivityList", "joinedActivityList", "applyingActivityList", "userActivityRelations", "messages"})
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    String name;

    String mail;

    @ColumnTransformer(
            read = "CAST(AES_DECRYPT(password, '" + Constants.KEY + "') as char(1000))",
            write = "AES_ENCRYPT(?, '" + Constants.KEY + "')" )
    String password;

    @Column(name = "avatar_url")
    String avatarUrl;

    @Column(name = "[check]")
    int check;

    @Column(name = "wechat")
    String weChat;

    @Column(name = "created_at")
    Date createdAt;

    @OneToMany(mappedBy = "creator")
    Set<ActivityEntity> createdActivityList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "r_user_activity",
            joinColumns = @JoinColumn(
                    name = "t_user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "t_activity_id",
                    referencedColumnName = "id"))
    @WhereJoinTable(clause = "status = 1")
    Set<ActivityEntity> joinedActivityList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "r_user_activity",
            joinColumns = @JoinColumn(
                    name = "t_user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "t_activity_id",
                    referencedColumnName = "id"))
    @WhereJoinTable(clause = "status = 0")
    Set<ActivityEntity> applyingActivityList;

    @OneToMany(mappedBy = "user")
    Set<RUserActivityEntity> userActivityRelations;

    @OneToMany(mappedBy = "user")
    @OrderBy("id desc")
    Set<MessageEntity> messages;

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();

        buf.append(String.format(
                "User[id=%d, title='%s', email='%s', avaUrl='%s', check=%d, wechat='%s']%n",
                id, name, mail, avatarUrl, check, weChat));

        if (createdActivityList != null) {
            for(ActivityEntity activity : createdActivityList) {
                buf.append(String.format(
                        "CreatedActivity[id=%d, title='%s']%n",
                        activity.getId(), activity.getName()));
            }
        }

        if (joinedActivityList != null) {
            for(ActivityEntity activity : joinedActivityList) {
                buf.append(String.format(
                        "JoinedActivity[id=%d, title='%s']%n",
                        activity.getId(), activity.getName()));
            }
        }

        if (applyingActivityList != null) {
            for(ActivityEntity activity : applyingActivityList) {
                buf.append(String.format(
                        "ApplyingActivity[id=%d, title='%s']%n",
                        activity.getId(), activity.getName()));
            }
        }

        return buf.toString();
    }

}
