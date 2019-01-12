package nju.agile.travel.dao;

import nju.agile.travel.entity.ActivityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by echo on 2019/1/8.
 */
public interface ActivityRepo extends JpaRepository<ActivityEntity, Integer> {

    Page<ActivityEntity> findByCheckAndAccess(int check, int access, Pageable pageable);

    Optional<ActivityEntity> findByIdAndCheck(int id, int check);

    Optional<ActivityEntity> findByIdAndCheckAndAccess(int id, int check, int access);

}
