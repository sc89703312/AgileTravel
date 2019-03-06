package nju.agile.travel.vo;

import lombok.Data;
import nju.agile.travel.entity.PostEntity;
import nju.agile.travel.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by echo on 2019/1/11.
 */
@Data
public class PostBaseVO {

    int id;

    String postedTime;

    String content;

    List<String> imageUrls;

    UserBaseVO author;

    public PostBaseVO(final PostEntity postEntity){
        this.id = postEntity.getId();
        this.postedTime = DateUtil.dateToString(postEntity.getTimestamps());
        this.content = postEntity.getContent();
        this.imageUrls = (postEntity.getImageUrls() == null
                || postEntity.getImageUrls().isEmpty()) ?
                new ArrayList<>() :
                Arrays.asList(postEntity.getImageUrls().split(" "));
        this.author = new UserBaseVO(postEntity.getAuthor());
    }

    @Override
    public String toString(){
        return String.format(
                "%nPostBaseVO[id=%d, postedTime='%s', content='%s', authorId=%d, imageUrls='%s']",
                id, postedTime, content, author.getId(), String.join(" ", imageUrls)
        );
    }

}
