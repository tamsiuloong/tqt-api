package com.coachtam.tqt.web.admin;

import com.coachtam.tqt.entity.Question;
import com.coachtam.tqt.service.QuestionService;
import com.coachtam.tqt.to.QuestionForm;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import com.coachtam.tqt.viewmodel.admin.question.QuestionEditRequestVM;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

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
    public ResultVM<Page> list(Integer pageNo, Integer pageSize, @RequestBody QuestionForm searchForm)
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
        return ResultVM.success(result);
    }


    @GetMapping("/{id}")
    public ResultVM<Question> list(@PathVariable("id") Integer id)
    {
        Question question = questionService.findById(id);

        return ResultVM.success(question);
    }


    @GetMapping("/all")
    public ResultVM<List<Question>> getAll()
    {
        List<Question> result = questionService.findAll();
        return ResultVM.success(result);
    }

    @DeleteMapping
    public ResultVM<Integer> delete(@RequestBody Integer[] ids)
    {
        questionService.deleteByIds(ids);
        return ResultVM.success(null);
    }


    @PutMapping
    public ResultVM<String> update(@RequestBody QuestionEditRequestVM question)
    {
        questionService.update(question);
        return ResultVM.success(null);
    }

    @PostMapping
    public ResultVM<Integer> add(@RequestBody QuestionEditRequestVM question)
    {
        questionService.save(question);
        return ResultVM.success(null);
    }
}
