package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.ExamPaperAnswer;
import com.coachtam.tqt.service.ExamPaperAnswerService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping
    public ResultVM<Integer> delete(@RequestBody Integer[] ids)
    {
        examPaperAnswerService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<Integer> update(@RequestBody ExamPaperAnswer examPaperAnswer)
    {
        examPaperAnswerService.update(examPaperAnswer);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<Integer> add(@RequestBody ExamPaperAnswer examPaperAnswer)
    {
        examPaperAnswerService.save(examPaperAnswer);
        return ResultVM.success(null);
    }
}
