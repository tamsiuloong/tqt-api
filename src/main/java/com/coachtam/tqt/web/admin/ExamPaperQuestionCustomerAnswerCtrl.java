package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.ExamPaperQuestionCustomerAnswer;
import com.coachtam.tqt.service.ExamPaperQuestionCustomerAnswerService;
import com.coachtam.tqt.vo.admin.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	学生考试答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:29:43
 */
@RequestMapping("/api/examPaperQuestionCustomerAnswer")
@RestController
public class ExamPaperQuestionCustomerAnswerCtrl {

    @Autowired
    private ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;

    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = examPaperQuestionCustomerAnswerService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<ExamPaperQuestionCustomerAnswer> list(@PathVariable("id") Integer id)
    {
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = examPaperQuestionCustomerAnswerService.findById(id);

        return ResultVO.success(examPaperQuestionCustomerAnswer);
    }


    @GetMapping("/all")
    public ResultVO<List<ExamPaperQuestionCustomerAnswer>> getAll()
    {
        List<ExamPaperQuestionCustomerAnswer> result = examPaperQuestionCustomerAnswerService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<Integer> delete(@RequestBody Integer[] ids)
    {
        examPaperQuestionCustomerAnswerService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<Integer> update(@RequestBody ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer)
    {
        examPaperQuestionCustomerAnswerService.update(examPaperQuestionCustomerAnswer);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<Integer> add(@RequestBody ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer)
    {
        examPaperQuestionCustomerAnswerService.save(examPaperQuestionCustomerAnswer);
        return ResultVO.success(null);
    }
}
