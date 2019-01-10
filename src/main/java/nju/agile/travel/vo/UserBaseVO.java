package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.UserEntity;

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

}
