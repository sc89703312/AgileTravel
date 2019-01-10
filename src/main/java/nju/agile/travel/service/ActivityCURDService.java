package nju.agile.travel.service;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.UserRepo;
import nju.agile.travel.entity.ActivityEntity;
import nju.agile.travel.entity.UserEntity;
import nju.agile.travel.util.Constants;
import nju.agile.travel.vo.ActivityBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by ShirokoSama on 2019/1/9.
 */
@Component
public class ActivityCURDService {

    @Autowired
    ActivityRepo activityRepo;

    @Autowired
    UserRepo userRepo;

    public List<ActivityBaseVO> queryActivityPage(int pageIndex) {
        return activityRepo
                .findByCheck(Constants.ACTIVITY_ON, PageRequest.of(pageIndex, Constants.PAGE_SIZE))
                .map(ActivityBaseVO::new)
                .getContent();
    }

    @Transactional
    public List<ActivityBaseVO> queryCreatedActivity(int userID) {
        Optional<UserEntity> optionalUser = userRepo.findById(userID);
        return optionalUser
                .map(userEntity -> userEntity
                        .getCreatedActivityList()
                        .stream()
                        .map(ActivityBaseVO::new)
                        .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

}
