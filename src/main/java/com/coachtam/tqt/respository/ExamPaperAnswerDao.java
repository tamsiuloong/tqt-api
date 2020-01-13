package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.ExamPaperAnswer;
import com.coachtam.tqt.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ExamPaperAnswerDao extends JpaRepository<ExamPaperAnswer,Integer>, JpaSpecificationExecutor<ExamPaperAnswer> {

    Page<ExamPaperAnswer> findByExamPaperId(Integer examPaperId, Pageable pageable);
}
