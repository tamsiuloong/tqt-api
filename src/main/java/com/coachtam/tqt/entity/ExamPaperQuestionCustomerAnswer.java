package com.coachtam.tqt.entity;

import java.util.Date;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	学生考试答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:29:42
 */
@Getter
@Setter
@Entity
@Table(name="EXAM_PAPER_QUESTION_CUSTOMER_ANSWER_P")
public class ExamPaperQuestionCustomerAnswer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//所属题目
	@Column(name = "question_id")
	private Integer questionId;
	//所属试卷
	@Column(name = "exam_paper_id")
	private Integer examPaperId;
	//所属试卷答案
	@Column(name = "exam_paper_answer_id")
	private Integer examPaperAnswerId;

	@Column(name = "question_type")
	private Integer questionType;

	@Column(name = "course_id")
	private String courseId;
	//回答分数
	@Column(name = "customer_score")
	private Integer customerScore;
	//问题分数
	@Column(name = "question_score")
	private Integer questionScore;

	@Column(name = "question_text_content_id")
	private Integer questionTextContentId;
	//答案
	@Column(name = "answer")
	private String answer;

	@Column(name = "text_content_id")
	private Integer textContentId;
	//是否正确
	@Column(name = "do_right")
	private Boolean doRight;

	@Column(name = "user_id")
	private String userId;
	//创建日期
	@Column(name = "create_time")
	private Date createTime;
	//排序
	@Column(name = "item_order")
	private Integer itemOrder;


}
