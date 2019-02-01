package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.service.ClassesService;
import com.coachtam.tqt.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-31 16:56:55
 */
@RequestMapping("/api/classes")
@RestController
public class ClassesCtrl {

    @Autowired
    private ClassesService classesService;

    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = classesService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Classes> list(@PathVariable("id") String id)
    {
        Classes classes = classesService.findById(id);

        return ResultVO.success(classes);
    }


    @GetMapping("/all")
    public ResultVO<List<Classes>> getAll()
    {
        List<Classes> result = classesService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        classesService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Classes classes)
    {
        classesService.update(classes);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Classes classes)
    {
        classesService.save(classes);
        return ResultVO.success(null);
    }
}
