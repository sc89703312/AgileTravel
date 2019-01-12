package nju.agile.travel.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by echo on 2019/1/9.
 */
@Entity(name = "t_share")
@Data
public class ShareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    Date timestamps;

    String content;

    @ManyToOne
    @JoinColumn(name = "t_user_id")
    UserEntity author;

    @ManyToOne
    @JoinColumn(name = "t_activity_id")
    ActivityEntity belongedActivity;

    @Column(name = "star_num")
    int starNum;

    @Column(name = "star_user_ids")
    String starUserIds;

    @Override
    public String toString() {
        String result = String.format(
                "Share[id=%d, content='%s', time='%s', stars=%d, star_user_ids='%s']%n",
                id, content, timestamps, starNum, starUserIds);

        result += String.format(
                "Author[id=%d, title='%s']%n",
                author.getId(), author.getName());

        result += String.format(
                "Activity[id=%d, title='%s']%n",
                belongedActivity.getId(), belongedActivity.getName());

        return result;
    }

}
