package com.coachtam.tqt.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	投票记录
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:46
 */
@Getter
@Setter
@Entity
@Table(name="VOTE_RECORD_P")
public class VoteRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;

	@Column(name = "voterecored_id")
	private Integer voterecoredId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "votetopic_id")
	private Integer votetopicId;
	//投票时间
	@Column(name = "vote_time")
	private Date voteTime;
	//投票IP
	@Column(name = "vote_ip")
	private String voteIp;
	//投票COOKIE
	@Column(name = "vote_cookie")
	private String voteCookie;
	//微信投票者OPENID
	@Column(name = "wx_open_id")
	private String wxOpenId;


}
