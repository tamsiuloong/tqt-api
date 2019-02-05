package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LeaveDao extends JpaRepository<Leave,String> {
}
