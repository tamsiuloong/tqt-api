package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.QuestionItems;
import com.coachtam.tqt.respository.QuestionItemsDao;
import com.coachtam.tqt.service.QuestionItemsService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	试卷试题选项
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:31:19
 */
@Transactional
@Service
public class QuestionItemsServiceImpl implements QuestionItemsService {
    @Autowired
    private QuestionItemsDao questionItemsDao;


    @Override
    public Page<QuestionItems> page(Integer pageNo,Integer pageSize)
    {
        return  questionItemsDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<QuestionItems> findAll() {
        return questionItemsDao.findAll();
    }

    @Override
    public void save(QuestionItems bean) {
        questionItemsDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            questionItemsDao.deleteById(id);
        }

    }

    @Override
    public void update(QuestionItems bean) {
        questionItemsDao.saveAndFlush(bean);
    }

    @Override
    public QuestionItems findById(Integer id) {
        return questionItemsDao.findById(id).get();
    }
}
