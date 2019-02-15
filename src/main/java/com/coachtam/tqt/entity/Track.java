package com.coachtam.tqt.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-14 15:41:23
 */
@Getter
@Setter
@Entity
@Table(name="track_p")
public class Track {
	@Id
	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
	@GeneratedValue(generator = "jpa-uuid")
	@Column(name = "id")
	private String id;

	//描述
	@Column(name = "description")
	private String description;
	//学生
//	@Column(name = "user_id")
//	private String userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	//创建日期
	@Column(name = "create_time")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createTime;
	//创建者
	@Column(name = "create_by")
	private String createBy;
	//状态
	@Column(name = "status")
	private Integer status;


}
