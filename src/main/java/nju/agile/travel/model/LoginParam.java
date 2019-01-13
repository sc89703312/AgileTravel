package nju.agile.travel.model;

import lombok.Data;

/**
 * Created by echo on 2019/1/13.
 */
@Data
public class LoginParam {
    String email;
    String password;

    public LoginParam(){

    }

    public LoginParam(String email, String password){
        this.email = email;
        this.password = password;
    }
}
