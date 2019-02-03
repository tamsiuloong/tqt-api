package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-12 10:26
 * @Description:
 */

@Repository
public interface FeedbackDao extends JpaRepository<Feedback,String>, JpaSpecificationExecutor<Feedback> {

    @Query(value = "select absorption,count(absorption) from feedback_p group by absorption",nativeQuery = true)
    List<Object[]> findAbsorption();

    @Query(value = "from Feedback f where f.user.id = ?1")
    Page<Feedback> findAllByUserId(String userId, Pageable pageable);
}
