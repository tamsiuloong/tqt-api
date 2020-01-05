package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.ExamPaperAnswer;
import com.coachtam.tqt.service.ExamPaperAnswerService;
import com.coachtam.tqt.vo.ResultVO;
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
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = examPaperAnswerService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<ExamPaperAnswer> list(@PathVariable("id") Integer id)
    {
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.findById(id);

        return ResultVO.success(examPaperAnswer);
    }


    @GetMapping("/all")
    public ResultVO<List<ExamPaperAnswer>> getAll()
    {
        List<ExamPaperAnswer> result = examPaperAnswerService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<Integer> delete(@RequestBody Integer[] ids)
    {
        examPaperAnswerService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<Integer> update(@RequestBody ExamPaperAnswer examPaperAnswer)
    {
        examPaperAnswerService.update(examPaperAnswer);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<Integer> add(@RequestBody ExamPaperAnswer examPaperAnswer)
    {
        examPaperAnswerService.save(examPaperAnswer);
        return ResultVO.success(null);
    }
}