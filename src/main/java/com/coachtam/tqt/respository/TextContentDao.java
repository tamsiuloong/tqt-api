package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.TextContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TextContentDao extends JpaRepository<TextContent,Integer> {
}
