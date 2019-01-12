package nju.agile.travel;

import nju.agile.travel.dao.ShareRepo;
import nju.agile.travel.service.ShareService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
