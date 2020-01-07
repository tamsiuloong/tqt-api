package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.config.utils.JsonUtil;
import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.entity.ExamPaperAnswer;
import com.coachtam.tqt.entity.ExamPaperContent;
import com.coachtam.tqt.entity.TaskExamCustomerAnswer;
import com.coachtam.tqt.entity.task.TaskItemAnswerObject;
import com.coachtam.tqt.respository.ExamPaperContentDao;
import com.coachtam.tqt.respository.TaskExamCustomerAnswerDao;
import com.coachtam.tqt.service.ExamPaperContentService;
import com.coachtam.tqt.service.TaskExamCustomerAnswerService;
import com.coachtam.tqt.utils.JsonUtils;
import com.coachtam.tqt.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description:	任务试卷答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-7 21:49:38
 */
@Transactional
@Service
public class TaskExamCustomerAnswerServiceImpl implements TaskExamCustomerAnswerService {
    @Autowired
    private TaskExamCustomerAnswerDao taskExamCustomerAnswerDao;

    @Autowired
    private ExamPaperContentService examPaperContentService;



    @Override
    public Page<TaskExamCustomerAnswer> page(Integer pageNo,Integer pageSize)
    {
        return  taskExamCustomerAnswerDao.findAll(PageUtils.of(pageNo,pageSize));
    }



    @Override
    public List<TaskExamCustomerAnswer> findAll() {
        return taskExamCustomerAnswerDao.findAll();
    }

    @Override
    public void save(TaskExamCustomerAnswer bean) {
        taskExamCustomerAnswerDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            taskExamCustomerAnswerDao.deleteById(id);
        }

    }

    @Override
    public void update(TaskExamCustomerAnswer bean) {
        taskExamCustomerAnswerDao.saveAndFlush(bean);
    }

    @Override
    public TaskExamCustomerAnswer findById(Integer id) {
        return taskExamCustomerAnswerDao.findById(id).get();
    }

    @Override
    public void save(ExamPaper examPaper, ExamPaperAnswer examPaperAnswer, Date now) {
        Integer taskId = examPaper.getTaskExamId();
        String userId = examPaperAnswer.getUserId();
        TaskExamCustomerAnswer taskExamCustomerAnswer = taskExamCustomerAnswerDao.getBytaskExamIdAndUserId(taskId, userId);
        if (null == taskExamCustomerAnswer) {
            taskExamCustomerAnswer = new TaskExamCustomerAnswer();
            taskExamCustomerAnswer.setCreateTime(now);
            taskExamCustomerAnswer.setUserId(userId);
            taskExamCustomerAnswer.setTaskExamId(taskId);
            List<TaskItemAnswerObject> taskItemAnswerObjects = Arrays.asList(new TaskItemAnswerObject(examPaperAnswer.getExamPaperId(), examPaperAnswer.getId(), examPaperAnswer.getStatus()));
            ExamPaperContent textContent = examPaperContentService.jsonConvertInsert(taskItemAnswerObjects, now, null);
            examPaperContentService.save(textContent);
            taskExamCustomerAnswer.setTextContentId(textContent.getId());
            taskExamCustomerAnswerDao.save(taskExamCustomerAnswer);
        } else {
            ExamPaperContent textContent = examPaperContentService.findById(taskExamCustomerAnswer.getTextContentId());
            List<TaskItemAnswerObject> taskItemAnswerObjects = JsonUtils.toJsonListObject(textContent.getContent(), TaskItemAnswerObject.class);
            taskItemAnswerObjects.add(new TaskItemAnswerObject(examPaperAnswer.getExamPaperId(), examPaperAnswer.getId(), examPaperAnswer.getStatus()));
            examPaperContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
            examPaperContentService.update(textContent);
        }
    }
}
