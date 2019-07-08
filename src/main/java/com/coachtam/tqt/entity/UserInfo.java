package com.coachtam.tqt.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	用户扩展信息
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-1 12:11:36
 */
@Getter
@Setter
@Entity
@Table(name="USER_INFO_P")
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties({"user"})
public class UserInfo {
	@Id
	@GeneratedValue(generator = "pkGenerator")
	@GenericGenerator(
			name = "pkGenerator",
			strategy = "foreign",
			parameters = @org.hibernate.annotations.Parameter(name = "property", value = "user"))
	@Column(name = "USER_INFO_ID")
	private String id;


	@Column(name = "name")
	private String name;

	@Column(name = "join_date")
	private Date joinDate;

	@Column(name = "salary")
	private String salary;

	@Column(name = "birthday")
	private Date birthday;


	@Column(name = "gender")
	private String gender;

	@Column(name = "station")
	private String station;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "degree")
	private Integer degree;

	@Column(name = "remark")
	private String remark;

	@Column(name = "order_no")
	private Integer orderNo;

	@Column(name = "email")
	private String email;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "create_dept")
	private String createDept;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "update_by")
	private String updateBy;

	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "manager_id")
	private String managerId;

	@Column(name = "major")
	private String major;//专业

	@Column(name = "experience")
	private Boolean experience;//工作经验

	@Column(name = "education")
	private Integer education;//学历

	@Column(name = "graduation_time")
	private Date graduationTime;//毕业时间

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@PrimaryKeyJoinColumn  // 这个注解不能少。如果不加这个注解，添加扩展信息时，就会自动在person_ext表中增加了一个外键列person_id.
	private User user;
}
