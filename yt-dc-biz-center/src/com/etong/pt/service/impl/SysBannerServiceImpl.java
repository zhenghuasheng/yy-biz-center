package com.etong.pt.service.impl;

import com.etong.pt.dao.SysBannerDao;
import com.etong.pt.data.sysbanner.SysBanner;
import com.etong.pt.service.SysBannerService;
import com.etong.pt.utility.PtResult;

import java.util.Date;

/**
 * Created by chenlinyang on 2015/11/30.
 */
public class SysBannerServiceImpl implements SysBannerService {
    private SysBannerDao sysBannerDao;

    public void setSysBannerDao(SysBannerDao sysBannerDao) {
        this.sysBannerDao = sysBannerDao;
    }

    @Override
    public PtResult findByAppId(String appId) {
        SysBanner param = new SysBanner();
        param.setStid(appId);
        return sysBannerDao.findByParam(param);
    }

    @Override
    public PtResult add(SysBanner record) {
        int now = new Long(new Date().getTime()/1000).intValue();
        record.setInitdate(now);
        return sysBannerDao.add(record);
    }

    @Override
    public PtResult update(SysBanner record) {
        return sysBannerDao.update(record);
    }

    @Override
    public PtResult delete(Long id) {
        return sysBannerDao.delete(id);
    }
}
