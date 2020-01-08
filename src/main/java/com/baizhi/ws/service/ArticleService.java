package com.baizhi.ws.service;

import com.baizhi.ws.entity.Article;

import java.util.Map;

public interface ArticleService {
    Map queryAllByPage(Integer page,Integer rows);

    void insert(Article article);

    void update(Article article);

    void delete(String id);

    Article queryById(String id);
}
