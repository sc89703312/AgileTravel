package nju.agile.travel.service;

import nju.agile.travel.dao.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ShirokoSama on 2019/3/5.
 */
@Component
public class MessageService {

    @Autowired
    MessageRepo messageRepo;

}
