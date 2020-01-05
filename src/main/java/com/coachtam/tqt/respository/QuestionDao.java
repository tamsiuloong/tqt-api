package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer>, JpaSpecificationExecutor<Question> {

    List<Question> findByIdIn(List<Integer> questionIds);
}
