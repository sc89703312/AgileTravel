package nju.agile.travel.service;

import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.UserEntity;
import nju.agile.travel.model.RegisterParam;
import nju.agile.travel.util.Base64Util;
import nju.agile.travel.util.Constants;
import nju.agile.travel.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

/**
 * Created by echo on 2019/1/9.
 */
@Component
public class AuthService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    MailService mailService;

    @Value("${check.host}")
    String serverHost;

    @Value("${check.port}")
    Integer serverPort;

    public void register(RegisterParam param){
        UserEntity userEntity = buildUserEntity(param);
        sendCheckMail(userEntity);
    }

    public int check(Integer id, String encodedCheckStr){
        return userRepo
                .findById(id)
                .map(userEntity -> {
                    Integer userId = userEntity.getId();
                    String userCreatedAtStr = DateUtil.dateToString(userEntity.getCreatedAt());
                    String userEncodedCheckStr = Base64Util.encode(userId, userCreatedAtStr);

                    if(userEncodedCheckStr.equals(encodedCheckStr)){
                        userEntity.setCheck(Constants.ACCOUNT_ON);
                        userRepo.save(userEntity);
                    }else{
                        throw new RuntimeException("激活码已失效，请重新注册");
                    }

                    return userEntity.getId();
                })
                .orElseThrow(() -> new RuntimeException("用户ID不存在"));
    }

    private UserEntity buildUserEntity(RegisterParam param){
        UserEntity userEntity = getUncheckedAccount(param.getMail());
        userEntity.setAvatarUrl(param.getAvatarUrl());
        userEntity.setMail(param.getMail());
        userEntity.setName(param.getName());
        userEntity.setPassword(param.getPassword());
        userEntity.setCheck(Constants.ACCOUNT_OFF);
        userEntity.setWeChat("");
        userEntity.setCreatedAt(DateUtil.getCurrentRoundDate());

        userRepo.save(userEntity);

        return userEntity;
    }

    private UserEntity getUncheckedAccount(String mail){
        return userRepo
                .findByMail(mail)
                .map(userEntity -> {
                    if(userEntity.getCheck() == Constants.ACCOUNT_ON){
                        throw new RuntimeException("该邮箱已注册");
                    }else{
                        return userEntity;
                    }
                })
                .orElseGet(UserEntity::new);
    }

    private void sendCheckMail(UserEntity userEntity){
        Integer id = userEntity.getId();
        String mail = userEntity.getMail();
        String createdAtStr = DateUtil.dateToString(userEntity.getCreatedAt());

        String encodedCheck = Base64Util.encode(id, createdAtStr);
        String checkUrl = String.format("http://%s:%d/user/check?id=%d&check=%s",
                serverHost, serverPort, id, encodedCheck);
        System.out.println("Check Url: " + checkUrl);
        mailService.sendEmail(mail, checkUrl);
    }
}
