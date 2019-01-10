package nju.agile.travel.service;

import nju.agile.travel.dao.ActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ShirokoSama on 2019/1/10.
 */
@Component
public class ActivityMemberService {

    @Autowired
    ActivityRepo activityRepo;



}
