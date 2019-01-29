package com.coachtam.tqt;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.respository.FeedbackDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeachingQualityTrackingApplicationTests {

    @Autowired
    private FeedbackDao dao;
    @Test
    public void contextLoads() {
        Iterable<Feedback> all = dao.findAll();
    }

}

