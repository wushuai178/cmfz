package com.baizhi.ws.dao;

import com.baizhi.ws.entity.Banner;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

@Repository
public interface BannerDao extends Mapper<Banner> , DeleteByIdListMapper<Banner,String>, InsertListMapper<Banner> {
    List<Banner> queryBannerByTime();
}
