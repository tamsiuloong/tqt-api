package com.coachtam.tqt.service;

import com.coachtam.tqt.entity.*;
import com.coachtam.tqt.respository.InterviewQuestionDao;
import com.coachtam.tqt.to.BatchInterviewQuestionForm;
import com.coachtam.tqt.to.InterviewQuestionForm;
import com.coachtam.tqt.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:49:48
 */
@Transactional
@Service
public class InterviewQuestionServiceImpl implements InterviewQuestionService {
    @Autowired
    private InterviewQuestionDao interviewQuestionDao;

    //正则表达式
    private Pattern p = Pattern.compile("<p.*?>(.*?)</p>");

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Page<InterviewQuestion> page(Integer pageNo, Integer pageSize, Integer interviewId)
    {
        //根据面试记录查询
        if(interviewId!=null)
        {
            InterviewQuestion interviewQuestion = new InterviewQuestion();
            Interview interview = new Interview();
            interview.setId(interviewId);
            interviewQuestion.setInterview(interview);
            Example<InterviewQuestion> example = Example.of(interviewQuestion);

            return interviewQuestionDao.findAll(example,PageUtils.of(pageNo,pageSize, Sort.by(Sort.Direction.DESC,"id")));
        }
        //否则查询所有
        return  interviewQuestionDao.findAll(PageUtils.of(pageNo,pageSize));
    }

