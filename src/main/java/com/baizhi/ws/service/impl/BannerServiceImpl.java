package com.baizhi.ws.service.impl;

import com.baizhi.ws.dao.BannerDao;
import com.baizhi.ws.entity.Banner;
import com.baizhi.ws.entity.BannerDto;
import com.baizhi.ws.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Resource
    BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public BannerDto queryByPage(int offSet, int limit) {
        BannerDto bannerDto = new BannerDto();
        //数据行数
        int count = bannerDao.selectCount(new Banner());
        bannerDto.setRecords(count);
        //数据页数
        int page;
        if (count%limit==0){
            page = count/limit;
        }else {
            page = count/limit+1;
        }
        bannerDto.setTotal(page);
        //当前页
        bannerDto.setPage(offSet);
        //查询数据
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(),new RowBounds((offSet-1)*limit,limit));
        bannerDto.setRows(banners);
        return bannerDto;
    }

    @Override
    public void insert(Banner banner) {
        Date date = new Date();
        banner.setId(UUID.randomUUID().toString().replace("-",""));
        banner.setCreateDate(date);
        bannerDao.insert(banner);
    }

    @Override
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void delete(String id) {
        bannerDao.deleteByPrimaryKey(id);
    }

    @Override
    public Banner selectOne(String id) {
        Banner banner = bannerDao.selectByPrimaryKey(id);
        return banner;
    }

    @Override
    public void updateUrl(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void deleteMany(List<String> id) {
        bannerDao.deleteByIdList(id);
    }

    @Override
    public List<Banner> queryAll() {
        return bannerDao.selectAll();
    }

    @Override
    public void insertMany(List<Banner> list) {
        bannerDao.insertList(list);
    }
}
