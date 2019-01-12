package nju.agile.travel.dao;

import nju.agile.travel.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by echo on 2019/1/8.
 */
public interface UserRepo extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByMail(String mail);

    Optional<UserEntity> findByMailAndCheck(String mail, int check);

    Optional<UserEntity> findByMailAndPassword(String mail, String password);

    Optional<UserEntity> findByIdAndCheck(Integer id, Integer check);

}
