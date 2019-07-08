package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VoteRecordDao extends JpaRepository<VoteRecord,String> {
}
