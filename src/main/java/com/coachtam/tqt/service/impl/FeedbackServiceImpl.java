package com.coachtam.tqt.service.impl;

import com.coachtam.tqt.entity.Feedback;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.respository.FeedbackDao;
import com.coachtam.tqt.service.FeedbackService;
import com.coachtam.tqt.service.UserService;
import com.coachtam.tqt.to.FeedbackForm;
import com.coachtam.tqt.utils.PageUtils;
import com.coachtam.tqt.vo.EchartLineStackVO;
import com.coachtam.tqt.vo.EchartVO;
import com.google.common.util.concurrent.AtomicDouble;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:	学习反馈
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-30 17:28:52
 */
@Transactional
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private UserService userService;


    @Override
    public Page<Feedback> page(Integer pageNo, Integer pageSize, String userId)
    {
        Sort sort = new Sort(Sort.Direction.DESC, "backTime");
        return  feedbackDao.findAllByUserId(userId,PageUtils.of(pageNo,pageSize,sort));
    }

    @Override
    public Page<Feedback> page(Integer pageNo, Integer pageSize, Specification<Feedback> specification) {
        Sort sort = new Sort(Sort.Direction.DESC, "backTime");
        return feedbackDao.findAll(specification,PageUtils.of(pageNo,pageSize,sort));
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackDao.findAll();
    }

    @Override
    public void save(Feedback bean) {
        bean.setBackTime(new Date());
        feedbackDao.save(bean);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids) {
            feedbackDao.deleteById(id);
        }

    }

    @Override
    public void update(Feedback bean) {

        feedbackDao.saveAndFlush(bean);
    }

    @Override
    public Feedback findById(String id) {
        return feedbackDao.findById(id).get();
    }

    @Override
    public List<Object[]> absorption(FeedbackForm feedbackForm) {
        Map<String, Object> paras = new HashMap<>();

        StringBuilder sb
                = new StringBuilder("select f.absorption,count(f.absorption) " +
                "from Feedback  f  " +
                "join f.user u " +
                "join f.course course " +
                "left join u.classes c " +
                " where 1 = 1");

        if(feedbackForm.getClassId()!=null && !feedbackForm.getClassId().isEmpty())
        {
            sb.append(" and c.id = :classId");
            paras.put("classId",feedbackForm.getClassId());
        }

        if(feedbackForm.getCourseId()!=null && !feedbackForm.getCourseId().isEmpty())
        {
            sb.append(" and course.id = :courseId");
            paras.put("courseId",feedbackForm.getCourseId());
        }

        if(feedbackForm.getDayNum()!=null && !feedbackForm.getDayNum().isEmpty())
        {
            sb.append(" and f.dayNum = :dayNum");
            paras.put("dayNum",Integer.valueOf(feedbackForm.getDayNum()));
        }

        if(feedbackForm.getStuName()!=null && !feedbackForm.getStuName().isEmpty())
        {
            sb.append(" and u.userInfo.name = :stuName");
            paras.put("stuName",feedbackForm.getStuName());
        }

        sb.append(" group by f.absorption");
        //创建jpql查询(hql)
        Query query = entityManager.createQuery(sb.toString());
        //设置查询参数
        paras.forEach((key,value)->{
            query.setParameter(key,value);
        });

        return query.getResultList();
    }

    @Override
    public EchartVO learnCurve(FeedbackForm feedbackForm) {

        EchartVO result = new EchartVO();

        Map<String, Object> paras = new HashMap<>();

        StringBuilder sb = new StringBuilder();

        if(StringUtils.isNotBlank(feedbackForm.getStuName()))
        {
            sb.append("select DATE_FORMAT(f.backTime,'%Y-%m-%d'),f.absorption " +
                    "from Feedback  f  " +
                    "join f.user u " +
                    "join f.course course " +
                    "join u.classes c " +
                    " where 1 = 1 ");

            if(feedbackForm.getClassId()!=null && !feedbackForm.getClassId().isEmpty())
            {
                sb.append(" and c.id = :classId");
                paras.put("classId",feedbackForm.getClassId());
            }

            if(feedbackForm.getCourseId()!=null && !feedbackForm.getCourseId().isEmpty())
            {
                sb.append(" and course.id = :courseId");
                paras.put("courseId",feedbackForm.getCourseId());
            }

            if(feedbackForm.getStuName()!=null && !feedbackForm.getStuName().isEmpty())
            {
                sb.append(" and u.userInfo.name = :stuName");
                paras.put("stuName",feedbackForm.getStuName());
            }

            sb.append(" order by f.backTime asc");
            //创建jpql查询(hql)
            Query query = entityManager.createQuery(sb.toString());
            //设置查询参数
            paras.forEach((key,value)->
                query.setParameter(key,value)
            );
            List<Object[]> list =  query.getResultList();

            list.forEach(objects -> {
                result.getTitles().add((String) objects[0]);
                //60-90 --> 60L
                try
                {
                    String[] split = ((String) objects[1]).split("-");
                    Long data = Long.valueOf(split[0]);
                    result.getValues().add(data);
                }
                catch (Exception e)
                {
                    result.getValues().add(0L);
                }

            });
        }
        else {
            if(StringUtils.isBlank(feedbackForm.getClassId()))
            {
                return null;
            }
            sb.append("SELECT backtime , avg(ab) FROM( SELECT DATE_FORMAT(f.back_Time , '%Y-%m-%d') backtime , SUBSTRING_INDEX(f.ABSORPTION , '-' , 1) ab FROM FEEDBACK_P f ");

            if(StringUtils.isNotBlank(feedbackForm.getClassId()))
            {
                sb.append("JOIN USER_P u ON f.USER_ID = u.USER_ID JOIN CLASSES_P c ON u.CLASS_ID = c.ID WHERE c.id = :classId");
                paras.put("classId", feedbackForm.getClassId());
            }
            if(StringUtils.isNotBlank(feedbackForm.getCourseId()))
            {
                sb.append(" and COURSE_ID = :couseId");
                paras.put("couseId", feedbackForm.getCourseId());
            }

            sb.append(") t GROUP BY backtime");
            Query nativeQuery = entityManager.createNativeQuery(sb.toString());
            //设置查询参数
            paras.forEach((key,value)->
                    nativeQuery.setParameter(key,value)
            );
            List<Object[]> list =   nativeQuery.getResultList();
            list.forEach(objects -> {
                result.getTitles().add((String) objects[0]);
                result.getValues().add(((Double)objects[1]).longValue());
            });
        }

        return result;
    }

    @Override
    public EchartLineStackVO learncurvepro(FeedbackForm searchForm) {
        EchartLineStackVO result = new EchartLineStackVO();
        Map<String, Object> paras = new HashMap<>();

        if(StringUtils.isNotBlank(searchForm.getClassId()))
        {

            //查询x轴日期列表
            StringBuilder sb = new StringBuilder(
                    "SELECT t.backtime FROM( SELECT DATE_FORMAT(f.back_Time , '%Y-%m-%d') backtime , ABSORPTION FROM FEEDBACK_P f JOIN USER_P u ON f.USER_ID = u.USER_ID WHERE u.CLASS_ID = :classId ");

            paras.put("classId", searchForm.getClassId());
            if(StringUtils.isNotBlank(searchForm.getCourseId()))
            {
                sb.append(" AND f.COURSE_ID = :courseId");
                paras.put("courseId", searchForm.getCourseId());
            }
            sb.append(" ) t GROUP BY t.backtime");


            Query nativeQuery = entityManager.createNativeQuery(sb.toString());
            paras.forEach((key,value)->
                    nativeQuery.setParameter(key,value)
            );
            List<String> dateList = nativeQuery.getResultList();
            List<User> userList = userService.findByClassId(searchForm.getClassId());
            List<String> userNameList = userList.stream().map(user -> user.getUserInfo().getName()).collect(Collectors.toList());


            result.setXData(dateList);
            List<Map<String, Object>> series = result.getSeries();

            //记录每个人的平均接收度
            Map<String, Double> avgMap = new HashMap<>(userList.size());
            userList.forEach(user->{
                    //查询这个人每一天吸收情况
                    List<Object[]> resultList = entityManager
                            .createNativeQuery("SELECT t.ti , avg(ab) FROM( SELECT DATE_FORMAT(f.back_Time , '%Y-%m-%d') ti , SUBSTRING_INDEX(f.ABSORPTION , '-' , 1) ab FROM FEEDBACK_P f WHERE f.USER_ID = :userId) t GROUP BY t.ti")
                            .setParameter("userId",user.getId())
                            .getResultList();

                    Map<String, Double> map = new HashMap<>();
                    resultList.forEach(objects -> {
                        map.put((String)objects[0],(Double)objects[1]);
                    });

                    Map<String, Object> serie = new HashMap<>();
                    //默认不选中
                    String name = user.getUserInfo().getName();
                    result.getSelected().put(name, false);
                    serie.put("name",name);
                    serie.put("type","line");
                    serie.put("smooth",true);
                    List<Double> data = new ArrayList<>();
                    //获取这个同学的平均吸收情况
                    AtomicDouble avg = new AtomicDouble();
                    try{
                        if(map.size()>0)
                        {
                            Double t =  map.values().stream().mapToDouble(Double::doubleValue).average().getAsDouble();
                            avg.set(t);
                            avgMap.put(name, t);
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    dateList.forEach(date->{
                        Double ab = map.get(date);
                        if(ab!=null)
                        {
                            data.add(ab);
                        }
                        else {
                            //如果当天没有提交，那么取他平均值
                            data.add(avg.get());
                        }
                    });
                    serie.put("data", data);
                    series.add(serie);

            });
            result.setSeries(series);
            //按照吸收程度升序排
            List<String> sortUserNameList = avgMap.entrySet().stream().sorted((e1, e2) -> {
                return e1.getValue().compareTo(e2.getValue());
            }).map(e -> e.getKey()).collect(Collectors.toList());
            result.setLegendData(sortUserNameList);

            //整体平均分
            Double totalAvg = avgMap.values().stream().mapToDouble(Double::doubleValue).average().getAsDouble();
            //查询平均接收最低的同学
            Map.Entry<String, Double> lastStu = avgMap.entrySet().stream().min((e1, e2) -> {
                return e1.getValue().compareTo(e2.getValue());
            }).get();

            result.getSelected().forEach((name,selected)->{
                if(name.equals(lastStu.getKey()))
                {
                    result.getSelected().put(name, true);
                }
            });
            //查询低于平均的同学
//            Map<String, Double> lastStuMap = avgMap.entrySet().stream().sorted((e1, e2) -> {
//                return e1.getValue().compareTo(e2.getValue());
//            }).filter(e -> e.getValue() < totalAvg).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

//            //将低于平均分的同学选中
//            result.getSelected().forEach((name,selected)->{
//                if(lastStuMap.keySet().contains(name))
//                {
//                    result.getSelected().put(name, true);
//                }
//            });

        }

        return result;
    }

    @Override
    public List<User> unCommitedList(Specification<Feedback> specification, FeedbackForm searchForm) {
        //所有已提交的反馈列表
        List<Feedback> commitedList = feedbackDao.findAll(specification);

        //查询当前班级所有人,存入map中，然后根据查询到的人一个一个的剔除，剩下的就是未提交的人
        List<User> userList = userService.findByClassId(searchForm.getClassId());

        //已提交的用户
        List<User> commitedUserList = commitedList.stream().map(f -> f.getUser()).collect(Collectors.toList());
        return userList.stream().filter(user -> !commitedUserList.contains(user)).collect(Collectors.toList());
    }
}
