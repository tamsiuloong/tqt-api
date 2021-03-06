package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Track;
import com.coachtam.tqt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-12 10:26
 * @Description:
 */

@Repository
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {
    User findByUserName(String username);

    /**
     * 根据角色名字查询用户列表
     * @param name 角色名字
     * @return
     */
    @Query("select u from User u join fetch u.roleSet r where r.name = ?1")
    List<User> findByRoleName(String name);

    /**
     * 根据班级id查询学生列表
     * @param classId
     * @return
     */
    @Query("select u from User u where u.classes.id = ?1")
    List<User> findByClassId(String classId);
}
