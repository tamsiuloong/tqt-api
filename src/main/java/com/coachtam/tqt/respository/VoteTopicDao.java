package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.entity.VoteTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface VoteTopicDao extends JpaRepository<VoteTopic,Integer>, JpaSpecificationExecutor<VoteTopic> {
}
