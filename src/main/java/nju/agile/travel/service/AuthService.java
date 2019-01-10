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

        UserEntity userEntity = getUncheckedAccount(param.getMail());
        userEntity.setAvatarUrl(param.getAvatarUrl());
        userEntity.setMail(param.getMail());
        userEntity.setName(param.getName());
        userEntity.setPassword(param.getPassword());
        userEntity.setCheck(Constants.ACCOUNT_OFF);
        userEntity.setWeChat("");
        userEntity.setCreatedAt(DateUtil.getCurrentRoundDate());
        userRepo.save(userEntity);

        Integer userId = userEntity.getId();
        String userMail = userEntity.getMail();
        String userCreatedAtStr = DateUtil.dateToString(userEntity.getCreatedAt());
        System.out.println(userCreatedAtStr);
        sendCheckMail(userMail, userId, userCreatedAtStr);
    }

    public void check(Integer id, String encodedCheckStr){
        Optional<UserEntity> optional = userRepo.findById(id);
        if(optional.isPresent()){
            UserEntity user = optional.get();
            Integer userId = user.getId();
            String userCreatedAtStr = DateUtil.dateToString(user.getCreatedAt());
            String userEncodedCheckStr = Base64Util.encode(userId, userCreatedAtStr);

            System.out.println("userId: " + userId +" createdAt: " + userCreatedAtStr);
            System.out.println("check: " + userEncodedCheckStr);

            if(userEncodedCheckStr.equals(encodedCheckStr)){
                user.setCheck(Constants.ACCOUNT_ON);
                userRepo.save(user);
            }else{
                throw new RuntimeException("激活码已失效，请重新注册");
            }

        }else{
            throw new RuntimeException("用户ID不存在");
        }
    }

    private UserEntity getUncheckedAccount(String mail){
        UserEntity user = userRepo.findByMail(mail);
        if(user == null){
            return new UserEntity();
        }else{
            if(user.getCheck() == 1){
                throw new RuntimeException("该邮箱已注册");
            }
            return user;
        }
    }

    private void sendCheckMail(String mail, Integer id, String createdAtStr){
        String encodedCheck = Base64Util.encode(id, createdAtStr);
        String checkUrl = String.format("http://%s:%d/user/check?id=%d&check=%s",
                serverHost, serverPort, id, encodedCheck);
        System.out.println("Check Url: " + checkUrl);
        mailService.sendEmail(mail, checkUrl);
    }
}
