package nju.agile.travel;

import nju.agile.travel.dao.ActivityRepo;
import nju.agile.travel.dao.PostRepo;
import nju.agile.travel.model.PostInfoParam;
import nju.agile.travel.service.PostService;
import nju.agile.travel.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created by echo on 2019/1/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepo postRepo;

    @Autowired
    ActivityRepo activityRepo;


    @Test
    public void testQuery(){
        System.out.println(postService.queryPost(1));
        System.out.println(postService.queryPost(2));
        System.out.println(postService.queryPost(100));
    }

    @Test
    public void testCreatePost(){
        PostInfoParam param = new PostInfoParam(1, 1, "中文测试", Arrays.asList("url1", "url2"));
        System.out.println(postService.post(param));
    }

    @Test(expected = RuntimeException.class)
    public void testCreatePostInvalidAuthor(){
        PostInfoParam param = new PostInfoParam(1000, 1, "post content", Arrays.asList("url1", "url2"));
        System.out.println(postService.post(param));
    }

    @Test(expected = RuntimeException.class)
    public void testCreatePostInvalidActivity(){
        PostInfoParam param = new PostInfoParam(2, 1000, "post content", Arrays.asList("url1", "url2"));
        System.out.println(postService.post(param));
    }

    @Test(expected = RuntimeException.class)
    public void testCreatePostNotJoined(){
        PostInfoParam param = new PostInfoParam(9, 24, "post content", Arrays.asList("url1", "url2"));
        System.out.println(postService.post(param));
    }

}
