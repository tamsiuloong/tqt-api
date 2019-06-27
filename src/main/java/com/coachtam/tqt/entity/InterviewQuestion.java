package com.coachtam.tqt.entity;

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
public class InterviewQuestion {
	@Id
	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
	@GeneratedValue(generator = "jpa-uuid")
	@Column(name = "id")
	private String id;

	//题目
	@Column(name = "title")
	private String title;
	//课程
	@Column(name = "course_id")
	private String courseId;
	//知识点
	@Column(name = "knowledge_point_id")
	private Integer knowledgePointId;
	//答案
	@Column(name = "answer")
	private String answer;
	//所属面试
	@Column(name = "interiew_id")
	private Integer interiewId;


}
