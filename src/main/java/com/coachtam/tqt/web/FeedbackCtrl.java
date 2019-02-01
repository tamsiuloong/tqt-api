package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.service.FeedbackService;
import com.coachtam.tqt.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	学习反馈
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 17:14:52
 */
@RequestMapping("/api/feedback")
@RestController
public class FeedbackCtrl {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = feedbackService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Feedback> list(@PathVariable("id") String id)
    {
        Feedback feedback = feedbackService.findById(id);

        return ResultVO.success(feedback);
    }


    @GetMapping("/all")
    public ResultVO<List<Feedback>> getAll()
    {
        List<Feedback> result = feedbackService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        feedbackService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Feedback feedback)
    {
        feedbackService.update(feedback);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Feedback feedback)
    {
        feedbackService.save(feedback);
        return ResultVO.success(null);
    }
}
