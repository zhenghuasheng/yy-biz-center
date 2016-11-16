package com.etong.pt.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.data.auto.car.Car;
import com.etong.data.auto.carparam.CarParam;
import com.etong.data.auto.vehicle.Vehicle;
import com.etong.dc.auto.service.AutoService;
import com.etong.pt.constants.EnumConstant;
import com.etong.pt.dao.HotVehicleDao;
import com.etong.pt.data.hotvehicle.HotVehicle;
import com.etong.pt.service.HotVehicleService;
import com.etong.pt.util.CarShopUtils;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;


import java.util.List;

/**
 * Created by chenlinyang on 2015/10/20.
 */
public class HotVehicleServiceImpl implements HotVehicleService {
    private HotVehicleDao hotVehicleDao;
    private AutoService autoService;

    public void setHotVehicleDao(HotVehicleDao hotVehicleDao) {
        this.hotVehicleDao = hotVehicleDao;
    }

    public void setAutoService(AutoService autoService) {
        this.autoService = autoService;
    }

    @Override
    public PtResult getHotBrand(String appId) {
        HotVehicle param = new HotVehicle();
        param.setF_type(EnumConstant.VehicleType.HOT_BRAND.getValue());
        param.setF_stid(appId);
        PtResult ptResult = hotVehicleDao.findByParam(param);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<HotVehicle> list = ptResult.getObject();
        JSONArray jsonArray = new JSONArray(list.size());
        JSONObject jsonObject = null;
        for (HotVehicle record : list) {
            jsonObject = new JSONObject();
            jsonObject.put("hvid", record.getF_hvid());
            jsonObject.put("id", record.getF_vid());

            PtResult etongResult = autoService.getCarsetInfo(record.getF_vid());

            if (!etongResult.isSucceed()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }
            List<Vehicle> vehicles=etongResult.getObject();
            Vehicle carSetInfo =vehicles.get(0);
            jsonObject.put("title", carSetInfo.getTitle());
            jsonObject.put("logo",CarShopUtils.trimPhoto(carSetInfo.getImage()));
            jsonArray.add(jsonObject);
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    @Override
    public PtResult addHotBrand(String appId, Integer brandId) {
        HotVehicle record = new HotVehicle();
        record.setF_stid(appId);
        record.setF_vid(brandId);
        record.setF_type(EnumConstant.VehicleType.HOT_BRAND.getValue());

        return hotVehicleDao.add(record);
    }

    @Override
    public PtResult getHotCarset(String appId) {
        HotVehicle param = new HotVehicle();
        param.setF_type(EnumConstant.VehicleType.HOT_CARSET.getValue());
        param.setF_stid(appId);
        PtResult ptResult = hotVehicleDao.findByParam(param);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<HotVehicle> list = ptResult.getObject();
        JSONArray jsonArray = new JSONArray(list.size());
        JSONObject jsonObject = null;
        for (HotVehicle record : list) {
            jsonObject = new JSONObject();
            jsonObject.put("hvid", record.getF_hvid());
            jsonObject.put("id", record.getF_vid());
            ptResult = autoService.getCarsetInfo(record.getF_vid(), "1001");
            if (!ptResult.isSucceed()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }
            List<Vehicle> carsets = ptResult.<List>getObject();
            Vehicle carSetInfo = carsets.get(0);
            jsonObject.put("title", carSetInfo.getTitle());
            jsonObject.put("photo", CarShopUtils.trimPhoto(carSetInfo.getImage()));
            jsonArray.add(jsonObject);
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    @Override
    public PtResult addHotCarset(String appId, Integer carsetId) {
        HotVehicle record = new HotVehicle();
        record.setF_stid(appId);
        record.setF_vid(carsetId);
        record.setF_type(EnumConstant.VehicleType.HOT_CARSET.getValue());

        return hotVehicleDao.add(record);
    }

    @Override
    public PtResult deleteHot(Integer id) {
        return hotVehicleDao.delete(id);
    }
}
