package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VoteItemDao extends JpaRepository<VoteItem,String> {
}
