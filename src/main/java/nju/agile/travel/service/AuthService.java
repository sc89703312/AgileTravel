package nju.agile.travel.service;

import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.UserEntity;
import nju.agile.travel.model.RegisterParam;
import nju.agile.travel.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by echo on 2019/1/9.
 */
@Component
public class AuthService {

    @Autowired
    UserRepo userRepo;

    public int register(RegisterParam param){
        int result = 0;

        if(accountNotExist(param.getMail())){
            result = 1;
            UserEntity userEntity = new UserEntity();
            userEntity.setAvatarUrl(param.getAvatarUrl());
            userEntity.setMail(param.getMail());
            userEntity.setName(param.getName());
            userEntity.setPassword(param.getPassword());
            userEntity.setCheck(Constants.ACCOUNT_OFF);
            userEntity.setWeChat("");

            System.out.println(userEntity);
            userRepo.save(userEntity);
        }

        return result;
    }

    private Boolean accountNotExist(String mail){
        return userRepo.findByMail(mail) == null;
    }
}
