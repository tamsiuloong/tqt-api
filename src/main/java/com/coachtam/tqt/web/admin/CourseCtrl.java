package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.Course;
import com.coachtam.tqt.service.CourseService;
import com.coachtam.tqt.vo.admin.ResultVO;
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
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = courseService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Course> list(@PathVariable("id") String id)
    {
        Course course = courseService.findById(id);

        return ResultVO.success(course);
    }


    @GetMapping("/all")
    public ResultVO<List<Course>> getAll()
    {
        List<Course> result = courseService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        courseService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Course course)
    {
        courseService.update(course);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Course course)
    {
        courseService.save(course);
        return ResultVO.success(null);
    }
}
