package com.coachtam.tqt.web;

import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.vo.ResultVO;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 12:23:59
 */
@RequestMapping("/api/message")
@RestController
public class MessageCtrl {


    @Autowired
    private TaskService taskService;

    @GetMapping("/count")
    public Integer count()
    {
        Integer result = 0;
        org.springframework.security.core.userdetails.User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //没有做认证提示，先去登陆
        List<Task> list = taskService.createTaskQuery().taskAssignee(user.getUsername()).orderByTaskCreateTime().desc().list();

        if(list!=null && list.size()>0)
        {
            result=  list.size();
        }
        return result;
    }


}
