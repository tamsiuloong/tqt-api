package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.InterviewQuestion;
import com.coachtam.tqt.service.InterviewQuestionService;
import com.coachtam.tqt.qo.BatchInterviewQuestionQO;
import com.coachtam.tqt.qo.InterviewQuestionQO;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
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
    public ResultVM<Page> list(Integer pageNo, Integer pageSize, Integer interviewId)
    {
        Page result = interviewQuestionService.page(pageNo,pageSize,interviewId);
        return ResultVM.success(result);
    }


    @PostMapping("/search")
    public ResultVM<Page> list(Integer pageNo, Integer pageSize, @RequestBody InterviewQuestionQO searchForm)
    {
        Page result = interviewQuestionService.page(pageNo,pageSize,searchForm);
        return ResultVM.success(result);
    }

    @GetMapping("/{id}")
    public ResultVM<InterviewQuestion> list(@PathVariable("id") Integer id)
    {
        InterviewQuestion interviewQuestion = interviewQuestionService.findById(id);

        return ResultVM.success(interviewQuestion);
    }


    @GetMapping("/all")
    public ResultVM<List<InterviewQuestion>> getAll()
    {
        List<InterviewQuestion> result = interviewQuestionService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<Integer> delete(@RequestBody Integer[] ids)
    {
        interviewQuestionService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<Integer> update(@RequestBody InterviewQuestion interviewQuestion)
    {
        interviewQuestionService.update(interviewQuestion);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<Integer> add(@RequestBody InterviewQuestion interviewQuestion)
    {
        interviewQuestionService.save(interviewQuestion);
        return ResultVM.success(null);
    }


    @PostMapping("/batchImport")
    public ResultVM<Integer> batchImport(@RequestBody BatchInterviewQuestionQO form)
    {
        try {
            interviewQuestionService.batchImport(form);
        }catch (Exception e)
        {
            ResultVM.fail(e.getMessage());
        }

        return ResultVM.success(null);
    }

    @PostMapping("/wordToTable")
    public ResultVM<List<InterviewQuestion>> wordToTable(@RequestBody BatchInterviewQuestionQO form)
    {
        List<InterviewQuestion> result = interviewQuestionService.parseWord(form.getText());
        return ResultVM.success(result);
    }
}
