package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.entity.UserInfo;
import com.coachtam.tqt.service.FeedbackService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.to.FeedbackForm;
import com.coachtam.tqt.vo.ResultVO;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @Description:	学习反馈
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 17:14:52
 */
@RequestMapping("/api/feedback")
@RestController
public class FeedbackCtrl {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;
    @GetMapping
    public ResultVO<Page> page(Integer pageNo, Integer pageSize)
    {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.coachtam.tqt.entity.User dbUser = userService.findByUsername(user.getUsername());

        //查询当前用户学习反馈
        Page result = feedbackService.page(pageNo,pageSize,dbUser.getId());
        return ResultVO.success(result);
    }

    /**
     * 老师使用
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/teaching")
    public ResultVO<Page> page(Integer pageNo, Integer pageSize,
                               @RequestBody FeedbackForm searchForm)
    {
//
        //查询当前用户学习反馈
        Page result = feedbackService.page(pageNo,pageSize,(root,query,builder)->{
            List<Predicate> predicates = Lists.newArrayList();
            if(searchForm.getClassId()!=null && !searchForm.getClassId().isEmpty() &&!"all".equals(searchForm.getClassId()))
            {
                Join<Feedback, Classes> joins = root.join("user").join("classes");
                Predicate equal = builder.equal(joins.get("id"), searchForm.getClassId());

                predicates.add(equal);
            }
            if(searchForm.getStuName()!=null && !searchForm.getStuName().isEmpty())
            {
                Join<Feedback, UserInfo> joins = root.join("user").join("userInfo");
                Predicate equal = builder.equal(joins.get("name"), searchForm.getStuName());

                predicates.add(equal);
            }
            if(searchForm.getCourseId()!=null && !searchForm.getCourseId().isEmpty())
            {
                Predicate equal = builder.equal(root.join("course").get("id"), searchForm.getCourseId());
                predicates.add(equal);
            }
            if(searchForm.getDayNum()!=null && !searchForm.getDayNum().isEmpty())
            {
                //条件二
                Predicate equal = builder.equal(root.get("dayNum"), searchForm.getDayNum());
                predicates.add(equal);
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Feedback> list(@PathVariable("id") String id)
    {
        Feedback feedback = feedbackService.findById(id);

        return ResultVO.success(feedback);
    }


    @GetMapping("/all")
    public ResultVO<List<Feedback>> getAll()
    {
        List<Feedback> result = feedbackService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<String> delete(@RequestBody String[] ids)
    {
        feedbackService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody Feedback feedback)
    {
        feedbackService.update(feedback);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<String> add(@RequestBody Feedback feedback)
    {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.coachtam.tqt.entity.User dbUser = userService.findByUsername(user.getUsername());
        feedback.setUser(dbUser);

        feedbackService.save(feedback);
        return ResultVO.success(null);
    }
}
