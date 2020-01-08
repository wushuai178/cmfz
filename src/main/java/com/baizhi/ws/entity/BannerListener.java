package com.baizhi.ws.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.ws.config.MyWebAware;
import com.baizhi.ws.dao.BannerDao;

import java.util.ArrayList;

public class BannerListener extends AnalysisEventListener<Banner> {
    private ArrayList<Banner> list =  new ArrayList<Banner>();

    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        list.add(banner);
        BannerDao bannerDao = (BannerDao)MyWebAware.getBeanByClass(BannerDao.class);
        bannerDao.insert(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
