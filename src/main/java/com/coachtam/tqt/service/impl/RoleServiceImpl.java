package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.Module;
import com.coachtam.tqt.entity.Role;
import com.coachtam.tqt.respository.RoleDao;
import com.coachtam.tqt.service.RoleService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	模块
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-1 16:07:19
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;


    @Override
    public Page<Role> page(Integer pageNo,Integer pageSize)
    {
        return  roleDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void save(Role bean) {
        roleDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            roleDao.deleteById(id);
        }

    }

    @Override
    public void update(Role role) {

        //更新角色模块
        String[] moduleIds = role.getModuleIds();
        //清空role已有的moduleSet
        role.getModuleSet().clear();
        //moduleIds --> moduleSet
        for(String moduleId:moduleIds)
        {
            //moduleId --> module
            Module module = new Module();
            module.setId(moduleId);

            role.getModuleSet().add(module);
        }
        roleDao.saveAndFlush(role);
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id).get();
    }
}
