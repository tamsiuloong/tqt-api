package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.ExamPaperQuestionCustomerAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamPaperQuestionCustomerAnswerDao extends JpaRepository<ExamPaperQuestionCustomerAnswer,Integer>, JpaSpecificationExecutor<ExamPaperQuestionCustomerAnswer> {

    List<ExamPaperQuestionCustomerAnswer> findByExamPaperAnswerId(Integer answerId);


    @Modifying
    @Query(value = "update EXAM_PAPER_QUESTION_CUSTOMER_ANSWER_P\n" +
            "    set customer_score=?1 , do_right=?2\n" +
            "    where id=?3",nativeQuery = true)
    void updateScore(Integer customerScore, Boolean doRight, Integer id);
}
