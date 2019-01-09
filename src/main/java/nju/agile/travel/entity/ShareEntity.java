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

    @Column(name = "image_url")
    String imageUrl;

    @ManyToOne
    @JoinColumn(name = "t_user_id")
    UserEntity author;

    @ManyToOne
    @JoinColumn(name = "t_activity_id")
    ActivityEntity belongedActivity;

}
