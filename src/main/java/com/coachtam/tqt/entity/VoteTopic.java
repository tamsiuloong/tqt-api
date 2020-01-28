package com.coachtam.tqt.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import javax.persistence.*;
/**
 * @Description:	投票主题
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:50:38
 */
@Getter
@Setter
@Entity
@Table(name="VOTE_TOPIC_P")
@JsonIgnoreProperties({"teacher"})
public class VoteTopic {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "votetopic_id")
	private Integer id;

	@Column(name = "site_id")
	private Integer siteId;
	//标题
	@Column(name = "title")
	private String title;
	//描述
	@Column(name = "description")
	private String description;
	//开始时间
	@Column(name = "start_time")
	private Date startTime;
	//结束时间
	@Column(name = "end_time")
	private Date endTime;
	//重复投票限制时间，单位小时，为空不允许重复投票
	@Column(name = "repeate_hour")
	private Integer repeateHour;
	//总投票数
	@Column(name = "total_count")
	private Integer totalCount;
	//最多可以选择几项
	@Column(name = "multi_select")
	private Integer multiSelect;
	//是否限制会员
	@Column(name = "is_restrict_member")
	private String isRestrictMember;
	//是否限制IP
	@Column(name = "is_restrict_ip")
	private String isRestrictIp;
	//是否限制COOKIE
	@Column(name = "is_restrict_cookie")
	private String isRestrictCookie;
	//是否禁用
	@Column(name = "is_disabled")
	private String isDisabled;
	//是否默认主题
	@Column(name = "is_def")
	private String isDef;
	//是否限制微信
	@Column(name = "limit_weixin")
	private String limitWeixin;
	//限定微信投票每个用户每日投票次数,为0时则投票期内限定投票一次
	@Column(name = "vote_day")
	private Integer voteDay;
	//班级
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private Classes classes;
	//教师
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	private User teacher;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="votetopic_id",referencedColumnName = "votetopic_id")
	private List<VoteSubtopic> voteSubtopicList;

	@Transient
	private String teacherName;

	@Transient
	private String teacherId;

	public String getTeacherName() {
		if(teacher!=null&&teacher.getUserInfo()!=null&& StringUtils.isNotBlank(teacher.getUserInfo().getName())) {
			return teacher.getUserInfo().getName();
		}
		else {
			return "";
		}
	}
}
