package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.VoteRecord;
import com.coachtam.tqt.entity.VoteReply;
import com.coachtam.tqt.entity.VoteTopic;
import com.coachtam.tqt.respository.VoteRecordDao;
import com.coachtam.tqt.respository.VoteReplyDao;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:	投票问题题目回复
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:29
 */
@Transactional
@Service
public class VoteReplyServiceImpl implements VoteReplyService {
    @Autowired
    private VoteReplyDao voteReplyDao;
    @Autowired
    private VoteRecordDao voteRecordDao;

    @Autowired
    private VoteTopicService voteTopicService;

    @Override
    public Page<VoteReply> page(Integer pageNo,Integer pageSize)
    {
        return  voteReplyDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<VoteReply> findAll() {
        return voteReplyDao.findAll();
    }

    @Override
    public void save(List<VoteReply> beanList, VoteRecord voteRecord) {
        //累计投票数
        VoteTopic voteTopic = voteTopicService.findById(voteRecord.getVotetopicId());
        voteTopic.setTotalCount(voteTopic.getTotalCount()+1);

        voteRecordDao.save(voteRecord);
        //设置和投票记录的关系
        beanList.forEach(reply->{
            reply.setVoterecoredId(voteRecord.getId());
        });
        voteReplyDao.saveAll(beanList);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            voteReplyDao.deleteById(id);
        }

    }

    @Override
    public void update(VoteReply bean) {
        voteReplyDao.saveAndFlush(bean);
    }

    @Override
    public VoteReply findById(String id) {
        return voteReplyDao.findById(id).get();
    }
}
