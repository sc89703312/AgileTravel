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

    @Column(name = "image_urls")
    String imageUrls;

    @ManyToOne
    @JoinColumn(name = "t_user_id")
    UserEntity author;

    @ManyToOne
    @JoinColumn(name = "t_activity_id")
    ActivityEntity belongedActivity;

    @Override
    public String toString() {
        String result = String.format(
                "Share[id=%d, content='%s', image_urls='%s', time='%s']%n",
                id, content, imageUrls, timestamps);

        result += String.format(
                "Author[id=%d, name='%s']%n",
                author.getId(), author.getName());

        result += String.format(
                "Activity[id=%d, name='%s']%n",
                belongedActivity.getId(), belongedActivity.getName());

        return result;
    }

}
