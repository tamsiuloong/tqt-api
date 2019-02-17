package com.coachtam.tqt.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	请假
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-2-5 22:48:28
 */
@Getter
@Setter
@Entity
@Table(name="LEAVE_P")
public class Leave {
	@Id
	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
	@GeneratedValue(generator = "jpa-uuid")
	@Column(name = "id")
	private String id;


	//开始日期
	@Column(name = "start_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	//结束日期
	@Column(name = "end_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	//天数
	@Column(name = "total_day")
	private Integer totalDay;
	//审核人
	@Column(name = "reviewer")
	private String reviewer;
	//原因
	@Column(name = "reason")
	private String reason;
	//流程实例编号
	@Column(name = "process_instance_id")
	private String processInstanceId;
	//请假人
	@Column(name = "create_by")
	private String createBy;
	//请假日期
	@Column(name = "create_time")
	private Date createTime;

	//备注
	@Transient
	private String comment;

}
