package nju.agile.travel.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by ShirokoSama on 2019/1/10.
 */
@Data
public class ActivityInfoParam {

    String name;

    String description;

    String location;

    Date startTime;

    Date endTime;

    String bannerUrl;

    int creatorID;

    public ActivityInfoParam(
            String name,
            String description,
            String location,
            Date startTime,
            Date endTime,
            String bannerUrl,
            int creatorID) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bannerUrl = bannerUrl;
        this.creatorID = creatorID;
    }

}
