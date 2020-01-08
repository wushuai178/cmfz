package com.baizhi.ws.service.impl;

import com.baizhi.ws.dao.ArticleDao;
import com.baizhi.ws.entity.Article;
import com.baizhi.ws.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryAllByPage(Integer page, Integer rows) {
        HashMap map = new HashMap();
        List<Article> articles = articleDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        int count = articleDao.selectCount(null);
        map.put("records",count);
        map.put("page",page);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("rows",articles);
        return map;
    }

    @Override
    public void insert(Article article) {
        articleDao.insertSelective(article);
    }

    @Override
    public void update(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public void delete(String id) {
        articleDao.deleteByPrimaryKey(id);
    }

    @Override
    public Article queryById(String id) {
        Article article = articleDao.selectByPrimaryKey(id);
        return article;
    }
}
