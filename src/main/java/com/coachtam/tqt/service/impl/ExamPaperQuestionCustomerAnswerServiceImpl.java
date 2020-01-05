package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.ExamPaperQuestionCustomerAnswer;
import com.coachtam.tqt.respository.ExamPaperQuestionCustomerAnswerDao;
import com.coachtam.tqt.service.ExamPaperQuestionCustomerAnswerService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	学生考试答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:29:43
 */
@Transactional
@Service
public class ExamPaperQuestionCustomerAnswerServiceImpl implements ExamPaperQuestionCustomerAnswerService {
    @Autowired
    private ExamPaperQuestionCustomerAnswerDao examPaperQuestionCustomerAnswerDao;


    @Override
    public Page<ExamPaperQuestionCustomerAnswer> page(Integer pageNo,Integer pageSize)
    {
        return  examPaperQuestionCustomerAnswerDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<ExamPaperQuestionCustomerAnswer> findAll() {
        return examPaperQuestionCustomerAnswerDao.findAll();
    }

    @Override
    public void save(ExamPaperQuestionCustomerAnswer bean) {
        examPaperQuestionCustomerAnswerDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            examPaperQuestionCustomerAnswerDao.deleteById(id);
        }

    }

    @Override
    public void update(ExamPaperQuestionCustomerAnswer bean) {
        examPaperQuestionCustomerAnswerDao.saveAndFlush(bean);
    }

    @Override
    public ExamPaperQuestionCustomerAnswer findById(Integer id) {
        return examPaperQuestionCustomerAnswerDao.findById(id).get();
    }
}
