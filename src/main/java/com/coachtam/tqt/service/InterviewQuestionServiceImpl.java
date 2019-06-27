package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.InterviewQuestion;
import com.coachtam.tqt.respository.InterviewQuestionDao;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:49:48
 */
@Transactional
@Service
public class InterviewQuestionServiceImpl implements InterviewQuestionService {
    @Autowired
    private InterviewQuestionDao interviewQuestionDao;


    @Override
    public Page<InterviewQuestion> page(Integer pageNo,Integer pageSize)
    {
        return  interviewQuestionDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<InterviewQuestion> findAll() {
        return interviewQuestionDao.findAll();
    }

    @Override
    public void save(InterviewQuestion bean) {
        interviewQuestionDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            interviewQuestionDao.deleteById(id);
        }

    }

    @Override
    public void update(InterviewQuestion bean) {
        interviewQuestionDao.saveAndFlush(bean);
    }

    @Override
    public InterviewQuestion findById(String id) {
        return interviewQuestionDao.findById(id).get();
    }
}
