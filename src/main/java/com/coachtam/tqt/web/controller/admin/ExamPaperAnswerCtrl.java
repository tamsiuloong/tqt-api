package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.Course;
import com.coachtam.tqt.entity.ExamPaperAnswer;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.service.CourseService;
import com.coachtam.tqt.service.ExamPaperAnswerService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.utils.DateTimeUtil;
import com.coachtam.tqt.utils.ExamUtil;
import com.coachtam.tqt.utils.ModelMapperSingle;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import com.coachtam.tqt.viewmodel.student.base.RestResponse;
import com.coachtam.tqt.viewmodel.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.coachtam.tqt.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:	试卷答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:30:18
 */
@RequestMapping("/api/examPaperAnswer")
@RestController

public class ExamPaperAnswerCtrl {

    @Autowired
    private ExamPaperAnswerService examPaperAnswerService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();

    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<Map<String, Object>> pageList(@RequestBody @Valid ExamPaperAnswerPageVM model) {

        Page<ExamPaperAnswer> page = examPaperAnswerService.findByExamPaperId(model);

        List<ExamPaperAnswerPageResponseVM> collect = page.getContent().stream().map(e -> {
            ExamPaperAnswerPageResponseVM vm = modelMapper.map(e, ExamPaperAnswerPageResponseVM.class);
            Course course = courseService.findById(e.getCourseId());
            User user = userService.findById(e.getUserId());
            vm.setUserName(user.getUserInfo().getName());
            vm.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            vm.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            vm.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            vm.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            vm.setCourseName(course.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        }).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("list", collect);
        map.put("total", page.getTotalElements());
        map.put("pageNum", page.getNumber()+1);
        map.put("size", page.getSize());


        return RestResponse.ok(map);
    }

    @GetMapping
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = examPaperAnswerService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<ExamPaperAnswer> list(@PathVariable("id") Integer id)
    {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.findById(id);

        return ResultVM.success(examPaperAnswer);
    }


    @GetMapping("/all")
    public ResultVM<List<ExamPaperAnswer>> getAll()
    {
        List<ExamPaperAnswer> result = examPaperAnswerService.findAll();
        return ResultVM.success(result);
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @DeleteMapping
    public ResultVM<Integer> delete(@RequestBody Integer[] ids)
    {
        examPaperAnswerService.deleteByIds(ids);
        return ResultVM.success(null);
    }

    @RolesAllowed({"老师","管理员","测试","班主任"})
    @PutMapping
    public ResultVM<Integer> update(@RequestBody ExamPaperAnswer examPaperAnswer)
    {
        examPaperAnswerService.update(examPaperAnswer);
        return ResultVM.success(null);
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @PostMapping
    public ResultVM<Integer> add(@RequestBody ExamPaperAnswer examPaperAnswer)
    {
        examPaperAnswerService.save(examPaperAnswer);
        return ResultVM.success(null);
    }
}
