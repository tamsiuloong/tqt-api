package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.interceptor.LoginInterceptor;
import com.coachtam.tqt.respository.VoteSubtopicDao;
import com.coachtam.tqt.respository.VoteTopicDao;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.service.VoteTopicService;
import com.coachtam.tqt.utils.PageUtils;
import com.coachtam.tqt.utils.jwt.UserInfo;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @Description:	投票主题
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:50:38
 */
@Transactional
@Service
public class VoteTopicServiceImpl implements VoteTopicService {
    @Autowired
    private VoteTopicDao voteTopicDao;

    @Autowired
    private VoteSubtopicDao voteSubtopicDao;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<VoteTopic> page(Integer pageNo, Integer pageSize, boolean isAll)
    {
        UserInfo user = LoginInterceptor.getCurrUser();
        String username = user.getUsername();
        User dbUser = userService.findByUsername(username);

        //自己班的问卷-for student
        if(!isAll)
        {

            VoteTopic voteTopic = new VoteTopic();
            if(dbUser!=null&&dbUser.getClasses()!=null)
            {
                voteTopic.setClasses(new Classes(dbUser.getClasses().getId()));
            }

            //查自己班的问卷调查
            return  voteTopicDao.findAll(Example.of(voteTopic),PageUtils.of(pageNo,pageSize));
        }
        //老师对应的问卷-for teacher
        else if(dbUser.getRoleSet().stream().anyMatch(role -> "老师".equals(role.getName()))&&!dbUser.getRoleSet().stream().anyMatch(role -> "管理员".equals(role.getName()))){

            Specification<VoteTopic> specification = (root, query, builder)->{
                List<Predicate> predicates = Lists.newArrayList();
                Join<VoteTopic, User> joins = root.join("teacher");
                Predicate equal = builder.equal(joins.get("id"), dbUser.getId());
                predicates.add(equal);

                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            };
            return  voteTopicDao.findAll(specification,PageUtils.of(pageNo,pageSize));
        }
        return  voteTopicDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<VoteTopic> findAll() {
        UserInfo user = LoginInterceptor.getCurrUser();
        User dbuser = userService.findByUsername(user.getUsername());

        if(dbuser.getRoleSet().stream().anyMatch(role -> "老师".equals(role.getName()))&&!dbuser.getRoleSet().stream().anyMatch(role -> "管理员".equals(role.getName()))) {

            StringBuilder sb
                    = new StringBuilder(
                    "from VoteTopic  f  " +
                            "join f.teacher u " +
                            " where 1 = 1 and u.id = :id");

            //创建jpql查询(hql)
            Query query = entityManager.createQuery(sb.toString());
            query.setParameter("id", dbuser.getId());

            return query.getResultList();

        }
        else {
            return voteTopicDao.findAll();
        }

    }

    @Override
    public void save(VoteTopic bean) {
        User teacher = new User();
        teacher.setId(bean.getTeacherId());
        bean.setTeacher(teacher);

        bean.setTotalCount(0);
        voteTopicDao.save(bean);
        bean.getVoteSubtopicList().forEach(voteSubtopic -> {
            voteSubtopic.setVoteTopic(bean);
        });
        voteSubtopicDao.saveAll(bean.getVoteSubtopicList());
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            VoteSubtopic voteSubtopic = new VoteSubtopic();
            VoteTopic voteTopic = new VoteTopic();
            voteTopic.setId(id);
            voteSubtopic.setVoteTopic(voteTopic);

            voteSubtopicDao.deleteAll(voteSubtopicDao.findAll(Example.of(voteSubtopic)));
            voteTopicDao.deleteById(id);
        }

    }

    @Override
    public void update(VoteTopic bean) {
        voteTopicDao.saveAndFlush(bean);
    }

    @Override
    public VoteTopic findById(Integer id) {
        return voteTopicDao.findById(id).get();
    }
}
