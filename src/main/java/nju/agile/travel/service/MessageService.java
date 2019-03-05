package nju.agile.travel.service;

import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.UserEntity;
import nju.agile.travel.util.Constants;
import nju.agile.travel.vo.NormalMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ShirokoSama on 2019/3/5.
 */
@Component
public class MessageService {

    @Autowired
    UserRepo userRepo;

    public List<NormalMessageVO> queryUserMessages(int userID) {
        return userRepo
                .findById(userID)
                .map(userEntity -> userEntity
                        .getMessages()
                        .stream()
                        .map(messageEntity -> {
                            if (messageEntity.getType() == Constants.MEMBER_EXIT) {
                                UserEntity contentUser = userRepo
                                        .findById(messageEntity.getContentUserID())
                                        .orElseThrow(() -> new RuntimeException("用户ID不存在"));
                                return new NormalMessageVO(messageEntity.getActivity(), contentUser, messageEntity.getType());
                            }
                            else
                                return new NormalMessageVO(messageEntity.getActivity(), null, messageEntity.getType());
                        })
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("用户ID不存在"));
    }

}
