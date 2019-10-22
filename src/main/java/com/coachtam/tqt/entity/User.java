package com.coachtam.tqt.entity;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-31 11:38:33
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="USER_P")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User  {
	@Id
	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
	@GeneratedValue(generator = "jpa-uuid")
	@Column(name = "user_id")
	private String id;

	//用户名
	@Column(name = "user_name")
	private String userName;
	//密码
	@Column(name = "password")
	private String password;
	//状态 0.不可用    1.可用
	@Column(name = "state")
	private Integer state;


	//创建部门
	@Column(name = "dept_id")
	private String deptId;


	//笔记地址
	@Column(name = "note_url")
	private String noteUrl;


	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private UserInfo userInfo;


	/*所属班级*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private Classes classes;

//	@ManyToMany(
//			targetEntity=Role.class,
//			cascade={CascadeType.PERSIST, CascadeType.MERGE}
//	)
//	@JoinTable(
//			name="user_role_p",
//			joinColumns=@JoinColumn(name="USER_ID"),
//			inverseJoinColumns=@JoinColumn(name="ROLE_ID")
//	)


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLE_USER_P",joinColumns = @JoinColumn(name="USER_ID",referencedColumnName = "USER_ID"),inverseJoinColumns = @JoinColumn(name = "ROLE_ID",referencedColumnName = "ROLE_ID"))
	private Set<Role> roleSet = new HashSet<>();




	public User(String id) {
		this.id = id;
	}


}
