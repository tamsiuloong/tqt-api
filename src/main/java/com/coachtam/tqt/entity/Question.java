package com.coachtam.tqt.entity;

import java.util.Date;

import com.coachtam.tqt.utils.StringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	试卷试题
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:30:46
 */
@Getter
@Setter
@Entity
@Table(name="QUESTION_P")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Question {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//题干
	@Column(name = "title")
	private String title;
	//解析
	@Column(name = "analyzer")
	private String analyze;
	//题型
	@JsonSerialize(using = StringSerializer.class)
	@Column(name = "question_type")
	private Integer questionType;

	/*所属课程*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;


//	@Column(name = "course_id")
//	private String courseId;

	//分数
	@Column(name = "score")
	private Integer score;
	//难度
	@Column(name = "difficult")
	private Integer difficult;
	//正确答案
	@Column(name = "correct")
	private String correct;

	//创建用户
//	@Column(name = "user_id")
//	private String userId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;


	@OneToOne(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.LAZY)
	private QuestionItems questionItems;

	//状态
	@Column(name = "status")
	private Integer status;
	//创建日期
	@Column(name = "create_time")
	private Date createTime;
	//删除
	@Column(name = "deleted")
	private Boolean deleted;


}
