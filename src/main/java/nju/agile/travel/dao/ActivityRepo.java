package nju.agile.travel.dao;

import nju.agile.travel.entity.ActivityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by echo on 2019/1/8.
 */
public interface ActivityRepo extends JpaRepository<ActivityEntity, Integer> {

    Page<ActivityEntity> findByCheck(int check, Pageable pageable);

}
