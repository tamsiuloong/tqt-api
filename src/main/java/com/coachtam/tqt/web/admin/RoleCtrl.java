package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.Role;
import com.coachtam.tqt.service.RoleService;
import com.coachtam.tqt.vo.admin.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	模块
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-1 16:07:19
 */
@RequestMapping("/api/role")
@RestController
public class RoleCtrl {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResultVO<Page> list(@RequestParam(defaultValue = "1")Integer pageNo, @RequestParam(defaultValue = "20") Integer pageSize)
    {
        Page result = roleService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Role> list(@PathVariable("id") String id)
    {
        Role role = roleService.findById(id);

        return ResultVO.success(role);
    }


    @GetMapping("/all")
    public ResultVO<List<Role>> getAll()
    {
        List<Role> result = roleService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        roleService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Role role)
    {
        roleService.update(role);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Role role)
    {
        roleService.save(role);
        return ResultVO.success(null);
    }
}
