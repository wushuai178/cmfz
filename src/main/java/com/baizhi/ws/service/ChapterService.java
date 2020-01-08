package com.baizhi.ws.service;

import com.baizhi.ws.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    Map queryChapterByPage(String albumId,int offset,int limit);

    void insert(Chapter chapter);

    void update(Chapter chapter);

    void delete(List<String> ids);
}
