package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.ExamPaperContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ExamPaperContentDao extends JpaRepository<ExamPaperContent,Integer> {
}
