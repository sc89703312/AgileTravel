package nju.agile.travel.dao;


import nju.agile.travel.entity.RUserActivityEntity;
import nju.agile.travel.entity.RUserActivityID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by ShirokoSama on 2019/1/13.
 */
public interface RUserActivityRepo extends JpaRepository<RUserActivityEntity, RUserActivityID> {

    Optional<RUserActivityEntity> findByIdAndStatus(RUserActivityID id, int status);

}