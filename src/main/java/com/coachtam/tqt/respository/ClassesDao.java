package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesDao extends JpaRepository<Classes,String> {
}
