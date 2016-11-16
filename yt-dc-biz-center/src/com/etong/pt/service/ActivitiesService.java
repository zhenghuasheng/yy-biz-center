package com.etong.pt.service;

import com.etong.pt.data.activities.Activities;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/12/1.
 */
public interface ActivitiesService {
    PtResult findByAppId(String appId);
    PtResult findByParam(Activities param, Boolean status);
    PtResult getById(Long id);
    PtResult add(Activities record);
    PtResult update(Activities record);
    PtResult delete(Long id);
}
