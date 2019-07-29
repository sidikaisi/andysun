package com.andysun.drools;

import com.alibaba.fastjson.JSONObject;
import com.andysun.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionsPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.SpringContextUtil;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2019/7/26
 *  @Version 1.0.0
 **/
@Service
@Slf4j
public class DroolsService implements IDroolsService {

	@Override
	public UserEntity testRule(){
		KieSessionsPool kieSessionsPool = (KieSessionsPool)SpringContextUtil.getBean(KieSessionsPool.class);
		UserEntity user = new UserEntity();
		UserEntity t = new UserEntity();
		t.setName("andysun");
		KieSession kieSession = kieSessionsPool.newKieSession();
		kieSession.insert(user);
		kieSession.insert(t);
		long t1 =System.currentTimeMillis();
		int count = kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter("test_"));
		log.info(">>>>>>规则个数{},耗时{}",count,(System.currentTimeMillis()-t1));
		log.info(">>>>>>,user={}",JSONObject.toJSONString(user));
		return user;
	}
}
