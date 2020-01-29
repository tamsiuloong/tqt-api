package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.config.properties.GlobalProperteis;
import com.coachtam.tqt.config.properties.UploadProperteis;
import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.interceptor.LoginInterceptor;
import com.coachtam.tqt.respository.InterviewDao;
import com.coachtam.tqt.service.InterviewService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.to.InterviewForm;
import com.coachtam.tqt.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
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
@EnableConfigurationProperties({UploadProperteis.class, GlobalProperteis.class})
public class InterviewServiceImpl implements InterviewService {
    @Autowired
    private InterviewDao interviewDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UploadProperteis uploadProperteis;

    @Autowired
    private GlobalProperteis globalProperteis;

    @Override
    public Page<Interview> page(Integer pageNo, Integer pageSize, InterviewForm searchForm,boolean all)
    {

        com.coachtam.tqt.utils.jwt.UserInfo user = LoginInterceptor.getCurrUser();
        String username = user.getUsername();

        User dbUser = userService.findByUsername(username);



        //如果是管理员,查询所有面试记录,否则只能查看自己的
        if(!all&&!dbUser.getRoleSet().stream().anyMatch(role->globalProperteis.getAdminRoles().contains(role.getName()))){
            Interview interview = new Interview();
            User u = new User();
            u.setId(dbUser.getId());
            interview.setUser(u);
            Example<Interview> example = Example.of(interview);
            return interviewDao.findAll(example,PageUtils.of(pageNo,pageSize,new Sort(Sort.Direction.DESC,"createTime")));
        }

        return  interviewDao.findAll((root,query,builder)->{
            List<Predicate> predicates = Lists.newArrayList();

            if(searchForm.getClassId()!=null && !searchForm.getClassId().isEmpty() &&!"all".equals(searchForm.getClassId()))
            {
                Join<Feedback, Classes> joins = root.join("user").join("classes");
                Predicate equal = builder.equal(joins.get("id"), searchForm.getClassId());

                predicates.add(equal);
            }
            if(searchForm.getStuName()!=null && !searchForm.getStuName().isEmpty())
            {
                Join<Feedback, UserInfo> joins = root.join("user").join("userInfo");
                Predicate equal = builder.equal(joins.get("name"), searchForm.getStuName());

                predicates.add(equal);
            }
            if(searchForm.getCompanyName()!=null && !searchForm.getCompanyName().isEmpty())
            {
                Predicate equal = builder.like(root.get("companyName"), "%"+searchForm.getCompanyName()+"%");
                predicates.add(equal);
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        },PageUtils.of(pageNo,pageSize,new Sort(Sort.Direction.DESC,"createTime")));
    }



    @Override
    public List<Interview> findAll() {


        return interviewDao.findAll();
    }

    @Override
    public void save(Interview bean) {
        com.coachtam.tqt.utils.jwt.UserInfo currUser = LoginInterceptor.getCurrUser();
        String username = currUser.getUsername();

        User dbUser = userService.findByUsername(username);
        bean.setUser(dbUser);

        bean.setCreateTime(new Date());
        interviewDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            interviewDao.deleteById(id);
        }

    }

    @Override
    public void update(Interview bean) {
        Interview dbBean = interviewDao.getOne(bean.getId());
        dbBean.setBsInfo(bean.getBsInfo());
        dbBean.setMsInfo(bean.getMsInfo());
        dbBean.setCompanyAddr(bean.getCompanyAddr());
        dbBean.setCompanyName(bean.getCompanyName());
        dbBean.setCompanyTel(bean.getCompanyTel());
        dbBean.setInterviewTime(bean.getInterviewTime());


        if(StringUtils.isNotBlank(bean.getAppendixs()))
        {
            dbBean.setAppendixs(bean.getAppendixs().replaceAll(uploadProperteis.getAccessPath()+"/image/",""));
        }

        if(StringUtils.isNotBlank(bean.getSoundRecording()))
        {
            dbBean.setSoundRecording(bean.getSoundRecording().replaceAll(uploadProperteis.getAccessPath()+"/sound/",""));
        }

        dbBean.setExperience(bean.getExperience());
        interviewDao.saveAndFlush(dbBean);
    }

    @Override
    public Interview findById(Integer id) {
        return interviewDao.findById(id).get();
    }
}
