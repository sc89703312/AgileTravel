package nju.agile.travel.dao;

import nju.agile.travel.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by echo on 2019/1/9.
 */
public interface PostRepo extends JpaRepository<PostEntity, Integer> {

    @Query("select p from t_post p where p.belongedActivity.id = ?1")
    List<PostEntity> findAllByActivityId(Integer t_activity_id);

}
