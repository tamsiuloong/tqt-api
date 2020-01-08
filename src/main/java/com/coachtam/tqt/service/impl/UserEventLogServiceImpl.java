package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.UserEventLog;
import com.coachtam.tqt.respository.UserEventLogDao;
import com.coachtam.tqt.service.UserEventLogService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	用户操作日志
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-7 20:16:05
 */
@Transactional
@Service
public class UserEventLogServiceImpl implements UserEventLogService {
    @Autowired
    private UserEventLogDao userEventLogDao;


    @Override
    public Page<UserEventLog> page(Integer pageNo,Integer pageSize)
    {
        return  userEventLogDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<UserEventLog> findAll() {
        return userEventLogDao.findAll();
    }

    @Override
    public void save(UserEventLog bean) {
        userEventLogDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            userEventLogDao.deleteById(id);
        }

    }

    @Override
    public void update(UserEventLog bean) {
        userEventLogDao.saveAndFlush(bean);
    }

    @Override
    public UserEventLog findById(Integer id) {
        return userEventLogDao.findById(id).get();
    }

    @Override
    public List<UserEventLog> getUserEventLogByUserId(String userId) {
        return userEventLogDao.findByUserId(userId);
    }
}
