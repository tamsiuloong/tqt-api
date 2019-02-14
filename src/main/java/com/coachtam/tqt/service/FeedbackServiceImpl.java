package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.respository.FeedbackDao;
import com.coachtam.tqt.to.FeedbackForm;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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



    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Feedback> page(Integer pageNo, Integer pageSize, String userId)
    {
        Sort sort = new Sort(Sort.Direction.DESC, "backTime");
        return  feedbackDao.findAllByUserId(userId,PageUtils.of(pageNo,pageSize,sort));
    }

    @Override
    public Page<Feedback> page(Integer pageNo, Integer pageSize, Specification<Feedback> specification) {
        Sort sort = new Sort(Sort.Direction.DESC, "backTime");
        return feedbackDao.findAll(specification,PageUtils.of(pageNo,pageSize,sort));
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
    public List<Object[]> absorption(FeedbackForm feedbackForm) {
        Map<String, Object> paras = new HashMap<>();

        StringBuilder sb = new StringBuilder("select f.absorption,count(f.absorption) from Feedback  f  join f.user u join f.course course  join u.classes c  where 1 = 1");

        if(feedbackForm.getClassId()!=null && !feedbackForm.getClassId().isEmpty())
        {
            sb.append(" and c.id = :classId");
            paras.put("classId",feedbackForm.getClassId());
        }

        if(feedbackForm.getCourseId()!=null && !feedbackForm.getCourseId().isEmpty())
        {
            sb.append(" and course.id = :courseId");
            paras.put("courseId",feedbackForm.getCourseId());
        }

        if(feedbackForm.getDayNum()!=null && !feedbackForm.getDayNum().isEmpty())
        {
            sb.append(" and f.dayNum = :dayNum");
            paras.put("dayNum",Integer.valueOf(feedbackForm.getDayNum()));
        }

        if(feedbackForm.getStuName()!=null && !feedbackForm.getStuName().isEmpty())
        {
            sb.append(" and u.userInfo.name = :stuName");
            paras.put("stuName",feedbackForm.getStuName());
        }

        sb.append(" group by f.absorption");

        Query query = entityManager.createQuery(sb.toString());

        paras.forEach((key,value)->{
            query.setParameter(key,value);
        });

        return query.getResultList();
    }
}
