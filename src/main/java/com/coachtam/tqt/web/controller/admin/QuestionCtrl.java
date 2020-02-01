package com.coachtam.tqt.web.controller.admin;

import com.coachtam.tqt.entity.Question;
import com.coachtam.tqt.service.QuestionService;
import com.coachtam.tqt.qo.QuestionQO;
import com.coachtam.tqt.viewmodel.admin.ResultVM;
import com.coachtam.tqt.viewmodel.admin.question.QuestionEditRequestVM;
import com.coachtam.tqt.viewmodel.admin.question.QuestionResponseVM;
import com.coachtam.tqt.web.converter.QuestionConverter;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.criteria.Predicate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:	试卷试题
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:30:46
 */
@RequestMapping("/api/question")
@RestController
@RolesAllowed({"老师","管理员","测试","班主任"})
public class QuestionCtrl {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionConverter questionConverter;

    @PostMapping("page")
    public ResultVM<Map<String, Object>> list(Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize, @RequestBody QuestionQO searchForm)
    {

        Specification<Question> specification = (root, query, builder)->{
            List<Predicate> predicates = Lists.newArrayList();



            if(StringUtils.isNotBlank(searchForm.getTitle()))
            {
                Predicate equal = builder.like(root.get("title"), "%"+searchForm.getTitle()+"%");
                predicates.add(equal);
            }
            if(null!=searchForm.getQuestionType())
            {
                Predicate equal = builder.equal(root.get("questionType"), searchForm.getQuestionType());
                predicates.add(equal);
            }
            if(StringUtils.isNotBlank(searchForm.getCourseId()))
            {
                Predicate equal = builder.equal(root.join("course").get("id"), searchForm.getCourseId());
                predicates.add(equal);
            }
            //查询可用的
            Predicate equal = builder.equal(root.get("deleted"), 0);
            predicates.add(equal);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

//        questionService.page(pageNo,pageSize,specification).getContent().stream().map(question->questionConverter.domain2dto(question));
        Page<Question> page = questionService.page(pageNo, pageSize, specification);
        List<QuestionResponseVM> vmList = questionConverter.domain2dto(page.getContent());
        Map<String, Object> map = new HashMap<>();
        map.put("totalPages", page.getTotalPages());
        map.put("totalElements", page.getTotalElements());
        map.put("content", vmList);
        map.put("number", page.getNumber());
        map.put("size", page.getSize());
        return ResultVM.success(map);
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
