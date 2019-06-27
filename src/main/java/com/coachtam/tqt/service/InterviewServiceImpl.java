package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.Interview;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.respository.InterviewDao;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:49:39
 */
@Transactional
@Service
public class InterviewServiceImpl implements InterviewService {
    @Autowired
    private InterviewDao interviewDao;

    @Autowired
    private UserService userService;

    @Override
    public Page<Interview> page(Integer pageNo,Integer pageSize)
    {
        return  interviewDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<Interview> findAll() {
        return interviewDao.findAll();
    }

    @Override
    public void save(Interview bean) {
        org.springframework.security.core.userdetails.User user  = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        User dbUser = userService.findByUsername(username);
        bean.setUser(dbUser);

        bean.setCreateTime(new Date());
        interviewDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            interviewDao.deleteById(id);
        }

    }

    @Override
    public void update(Interview bean) {
        interviewDao.saveAndFlush(bean);
    }

    @Override
    public Interview findById(String id) {
        return interviewDao.findById(id).get();
    }
}
