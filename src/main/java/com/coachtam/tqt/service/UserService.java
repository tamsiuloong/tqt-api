package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 12:23:59
 */
public interface UserService {

    public Page<User> page(Integer pageNo, Integer pageSize);

    List<User> findAll();

    void save(User model);

    User findById(String id);

    void update(User model);

    void deleteByIds(String[] id);

    User findByUsername(String username);
}
