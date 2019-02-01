package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.respository.ClassesDao;
import com.coachtam.tqt.service.ClassesService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-31 16:56:55
 */
@Transactional
@Service
public class ClassesServiceImpl implements ClassesService {
    @Autowired
    private ClassesDao classesDao;


    @Override
    public Page<Classes> page(Integer pageNo,Integer pageSize)
    {
        return  classesDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<Classes> findAll() {
        return classesDao.findAll();
    }

    @Override
    public void save(Classes bean) {
        classesDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            classesDao.deleteById(id);
        }

    }

    @Override
    public void update(Classes bean) {
        classesDao.saveAndFlush(bean);
    }

    @Override
    public Classes findById(String id) {
        return classesDao.findById(id).get();
    }
}
