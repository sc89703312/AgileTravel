package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.UserEntity;
import nju.agile.travel.util.Constants;

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
        if (userEntity.getAvatarUrl() != null && !userEntity.getAvatarUrl().equals(""))
            this.avaUrl = userEntity.getAvatarUrl();
        else
            this.avaUrl = Constants.defaultAvatarUrl;
        this.weChat = userEntity.getWeChat();
    }

    @Override
    public String toString() {
        return String.format(
                "UserBaseVO[id=%d, title=%s, email=%s, avaUrl=%s, weChat=%s]",
                id, username, email, avaUrl, weChat);
    }

}
