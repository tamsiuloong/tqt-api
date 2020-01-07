package com.coachtam.tqt.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	用户操作日志
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2020-1-7 20:16:05
 */
@Getter
@Setter
@Entity
@Table(name="USER_EVENT_LOG_P")
@AllArgsConstructor
@NoArgsConstructor
public class UserEventLog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	//操作用户
	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_name")
	private String userName;
	//名字
	@Column(name = "real_name")
	private String realName;
	//操作内容
	@Column(name = "content")
	private String content;
	//操作时间
	@Column(name = "create_time")
	private Date createTime;

	public UserEventLog(String userId, String userName, String realName, Date createTime) {
		this.userId = userId;
		this.userName = userName;
		this.realName = realName;
		this.createTime = createTime;
	}

}
