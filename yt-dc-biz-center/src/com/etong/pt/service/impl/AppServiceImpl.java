package com.etong.pt.service.impl;

import com.etong.pt.dao.AppDao;
import com.etong.pt.data.app.App;
import com.etong.pt.service.AppService;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/10/19.
 */
public class AppServiceImpl implements AppService {
    private AppDao appDao;

    public void setAppDao(AppDao appDao) {
        this.appDao = appDao;
    }

    @Override
    public PtResult getById(String id) {
        return appDao.getById(id);
    }

    @Override
    public PtResult addApp(App app) {
        return appDao.add(app);
    }

    @Override
    public PtResult getAppList(String psid, Integer start, Integer limit) {
        return appDao.getAppinfoList(psid,start,limit) ;
    }

    @Override
    public PtResult edit(App app) {
        return appDao.edit(app);
    }
}
