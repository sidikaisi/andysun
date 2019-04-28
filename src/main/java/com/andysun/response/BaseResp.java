package com.andysun.response;

import lombok.Data;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2019/4/28
 *  @Version 1.0.0
 **/
@Data
public class BaseResp {

	private String successMessage;

	private String errorMessage;

	private String code = "0";

	private Object result;

	public BaseResp success(Object result){
		this.result = result;
		return this;
	}
}

