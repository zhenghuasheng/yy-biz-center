package com.etong.pt.dao;

import com.etong.pt.data.activities.Activities;
import com.etong.pt.utility.PtResult;

/**
 * Created by chenlinyang on 2015/12/1.
 */
public interface ActivitiesDao {
    PtResult findByParam(Activities param, Boolean status);
    PtResult getById(Long id);
    PtResult add(Activities record);
    PtResult update(Activities record);
    PtResult delete(Long id);
}
