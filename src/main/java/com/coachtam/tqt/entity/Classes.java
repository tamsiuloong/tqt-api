package com.coachtam.tqt.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * @Description:	用户
 * @Author:			Coach tam
 * @Company:		坚持灵活  灵活坚持
 * @CreateDate:		2019-1-31 16:56:55
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="CLASSES_P")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Classes {
	@Id
	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
	@GeneratedValue(generator = "jpa-uuid")
	@Column(name = "id")
	private String id;
	//班级名字
	@Column(name = "name")
	private String name;
	//开班日期
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "begin_time")
	private Date beginTime;
	//结束日期
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "end_time")
	private Date endTime;
	//类型
	@Column(name = "type")
	private String type;
	//关闭-停止注册
	@Column(name = "closed")
	private Boolean closed;


	public Classes(String id) {
		this.id = id;
	}
}
