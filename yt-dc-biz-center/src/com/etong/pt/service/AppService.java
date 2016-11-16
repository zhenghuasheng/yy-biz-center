package com.etong.pt.service;

import com.etong.pt.data.app.App;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/10/19.
 */
public interface AppService {
    PtResult getById(String id);
    PtResult addApp(App app);
    PtResult getAppList(String psid,Integer start,Integer limit);
    PtResult edit(App app);
}
