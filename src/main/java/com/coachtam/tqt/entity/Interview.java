package com.coachtam.tqt.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	学员信息跟踪
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-6-27 14:58:26
 */
@Getter
@Setter
@Entity
@Table(name="interview_p")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Interview {
	@Id
//	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
//	@GeneratedValue(generator = "jpa-uuid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	//学生
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	//笔试情况
	@Column(name = "bs_info")
	private String bsInfo;
	//面试情况
	@Column(name = "ms_info")
	private String msInfo;
	//附件(多个附件用逗号,隔开)
	@Column(name = "appendixs")
	private String appendixs;
	//录音
	@Column(name = "sound_recording")
	private String soundRecording;
	//面试公司
	@Column(name = "company_name")
	private String companyName;
	//创建日期
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "create_time")
	private Date createTime;
	//公司地址
	@Column(name = "company_addr")
	private String companyAddr;
	//公司电话
	@Column(name = "company_tel")
	private String companyTel;
	//面试时间
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "interview_time")
	private Date interviewTime;

	@Column(name = "experience")
	private String experience;
}
