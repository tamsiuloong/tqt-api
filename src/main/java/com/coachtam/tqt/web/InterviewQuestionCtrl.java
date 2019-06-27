package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.InterviewQuestion;
import com.coachtam.tqt.service.InterviewQuestionService;
import com.coachtam.tqt.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:49:48
 */
@RequestMapping("/api/interviewQuestion")
@RestController
public class InterviewQuestionCtrl {

    @Autowired
    private InterviewQuestionService interviewQuestionService;

    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = interviewQuestionService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<InterviewQuestion> list(@PathVariable("id") String id)
    {
        InterviewQuestion interviewQuestion = interviewQuestionService.findById(id);

        return ResultVO.success(interviewQuestion);
    }


    @GetMapping("/all")
    public ResultVO<List<InterviewQuestion>> getAll()
    {
        List<InterviewQuestion> result = interviewQuestionService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        interviewQuestionService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody InterviewQuestion interviewQuestion)
    {
        interviewQuestionService.update(interviewQuestion);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody InterviewQuestion interviewQuestion)
    {
        interviewQuestionService.save(interviewQuestion);
        return ResultVO.success(null);
    }
}
