package nju.agile.travel.dao;

import nju.agile.travel.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by echo on 2019/1/9.
 */
public interface PostRepo extends JpaRepository<PostEntity, Integer> {
}
