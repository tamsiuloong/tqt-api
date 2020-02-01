package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.Role;
import com.coachtam.tqt.service.RoleService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @Description:	模块
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-1 16:07:19
 */
@RequestMapping("/api/role")
@RestController
@RolesAllowed({"老师","管理员","测试","班主任"})
public class RoleCtrl {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResultVM<Page> list(@RequestParam(defaultValue = "1")Integer pageNo, @RequestParam(defaultValue = "20") Integer pageSize)
    {
        Page result = roleService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<Role> list(@PathVariable("id") String id)
    {
        Role role = roleService.findById(id);

        return ResultVM.success(role);
    }


    @GetMapping("/all")
    public ResultVM<List<Role>> getAll()
    {
        List<Role> result = roleService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<String> delete(@RequestBody String[] ids)
    {
        roleService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody Role role)
    {
        roleService.update(role);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<String> add(@RequestBody Role role)
    {
        roleService.save(role);
        return ResultVM.success(null);
    }
}
