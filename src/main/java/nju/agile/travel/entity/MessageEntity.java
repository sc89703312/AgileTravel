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

    @Column(name = "type")
    int type;

    @Override
    public String toString() {
        return String.format(
                "MessageEntity[uid=%d, aid=%d, type=%d]%n",
                this.getUser().getId(), this.getActivity().getId(), this.getType()
        );
    }

}
