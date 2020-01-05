package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.ExamPaperAnswer;
import com.coachtam.tqt.respository.ExamPaperAnswerDao;
import com.coachtam.tqt.service.ExamPaperAnswerService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	试卷答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:30:17
 */
@Transactional
@Service
public class ExamPaperAnswerServiceImpl implements ExamPaperAnswerService {
    @Autowired
    private ExamPaperAnswerDao examPaperAnswerDao;


    @Override
    public Page<ExamPaperAnswer> page(Integer pageNo,Integer pageSize)
    {
        return  examPaperAnswerDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<ExamPaperAnswer> findAll() {
        return examPaperAnswerDao.findAll();
    }

    @Override
    public void save(ExamPaperAnswer bean) {
        examPaperAnswerDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            examPaperAnswerDao.deleteById(id);
        }

    }

    @Override
    public void update(ExamPaperAnswer bean) {
        examPaperAnswerDao.saveAndFlush(bean);
    }

    @Override
    public ExamPaperAnswer findById(Integer id) {
        return examPaperAnswerDao.findById(id).get();
    }
}
