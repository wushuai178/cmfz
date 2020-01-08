package com.baizhi.ws.dao;

import com.baizhi.ws.entity.Guru;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface GuruDao extends Mapper<Guru> , DeleteByIdListMapper<Guru,String> {
}
