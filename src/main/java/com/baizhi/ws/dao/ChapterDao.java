package com.baizhi.ws.dao;

import com.baizhi.ws.entity.Chapter;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChapterDao extends Mapper<Chapter>, DeleteByIdListMapper<Chapter,String> {
}
