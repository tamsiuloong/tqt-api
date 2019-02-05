package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Leave;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.respository.LeaveDao;
import com.coachtam.tqt.utils.PageUtils;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Override
    public Page<Leave> page(Integer pageNo,Integer pageSize)
    {
        return  leaveDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<Leave> findAll() {
        return leaveDao.findAll();
    }

    @Override
    public void save(Leave bean) {
        org.springframework.security.core.userdetails.User user  = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        User dbUser = userService.findByUsername(user.getUsername());
        //1.开启申请流程
        //用来封装流程所需要的变量
        Map<String,Object> valMap = new HashMap<String, Object>();
        valMap.put("student",user.getUsername());
        //流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qingjia",valMap);

        //2.数据库记录报销信息
        //业务数据绑定流程id
        bean.setProcessInstanceId(processInstance.getProcessInstanceId());
        bean.setCreateBy(user.getUsername());
        bean.setCreateTime(new Date());
        leaveDao.save(bean);
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
}
