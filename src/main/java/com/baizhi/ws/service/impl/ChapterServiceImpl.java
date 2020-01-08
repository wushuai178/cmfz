package com.baizhi.ws.service.impl;

import com.baizhi.ws.dao.ChapterDao;
import com.baizhi.ws.entity.Chapter;
import com.baizhi.ws.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryChapterByPage(String albumId, int offset, int limit) {
        Chapter chapter = new Chapter();
        HashMap map = new HashMap();
        chapter.setAlbumId(albumId);
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, new RowBounds((offset - 1) * limit, limit));
        int count = chapterDao.selectCount(chapter);
        map.put("records",count);
        map.put("total",count%limit==0 ? count/limit : count/limit+1);
        map.put("page",offset);
        map.put("rows",chapters);
        return map;
    }

    @Override
    public void insert(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapterDao.insertSelective(chapter);
    }

    @Override
    public void update(Chapter chapter) {
        /*if (chapter.getUrl().equals("")){
            chapter.setUrl(null);
        }*/
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    public void delete(List<String> ids) {
        chapterDao.deleteByIdList(ids);
    }
}
