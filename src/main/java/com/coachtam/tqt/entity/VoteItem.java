package com.coachtam.tqt.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	投票项
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-7-8 16:51:58
 */
@Getter
@Setter
@Entity
@Table(name="VOTE_ITEM_P")
public class VoteItem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;

	@Column(name = "voteitem_id")
	private Integer voteitemId;

	@Column(name = "votetopic_id")
	private Integer votetopicId;
	//标题
	@Column(name = "title")
	private String title;
	//投票数量
	@Column(name = "vote_count")
	private Integer voteCount;
	//排列顺序
	@Column(name = "priority")
	private Integer priority;

	@Column(name = "subtopic_id")
	private Integer subtopicId;
	//图片
	@Column(name = "picture")
	private String picture;


}
