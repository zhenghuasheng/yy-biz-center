package com.etong.pt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.data.auto.car.Car;
import com.etong.data.auto.carparam.CarParam;
import com.etong.dc.auto.service.AutoService;
import com.etong.pt.dao.CollectionDao;
import com.etong.pt.data.collection.CollectionVehicle;
import com.etong.pt.service.CollectionService;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/28.
 */
public class CollectionServiceImpl implements CollectionService {
    private CollectionDao collectionDao;
    private AutoService autoService;

    public void setCollectionDao(CollectionDao collectionDao) {
        this.collectionDao = collectionDao;
    }

    public void setAutoService(AutoService autoService) {
        this.autoService = autoService;
    }

    @Override
    public PtResult addCollectionRequest(CollectionVehicle record) {

        PtResult findResult=collectionDao.findCollectionsRequest(record.getF_mid(),record.getF_stid(),record.getF_vid());
        if (!findResult.isSucceed()){
            return findResult;
        }

        record.setF_collecttime((int) (new Date().getTime()/1000));
        PtResult ptResult=collectionDao.addCollectionRequest(record);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("id",ptResult.getObject());
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult deleteCollectionRequest(Long f_clid) {
        PtResult ptResult=collectionDao.deleteCollectionRequest(f_clid);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        Map<String,Object>objectMap = new HashMap<>();
        objectMap.put("id",ptResult.getObject());
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult modifyCollectionRequest(CollectionVehicle record) {
        PtResult ptResult=collectionDao.modifyCollectionRequest(record);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        Map<String,Object>objectMap=new HashMap<>();
        objectMap.put("id",ptResult.getObject());
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult getCollectionRequest(Long f_clid) {
        PtResult ptResult=collectionDao.getCollectionRequest(f_clid);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        Map<String,Object>objectMap=new HashMap<>();
        objectMap.put("collectionVhicle",ptResult.getObject());
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult getCollectionListRequest(Long f_mid, String f_stid) {
        PtResult ptResult=collectionDao.getCollectionListRequest(f_mid,f_stid);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        List<CollectionVehicle>list=ptResult.getObject();
        JSONArray jsonArray=new JSONArray();
        for (CollectionVehicle collectionVehicle:list){
            JSONObject jsonObject= (JSONObject) JSON.toJSON(collectionVehicle);
            Integer f_vid=collectionVehicle.getF_vid();
            if (f_vid!=null){
                PtResult etongResult=autoService.getCarInfo(f_vid);
                if (etongResult.isSucceed()){
                    CarParam carParam=etongResult.getObject();
                    jsonObject.put("title",carParam.getFullname());
                    Float guidePrice=carParam.getPrices();
                    if (guidePrice!=null){
                        jsonObject.put("guidePrice", new DecimalFormat("0.00").format(guidePrice));
                    }
                    jsonObject.put("carTypeImag", carParam.getImage());
                }
            }
            jsonArray.add(jsonObject);
        }
        Map<String,Object>objectMap=new HashMap<>();
        objectMap.put("collectionVehicleList",jsonArray);
        ptResult.setObject(objectMap);
        return ptResult;
    }

    @Override
    public PtResult findCollectionsRequest(Long f_mid, String f_stid, Integer f_vid) {
        PtResult ptResult=collectionDao.findCollectionsRequest(f_mid,f_stid,f_vid);

        Map<String,Object>objectMap=new HashMap<>();

        if (ptResult.isSucceed()){//无指定参数下的，收藏记录
            objectMap.put("validate",false);
            return new PtResult(PtCommonError.PT_ERROR_NODATA,null,objectMap);
        }

        objectMap.put("collected", true);
        List<CollectionVehicle>list= ptResult.getObject();
        objectMap.put("clid", list.get(0).getF_clid());
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,objectMap);
    }
}
