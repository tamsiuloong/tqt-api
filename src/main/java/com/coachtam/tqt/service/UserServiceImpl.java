package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Role;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.entity.UserInfo;
import com.coachtam.tqt.respository.UserDao;
import com.coachtam.tqt.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private RoleService roleService;

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
    public Boolean save(User model) {
        //检查用户名是否存在
        User user = userDao.findByUserName(model.getUserName());
        if(user!=null)
        {
            //保存失败
            return false;
        }

        model.getUserInfo().setUser(model);
        model.getUserInfo().setCreateTime(new Date());

        String password = "";
        if(model.getPassword()==null && StringUtils.isEmpty(model.getPassword()))
        {
            password = "123456";
        }
        else
        {
            password = model.getPassword();
        }
        //初始化密码
        model.setPassword(passwordEncoder.encode(password));
        //默认角色就是学生
        Role role = roleService.findById("40289f6e68a880bb0168a8864eb90001");
        model.getRoleSet().add(role);

        //默认状态可用
        model.setState(1);
        userDao.save(model);

        return true;

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
    public void updateRole(String id, String[] roleIds) {
        //设置用户和角色的关系
        User user = userDao.getOne(id);
        //roleids-->roleSet
        Set<Role> roleSet = new HashSet<>(roleIds.length);
        for(String roleId:roleIds)
        {
            //roleid-->role
            Role role = new Role();
            role.setId(roleId);

            roleSet.add(role);

        }

        user.setRoleSet(roleSet);
        userDao.saveAndFlush(user);
    }

    @Override
    public void updateMyInfo(User user) {
        User dbUser = userDao.getOne(user.getId());

        if(user.getPassword()!=null && !user.getPassword().isEmpty())
        {
            dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        UserInfo userInfo = user.getUserInfo();
        if(userInfo!=null)
        {
            dbUser.getUserInfo().setName(userInfo.getName());
            dbUser.getUserInfo().setGender(userInfo.getGender());
            dbUser.getUserInfo().setTelephone(userInfo.getTelephone());
            dbUser.getUserInfo().setEmail(userInfo.getEmail());
            dbUser.getUserInfo().setBirthday(userInfo.getBirthday());
        }

        userDao.saveAndFlush(dbUser);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public List<User> findAllTeachers() {
        return userDao.findByRoleName("老师");
    }

    @Override
    public List<User> findByClassId(String classId) {
        return userDao.findByClassId(classId);
    }
}
