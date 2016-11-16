package com.etong.pt.service.impl;

import com.etong.pt.dao.ActivitiesDao;
import com.etong.pt.dao.SalesVehicleDao;
import com.etong.pt.data.activities.Activities;
import com.etong.pt.data.salesvehicle.SalesVehicle;
import com.etong.pt.service.ActivitiesService;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;

import java.util.List;

/**
 * Created by chenlinyang on 2015/12/1.
 */
public class ActivitiesServiceImpl implements ActivitiesService {
    private ActivitiesDao activitiesDao;

    private SalesVehicleDao salesVehicleDao;

    public void setActivitiesDao(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    public void setSalesVehicleDao(SalesVehicleDao salesVehicleDao) {
        this.salesVehicleDao = salesVehicleDao;
    }

    @Override
    public PtResult findByAppId(String appId) {
        Activities param = new Activities();
        param.setStid(appId);
        return activitiesDao.findByParam(param, null);
    }

    @Override
    public PtResult findByParam(Activities param, Boolean status) {
        return activitiesDao.findByParam(param, status);
    }

    @Override
    public PtResult getById(Long id) {
        return activitiesDao.getById(id);
    }

    @Override
    public PtResult add(Activities record) {
        return activitiesDao.add(record);
    }

    @Override
    public PtResult update(Activities record) {
        return activitiesDao.update(record);
    }

    @Override
    public PtResult delete(Long id) {
        PtResult ptResult = activitiesDao.delete(id);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        SalesVehicle param = new SalesVehicle();
        param.setAcid(id);
        ptResult = salesVehicleDao.findByParam(param);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<SalesVehicle> children = ptResult.<List>getObject();
        for (SalesVehicle record : children) {
            ptResult = salesVehicleDao.delete(record.getSvid());
            if(!ptResult.isSucceed()) {
                return ptResult;
            }
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
    }
}
