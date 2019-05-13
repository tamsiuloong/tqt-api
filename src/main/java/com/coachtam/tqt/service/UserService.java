package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.to.UserForm;
import org.springframework.data.domain.Page;

import java.util.List;
/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 12:23:59
 */
public interface UserService {

    public Page<User> page(Integer pageNo, Integer pageSize, UserForm userForm);

    List<User> findAll();

    Boolean save(User model);

    User findById(String id);

    void update(User model);

    void deleteByIds(String[] id);

    User findByUsername(String username);

    void updateRole(String id, String[] roleIds);

    /**
     * 更新我的资料
     * @param user
     */
    void updateMyInfo(User user);

    List<User> findAllTeachers();

    List<User> findByClassId(String classId);
}
