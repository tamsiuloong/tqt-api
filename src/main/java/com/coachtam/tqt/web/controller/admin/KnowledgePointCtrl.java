package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.KnowledgePoint;
import com.coachtam.tqt.service.KnowledgePointService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @Description:	知识点
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-28 13:55:31
 */
@RequestMapping("/api/knowledgePoint")
@RestController
@RolesAllowed({"老师","管理员","测试","班主任"})
public class KnowledgePointCtrl {

    @Autowired
    private KnowledgePointService knowledgePointService;

    @GetMapping
    public ResultVM<Page> list(Integer pageNo, Integer pageSize, String keyWord, String courseId)
    {
        Page result = knowledgePointService.page(pageNo,pageSize,keyWord,courseId);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<KnowledgePoint> list(@PathVariable("id") Integer id)
    {
        KnowledgePoint knowledgePoint = knowledgePointService.findById(id);

        return ResultVM.success(knowledgePoint);
    }


    @GetMapping("/all/{courseId}")
    public ResultVM<List<KnowledgePoint>> getAll(@PathVariable("courseId") String courseId)
    {
        List<KnowledgePoint> result = knowledgePointService.findAll(courseId);
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<Integer> delete(@RequestBody Integer[] ids)
    {
        knowledgePointService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<Integer> update(@RequestBody KnowledgePoint knowledgePoint)
    {
        knowledgePointService.update(knowledgePoint);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<Integer> add(@RequestBody KnowledgePoint knowledgePoint)
    {
        knowledgePointService.save(knowledgePoint);
        return ResultVM.success(null);
    }
}
