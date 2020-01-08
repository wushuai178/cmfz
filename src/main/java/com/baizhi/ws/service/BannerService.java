package com.baizhi.ws.service;

import com.baizhi.ws.entity.Banner;
import com.baizhi.ws.entity.BannerDto;

import java.util.List;

public interface BannerService {
    BannerDto queryByPage(int offSet, int limit);

    void insert(Banner banner);

    void update(Banner banner);

    void delete(String id);

    Banner selectOne(String id);

    void updateUrl(Banner banner);

    void deleteMany(List<String> id);

    List<Banner> queryAll();

    void insertMany(List<Banner> list);
}
