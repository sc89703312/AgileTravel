package nju.agile.travel.model;

import lombok.Data;

/**
 * Created by echo on 2019/1/13.
 */
@Data
public class ShareInfoParam {

    int userId;
    String contents;
    int activityId;

    public ShareInfoParam(){}

    public ShareInfoParam(int userId, String contents, int activityId){
        this.userId = userId;
        this.contents = contents;
        this.activityId = activityId;
    }

}
