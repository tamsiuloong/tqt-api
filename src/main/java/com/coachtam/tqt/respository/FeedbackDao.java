package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Copyright (C), 2018-2019
 * @Author: JAVA在召唤
 * @Date: 2019-01-12 10:26
 * @Description:
 */

@Repository
public interface FeedbackDao extends CrudRepository<Feedback,Integer> {
}
