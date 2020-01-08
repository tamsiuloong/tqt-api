package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.UserEventLog;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	用户操作日志
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-7 20:16:05
 */
public interface UserEventLogService {
    public Page<UserEventLog> page(Integer pageNo, Integer pageSize);

    List<UserEventLog> findAll();

    void save(UserEventLog model);

    UserEventLog findById(Integer id);

    void update(UserEventLog model);

    void deleteByIds(Integer[] id);

    List<UserEventLog> getUserEventLogByUserId(String userId);
}
