package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.KnowledgePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface KnowledgePointDao extends JpaRepository<KnowledgePoint,Integer> {
}
