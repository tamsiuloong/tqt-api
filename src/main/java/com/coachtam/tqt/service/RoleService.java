package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Role;
import org.springframework.data.domain.Page;
import java.util.List;
/**
 * @Description:	模块
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-1 16:07:19
 */
public interface RoleService {
    public Page<Role> page(Integer pageNo, Integer pageSize);

    List<Role> findAll();

    void save(Role model);

    Role findById(String id);

    void update(Role model);

    void deleteByIds(String[] id);
}
