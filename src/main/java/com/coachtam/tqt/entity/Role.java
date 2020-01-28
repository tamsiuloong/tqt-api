package com.coachtam.tqt.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	角色
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-1 13:50:25
 */
@Getter
@Setter
@Entity
@Table(name="ROLE_P")
@JsonIgnoreProperties({"moduleSet"})
public class Role {
	@Id
	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
	@GeneratedValue(generator = "jpa-uuid")
	@Column(name = "role_id")
	private String id;


	@Column(name = "name")
	private String name;

	@Column(name = "remark")
	private String remark;

	@Column(name = "order_no")
	private Integer orderNo;

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
	@ManyToMany(
			targetEntity= Module.class
	)
	@JoinTable(
			name="ROLE_MODULE_P",
			joinColumns=@JoinColumn(name="ROLE_ID"),
			inverseJoinColumns=@JoinColumn(name="MODULE_ID")
	)
	private Set<Module> moduleSet = new HashSet<>();

	//just for view
	@Transient
	private String[] moduleIds;
}
