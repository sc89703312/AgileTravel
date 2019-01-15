package nju.agile.travel.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by ShirokoSama on 2019/1/10.
 */
@Data
public class ActivityInfoParam {

    String name;

    String description;

    String location;

    String startTime;

    String endTime;

    List<String> imageUrls;

    boolean isPublic;

    public ActivityInfoParam(
            String name,
            String description,
            String location,
            String startTime,
            String endTime,
            List<String> imageUrls,
            boolean isPublic) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageUrls = imageUrls;
        this.isPublic = isPublic;
    }

    public String getImageUrls() {
        return String.join(" ", imageUrls);
    }

}
