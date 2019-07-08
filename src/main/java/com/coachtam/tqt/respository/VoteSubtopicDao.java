package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.VoteSubtopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VoteSubtopicDao extends JpaRepository<VoteSubtopic,String> {
}
