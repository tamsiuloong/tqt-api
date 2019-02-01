package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface FeedbackDao extends JpaRepository<Feedback,String> {

    @Query(value = "select absorption,count(absorption) from feedback_p group by absorption",nativeQuery = true)
    List<Object[]> findAbsorption();
}
