package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface InterviewDao extends JpaRepository<Interview,String> {
}
