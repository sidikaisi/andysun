package com.andysun.controller;

import com.andysun.drools.IDroolsService;
import com.andysun.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2019/7/26
 *  @Version 1.0.0
 **/
@RestController
@RequestMapping("drools/test")
public class DroolsController {

	@Autowired
	private IDroolsService droolsService;

	@GetMapping("test-rule")
	public UserEntity testRule() {
		return droolsService.testRule();
	}
}
