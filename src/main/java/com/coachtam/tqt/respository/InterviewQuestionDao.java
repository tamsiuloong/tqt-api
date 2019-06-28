package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Interview;
import com.coachtam.tqt.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface InterviewQuestionDao extends JpaRepository<InterviewQuestion,Integer>, JpaSpecificationExecutor<InterviewQuestion> {
}
