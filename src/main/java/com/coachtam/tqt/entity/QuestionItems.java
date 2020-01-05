package com.coachtam.tqt.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	试卷试题选项
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-4 17:31:19
 */
@Getter
@Setter
@Entity
@Table(name="QUESTION_ITEMS_P")
@JsonIgnoreProperties({"question"})
public class QuestionItems {
	@Id
	@GeneratedValue(generator = "pkGenerator")
	@GenericGenerator(
			name = "pkGenerator",
			strategy = "foreign",
			parameters = @org.hibernate.annotations.Parameter(name = "property", value = "question"))
	@Column(name = "id")
	private Integer id;

	//问题选项
	@Column(name = "content")
	private String content;

//	@Column(name = "create_time")
//	private Date createTime;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@PrimaryKeyJoinColumn  // 这个注解不能少。如果不加这个注解，添加扩展信息时，就会自动在person_ext表中增加了一个外键列person_id.
	private Question question;


}
