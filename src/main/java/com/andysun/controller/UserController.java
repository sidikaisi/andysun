package com.andysun.controller;

import com.andysun.entity.UserEntity;
import com.andysun.repository.UserEsRepository;
import com.andysun.response.BaseResp;
import lombok.experimental.var;
import lombok.val;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	@PostMapping("save-user")
	public BaseResp saveUser(@RequestBody UserEntity userEntity){
		return new BaseResp().success(userEsRepository.save(userEntity));
	}

	/**
	 * @Author zhangxiaojun
	 * @Description  更新es
	 * @Date 14:54 2019/11/26
	 * @Param [userEntity]
	 * @return com.andysun.response.BaseResp
	 * @Version 1.0.0
	 **/
	@PostMapping("update-user")
	public BaseResp updateUser(@RequestBody UserEntity userEntity){
		return new BaseResp().success(userEsRepository.save(userEntity));
	}

	/**
	 * @Author zhangxiaojun
	 * @Description  查询用户
	 * @Date 14:55 2019/11/26
	 * @Param [userEntity]
	 * @return com.andysun.response.BaseResp
	 * @Version 1.0.0
	 **/
	@PostMapping("search-user")
	public BaseResp searchUser(@RequestBody UserEntity userEntity){
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		RangeQueryBuilder query = QueryBuilders.rangeQuery("age").from(30).to(60);
		builder.must(QueryBuilders.matchQuery("name",userEntity.getName()));
		builder.must(query);
		return new BaseResp().success(userEsRepository.search(builder));
	}
}
