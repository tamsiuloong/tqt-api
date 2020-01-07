package com.coachtam.tqt.respository;

import com.coachtam.tqt.entity.TaskExamCustomerAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TaskExamCustomerAnswerDao extends JpaRepository<TaskExamCustomerAnswer,Integer> {
    TaskExamCustomerAnswer getBytaskExamIdAndUserId(Integer taskId, String userId);
}
