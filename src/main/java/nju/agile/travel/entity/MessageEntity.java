package nju.agile.travel.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by ShirokoSama on 2019/3/5.
 */
@Entity(name = "t_message")
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "t_user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "t_activity_id")
    ActivityEntity activity;

    @Column(name = "content_user_id")
    int contentUserID;

    @Column(name = "type")
    int type;

    static public MessageEntity of(UserEntity userEntity, ActivityEntity activityEntity, int contentUserID, int type) {
        MessageEntity entity = new MessageEntity();
        entity.setUser(userEntity);
        entity.setActivity(activityEntity);
        entity.setContentUserID(contentUserID);
        entity.setType(type);
        return entity;
    }

    @Override
    public String toString() {
        return String.format(
                "MessageEntity[uid=%d, aid=%d, cuid=%d, type=%d]%n",
                this.getUser().getId(), this.getActivity().getId(), this.getContentUserID(), this.getType()
        );
    }

}
