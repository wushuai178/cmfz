package com.baizhi.ws.dao;

import com.baizhi.ws.entity.Admin;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AdminDao extends Mapper<Admin> {
}
