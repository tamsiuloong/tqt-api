package com.coachtam.tqt.entity;

import java.util.Date;

import com.coachtam.tqt.utils.StringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	试卷
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:28:31
 */
@Getter
@Setter
@Entity
@Table(name="EXAM_PAPER_P")
public class ExamPaper {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//试卷名字
	@Column(name = "name")
	private String name;
//	//课程
//	@Column(name = "course_id")
//	private String courseId;
	//班级
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private Classes classes;
	/*所属课程*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;
	//试卷类型
	@JsonSerialize(using = StringSerializer.class)
	@Column(name = "paper_type")
	private Integer paperType;
	//阶段
	@Column(name = "grade_level")
	private Integer gradeLevel;
	//得分
	@Column(name = "score")
	private Integer score;
	//题数
	@Column(name = "question_count")
	private Integer questionCount;
	//建议时间
	@Column(name = "suggest_time")
	private Integer suggestTime;
	//限制开始时间
	@Column(name = "limit_start_time")
	private Date limitStartTime;
	//限制结束时间
	@Column(name = "limit_end_time")
	private Date limitEndTime;
	//创建用户
//	@Column(name = "user_id")
//	private String userId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	//创建日期
	@Column(name = "create_time")
	private Date createTime;
	//是否删除
	@Column(name = "deleted")
	private Boolean deleted;
	//任务
	@Column(name = "task_exam_id")
	private Integer taskExamId;


}
