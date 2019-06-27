package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Interview;
import com.coachtam.tqt.service.InterviewService;
import com.coachtam.tqt.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:58:27
 */
@RequestMapping("/api/interview")
@RestController
public class InterviewCtrl {

    @Autowired
    private InterviewService interviewService;

    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = interviewService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Interview> list(@PathVariable("id") String id)
    {
        Interview interview = interviewService.findById(id);

        return ResultVO.success(interview);
    }


    @GetMapping("/all")
    public ResultVO<List<Interview>> getAll()
    {
        List<Interview> result = interviewService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        interviewService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Interview interview)
    {
        interviewService.update(interview);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Interview interview)
    {
        interviewService.save(interview);
        return ResultVO.success(null);
    }
}
