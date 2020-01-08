package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.UserEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEventLogDao extends JpaRepository<UserEventLog,Integer> {
    List<UserEventLog> findByUserId(String userId);
}
