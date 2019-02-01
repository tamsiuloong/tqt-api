package com.coachtam.tqt.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	模块
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-1 13:54:18
 */
@Getter
@Setter
@Entity
@Table(name="module_p")
public class Module {
	@Id
	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
	@GeneratedValue(generator = "jpa-uuid")
	@Column(name = "module_id")
	private String id;


	@Column(name = "parent_id")
	private String parentId;

	@Column(name = "parent_name")
	private String parentName;

	@Column(name = "name")
	private String name;

	@Column(name = "layer_num")
	private Long layerNum;

	@Column(name = "is_leaf")
	private Long isLeaf;

	@Column(name = "ico")
	private String ico;

	@Column(name = "cpermission")
	private String cpermission;

	@Column(name = "curl")
	private String curl;

	@Column(name = "ctype")
	private Long ctype;

	@Column(name = "state")
	private Long state;

	@Column(name = "belong")
	private String belong;

	@Column(name = "cwhich")
	private String cwhich;

	@Column(name = "quote_num")
	private String quoteNum;

	@Column(name = "remark")
	private String remark;

	@Column(name = "order_no")
	private Long orderNo;

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

	@ManyToMany(mappedBy = "moduleSet")
	private Set<Role> roleSet = new HashSet<>();
}
