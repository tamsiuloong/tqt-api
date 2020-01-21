package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.config.security.UserDetail;
import com.coachtam.tqt.entity.ExamPaper;
import com.coachtam.tqt.entity.TextContent;
import com.coachtam.tqt.entity.Question;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.entity.enums.ExamPaperTypeEnum;
import com.coachtam.tqt.entity.exam.ExamPaperQuestionItemObject;
import com.coachtam.tqt.entity.exam.ExamPaperTitleItemObject;
import com.coachtam.tqt.interceptor.LoginInterceptor;
import com.coachtam.tqt.respository.TextContentDao;
import com.coachtam.tqt.respository.ExamPaperDao;
import com.coachtam.tqt.respository.QuestionDao;
import com.coachtam.tqt.service.ExamPaperService;
import com.coachtam.tqt.service.QuestionService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.utils.DateTimeUtil;
import com.coachtam.tqt.utils.ExamUtil;
import com.coachtam.tqt.utils.JsonUtils;
import com.coachtam.tqt.utils.PageUtils;
import com.coachtam.tqt.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.coachtam.tqt.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.coachtam.tqt.viewmodel.admin.exam.ExamPaperTitleItemVM;
import com.coachtam.tqt.viewmodel.admin.question.QuestionEditRequestVM;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Description:	试卷
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:28:32
 */
@Transactional
@Service
public class ExamPaperServiceImpl implements ExamPaperService {
    @Autowired
    private ExamPaperDao examPaperDao;

    @Autowired
    private TextContentDao textContentDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    protected  final static ModelMapper modelMapper = new ModelMapper();
    @Override
    public Page<ExamPaper> page(Integer pageNo,Integer pageSize, Specification<ExamPaper> specification)
    {
        return  examPaperDao.findAll(specification,PageUtils.of(pageNo,pageSize, Sort.by(Sort.Direction.DESC,"createTime")));
    }

    @Override
    public Page page(ExamPaperPageRequestVM model) {
        return  examPaperDao.findAll(PageUtils.of(model.getPageIndex(),model.getPageSize()));
    }



    @Override
    public List<ExamPaper> findAll() {
        return examPaperDao.findAll();
    }

    @Override
    public void save(ExamPaperEditRequestVM model) {
        ExamPaper examPaper = new ExamPaper();

        UserDetail currUser  = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.findByUsername(currUser.getUsername());


        List<ExamPaperTitleItemVM> titleItemsVM = model.getTitleItems();
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
        String frameTextContentStr = JsonUtils.toJson(frameTextContentList);


        //保存试卷详情
        TextContent frameTextContent = new TextContent(frameTextContentStr, examPaper.getCreateTime());
        textContentDao.save(frameTextContent);

        examPaper = modelMapper.map(model, ExamPaper.class);


        examPaper.setDeleted(false);
        examPaperFromVM(model, examPaper, titleItemsVM);

        examPaper.setUser(user);
        examPaper.setCreateTime(new Date());
        //设置关系
        examPaper.setFrameTextContentId(frameTextContent.getId());
        examPaperDao.save(examPaper);


    }

