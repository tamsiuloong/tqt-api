package com.coachtam.tqt.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	投票子题目
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:01
 */
@Getter
@Setter
@Entity
@Table(name="VOTE_SUBTOPIC_P")
@JsonIgnoreProperties({"voteTopic"})
public class VoteSubtopic {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "subtopic_id")
	private Integer id;
	//标题
	@Column(name = "title")
	private String title;
	//投票（调查）
//	@Column(name = "votetopic_id")
//	private Integer votetopicId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "votetopic_id")
	private VoteTopic voteTopic;

	//类型（1单选，2多选，3文本）
	@Column(name = "subtopic_type")
	private Integer subtopicType;

	@Column(name = "priority")
	private Integer priority;


}
