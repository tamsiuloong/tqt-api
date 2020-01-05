package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.ExamPaperAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ExamPaperAnswerDao extends JpaRepository<ExamPaperAnswer,Integer> {
}
