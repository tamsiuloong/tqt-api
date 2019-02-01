package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.respository.FeedbackDao;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:	学习反馈
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 17:28:52
 */
@Transactional
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;


    @Override
    public Page<Feedback> page(Integer pageNo,Integer pageSize)
    {
        Sort sort = new Sort(Sort.Direction.DESC, "backTime");
        return  feedbackDao.findAll(PageUtils.of(pageNo,pageSize,sort));
    }



    @Override
    public List<Feedback> findAll() {
        return feedbackDao.findAll();
    }

    @Override
    public void save(Feedback bean) {
        bean.setBackTime(new Date());
        feedbackDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            feedbackDao.deleteById(id);
        }

    }

    @Override
    public void update(Feedback bean) {
        feedbackDao.saveAndFlush(bean);
    }

    @Override
    public Feedback findById(String id) {
        return feedbackDao.findById(id).get();
    }

    @Override
    public List<Object[]> absorption() {
        return feedbackDao.findAbsorption();
    }
}
