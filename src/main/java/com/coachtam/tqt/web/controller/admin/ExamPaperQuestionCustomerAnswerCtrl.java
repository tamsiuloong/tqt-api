package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.ExamPaperQuestionCustomerAnswer;
import com.coachtam.tqt.service.ExamPaperQuestionCustomerAnswerService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @Description:	学生考试答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:29:43
 */
@RequestMapping("/api/examPaperQuestionCustomerAnswer")
@RestController
@RolesAllowed({"老师","管理员","测试","班主任"})
public class ExamPaperQuestionCustomerAnswerCtrl {

    @Autowired
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;

    @GetMapping
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = examPaperQuestionCustomerAnswerService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<ExamPaperQuestionCustomerAnswer> list(@PathVariable("id") Integer id)
    {
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = examPaperQuestionCustomerAnswerService.findById(id);

        return ResultVM.success(examPaperQuestionCustomerAnswer);
    }


    @GetMapping("/all")
    public ResultVM<List<ExamPaperQuestionCustomerAnswer>> getAll()
    {
        List<ExamPaperQuestionCustomerAnswer> result = examPaperQuestionCustomerAnswerService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<Integer> delete(@RequestBody Integer[] ids)
    {
        examPaperQuestionCustomerAnswerService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<Integer> update(@RequestBody ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer)
    {
        examPaperQuestionCustomerAnswerService.update(examPaperQuestionCustomerAnswer);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<Integer> add(@RequestBody ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer)
    {
        examPaperQuestionCustomerAnswerService.save(examPaperQuestionCustomerAnswer);
        return ResultVM.success(null);
    }
}
