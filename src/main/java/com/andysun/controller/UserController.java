package com.andysun.controller;

import com.andysun.entity.UserEntity;
import com.andysun.repository.UserEsRepository;
import com.andysun.response.BaseResp;
import com.andysun.service.IUserService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2019/4/28
 *  @Version 1.0.0
 **/
@RestController
@RequestMapping("es/user")
public class UserController extends BaseController {

	// es 开始是注入不进来，需要增加 @EnableElasticsearchRepositories(basePackages = "com.andysun.repository")
	@Autowired
	private UserEsRepository userEsRepository;

	/**
	 * @Author zhangxiaojun
	 * @Description  保存到es
	 * @Date 14:18 2019/4/28
	 * @Param []
	 * @return com.andysun.response.BaseResp
	 * @Version 1.0.0
	 **/
	@GetMapping("saveUser")
	public BaseResp saveUser(){
		UserEntity userEntity = new UserEntity();
		userEntity.setId(System.currentTimeMillis());
		userEntity.setBirthday(new Date());
		userEntity.setName("张小俊");
		return new BaseResp().success(userEsRepository.save(userEntity));
	}

}
