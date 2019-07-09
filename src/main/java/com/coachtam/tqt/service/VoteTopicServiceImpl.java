package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.entity.VoteSubtopic;
import com.coachtam.tqt.entity.VoteTopic;
import com.coachtam.tqt.respository.VoteSubtopicDao;
import com.coachtam.tqt.respository.VoteTopicDao;
import com.coachtam.tqt.service.VoteTopicService;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Page<VoteTopic> page(Integer pageNo,Integer pageSize)
    {
        return  voteTopicDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<VoteTopic> findAll() {
        return voteTopicDao.findAll();
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
