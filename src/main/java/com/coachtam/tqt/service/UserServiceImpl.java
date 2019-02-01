package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.respository.UserDao;
import com.coachtam.tqt.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 12:23:59
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> page(Integer pageNo,Integer pageSize)
    {
        return  userDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<User> findAll() {

        return userDao.findAll();
    }

    @Override
    public void save(User model) {
        model.getUserInfo().setUser(model);
        model.getUserInfo().setCreateTime(new Date());

        String password = "";
        if(model.getPassword()==null && StringUtils.isEmpty(model.getPassword()))
        {
            password = "123456";
        }
        //初始化密码
        model.setPassword(passwordEncoder.encode(password));
        userDao.save(model);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            userDao.deleteById(id);
        }

    }

    @Override
    public void update(User model) {
        //密码，创建日期不变
        User dbUser = userDao.findByUserName(model.getUserName());


        model.setPassword(dbUser.getPassword());
        model.getUserInfo().setCreateTime(dbUser.getUserInfo().getCreateTime());
        model.getUserInfo().setUpdateTime(new Date());
        model.getUserInfo().setId(model.getId());
        model.getUserInfo().setUser(model);
        userDao.saveAndFlush(model);
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUserName(username);
    }
}
