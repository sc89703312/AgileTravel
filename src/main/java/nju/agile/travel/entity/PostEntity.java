package nju.agile.travel.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by echo on 2019/1/9.
 */
@Entity(name = "t_post")
@Data
public class PostEntity {

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

    @Override
    public String toString() {
        String result = String.format(
                "Post[id=%d, content='%s', time='%s']%n",
                id, content, timestamps);

        result += String.format(
                "Author[id=%d, name='%s']%n",
                author.getId(), author.getName());

        result += String.format(
                "Activity[id=%d, name='%s']%n",
                belongedActivity.getId(), belongedActivity.getName());

        return result;
    }

}
