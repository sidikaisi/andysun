package com.andysun.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2019/4/28
 *  @Version 1.0.0
 **/
@Data
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Date createTime;

	private Date updateTime;


}
