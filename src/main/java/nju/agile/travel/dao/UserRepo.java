package nju.agile.travel.dao;

import nju.agile.travel.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by echo on 2019/1/8.
 */
public interface UserRepo extends JpaRepository<UserEntity, Integer> {

    UserEntity findByMail(String mail);

}
