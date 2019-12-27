package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.respository.UserDao;
import com.coachtam.tqt.service.RoleService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.to.UserForm;
import com.coachtam.tqt.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
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
    public Page<User> page(Integer pageNo, Integer pageSize, UserForm searchForm)
    {

        return  userDao.findAll((root,query,builder)->{
            List<Predicate> predicates = Lists.newArrayList();

            if(searchForm.getClassId()!=null && !searchForm.getClassId().isEmpty() &&!"all".equals(searchForm.getClassId()))
            {
                Join<User, Classes> joins = root.join("classes");
                Predicate equal = builder.equal(joins.get("id"), searchForm.getClassId());

                predicates.add(equal);
            }
            if(searchForm.getStuName()!=null && !searchForm.getStuName().isEmpty())
            {
                Join<User, UserInfo> joins = root.join("userInfo");
                Predicate equal = builder.equal(joins.get("name"), searchForm.getStuName());

                predicates.add(equal);
            }


            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        },PageUtils.of(pageNo,pageSize));
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

        String password;
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
            User user = userDao.findById(id).get();
            //不删除  只改状态
            user.setState(0);
//            userDao.deleteById(id);
        }

    }

    @Override
    public void update(User model) {
        //表单如果没有选中class，那么class.id为空字符串，因此需要解除无用关系
        if(model.getClasses()!=null&&model.getClasses().getId()!=null)
        {
            if(model.getClasses().getId().isEmpty())
            {
                model.setClasses(null);
            }
        }
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
        if(StringUtils.isNotBlank(user.getNoteUrl()))
        {
            dbUser.setNoteUrl(user.getNoteUrl());
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
