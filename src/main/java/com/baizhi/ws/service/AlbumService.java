package com.baizhi.ws.service;

import com.baizhi.ws.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    Map queryAlbumByPage(int offset, int limit);

    Map insert(Album album);

    Map update(Album album);

    void delete(List<String> id);
}
