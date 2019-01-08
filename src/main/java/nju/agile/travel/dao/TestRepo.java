package nju.agile.travel.dao;

import nju.agile.travel.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by echo on 2019/1/8.
 */
public interface TestRepo extends JpaRepository<TestEntity, Integer> {

}
