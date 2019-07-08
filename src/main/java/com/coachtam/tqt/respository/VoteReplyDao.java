package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.VoteReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VoteReplyDao extends JpaRepository<VoteReply,String> {
}
