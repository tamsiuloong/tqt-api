package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.respository.FeedbackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-28 17:00
 * @Description:
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;
    @Override
    @Transactional(rollbackFor = Exception.class )
    public void save(Feedback feedback) {
        feedbackDao.save(feedback);
    }
}
