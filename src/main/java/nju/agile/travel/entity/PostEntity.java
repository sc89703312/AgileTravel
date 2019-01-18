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

    @Lob
    @Column(name = "image_urls", columnDefinition="text")
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
                "Post[id=%d, content='%s', image_urls='%s', time='%s']%n",
                id, content, imageUrls, timestamps);

        result += String.format(
                "Author[id=%d, title='%s']%n",
                author.getId(), author.getName());

        result += String.format(
                "Activity[id=%d, title='%s']%n",
                belongedActivity.getId(), belongedActivity.getName());

        return result;
    }

}
