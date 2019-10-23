package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.KnowledgePoint;
import com.coachtam.tqt.service.KnowledgePointService;
import com.coachtam.tqt.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	知识点
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-28 13:55:31
 */
@RequestMapping("/api/knowledgePoint")
@RestController
public class KnowledgePointCtrl {

    @Autowired
    private KnowledgePointService knowledgePointService;

    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize,String keyWord,String courseId)
    {
        Page result = knowledgePointService.page(pageNo,pageSize,keyWord,courseId);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<KnowledgePoint> list(@PathVariable("id") Integer id)
    {
        KnowledgePoint knowledgePoint = knowledgePointService.findById(id);

        return ResultVO.success(knowledgePoint);
    }


    @GetMapping("/all/{courseId}")
    public ResultVO<List<KnowledgePoint>> getAll(@PathVariable("courseId") String courseId)
    {
        List<KnowledgePoint> result = knowledgePointService.findAll(courseId);
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<Integer> delete(@RequestBody Integer[] ids)
    {
        knowledgePointService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<Integer> update(@RequestBody KnowledgePoint knowledgePoint)
    {
        knowledgePointService.update(knowledgePoint);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<Integer> add(@RequestBody KnowledgePoint knowledgePoint)
    {
        knowledgePointService.save(knowledgePoint);
        return ResultVO.success(null);
    }
}
