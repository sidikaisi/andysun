package com.andysun.repository;

import com.andysun.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component(value = "userEsRepository")
public interface UserEsRepository extends ElasticsearchRepository<UserEntity, Long> {

}
