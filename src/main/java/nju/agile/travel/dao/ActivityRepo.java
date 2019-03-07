package nju.agile.travel.dao;

import nju.agile.travel.entity.ActivityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by echo on 2019/1/8.
 */
public interface ActivityRepo extends JpaRepository<ActivityEntity, Integer> {

    Page<ActivityEntity> findByCheckAndAccess(int check, int access, Pageable pageable);

    Optional<ActivityEntity> findByIdAndCheck(int id, int check);

    Optional<ActivityEntity> findByIdAndCheckAndAccess(int id, int check, int access);

    @Query("select a from t_activity a where a.check = :check and a.access = :access and (a.name like concat('%',:pattern,'%') " +
            "or a.location like concat('%',:pattern,'%') or a.description like concat('%',:pattern,'%'))")
    List<ActivityEntity> findAllByPatternAndCheckAndAccess(@Param("pattern") String pattern,
                                                           @Param("check") int check,
                                                           @Param("access") int access);

}
