package com.coachtam.tqt.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	投票问题题目回复
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:29
 */
@Getter
@Setter
@Entity
@Table(name="VOTE_REPLY_P")
public class VoteReply {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "votereply_id")
	private Integer id;

	//回复内容
	@Column(name = "reply")
	private String reply;

	@Column(name = "subtopic_id")
	private Integer subtopicId;

	@Column(name = "voterecored_id")
	private Integer voterecoredId;

}
