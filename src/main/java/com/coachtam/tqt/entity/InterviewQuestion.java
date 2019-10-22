package com.coachtam.tqt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:49:47
 */
@Getter
@Setter
@Entity
@Table(name="interview_question_p")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class InterviewQuestion {
	@Id
//	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
//	@GeneratedValue(generator = "jpa-uuid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//题目
	@Column(name = "title")
	private String title;
	//课程
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;
	//知识点
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "knowledge_point_id")
	private KnowledgePoint knowledgePoint;
	//答案
	@Column(name = "answer")
	private String answer;
	//公司
	@Column(name = "company")
	private String company;
	//所属面试
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interiew_id")
	private Interview interview;


}
