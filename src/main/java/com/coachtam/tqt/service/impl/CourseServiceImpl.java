package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.Course;
import com.coachtam.tqt.respository.CourseDao;
import com.coachtam.tqt.service.CourseService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	课程
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 18:14:43
 */
@Transactional
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;


    @Override
    public Page<Course> page(Integer pageNo,Integer pageSize)
    {
        return  courseDao.findAll(PageUtils.of(pageNo,pageSize,Sort.by(Sort.Direction.ASC,"orderNo")));
    }



    @Override
    public List<Course> findAll() {
        return courseDao.findAll(new Sort(Sort.Direction.ASC,"orderNo"));
    }

    @Override
    public void save(Course model) {
        courseDao.save(model);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            courseDao.deleteById(id);
        }

    }

    @Override
    public void update(Course model) {
        courseDao.saveAndFlush(model);
    }

    @Override
    public Course findById(String id) {
        return courseDao.findById(id).get();
    }
}
