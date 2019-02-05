package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Leave;
import com.coachtam.tqt.service.LeaveService;
import com.coachtam.tqt.vo.ResultVO;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:	请假
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-5 22:48:29
 */
@RequestMapping("/api/leave")
@RestController
public class LeaveCtrl {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("/deploy")
    public ResultVO<Page> deploy()
    {
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("baoxiao.bpmn").deploy();

        //通过RepositoryService -->查询流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        System.out.println("流程key:"+processDefinition.getKey());
        System.out.println("流程ID:"+processDefinition.getId());
        return ResultVO.success(null);
    }
    @GetMapping
    public ResultVO<Page> list(Integer pageNo, Integer pageSize)
    {
        Page result = leaveService.page(pageNo,pageSize);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Leave> list(@PathVariable("id") String id)
    {
        Leave leave = leaveService.findById(id);

        return ResultVO.success(leave);
    }


    @GetMapping("/all")
    public ResultVO<List<Leave>> getAll()
    {
        List<Leave> result = leaveService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        leaveService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Leave leave)
    {
        leaveService.update(leave);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Leave leave)
    {


        leaveService.save(leave);
        return ResultVO.success(null);
    }
}
