package com.baizhi.ws.dao;

import com.baizhi.ws.entity.Article;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article,String> {
    List<Article> queryArticleByFan(String userId);
}
