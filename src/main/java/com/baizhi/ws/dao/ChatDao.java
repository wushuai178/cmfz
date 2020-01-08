package com.baizhi.ws.dao;

import com.baizhi.ws.entity.Chat;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ChatDao extends Mapper<Chat> {
    List<Chat> queryByTime();
}
