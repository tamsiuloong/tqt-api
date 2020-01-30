package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.Module;
import com.coachtam.tqt.entity.Role;
import com.coachtam.tqt.service.ModuleService;
import com.coachtam.tqt.service.RoleService;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import com.coachtam.tqt.viewmodel.admin.ZtreeVM;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description:	模块
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-1 13:54:19
 */
@RequestMapping("/api/module")
@RestController
public class ModuleCtrl {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private RoleService roleService;
    @GetMapping
    public ResultVM<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = moduleService.page(pageNo,pageSize);
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<Module> list(@PathVariable("id") String id)
    {
        Module module = moduleService.findById(id);

        return ResultVM.success(module);
    }


    @GetMapping("/all")
    public ResultVM<List<Module>> getAll()
    {
        List<Module> result = moduleService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<String> delete(@RequestBody String[] ids)
    {
        moduleService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody Module module)
    {
        moduleService.update(module);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<String> add(@RequestBody Module module)
    {
        moduleService.save(module);
        return ResultVM.success(null);
    }
    @GetMapping("/getParent/{layerNum}")
    public ResultVM<List<Module>> getParentList(@PathVariable("layerNum") Long layerNum)
    {
        List<Module> data = moduleService.getListByLayerNum(layerNum);
        return ResultVM.success(data);
    }
    @GetMapping("/all/{roleId}")
    public ResultVM<List<ZtreeVM>> all(@PathVariable("roleId") String roleId)
    {
        /*获取所有的模块*/
        List<Module> moduleList = moduleService.findAll();
        /*获取当前角色拥有的模块*/
        Role role = roleService.findById(roleId);
        Set<Module> moduleSet = role.getModuleSet();
        /*List<Module>  -->  List<ZtreeVM>*/
        List<ZtreeVM> ztreeVMList = new ArrayList<>(moduleList.size());
        //循环遍历时，判断该角色是否拥有该模块-->checked=true
        moduleList.forEach(module -> {
            //module --> ztreeVM
            ZtreeVM ztreeVM = new ZtreeVM();
            ztreeVM.setId(module.getId());
            ztreeVM.setName(module.getName());
            ztreeVM.setPId(module.getParentId());

            ztreeVM.setChecked(moduleSet.contains(module));
            //默认展开
            ztreeVM.setOpen(true);

            ztreeVMList.add(ztreeVM);

        });

        return ResultVM.success(ztreeVMList);
    }
}
