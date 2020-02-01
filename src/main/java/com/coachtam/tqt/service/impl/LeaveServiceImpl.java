package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.Leave;
import com.coachtam.tqt.interceptor.AuthInterceptor;
import com.coachtam.tqt.respository.LeaveDao;
import com.coachtam.tqt.service.LeaveService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.utils.PageUtils;
import com.coachtam.tqt.utils.jwt.UserInfo;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:	请假
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-5 22:48:29
 */
@Transactional
@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveDao leaveDao;


    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;
    @Override
    public Page<Leave> page(Integer pageNo, Integer pageSize, String username)
    {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return  leaveDao.findAllByCreateBy(username,PageUtils.of(pageNo,pageSize,sort));
    }



    @Override
    public List<Leave> findAll() {
        return leaveDao.findAll();
    }

    @Override
    public void save(Leave bean) {
        UserInfo user = AuthInterceptor.getCurrUser();
//        User dbUser = userService.findByUsername(user.getUsername());
        //1.开启申请流程
        //用来封装流程所需要的变量
        Map<String,Object> valMap = new HashMap<String, Object>();
        valMap.put("to",user.getUsername());
        //流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qingjia",valMap);

        //2.数据库记录报销信息
        //业务数据绑定流程id
        bean.setProcessInstanceId(processInstance.getProcessInstanceId());
        bean.setCreateBy(user.getUsername());
        bean.setCreateTime(new Date());
        leaveDao.save(bean);

        //确定请假
        List<Task> list = taskService.createTaskQuery().taskAssignee(user.getUsername()).orderByTaskCreateTime().desc().list();

        if(list!=null&&list.size()>0)
        {
            Task task = list.get(0);
            //完成该任务，并指定由他的上级来处理
            valMap.put("to",bean.getReviewer());
            taskService.complete(task.getId(),valMap);
        }

    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            leaveDao.deleteById(id);
        }

    }

    @Override
    public void update(Leave bean) {
        leaveDao.saveAndFlush(bean);
    }

    @Override
    public Leave findById(String id) {
        return leaveDao.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public Leave findByProcessInstanceId(String processInstanceId) {
        Leave result = leaveDao.findByProcessInstanceId(processInstanceId);
        return result;
    }
}
