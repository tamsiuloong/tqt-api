package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.entity.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface LeaveDao extends JpaRepository<Leave,String>, JpaSpecificationExecutor<Leave> {
    Leave findByProcessInstanceId(String processInstanceId);

    @Query(value = "from Leave l where l.createBy = ?1")
    Page<Leave> findAllByCreateBy(String userName, Pageable pageable);
}
