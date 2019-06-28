package com.coachtam.tqt.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	知识点
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-28 13:55:31
 */
@Getter
@Setter
@Entity
@Table(name="knowledge_point_p")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class KnowledgePoint {
	@Id
//	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
//	@GeneratedValue(generator = "jpa-uuid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//知识点
	@Column(name = "name")
	private String name;

	//所属课程
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;


}
