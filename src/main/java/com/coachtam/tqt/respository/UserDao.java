package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-12 10:26
 * @Description:
 */

@Repository
public interface UserDao extends JpaRepository<User,String> {
    User findByUserName(String username);
}
