package nju.agile.travel.controller;

import nju.agile.travel.service.MessageService;
import nju.agile.travel.vo.NormalMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ShirokoSama on 2019/3/5.
 */
@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/user/{userID}/messages")
    public List<NormalMessageVO> getUserMessages(@PathVariable Integer userID) {
        return messageService.queryUserMessages(userID);
    }

}
