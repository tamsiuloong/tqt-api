package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.Module;
import com.coachtam.tqt.entity.Role;
import com.coachtam.tqt.service.ModuleService;
import com.coachtam.tqt.service.RoleService;
import com.coachtam.tqt.vo.admin.ResultVO;
import com.coachtam.tqt.vo.admin.ZtreeVO;
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
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = moduleService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Module> list(@PathVariable("id") String id)
    {
        Module module = moduleService.findById(id);

        return ResultVO.success(module);
    }


    @GetMapping("/all")
    public ResultVO<List<Module>> getAll()
    {
        List<Module> result = moduleService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        moduleService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Module module)
    {
        moduleService.update(module);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Module module)
    {
        moduleService.save(module);
        return ResultVO.success(null);
    }
    @GetMapping("/getParent/{layerNum}")
    public ResultVO<List<Module>> getParentList(@PathVariable("layerNum") Long layerNum)
    {
        List<Module> data = moduleService.getListByLayerNum(layerNum);
        return ResultVO.success(data);
    }
    @GetMapping("/all/{roleId}")
    public ResultVO<List<ZtreeVO>> all(@PathVariable("roleId") String roleId)
    {
        /*获取所有的模块*/
        List<Module> moduleList = moduleService.findAll();
        /*获取当前角色拥有的模块*/
        Role role = roleService.findById(roleId);
        Set<Module> moduleSet = role.getModuleSet();
        /*List<Module>  -->  List<ZtreeVO>*/
        List<ZtreeVO> ztreeVOList = new ArrayList<>(moduleList.size());
        //循环遍历时，判断该角色是否拥有该模块-->checked=true
        moduleList.forEach(module -> {
            //module --> ztreeVO
            ZtreeVO ztreeVO = new ZtreeVO();
            ztreeVO.setId(module.getId());
            ztreeVO.setName(module.getName());
            ztreeVO.setPId(module.getParentId());

            ztreeVO.setChecked(moduleSet.contains(module));
            //默认展开
            ztreeVO.setOpen(true);

            ztreeVOList.add(ztreeVO);

        });

        return ResultVO.success(ztreeVOList);
    }
}
