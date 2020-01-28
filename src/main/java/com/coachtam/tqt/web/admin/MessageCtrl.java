package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.interceptor.LoginInterceptor;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        com.coachtam.tqt.config.utils.UserInfo user = LoginInterceptor.getCurrUser();
        //没有做认证提示，先去登陆
        List<Task> list = taskService.createTaskQuery().taskAssignee(user.getUsername()).orderByTaskCreateTime().desc().list();

        if(list!=null && list.size()>0)
        {
            result=  list.size();
        }
        return result;
    }


}
