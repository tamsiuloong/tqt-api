package com.coachtam.tqt.web;

import com.coachtam.tqt.entity.Classes;
import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.entity.Question;
import com.coachtam.tqt.service.QuestionService;
import com.coachtam.tqt.to.QuestionForm;
import com.coachtam.tqt.vo.ResultVO;
import com.coachtam.tqt.vo.question.QuestionEditRequestVM;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @Description:	试卷试题
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:30:46
 */
@RequestMapping("/api/question")
@RestController
public class QuestionCtrl {

    @Autowired
    private QuestionService questionService;

    @PostMapping("page")
    public ResultVO<Page> list(Integer pageNo, Integer pageSize, @RequestBody QuestionForm searchForm)
    {

        Specification<Question> specification = (root, query, builder)->{
            List<Predicate> predicates = Lists.newArrayList();



            if(StringUtils.isNotBlank(searchForm.getTitle()))
            {
                Predicate equal = builder.like(root.get("title"), "%"+searchForm.getTitle()+"%");
                predicates.add(equal);
            }
            if(searchForm.getCourseId()!=null && !searchForm.getCourseId().isEmpty())
            {
                Predicate equal = builder.equal(root.join("course").get("id"), searchForm.getCourseId());
                predicates.add(equal);
            }
            //查询可用的
            Predicate equal = builder.equal(root.get("deleted"), 0);
            predicates.add(equal);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page result = questionService.page(pageNo,pageSize,specification);
        return ResultVO.success(result);
    }


    @GetMapping("/{id}")
    public ResultVO<Question> list(@PathVariable("id") Integer id)
    {
        Question question = questionService.findById(id);

        return ResultVO.success(question);
    }


    @GetMapping("/all")
    public ResultVO<List<Question>> getAll()
    {
        List<Question> result = questionService.findAll();
        return ResultVO.success(result);
    }

    @DeleteMapping
    public ResultVO<Integer> delete(@RequestBody Integer[] ids)
    {
        questionService.deleteByIds(ids);
        return ResultVO.success(null);
    }


    @PutMapping
    public ResultVO<String> update(@RequestBody QuestionEditRequestVM question)
    {
        questionService.update(question);
        return ResultVO.success(null);
    }

    @PostMapping
    public ResultVO<Integer> add(@RequestBody QuestionEditRequestVM question)
    {
        questionService.save(question);
        return ResultVO.success(null);
    }
}