    @Override
    public Page<InterviewQuestion> page(Integer pageNo, Integer pageSize, InterviewQuestionForm searchForm) {


        return  interviewQuestionDao.findAll((root,query,builder)->{
            List<Predicate> predicates = Lists.newArrayList();

            if(searchForm.getCourseId()!=null && !searchForm.getCourseId().isEmpty() &&!"all".equals(searchForm.getCourseId()))
            {
                Join<InterviewQuestion, Course> joins = root.join("course");
                Predicate equal = builder.equal(joins.get("id"), searchForm.getCourseId());

                predicates.add(equal);
            }
            if(searchForm.getKnowledgePointId()!=null && !searchForm.getKnowledgePointId().isEmpty())
            {
                Join<InterviewQuestion, KnowledgePoint> joins = root.join("knowledgePoint");
                Predicate equal = builder.equal(joins.get("id"), searchForm.getKnowledgePointId());

                predicates.add(equal);
            }
            if(searchForm.getTitle()!=null && !searchForm.getTitle().isEmpty())
            {
                Predicate equal = builder.like(root.get("title"), "%"+searchForm.getTitle()+"%");
                predicates.add(equal);
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        },PageUtils.of(pageNo,pageSize));
    }

    @Override
    public List<InterviewQuestion> findAll() {
        return interviewQuestionDao.findAll();
    }

    @Override
    public void save(InterviewQuestion bean) {
        interviewQuestionDao.save(bean);
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        for (Integer id:ids) {
            interviewQuestionDao.deleteById(id);
        }

    }

    @Override
    public void update(InterviewQuestion bean) {
        interviewQuestionDao.saveAndFlush(bean);
    }

    @Override
    public InterviewQuestion findById(Integer id) {
        return interviewQuestionDao.findById(id).get();
    }



    @Override
    public void batchImport(BatchInterviewQuestionForm form)  {

//        SAXReader reader = new SAXReader();
//
//        List<InterviewQuestion> interviewQuestionList = new ArrayList<>();
//        try {
//            StringBuilder sb = new StringBuilder();
//            sb.append("<list>").append(form.getText()).append("</list>");
//            Document doc = reader.read(new StringReader(sb.toString()));
//            Element root = doc.getRootElement();
//            Iterator<Element> it = root.elementIterator();
//            while(it.hasNext())
//            {
//                InterviewQuestion interviewQuestion = new InterviewQuestion();
//                Element e = it.next();//获取子元素
//                interviewQuestion.setTitle(e.getText());
//                if(it.hasNext())
//                {
//                    e = it.next();//获取子元素
//                    interviewQuestion.setAnswer(e.getText());
//                }
//                interviewQuestion.setCompany(form.getCompany());
//                interviewQuestionList.add(interviewQuestion);
//            }
//
//            interviewQuestionDao.saveAll(interviewQuestionList);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }


        form.getInterviewQuestionList().forEach(interviewQuestion -> {
            interviewQuestion.setCompany(form.getCompany());
            if(interviewQuestion.getCourse().getId()==null)
            {
                interviewQuestion.setCourse(null);
            }
            if(interviewQuestion.getKnowledgePoint().getId()==null)
            {
                interviewQuestion.setKnowledgePoint(null);
            }
        });
        interviewQuestionDao.saveAll(form.getInterviewQuestionList());
    }

    @Override
    public List<InterviewQuestion> parseWord(String text) {
        //取到所有超链接的p标签里的所有内容
        Matcher m = p.matcher(text);
        List<InterviewQuestion> interviewQuestionList = new ArrayList<>();
        while(m.find()) {
            InterviewQuestion interviewQuestion = new InterviewQuestion();
            String title = null;
            try {
                title=m.group(1);
                if(StringUtils.isNotBlank(title)&&!title.equalsIgnoreCase("<br>"))
                {
                    interviewQuestion.setTitle(title.replaceAll("面试:","").replaceAll("面试：",""));
                    String answer;
                    do
                    {
                        m.find();
                        answer = m.group(1);
                        interviewQuestion.setAnswer(answer.replaceAll("答:","").replaceAll("答：",""));
                    }while (StringUtils.isBlank(answer)||answer.equalsIgnoreCase("<br>"));

                    interviewQuestion.setCourse(new Course());
                    interviewQuestion.setKnowledgePoint(new KnowledgePoint());
                    interviewQuestionList.add(interviewQuestion);
                }
            }
            catch (Exception e)
            {
                new RuntimeException(title + "附近格式有问题");
            }
        }

        return interviewQuestionList;
    }

    public static void main(String[] args) throws DocumentException {
//        SAXReader reader = new SAXReader();
//        String s = "<list><p>面试：开始会问你学历，年龄，毕业好久？</p><p>答：根据自身回答</p><p>面试：自我介绍下，以及介绍下你的项目</p><p>答：控制在3分钟的样子，毕竟介绍下项目</p><p>面试：介绍第一个项目</p><p>答：本次项目用到前后端分离技术，前段我们使用了门户系统，用户用来登陆注册，插看我的订单，购物车。。。。。。。。。。。我主要负责了订单，购物车，后台管理</p><p>面试：权限这块了解没有</p><p>答：这个模块我没有参与</p></list>";
//        Document doc = reader.read(new StringReader(s));
//        Element root = doc.getRootElement();
//        Iterator<Element> it = root.elementIterator();
//        while(it.hasNext())
//        {
//            Element e1 = it.next();//获取子元素
//            String text = e1.getText();
//            System.out.println(text);
//            Element answer = it.next();//获取子元素
//            System.out.println(answer.getText());
//        }


        String s = "<p><p><p>面试：开始会问你学历，年龄，毕业好久？</p><p>答：根据自身回答</p><p>面试：自我介绍下，以及介绍下你的项目</p><p>答：控制在3分钟的样子，毕竟介绍下项目</p><p>面试：介绍第一个项目</p><p>答：本次项目用到前后端分离技术，前段我们使用了门户系统，用户用来登陆注册，插看我的订单，购物车。。。。。。。。。。。我主要负责了订单，购物车，后台管理</p><p>面试：权限这块了解没有</p><p>答：这个模块我没有参与</p></p></p></p></p>";
        Pattern p = Pattern.compile("<p.*?>(.*?)</p>"); //取到所有超链接的a标签里的所有内容
//        Pattern p = Pattern.compile(regexStr);	//取到超链接的地址
        Matcher m = p.matcher(s);
        List<String> result = new ArrayList<String>();
        while(m.find()) {
            result.add(m.group(1));
        }

        System.out.println();
    }
}
