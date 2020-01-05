package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.QuestionItems;
import com.coachtam.tqt.service.QuestionItemsService;
import com.coachtam.tqt.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	试卷试题选项
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:31:19
 */
@RequestMapping("/api/questionItems")
@RestController
public class QuestionItemsCtrl {

    @Autowired
    private QuestionItemsService questionItemsService;

    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = questionItemsService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<QuestionItems> list(@PathVariable("id") Integer id)
    {
        QuestionItems questionItems = questionItemsService.findById(id);

        return ResultVO.success(questionItems);
    }


    @GetMapping("/all")
    public ResultVO<List<QuestionItems>> getAll()
    {
        List<QuestionItems> result = questionItemsService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<Integer> delete(@RequestBody Integer[] ids)
    {
        questionItemsService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<Integer> update(@RequestBody QuestionItems questionItems)
    {
        questionItemsService.update(questionItems);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<Integer> add(@RequestBody QuestionItems questionItems)
    {
        questionItemsService.save(questionItems);
        return ResultVO.success(null);
    }
}
