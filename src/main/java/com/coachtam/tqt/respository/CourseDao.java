package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CourseDao extends JpaRepository<Course,String> {
}
