package com.etong.pt.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.data.auto.carparam.CarParam;
import com.etong.data.auto.vehicle.Vehicle;
import com.etong.dc.auto.service.AutoService;
import com.etong.pt.dao.SalesVehicleDao;
import com.etong.pt.data.salesvehicle.SalesVehicle;
import com.etong.pt.service.SalesVehicleService;
import com.etong.pt.util.CarShopUtils;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import java.util.List;
import java.util.Map;

/**
 * Created by chenlinyang on 2015/10/20.
 */
public class SalesVehicleServiceImpl implements SalesVehicleService {
    private SalesVehicleDao salesVehicleDao;
    private AutoService autoService;

    public void setSalesVehicleDao(SalesVehicleDao salesVehicleDao) {
        this.salesVehicleDao = salesVehicleDao;
    }

    public void setAutoService(AutoService autoService) {
        this.autoService = autoService;
    }

    @Override
    public PtResult getSales(String appId) {
        SalesVehicle param = new SalesVehicle();
        param.setStid(appId);
        PtResult ptResult = salesVehicleDao.findByParam(param);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        else {
            List<SalesVehicle> list = ptResult.getObject();
            JSONArray jsonArray = new JSONArray(list.size());
            JSONObject jsonObject = null;
            for (SalesVehicle record : list) {
                jsonObject = new JSONObject();
                jsonObject.put("id", record.getVid());
//                EtongResult etongResult = carShopService.getCarInfo(record.getF_vid());
//                if (!etongResult.isSucceed()) {
//                    return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
//                }
//                @SuppressWarnings("unchecked")
//                Map<String, Object> carInfoMap = (Map<String, Object>) etongResult.getResult();
//                CarInfo carInfo = (CarInfo) carInfoMap.get("CarInfo");
//
//                jsonObject.put("brand", carInfo.getBrand());
//                jsonObject.put("title", carInfo.getCarSetName() + " " + carInfo.getDetailName() );
//                jsonObject.put("photo", CarShopUtils.trimPhoto(carInfo.getPhoto()));
                jsonObject.put("lessPrice", record.getLessprice());
                jsonObject.put("endDate", record.getEnddate());
                jsonArray.add(jsonObject);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
        }
    }

    @Override
    public PtResult findByParam(SalesVehicle param) {
        PtResult ptResult = salesVehicleDao.findByParam(param);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<SalesVehicle> list = ptResult.<List>getObject();
        for (SalesVehicle record : list) {
            // 品牌
            if(record.getCarbrandid() != null) {
                ptResult = autoService.getBrandInfo(record.getCarbrandid());
                if(ptResult.isSucceed()) {
                    List<Vehicle> brands = ptResult.getObject();
                    Vehicle brand = brands.get(0);
                    record.setCarbrand(brand.getTitle());
                }
            }
            // 车型
            if(record.getCarsetid() != null) {
                ptResult = autoService.getCarsetInfo(record.getCarsetid());
                if(ptResult.isSucceed()) {
                    List<Vehicle> carsets = ptResult.getObject();
                    Vehicle carset = carsets.get(0);
                    record.setCarset(carset.getTitle());
                }
            }
            // 车款
            if(record.getVid() != null) {
                ptResult = autoService.getCarInfo(record.getVid());
                if(ptResult.isSucceed()) {
                    CarParam carInfo = ptResult.getObject();
                    record.setCartype(carInfo.getFullname());
                }
            }
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list);
    }

    @Override
    public PtResult add( SalesVehicle record) {
        return salesVehicleDao.add(record);
    }

    @Override
    public PtResult update( SalesVehicle record) {
        return salesVehicleDao.update(record);
    }

    @Override
    public PtResult delete(Integer id) {
        return salesVehicleDao.delete(id);
    }
}
