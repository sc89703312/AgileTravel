package nju.agile.travel.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by echo on 2019/1/11.
 */
@Data
public class PostInfoParam {

    int authorId;
    int activityId;
    String content;
    List<String> imageUrls;

    public PostInfoParam(){}

    public PostInfoParam(int authorId, int activityId, String content, List<String> imageUrls){
        this.authorId = authorId;
        this.activityId = activityId;
        this.content = content;
        this.imageUrls = imageUrls;

    }

    public String getImageUrls(){
        return String.join(" ", imageUrls);
    }

}
