package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.ExamPaperContent;
import com.coachtam.tqt.respository.ExamPaperContentDao;
import com.coachtam.tqt.service.ExamPaperContentService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	试卷内容
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-5 16:39:37
 */
@Transactional
@Service
public class ExamPaperContentServiceImpl implements ExamPaperContentService {
    @Autowired
    private ExamPaperContentDao examPaperContentDao;


    @Override
    public Page<ExamPaperContent> page(Integer pageNo,Integer pageSize)
    {
        return  examPaperContentDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<ExamPaperContent> findAll() {
        return examPaperContentDao.findAll();
    }

    @Override
    public void save(ExamPaperContent bean) {
        examPaperContentDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            examPaperContentDao.deleteById(id);
        }

    }

    @Override
    public void update(ExamPaperContent bean) {
        examPaperContentDao.saveAndFlush(bean);
    }

    @Override
    public ExamPaperContent findById(Integer id) {
        return examPaperContentDao.findById(id).get();
    }
}
