package com.coachtam.tqt.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	入职邀请
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-10-23 9:45:45
 */
@Getter
@Setter
@Entity
@Table(name="OFFER_P")
public class Offer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	//公司名字
	@Column(name = "company")
	private String company;
	//薪资
	@Column(name = "salary")
	private Double salary;
	//入职日期
	@Column(name = "join_date")
	private Date joinDate;
	//学生
	@Column(name = "user_id")
	private String userId;
	//是否入职
	@Column(name = "is_entry")
	private Boolean isEntry;
	//福利待遇
	@Column(name = "welfare")
	private String welfare;


}
