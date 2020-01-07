package com.coachtam.tqt.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	任务试卷答案
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-7 21:49:38
 */
@Getter
@Setter
@Entity
@Table(name="TASK_EXAM_CUSTOMER_ANSWER_P")
public class TaskExamCustomerAnswer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;


	@Column(name = "task_exam_id")
	private Integer taskExamId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "text_content_id")
	private Integer textContentId;


}
