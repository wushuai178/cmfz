package com.baizhi.ws.dao;

import com.baizhi.ws.entity.Counter;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CounterDao extends Mapper<Counter> {
}
