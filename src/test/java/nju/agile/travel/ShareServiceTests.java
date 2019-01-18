package nju.agile.travel;

import nju.agile.travel.dao.ShareRepo;
import nju.agile.travel.model.ShareInfoParam;
import nju.agile.travel.service.ShareService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by echo on 2019/1/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShareServiceTests {

    @Autowired
    ShareRepo shareRepo;

    @Autowired
    ShareService shareService;

    @Test
    public void testShareRepo(){
        System.out.println(shareRepo.findAllByOrderByStarNumAsc());
        System.out.println(shareRepo.findAllByOrderByStarNumDesc());

        System.out.println(shareRepo.findAllByOrderByTimestampsAsc());
        System.out.println(shareRepo.findAllByOrderByTimestampsDesc());
    }

    @Test
    public void testQueryAll(){
        System.out.println(shareService.queryShareList(1));
        System.out.println(shareService.queryShareList(2));
        System.out.println(shareService.queryShareList(100));
    }

    @Test
    public void testShare(){
        ShareInfoParam param = new ShareInfoParam(1, "hehe", 1);
        System.out.println(shareService.share(param));
    }

    @Test(expected = RuntimeException.class)
    public void testShareUserInvalid(){
        ShareInfoParam param = new ShareInfoParam(1000, "hehe", 1);
        System.out.println(shareService.share(param));
    }

    @Test(expected = RuntimeException.class)
    public void testShareActivityInvalid(){
        ShareInfoParam param = new ShareInfoParam(1, "hehe", 1000);
        System.out.println(shareService.share(param));
    }

    @Test(expected = RuntimeException.class)
    public void testShareUserNotJoined(){
        ShareInfoParam param = new ShareInfoParam(8, "hehe", 24);
        System.out.println(shareService.share(param));
    }

    @Test
    public void testShareStar(){
        shareService.star(1,2); //works
        shareService.star(1,2); //not works
    }

    @Test(expected = RuntimeException.class)
    public void testShareStarInvalidShare(){
        shareService.star(2, 100);
    }

    @Test
    public void testListStr(){
        String test = "";
        List<String> list = new ArrayList<>(Arrays.asList(test.split(" ")));
        System.out.println(list);
        list.add("123");
        System.out.println(list);
    }
}
