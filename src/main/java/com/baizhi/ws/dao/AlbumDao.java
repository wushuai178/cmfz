package com.baizhi.ws.dao;

import com.baizhi.ws.entity.Album;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AlbumDao extends Mapper<Album>, DeleteByIdListMapper<Album,String> {
}
