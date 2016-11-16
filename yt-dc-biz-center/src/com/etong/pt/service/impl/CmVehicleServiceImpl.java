package com.etong.pt.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.data.auto.car.Car;
import com.etong.data.auto.vehicle.Vehicle;
import com.etong.dc.auto.service.AutoService;
import com.etong.pt.constants.EnumConstant;
import com.etong.pt.dao.CmVehicleDao;
import com.etong.pt.data.cmvehicle.CmVehicle;
import com.etong.pt.service.AppVehicleService;
import com.etong.pt.service.CmVehicleService;
import com.etong.pt.util.CarShopUtils;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;

import java.util.Iterator;
import java.util.List;

/**
 * Created by chenlinyang on 2015/10/20.
 */
public class CmVehicleServiceImpl implements CmVehicleService {
    private CmVehicleDao cmVehicleDao;
    private AutoService autoService;
    private AppVehicleService appVehicleService;

    public void setAutoService(AutoService autoService) {
        this.autoService = autoService;
    }

    public void setCmVehicleDao(CmVehicleDao cmVehicleDao) {
        this.cmVehicleDao = cmVehicleDao;
    }

    public void setAppVehicleService(AppVehicleService appVehicleService) {
        this.appVehicleService = appVehicleService;
    }

    @Override
    public PtResult findByParam(EnumConstant.PriceRange e, Integer carLever) {
        CmVehicle param = new CmVehicle();
        if(e != null) {
            Double[] range = e.getRange();
            param.setMinPrice(range[0]);
            param.setMaxPrice(range[1]);
        }
        param.setF_models_level(carLever);
        param.setF_level(EnumConstant.DataLevel.CARSET.getValue());

        PtResult ptResult = cmVehicleDao.findByParam(param);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<CmVehicle> list = ptResult.getObject();
        JSONArray jsonArray = new JSONArray(list.size());
        JSONObject jsonObject = null;
        for (CmVehicle record : list) {
            jsonObject = new JSONObject();
            jsonObject.put("id", record.getF_id());
            ptResult = autoService.getCarsetInfo(record.getF_id(),"1001");
            if (!ptResult.isSucceed()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }
            List<Vehicle> carsets = ptResult.<List>getObject();
            Vehicle carSetInfo = carsets.get(0);
            jsonObject.put("title", carSetInfo.getTitle());
            jsonObject.put("price", String.format("%.2f-%.2f",carSetInfo.getMinguide(),carSetInfo.getMaxguide()));
            jsonObject.put("photo", CarShopUtils.trimPhoto(carSetInfo.getImage()));

            /**如果车型下面没有在销车款，remove该数据*/
            List<Car> dataList = this.getSalesCarset(carSetInfo.getId().intValue());
            if(dataList != null && dataList.size() > 0) {
                jsonArray.add(jsonObject);
            }
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    private List<Car> getSalesCarset(Integer carsetId) {
        PtResult ptResult = autoService.getCarByCarsetId(carsetId);

        if (!ptResult.isSucceed()) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<Car> carInfoList = ptResult.getObject();
        /**过滤车型库数据*/
        for(Iterator<Car> iter = carInfoList.iterator(); iter.hasNext();) {
            Car record = iter.next();
            if(record.getSalestatusid().intValue() != 1325) {
                iter.remove();
            }
        }

        return carInfoList;
    }
}
