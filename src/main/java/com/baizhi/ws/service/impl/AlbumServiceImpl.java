package com.baizhi.ws.service.impl;

import com.baizhi.ws.dao.AlbumDao;
import com.baizhi.ws.entity.Album;
import com.baizhi.ws.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryAlbumByPage(int offset, int limit) {
        HashMap map = new HashMap();
        List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds((offset - 1) * limit, limit));
        int count = albumDao.selectCount(null);
        map.put("page",offset);
        map.put("records",count);
        map.put("total",count%limit==0 ? count/limit: count/limit+1);
        map.put("rows",albums);
        return map;
    }

    @Override
    public Map insert(Album album) {
        HashMap map = new HashMap();
        album.setId(UUID.randomUUID().toString());
        map.put("albumId",album.getId());
        if (album.getCreateDate()==null || album.getCreateDate().equals("")){
            album.setCreateDate(new Date());
        }
        albumDao.insertSelective(album);
        return map;
    }

    @Override
    public Map update(Album album) {
        HashMap map = new HashMap();
        map.put("albumId",album.getId());
        albumDao.updateByPrimaryKeySelective(album);
        return map;
    }

    @Override
    public void delete(List<String> id) {
        albumDao.deleteByIdList(id);
    }


}
