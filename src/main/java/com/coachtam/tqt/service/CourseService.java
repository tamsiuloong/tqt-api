package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Course;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	课程
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 18:14:43
 */
public interface CourseService {
    public Page<Course> page(Integer pageNo, Integer pageSize);

    List<Course> findAll();

    void save(Course model);

    Course findById(String id);

    void update(Course model);

    void deleteByIds(String[] id);
}
