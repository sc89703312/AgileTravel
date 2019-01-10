package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.UserEntity;

/**
 * Created by ShirokoSama on 2019/1/10.
 */
@Data
public class UserBaseVO {

    int id;

    String name;

    String mail;

    String avatarUrl;

    String weChat;

    public UserBaseVO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.name = userEntity.getName();
        this.mail = userEntity.getMail();
        this.avatarUrl = userEntity.getAvatarUrl();
        this.weChat = userEntity.getWeChat();
    }

    @Override
    public String toString() {
        return String.format(
                "UserBaseVO[id=%d, name=%s, mail=%s, avatarUrl=%s, weChat=%s]",
                id, name, mail, avatarUrl, weChat);
    }

}
