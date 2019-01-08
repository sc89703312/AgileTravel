package nju.agile.travel.service;

import nju.agile.travel.dao.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by echo on 2019/1/8.
 */
@Component
public class TestService {

    @Autowired
    TestRepo testRepo;

    public int calculateLength(){
        return testRepo.findAll().size();
    }
}
