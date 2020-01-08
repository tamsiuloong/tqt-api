package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.QuestionItems;
import com.coachtam.tqt.service.QuestionItemsService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
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
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = questionItemsService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<QuestionItems> list(@PathVariable("id") Integer id)
    {
        QuestionItems questionItems = questionItemsService.findById(id);

        return ResultVM.success(questionItems);
    }


    @GetMapping("/all")
    public ResultVM<List<QuestionItems>> getAll()
    {
        List<QuestionItems> result = questionItemsService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<Integer> delete(@RequestBody Integer[] ids)
    {
        questionItemsService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<Integer> update(@RequestBody QuestionItems questionItems)
    {
        questionItemsService.update(questionItems);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<Integer> add(@RequestBody QuestionItems questionItems)
    {
        questionItemsService.save(questionItems);
        return ResultVM.success(null);
    }
}
