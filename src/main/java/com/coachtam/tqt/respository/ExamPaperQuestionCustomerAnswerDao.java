package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.ExamPaperQuestionCustomerAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ExamPaperQuestionCustomerAnswerDao extends JpaRepository<ExamPaperQuestionCustomerAnswer,Integer> {
}
