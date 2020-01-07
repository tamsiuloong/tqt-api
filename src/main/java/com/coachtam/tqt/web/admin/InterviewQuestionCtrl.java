package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.InterviewQuestion;
import com.coachtam.tqt.service.InterviewQuestionService;
import com.coachtam.tqt.to.BatchInterviewQuestionForm;
import com.coachtam.tqt.to.InterviewQuestionForm;
import com.coachtam.tqt.vo.admin.ResultVO;
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
    public ResultVO<Page> list(Integer pageNo, Integer pageSize,Integer interviewId)
    {
        Page result = interviewQuestionService.page(pageNo,pageSize,interviewId);
        return ResultVO.success(result);
    }


    @PostMapping("/search")
    public ResultVO<Page> list(Integer pageNo, Integer pageSize,@RequestBody InterviewQuestionForm searchForm)
    {
        Page result = interviewQuestionService.page(pageNo,pageSize,searchForm);
        return ResultVO.success(result);
    }

    @GetMapping("/{id}")
    public ResultVO<InterviewQuestion> list(@PathVariable("id") Integer id)
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
    public ResultVO<Integer> delete(@RequestBody Integer[] ids)
    {
        interviewQuestionService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<Integer> update(@RequestBody InterviewQuestion interviewQuestion)
    {
        interviewQuestionService.update(interviewQuestion);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<Integer> add(@RequestBody InterviewQuestion interviewQuestion)
    {
        interviewQuestionService.save(interviewQuestion);
        return ResultVO.success(null);
    }


    @PostMapping("/batchImport")
    public ResultVO<Integer> batchImport(@RequestBody BatchInterviewQuestionForm form)
    {
        try {
            interviewQuestionService.batchImport(form);
        }catch (Exception e)
        {
            ResultVO.fail(e.getMessage());
        }

        return ResultVO.success(null);
    }

    @PostMapping("/wordToTable")
    public ResultVO<List<InterviewQuestion>> wordToTable(@RequestBody BatchInterviewQuestionForm form)
    {
        List<InterviewQuestion> result = interviewQuestionService.parseWord(form.getText());
        return ResultVO.success(result);
    }
}
