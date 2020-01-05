package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.ExamPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface ExamPaperDao extends JpaRepository<ExamPaper,Integer>, JpaSpecificationExecutor<ExamPaper> {
}