    private void examPaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, ExamPaper examPaper, List<ExamPaperTitleItemVM> titleItemsVM) {
        Integer questionCount = titleItemsVM.stream()
                .mapToInt(t -> t.getQuestionItems().size()).sum();
        Integer score = titleItemsVM.stream().
                flatMapToInt(t -> t.getQuestionItems().stream()
                        .mapToInt(q -> ExamUtil.scoreFromVM(q.getScore().toString()))
                ).sum();
        examPaper.setQuestionCount(questionCount);
        examPaper.setScore(score);
        List<String> dateTimes = examPaperEditRequestVM.getLimitDateTime();
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            examPaper.setLimitStartTime(DateTimeUtil.parse(dateTimes.get(0), DateTimeUtil.STANDER_FORMAT));
            examPaper.setLimitEndTime(DateTimeUtil.parse(dateTimes.get(1), DateTimeUtil.STANDER_FORMAT));
        }
    }
    private List<ExamPaperTitleItemObject> frameTextContentFromVM(List<ExamPaperTitleItemVM> titleItems) {
        AtomicInteger index = new AtomicInteger(1);
        return titleItems.stream().map(t -> {
            ExamPaperTitleItemObject titleItem = modelMapper.map(t, ExamPaperTitleItemObject.class);
            List<ExamPaperQuestionItemObject> questionItems = t.getQuestionItems().stream()
                    .map(q -> {
                        ExamPaperQuestionItemObject examPaperQuestionItemObject = modelMapper.map(q, ExamPaperQuestionItemObject.class);
                        examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());
                        return examPaperQuestionItemObject;
                    })
                    .collect(Collectors.toList());
            titleItem.setQuestionItems(questionItems);
            return titleItem;
        }).collect(Collectors.toList());
    }
    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            ExamPaper one = examPaperDao.getOne(id);
            one.setDeleted(true);
        }

    }

    @Override
    public void update(ExamPaperEditRequestVM model) {
        ExamPaper dbExamPaper = examPaperDao.findById(model.getId()).get();



        ExamPaper examPaper = new ExamPaper();
        List<ExamPaperTitleItemVM> titleItemsVM = model.getTitleItems();
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
        String frameTextContentStr = JsonUtils.toJson(frameTextContentList);

        //获取试卷对应详情
        TextContent textContent = textContentDao.getOne(dbExamPaper.getFrameTextContentId());
        textContent.setContent(frameTextContentStr);
        textContentDao.save(textContent);

        examPaper = modelMapper.map(model, ExamPaper.class);


        examPaperFromVM(model, examPaper, titleItemsVM);

        examPaper.setFrameTextContentId(dbExamPaper.getFrameTextContentId());
        examPaper.setUser(dbExamPaper.getUser());
        examPaper.setDeleted(dbExamPaper.getDeleted());
        examPaper.setCreateTime(dbExamPaper.getCreateTime());
        examPaperDao.save(examPaper);

    }

    @Override
    public ExamPaper findById(Integer id) {
        return examPaperDao.findById(id).get();
    }

    @Override
    public ExamPaperEditRequestVM examPaperToVM(Integer id) {
        ExamPaper examPaper = examPaperDao.getOne(id);
        ExamPaperEditRequestVM vm = modelMapper.map(examPaper, ExamPaperEditRequestVM.class);
//        vm.setLevel(examPaper.getGradeLevel());
        TextContent frameTextContent = textContentDao.getOne(examPaper.getFrameTextContentId());
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtils.toJsonListObject(frameTextContent.getContent(), ExamPaperTitleItemObject.class);
        List<Integer> questionIds = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream()
                        .map(q -> q.getId()))
                .collect(Collectors.toList());
        List<Question> questions = questionDao.findByIdIn(questionIds);
        List<ExamPaperTitleItemVM> examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {
            ExamPaperTitleItemVM tTitleVM = modelMapper.map(t, ExamPaperTitleItemVM.class);
            List<QuestionEditRequestVM> questionItemsVM = t.getQuestionItems().stream().map(i -> {
                Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(question);
                questionEditRequestVM.setItemOrder(i.getItemOrder());
                return questionEditRequestVM;
            }).collect(Collectors.toList());
            tTitleVM.setQuestionItems(questionItemsVM);
            return tTitleVM;
        }).collect(Collectors.toList());
        vm.setTitleItems(examPaperTitleItemVMS);
//        vm.setScore();
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            vm.setLimitDateTime(limitDateTime);
        }
        return vm;
    }

    @Override
    public List<ExamPaper> findPaperByType(int type) {

        User user = userService.findByUsername(LoginInterceptor.getCurrUser().getUsername());
        if(user.getClasses()!=null)
        {
            return examPaperDao.findListByTypeAndClass(type,user.getClasses().getId(),PageRequest.of(0,5));
        }

        return null;
    }

    @Override
    public void updateStatus(Integer paperId, Boolean deleted) {
        ExamPaper examPaper = examPaperDao.getOne(paperId);
        examPaper.setDeleted(deleted);
    }
}
