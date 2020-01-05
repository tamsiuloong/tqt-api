package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.QuestionItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface QuestionItemsDao extends JpaRepository<QuestionItems,Integer> {
}
