package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.service.ClassesService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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

    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = classesService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<Classes> list(@PathVariable("id") String id)
    {
        Classes classes = classesService.findById(id);

        return ResultVM.success(classes);
    }


    @GetMapping("/all/{closed}")
    public ResultVM<List<Classes>> getAll(@PathVariable("closed")Boolean closed)
    {
        List<Classes> result = classesService.findAllByClosed(closed);
        return ResultVM.success(result);
    }

    @GetMapping("/all")
    public ResultVM<List<Classes>> getAll()
    {
        List<Classes> result = classesService.findAll();
        return ResultVM.success(result);
    }

    @RolesAllowed({"老师","管理员","测试","班主任"})
    @DeleteMapping
    public ResultVM<String> delete(@RequestBody String[] ids)
    {
        classesService.deleteByIds(ids);
        return ResultVM.success(null);
    }

    @RolesAllowed({"老师","管理员","测试","班主任"})
    @PutMapping
    public ResultVM<String> update(@RequestBody Classes classes)
    {
        classesService.update(classes);
        return ResultVM.success(null);
    }
    @RolesAllowed({"老师","管理员","测试","班主任"})
    @PostMapping
    public ResultVM<String> add(@RequestBody Classes classes)
    {
        classesService.save(classes);
        return ResultVM.success(null);
    }
}
