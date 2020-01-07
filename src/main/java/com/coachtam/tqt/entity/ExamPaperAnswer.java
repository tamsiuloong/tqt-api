package com.coachtam.tqt.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	试卷答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:30:17
 */
@Getter
@Setter
@Entity
@Table(name="EXAM_PAPER_ANSWER_P")
public class ExamPaperAnswer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;


	@Column(name = "exam_paper_id")
	private Integer examPaperId;

	@Column(name = "paper_name")
	private String paperName;

	@Column(name = "paper_type")
	private Integer paperType;
	//课程
	@Column(name = "course_id")
	private String courseId;

	@Column(name = "system_score")
	private Integer systemScore;

	@Column(name = "user_score")
	private Integer userScore;

	@Column(name = "paper_score")
	private Integer paperScore;

	@Column(name = "question_correct")
	private Integer questionCorrect;

	@Column(name = "question_count")
	private Integer questionCount;

	@Column(name = "do_time")
	private Integer doTime;

	@Column(name = "status")
	private Integer status;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "task_exam_id")
	private Integer taskExamId;


}
