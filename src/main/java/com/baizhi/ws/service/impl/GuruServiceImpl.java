package com.baizhi.ws.service.impl;

import com.baizhi.ws.dao.GuruDao;
import com.baizhi.ws.entity.Guru;
import com.baizhi.ws.service.GuruService;
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
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryAllByPage(int page, int rows) {
        HashMap map = new HashMap();
        List<Guru> gurus = guruDao.selectByExampleAndRowBounds(null, new RowBounds((page - 1) * rows, rows));
        int count = guruDao.selectCount(null);
        map.put("records",count);
        map.put("page",page);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("rows",gurus);
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Guru> queryAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

    @Override
    public Map insertGuru(Guru guru) {
        HashMap map = new HashMap();
        guru.setId(UUID.randomUUID().toString());
        guruDao.insertSelective(guru);
        map.put("guruId",guru.getId());
        return map;
    }

    @Override
    public void deleteGuru(List<String> id) {
        guruDao.deleteByIdList(id);
    }

    @Override
    public Map updateGuru(Guru guru) {
        HashMap map = new HashMap();
        guruDao.updateByPrimaryKeySelective(guru);
        map.put("guruId",guru.getId());
        return map;
    }
}
