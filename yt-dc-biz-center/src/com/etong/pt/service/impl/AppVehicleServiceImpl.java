package com.etong.pt.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.data.auto.car.Car;
import com.etong.data.auto.vehicle.Vehicle;
import com.etong.dc.auto.service.AutoService;
import com.etong.pt.constants.EnumConstant;
import com.etong.pt.dao.AppVehicleDao;
import com.etong.pt.data.appvehicle.AppVehicle;
import com.etong.pt.service.AppVehicleService;
import com.etong.pt.util.CarShopUtils;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;

import java.util.*;

/**
 * Created by chenlinyang on 2015/11/26.
 */
public class AppVehicleServiceImpl implements AppVehicleService {
    private AppVehicleDao appVehicleDao;
    private AutoService autoService;

    public void setAppVehicleDao(AppVehicleDao appVehicleDao) {
        this.appVehicleDao = appVehicleDao;
    }

    public void setAutoService(AutoService autoService) {
        this.autoService = autoService;
    }

    @Override
    public PtResult getAppBrand(String appId, Boolean status) {
        AppVehicle param = new AppVehicle();
        param.setStid(appId);
        param.setStatus(Boolean.TRUE);
        param.setLevel(EnumConstant.VehicleLevel.BRAND.getValue());

        PtResult ptResult = appVehicleDao.findByParam(param);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        if(status == Boolean.TRUE) {
            if(!ptResult.isSucceed()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            List<AppVehicle> list = ptResult.<List>getObject();
            for (AppVehicle record : list) {
                jsonObject = new JSONObject();
                jsonObject.put("id", record.getCarbrandid());
                //jsonObject.put("title", record.getCarbrand());
                ptResult = autoService.getBrandInfo(record.getCarbrandid());
                if (!ptResult.isSucceed()) {
                    return ptResult;
                }
                List<Vehicle> brands = ptResult.getObject();
                Vehicle brand = brands.get(0);
                jsonObject.put("title", brand.getTitle());
                jsonObject.put("logo", CarShopUtils.trimPhoto(brand.getImage()));
                jsonObject.put("status", Boolean.TRUE);
                jsonArray.add(jsonObject);
            }
        }
        else {
            List<AppVehicle> list = new ArrayList<AppVehicle>();
            if(ptResult.isSucceed()) {
                list = ptResult.<List>getObject();
            }

            ptResult= autoService.getBrands();
            if (!ptResult.isSucceed()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            @SuppressWarnings("unchecked")
            List<Vehicle> brands = ptResult.getObject();
            for(Iterator<Vehicle> iter = brands.iterator(); iter.hasNext();) {
                Vehicle record = iter.next();

                for (AppVehicle _record : list) {
                    if(record.getId().intValue() == _record.getCarbrandid()) {
                        iter.remove();
                        continue;
                    }
                }

            }
            for(Vehicle brand : brands) {
                jsonObject = new JSONObject();
                jsonObject.put("id", brand.getId());
                jsonObject.put("title", brand.getTitle());
                jsonObject.put("logo", CarShopUtils.trimPhoto(brand.getImage()));
                jsonObject.put("status", Boolean.FALSE);
                jsonArray.add(jsonObject);
            }
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    @Override
    public PtResult editAppBrand(String appId, Integer brandId, Boolean status) {
        AppVehicle record = null;

        AppVehicle param = new AppVehicle();
        param.setStid(appId);
        param.setCarbrandid(brandId);
        param.setLevel(EnumConstant.VehicleLevel.BRAND.getValue());
        PtResult ptResult = appVehicleDao.findByParam(param);
        if (ptResult.isSucceed()) {
            List<AppVehicle> list = ptResult.<List>getObject();
            record = list.get(0);
            record.setStatus(status);
            return appVehicleDao.update(record);
        }
        else {
            record = new AppVehicle();
            record.setStid(appId);
            record.setCarbrandid(brandId);
            record.setStatus(status);
            record.setLevel(EnumConstant.VehicleLevel.BRAND.getValue());
            return appVehicleDao.add(record);
        }
    }

    @Override
    public PtResult findExistBrands(String appId) {
        AppVehicle param = new AppVehicle();
        param.setStid(appId);
        param.setStatus(Boolean.TRUE);
        param.setLevel(EnumConstant.VehicleLevel.BRAND.getValue());

        return appVehicleDao.findByParam(param);
    }

    /*@Override
    public PtResult add(AppVehicle record) {
        return appVehicleDao.add(record);
    }

    @Override
    public PtResult update(AppVehicle record) {
        return appVehicleDao.update(record);
    }*/

    @Override
    public PtResult getAppCar(String appId, Integer brandId, Integer carsetId, Boolean status,
                              Integer page, Integer pageSize) {
        AppVehicle param = new AppVehicle();
        param.setStid(appId);
        param.setStatus(Boolean.TRUE);
        param.setLevel(EnumConstant.VehicleLevel.CAR.getValue());

        PtResult ptResult = appVehicleDao.findByParam(param);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<AppVehicle> list = ptResult.<List>getObject();

        ptResult = autoService.getCarByCarsetId(carsetId);

        if (!ptResult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
        }

        @SuppressWarnings("unchecked")
        List<Car> carInfoList = ptResult.getObject();

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < carInfoList.size(); i++) {
            Car carInfo = carInfoList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", Boolean.FALSE);

            for (AppVehicle record : list) {
                if (record.getCartypeid() == carInfo.getVid()) {
                    jsonObject.put("status", record.getStatus());
                    break;
                }
            }

            jsonObject.put("id", carInfo.getVid());
            jsonObject.put("title", carInfo.getFullName());
            jsonObject.put("image", CarShopUtils.trimPhoto(carInfo.getImage()));
            jsonObject.put("guidePrice", carInfo.getPrices());
            jsonObject.put("brand", carInfo.getBrand());
            jsonObject.put("carSetName", carInfo.getCarset());
            jsonObject.put("carName", carInfo.getTitle());

            if(status == null || status == jsonObject.getBoolean("status")) {
                jsonArray.add(jsonObject);
            }

        }

        int size = jsonArray.size();
        //在内存做分页
        int start = 0;

        if (page != null && pageSize != null) {
            start = (page - 1) * pageSize;
            size = Math.min(start + pageSize, size);
        }

        if (start >= size) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
        }

        JSONArray resultArray = new JSONArray();
        for(int i = start; i < size; i ++) {
            resultArray.add(jsonArray.get(i));
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("carList", resultArray);
        resultMap.put("total", jsonArray.size());

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, resultMap);
    }

    @Override
    public PtResult editAppCar(String appId, Integer brandId, Integer carsetId, Integer carId, Boolean status) {
        AppVehicle param = new AppVehicle();
        param.setStid(appId);
        param.setCartypeid(carId);
        param.setLevel(EnumConstant.VehicleLevel.CAR.getValue());

        PtResult ptResult = appVehicleDao.findByParam(param);
        AppVehicle record = null;
        if(!ptResult.isSucceed()) {
            record = new AppVehicle();
            record.setStid(appId);
            record.setCarbrandid(brandId);
            record.setCarsetid(carsetId);
            record.setCartypeid(carId);
            record.setLevel(EnumConstant.VehicleLevel.CAR.getValue());
            record.setStatus(status);

            ptResult = appVehicleDao.add(record);
            return ptResult;
        }
        else {
            List<AppVehicle> list = ptResult.<List>getObject();
            Long id = list.get(0).getDvid();

            record = new AppVehicle();
            record.setDvid(id);
            record.setStatus(status);

            ptResult = appVehicleDao.update(record);
            return ptResult;
        }
    }

    /*@Override
    public PtResult findExistCarset(String appId, Integer carsetId) {
        AppVehicle param = new AppVehicle();
        param.setStid(appId);
        param.setStatus(Boolean.TRUE);
        param.setLevel(EnumConstant.VehicleLevel.CAR.getValue());
        param.setCarsetid(carsetId);

        return appVehicleDao.findByParam(param);
    }

    @Override
    public PtResult findExistCar(String appId, Integer carId) {
        AppVehicle param = new AppVehicle();
        param.setStid(appId);
        param.setStatus(Boolean.TRUE);
        param.setLevel(EnumConstant.VehicleLevel.CAR.getValue());
        param.setCartypeid(carId);

        return appVehicleDao.findByParam(param);
    }*/
}
