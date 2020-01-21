package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.ExamPaper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamPaperDao extends JpaRepository<ExamPaper,Integer>, JpaSpecificationExecutor<ExamPaper> {
    @Query(value="select ep from ExamPaper  ep where ep.deleted = 0 and  ep.paperType = ?1 and ep.classes.id=?2 order by ep.createTime desc ")
    List<ExamPaper> findListByTypeAndClass(int type, String id, Pageable pageable);

}
