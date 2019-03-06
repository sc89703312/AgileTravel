package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.UserEntity;

/**
 * Created by echo on 2019/3/6.
 */
@Data
public class UserCheckVO extends UserBaseVO {

    private int check;

    public UserCheckVO(UserEntity userEntity) {
        super(userEntity);
        this.check = userEntity.getCheck();
    }

    @Override
    public String toString() {
        return String.format(
                "UserCheckVO[id=%d, title=%s, email=%s, avaUrl=%s, weChat=%s, check=%d]",
                id, username, email, avaUrl, weChat, check);
    }

}
