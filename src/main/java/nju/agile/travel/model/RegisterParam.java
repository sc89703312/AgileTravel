package nju.agile.travel.model;

import lombok.Data;

/**
 * Created by echo on 2019/1/9.
 */
@Data
public class RegisterParam {

    String mail;

    String name;

    String password;

    String avatarUrl;

    public RegisterParam(String mail, String name, String password, String avatarUrl){
        this.mail = mail;
        this.name = name;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }
}
