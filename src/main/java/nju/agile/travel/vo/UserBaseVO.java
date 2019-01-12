package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.UserEntity;

/**
 * Created by ShirokoSama on 2019/1/10.
 */
@Data
public class UserBaseVO {

    int id;

    String username;

    String email;

    String avaUrl;

    String weChat;

    public UserBaseVO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getName();
        this.email = userEntity.getMail();
        this.avaUrl = userEntity.getAvatarUrl();
        this.weChat = userEntity.getWeChat();
    }

    @Override
    public String toString() {
        return String.format(
                "UserBaseVO[id=%d, title=%s, email=%s, avaUrl=%s, weChat=%s]",
                id, username, email, avaUrl, weChat);
    }

}
