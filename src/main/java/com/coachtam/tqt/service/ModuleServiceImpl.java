package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Module;
import com.coachtam.tqt.respository.ModuleDao;
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
 * @CreateDate:		2019-2-1 13:54:19
 */
@Transactional
@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;


    @Override
    public Page<Module> page(Integer pageNo,Integer pageSize)
    {
        return  moduleDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    @Override
    public void save(Module bean) {
        if(bean.getParentId()==null||bean.getParentId().isEmpty())
        {
            bean.setParentId(null);
        }
        moduleDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
//            Module one = moduleDao.getOne(id);
//            one.getRoleSet().clear();
//            moduleDao.delete(one);
            moduleDao.deleteRelationship(id);
            moduleDao.deleteById(id);
        }

    }

    @Override
    public List<Module> getListByLayerNum(Long layerNum) {

        return  moduleDao.getListByLayerNum(layerNum);
    }

    @Override
    public void update(Module bean) {
        moduleDao.saveAndFlush(bean);
    }

    @Override
    public Module findById(String id) {
        return moduleDao.findById(id).get();
    }
}
