package nju.agile.travel.dao;

import nju.agile.travel.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ShirokoSama on 2019/3/5.
 */
public interface MessageRepo extends JpaRepository<MessageEntity, Integer> {

}
