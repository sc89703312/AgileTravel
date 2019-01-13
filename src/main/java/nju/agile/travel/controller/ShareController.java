package nju.agile.travel.controller;

import nju.agile.travel.model.ShareInfoParam;
import nju.agile.travel.service.ShareService;
import nju.agile.travel.vo.ShareBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by echo on 2019/1/13.
 */
@RestController
public class ShareController {

    @Autowired
    ShareService shareService;

    @GetMapping("/user/{userId}/share")
    public List<ShareBaseVO> getUserShareList(@PathVariable Integer userId){
        return shareService.queryShareList(userId);
    }

    @PostMapping("/user/{userId}/share")
    public int userShare(@PathVariable Integer userId, @RequestBody @Valid ShareInfoParam param){
        param.setUserId(userId);
        return shareService.share(param);
    }

    @PostMapping("/user/{userId}/share/{shareId}/star")
    public void userStar(@PathVariable Integer userId, @PathVariable Integer shareId){
        shareService.star(userId, shareId);
    }

}
