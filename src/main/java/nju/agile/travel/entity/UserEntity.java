package nju.agile.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by echo on 2019/1/8.
 */
@Entity(name = "t_user")
@Data
@EqualsAndHashCode(exclude = {"createdActivityList", "joinedActivityList"})
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    String name;

    String mail;

    String password;

    @Column(name = "avatar_url")
    String avatarUrl;

    int check;

    @Column(name = "wechat")
    String weChat;

    @OneToMany(mappedBy = "creator")
    Set<ActivityEntity> createdActivityList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "r_user_activity",
            joinColumns = @JoinColumn(name = "t_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "t_activity_id", referencedColumnName = "id"))
    Set<ActivityEntity> joinedActivityList;

    @Override
    public String toString() {
        String result = String.format(
                "User[id=%d, name='%s', mail='%s', password='%s', avatarUrl='%s', check=%d, wechat='%s']%n",
                id, name, mail, password, avatarUrl, check, weChat);
        if (createdActivityList != null) {
            for(ActivityEntity activity : createdActivityList) {
                result += String.format(
                        "CreatedActivity[id=%d, name='%s']%n",
                        activity.getId(), activity.getName());
            }
        }

        if (joinedActivityList != null) {
            for(ActivityEntity activity : joinedActivityList) {
                result += String.format(
                        "JoinedActivity[id=%d, name='%s']%n",
                        activity.getId(), activity.getName());
            }
        }

        return result;
    }

}
