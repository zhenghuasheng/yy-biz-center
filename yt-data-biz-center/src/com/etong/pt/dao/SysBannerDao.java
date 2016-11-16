package com.etong.pt.dao;

import com.etong.pt.data.sysbanner.SysBanner;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/11/30.
 */
public interface SysBannerDao {
    PtResult findByParam(SysBanner param);
    PtResult getById(Long id);
    PtResult add(SysBanner record);
    PtResult update(SysBanner record);
    PtResult delete(Long id);
}
