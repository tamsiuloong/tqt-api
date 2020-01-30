package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.Course;
import com.coachtam.tqt.service.CourseService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	课程
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 18:20:19
 */
@RequestMapping("/api/course")
@RestController
public class CourseCtrl {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = courseService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<Course> list(@PathVariable("id") String id)
    {
        Course course = courseService.findById(id);

        return ResultVM.success(course);
    }


    @GetMapping("/all")
    public ResultVM<List<Course>> getAll()
    {
        List<Course> result = courseService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<String> delete(@RequestBody String[] ids)
    {
        courseService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody Course course)
    {
        courseService.update(course);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<String> add(@RequestBody Course course)
    {
        courseService.save(course);
        return ResultVM.success(null);
    }
}
