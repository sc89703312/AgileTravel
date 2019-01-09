package nju.agile.travel.dao;

import nju.agile.travel.entity.ShareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by echo on 2019/1/9.
 */
public interface ShareRepo extends JpaRepository<ShareEntity, Integer> {
}
