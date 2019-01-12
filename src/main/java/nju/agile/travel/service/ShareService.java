package nju.agile.travel.service;

import nju.agile.travel.dao.ShareRepo;
import nju.agile.travel.entity.ShareEntity;
import nju.agile.travel.vo.ShareBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by echo on 2019/1/12.
 */
@Component
public class ShareService {

    @Autowired
    ShareRepo shareRepo;

    public List<ShareBaseVO> queryShareList(Integer userId){
        return shareRepo
                .findAllByOrderByStarNumDesc()
                .stream()
                .map(entity -> new ShareBaseVO(entity, userId))
                .collect(Collectors.toList());
    }
}
