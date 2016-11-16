package com.etong.pt.dao;

import com.etong.pt.data.app.App;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/10/19.
 */
public interface AppDao {
    PtResult findAll();
    PtResult getById(String id);
    //PtResult findByPraram();
    PtResult add(App record);
    PtResult edit(App record);
    PtResult remove(String id);
    PtResult getAppinfoList(String pstid,Integer start,Integer limit);
}
