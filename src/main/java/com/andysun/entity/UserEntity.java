package com.andysun.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2019/4/28
 *  @Version 1.0.0
 **/
@Data
@Document(indexName = "test",type = "user")
public class UserEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Date birthday;
}
