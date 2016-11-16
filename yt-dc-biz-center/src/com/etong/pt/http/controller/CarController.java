package com.etong.pt.http.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.data.auto.car.Car;
import com.etong.data.auto.carparam.CarParam;
import com.etong.data.auto.carphoto.CarPhoto;
import com.etong.data.auto.vehicle.Vehicle;
import com.etong.dc.auto.service.AutoConstant;
import com.etong.dc.auto.service.AutoService;
import com.etong.info.service.DocHeaderInfo;
import com.etong.info.service.InfoService;
import com.etong.pt.constants.EnumConstant;
import com.etong.pt.data.activities.Activities;
import com.etong.pt.data.appvehicle.AppVehicle;
import com.etong.pt.data.salesvehicle.SalesVehicle;
import com.etong.pt.data.sysbanner.SysBanner;
import com.etong.pt.data.sysitem.SysItem;
import com.etong.pt.service.*;
import com.etong.pt.util.CarShopUtils;
import com.etong.pt.util.ResourceUtil;
import com.etong.pt.util.TimestampUtils;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by chenlinyang on 2015/10/19.
 */
@Controller
public class CarController {
    @Autowired(required = false)
    private AutoService autoService;
    @Autowired(required = false)
    private InfoService infoService;
    @Autowired
    private AppService appService;
    @Autowired
    private HotVehicleService hotVehicleService;
    @Autowired
    private ActivitiesService activitiesService;
    @Autowired
    private SalesVehicleService salesVehicleService;
    @Autowired
    private CarLeverService carLeverService;
    @Autowired
    private CmVehicleService cmVehicleService;
    @Autowired
    private AppVehicleService appVehicleService;
    //@Autowired
    //private SysItemService sysItemService;
    @Autowired
    private SysBannerService sysBannerService;

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    /**
     * 热门品牌
     * @param appId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/biz/hot/brand/get.do")
    public PtResult getHotBrand(@RequestParam(required = true)String appId) {
        return hotVehicleService.getHotBrand(appId);

    }

    /**
     * 热门车型
     * @param appId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/biz/hot/carset/get.do")
    public PtResult getHotCarset(@RequestParam(required = true)String appId) {
        return hotVehicleService.getHotCarset(appId);
    }

    /**
     * 促销车款
     * @param appId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/biz/sales/car/get.do")
    public PtResult getSalesCar(@RequestParam(required = true)String appId) {
        return salesVehicleService.getSales(appId);
    }

    /**
     * 价格区间
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/biz/price/range/get.do")
    public PtResult getPriceRange() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        for(EnumConstant.PriceRange e : EnumConstant.PriceRange.values()) {
            jsonObject = new JSONObject();
            jsonObject.put("text", e.getDesc());
            jsonObject.put("value", e.getValue());
            jsonArray.add(jsonObject);
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    /**
     * 车型级别
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/biz/carset/lever/get.do")
    public PtResult getCarLever() {
        return carLeverService.findAll();
    }

    /**
     * 获取品牌列表
     *
     * @return 返回值：数组[(id(品牌ID)，title(品牌名称)，logo（品牌logo的URL）,firstLetter（品牌首字母）)，()]
     */
    @ResponseBody
    @RequestMapping(value = "/biz/brand/getall.do")
    public PtResult getAllBrands(@RequestParam(required = true) String appId) {
       PtResult ptResult= autoService.getBrands();

        if (!ptResult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
        }

        @SuppressWarnings("unchecked")
        List<Vehicle> carSetInfoList = ptResult.getObject();

        ptResult = appVehicleService.findExistBrands(appId);
        if(!ptResult.isSucceed()) {
            return  ptResult;
        }

        List<AppVehicle> exists = ptResult.<List>getObject();

        List<Vehicle> existsList = null;
        if(exists != null && exists.size() > 0) {
            existsList = new ArrayList<Vehicle>();

            for (AppVehicle exist : exists) {
                for (Vehicle _record : carSetInfoList) {
                    if(_record.getId().intValue() == exist.getCarbrandid()) {
                        existsList.add(_record);
                        continue;
                    }
                }
            }
        }

        // 排序
        List<Map> list = CarShopUtils.groupBrand(existsList);
        JSONArray jsonArray = new JSONArray();
        for(Map map : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstLetter", map.get("firstLetter").toString());

            List<Vehicle> dataList = (List<Vehicle>)map.get("dataList");
            JSONArray jsonArray1 = new JSONArray();

            for(Vehicle carSetInfo : dataList) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", carSetInfo.getId());
                jsonObject1.put("title", carSetInfo.getTitle());
                jsonObject1.put("logo", CarShopUtils.trimPhoto(carSetInfo.getImage()));

                jsonArray1.add(jsonObject1);
            }
            jsonObject.put("brand", jsonArray1);

            jsonArray.add(jsonObject);
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    /**
     * 获取品牌下面所有的车系车型
     *
     * @param brandId 参数：品牌ID
     * @return 返回值：数组[{id(车系ID),title(车系名称)}，{}]
     */
    @ResponseBody
    @RequestMapping(value = "/biz/carset/getall.do")
    public PtResult getAllCarsetOfBrand(@RequestParam(required = true)Integer brandId) {
        PtResult ptResult = autoService.getManuByBrandId(brandId);
        if (!ptResult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
        }

        @SuppressWarnings("unchecked")
        List<Vehicle> manuList = ptResult.getObject();
        JSONArray jsonArray = new JSONArray();

        for (Vehicle manu : manuList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", manu.getId());
            jsonObject.put("title", manu.getTitle());
            jsonArray.add(jsonObject);

            try {
                PtResult carResult = autoService.getCarsetByManuId(manu.getId(),"1001");

                if (!carResult.isSucceed()) {
                    continue;
                }

                List<Vehicle> carSetInfoList = (List<Vehicle>) carResult.getObject();
                JSONArray carsetJsonArray = new JSONArray();
                jsonObject.put("carset", carsetJsonArray);

                for (Vehicle carSetInfo : carSetInfoList) {
                    JSONObject jsonChid = new JSONObject();
                    jsonChid.put("id", carSetInfo.getId());
                    jsonChid.put("title", carSetInfo.getTitle());
                    jsonChid.put("price", String.format("%.2f - %.2f", carSetInfo.getMinguide(), carSetInfo.getMaxguide()));
                    jsonChid.put("photo", CarShopUtils.trimPhoto(carSetInfo.getImage()));

                    /**如果车型下面没有在销车款，remove该数据*/
                    List<Car> dataList = this.getSalesCarset(carSetInfo.getId().intValue());
                    if(dataList != null && dataList.size() > 0) {
                        carsetJsonArray.add(jsonChid);
                    }
                }
                // 如果车系下没有车型数据，删除车系信息
                if(carsetJsonArray.size() == 0) {
                    jsonArray.remove(jsonObject);
                }

            } catch (Exception e) {}
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    /**
     * 根据名称模糊查询车型信息
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/biz/carset/find.do")
    public PtResult findCarset(String name) {
        PtResult ptResult = autoService.findCarset(name);

        if (!ptResult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
        }

        @SuppressWarnings("unchecked")
        List<Vehicle> carsetList = (List<Vehicle>) ptResult.getObject();

        if (carsetList.isEmpty()) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
        }

        JSONArray jsonArray = new JSONArray();

        for (Vehicle carSetInfo : carsetList) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", carSetInfo.getId());
            jsonObject.put("title", carSetInfo.getTitle());
            jsonObject.put("price",String.format("%.2f - %.2f", carSetInfo.getMinguide(), carSetInfo.getMaxguide()));
            jsonObject.put("photo", CarShopUtils.trimPhoto(carSetInfo.getImage()));

            /**如果车型下面没有在销车款，remove该数据*/
            List<Car> dataList = this.getSalesCarset(carSetInfo.getId().intValue());
            if(dataList != null && dataList.size() > 0) {
                jsonArray.add(jsonObject);
            }

        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    /**
     * 根据条件查询车型信息
     * @param price 参数： 价格
     * @param level 参数：车型等级
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/biz/carset/params/find.do")
    public PtResult findCarsetByParams(Integer price, Integer level) {
        EnumConstant.PriceRange e = null;
        if(price != null) {
            e = EnumConstant.PriceRange.valueOf(price);
        }
        return cmVehicleService.findByParam(e, level);
    }

    /**
     * 获取车系下的所有车款
     *
     * @param carsetId 参数：车系ID
     *                 @param page
     *                 @param pageSize
     * @return 返回值：数组[{id(车款ID)，title(车款名称),image(车款图片的URL),guidePrice(厂商指导价)}，{}]
     */
    @ResponseBody
    @RequestMapping(value = "/biz/car/getall.do")
    public PtResult getAllCarOfCarset(@RequestParam(required = true)Integer carsetId, Integer page, Integer pageSize) {
        PtResult ptResult = autoService.getCarByCarsetId(carsetId);

        if (!ptResult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
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

        int size = carInfoList.size();
        JSONArray jsonArray = new JSONArray();
        //数据不多，在这里临时做了分页
        int start = 0;

        if (page != null && pageSize != null) {
            start = (page - 1) * pageSize;
            size = Math.min(start + pageSize, size);
        }

        if (start >= size) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
        }

        for (int i = start; i < size; ++i) {
            Car carInfo = carInfoList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", carInfo.getVid());
            jsonObject.put("title", carInfo.getYear()+"款"+carInfo.getSubject());
            jsonObject.put("year", carInfo.getYear());
            jsonObject.put("image", CarShopUtils.trimPhoto(carInfo.getImage()));
            jsonObject.put("guidePrice", carInfo.getPrices());
            jsonObject.put("selling", carInfo.getSalestatusid());
            jsonArray.add(jsonObject);

        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    /**
     * 获取车款详情
     *
     * @param id 参数：车款ID
     * @return 返回值：车款对象
     */
    @ResponseBody
    @RequestMapping(value = "/biz/car/detail.do")
    public PtResult getCarDetail(@RequestParam(required = true)Integer id) {
        PtResult ptResult = autoService.getCarInfo(id);

        if (!ptResult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
        }

        @SuppressWarnings("unchecked")
        CarParam carInfo = ptResult.getObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", carInfo.getVid());
        jsonObject.put("title", carInfo.getFullname());
        jsonObject.put("shortTitle",carInfo.getYear()+"款"+carInfo.getSubject());

        jsonObject.put("brand", carInfo.getBrand());
        jsonObject.put("carSetName", carInfo.getCarsetTitle());

        jsonObject.put("carName", carInfo.getSubject());
        jsonObject.put("color", carInfo.getColor());
        jsonObject.put("guidePrice", carInfo.getPrices());

        jsonObject.put("outVol", String.format("%.1fL", carInfo.getOutputVol() / 1000.0f));
        jsonObject.put("gearBox", carInfo.getGearbox());
        jsonObject.put("maxSpeed", carInfo.getMaxSpeed());
        jsonObject.put("driveSystem", carInfo.getDriveSystem());
        jsonObject.put("doorNum", carInfo.getDoorNum());
//        ///TODO 油箱容积待处理
        jsonObject.put("tankVol", carInfo.getTankVol());
        jsonObject.put("engine", carInfo.getEngine());
        jsonObject.put("oilWear", carInfo.getAvgOilwear());
        jsonObject.put("warranty", carInfo.getWarranty());

        ptResult = autoService.getCarPhoto(id, AutoConstant.PHOTO_WG);

        if (!ptResult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonObject);
        }

        @SuppressWarnings("unchecked")
        List<CarPhoto> photoInfoList = ptResult.getObject();
        int size = photoInfoList.size();
        JSONArray jsonArray = new JSONArray(size);

        for (CarPhoto carPhoto:photoInfoList) {

            JSONObject photo = new JSONObject();
            photo.put("url", CarShopUtils.trimPhoto(carPhoto.getImage()));
            jsonArray.add(photo);
        }

        jsonObject.put("photos", jsonArray);
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonObject);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/car/color/get.do")
    public PtResult getCarColor(@RequestParam(required = true)Integer carId){
        PtResult etongResult = autoService.getCarInfo(carId);

        if (!etongResult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
        }

        @SuppressWarnings("unchecked")
        CarParam carParam=etongResult.getObject();
        String color = carParam.getColor();
        String[] colorArray = color.split(",");
        JSONArray jsonArray = new JSONArray(colorArray.length);
        for (String c : colorArray) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", c);
            jsonObject.put("value", c);
            jsonArray.add(jsonObject);
        }

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/get.do")
    public PtResult getAppInfo(String id) {
        return appService.getById(id);
    }

    /**首页接口*/
    //@ResponseBody
    //@RequestMapping(value = "/biz/appitem/get.do")
    //public PtResult getAppItem(@RequestParam(required = true) String appId) {
    //    return sysItemService.findTreeByAppId(appId);
    //}

    @ResponseBody
    @RequestMapping(value = "/biz/app/index.do")
    public PtResult getAppIndex(@RequestParam(required = true) String appId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray bannerArray = this.getBannerJson(appId);
        JSONObject timelimitObject = this.getTimelimitJson(appId);
        JSONObject salesObject = this.getSalesJson(appId);
        JSONObject shaidanObject = this.getShaidanJson(appId);
        jsonObject.put("huodong", bannerArray);
        jsonObject.put("xianshigou", timelimitObject);
        jsonObject.put("cuxiaoche", salesObject);
        jsonObject.put("show_orders", shaidanObject);

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonObject);
    }

    /**限时购接口*/
    @ResponseBody
    @RequestMapping(value = "/biz/timelimit/get.do")
    public PtResult getTimeLimit(@RequestParam(required = true) String appId) {
        JSONObject jsonObject = this.getTimelimitJson(appId);
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonObject);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/timelimit/carset/get.do")
    public PtResult getTimeLimitCarset(@RequestParam(required = true)String appId, @RequestParam(required = true)Integer brandId, @RequestParam(required = true)Long actId, Integer page, Integer pageSize) {
        int now = new Long(new Date().getTime()/1000).intValue();
        JSONObject ret = new JSONObject();
        /*SysItem param1 = new SysItem();
        param1.setStid(appId);
        param1.setItemcode("title1");
        param1.setStatus(Boolean.TRUE);
        PtResult ptResult = sysItemService.findByParam(param1);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<SysItem> list1 = ptResult.<List>getObject();
        SysItem record1 = list1.get(0);
        ret.put("title", record1.getName());*/
        ret.put("title", ResourceUtil.bundle.getString("index.xianshigou.title"));

        // step 1,查询活动 - 限时购
        PtResult ptResult = activitiesService.getById(actId);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        Activities activity = ptResult.<Activities>getObject();

        SalesVehicle param3 = new SalesVehicle();
        param3.setStid(appId);
        param3.setAcid(actId);
        param3.setCarbrandid(brandId);
        ptResult = salesVehicleService.findByParam(param3);

        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<SalesVehicle> list = ptResult.<List>getObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;

        ptResult = autoService.getBrandInfo(brandId);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<Vehicle> brands = ptResult.getObject();
        Vehicle brand = brands.get(0);

        ret.put("huodong_id", activity.getAcid());
        ret.put("start_time_key", activity.getStartdate());
        ret.put("start_time", TimestampUtils.Timestamp2Str(activity.getStartdate()));
        ret.put("end_time_key", activity.getEnddate());
        ret.put("end_time", TimestampUtils.Timestamp2Str(activity.getEnddate()));
        ret.put("end_seconds", activity.getEnddate() - now);

        for (SalesVehicle record : list) {
            jsonObject = new JSONObject();
            jsonObject.put("icon", CarShopUtils.trimPhoto(brand.getImage()));
            jsonObject.put("carset_id", record.getCarsetid());
            ptResult = autoService.getCarsetInfo(record.getCarsetid());
            if(!ptResult.isSucceed()) {
                return ptResult;
            }
            List<Vehicle> carsets = ptResult.getObject();
            Vehicle carset = carsets.get(0);
            jsonObject.put("title", carset.getTitle());
            jsonObject.put("image", CarShopUtils.trimPhoto(carset.getImage()));

            jsonObject.put("youhui_key", 90000);
            jsonObject.put("youhui", "全系7折");
            jsonObject.put("youhui_ext", "全系7折");

            jsonArray.add(jsonObject);
        }
        ret.put("size", jsonArray.size());
        ret.put("carsets", jsonArray);

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, ret);
    }

    /**
     * 促销车接口 - 车款
     * @param appId
     * @param carsetId
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/biz/sales/carset/get.do")
    public PtResult getSalesCar(@RequestParam(required = true)String appId, @RequestParam(required = true)Integer carsetId, @RequestParam(required = true)Long actId,
                                Integer page, Integer pageSize) {
        int now = new Long(new Date().getTime()/1000).intValue();
        JSONObject ret = new JSONObject();
       /* SysItem param1 = new SysItem();
        param1.setStid(appId);
        param1.setItemcode("cuxiaoche");
        param1.setStatus(Boolean.TRUE);
        PtResult ptResult = sysItemService.findByParam(param1);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<SysItem> list1 = ptResult.<List>getObject();
        SysItem record1 = list1.get(0);
        ret.put("title", record1.getName());*/
        ret.put("title", ResourceUtil.bundle.getString("index.cuxiaoche.item"));

        // step 1,查询活动 - 促销车
        PtResult ptResult = activitiesService.getById(actId);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        Activities activity = ptResult.<Activities>getObject();

        // step 2,查询促销车活动车辆信息
        SalesVehicle param3 = new SalesVehicle();
        param3.setStid(appId);
        param3.setAcid(activity.getAcid());
        param3.setCarsetid(carsetId);
        ptResult = salesVehicleService.findByParam(param3);

        if(!ptResult.isSucceed()) {
            return ptResult;
        }
        List<SalesVehicle> list = ptResult.<List>getObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;

        for (SalesVehicle record : list) {
            jsonObject = new JSONObject();

            jsonObject.put("huodong_id", activity.getAcid());
            jsonObject.put("start_time_key", activity.getStartdate());
            jsonObject.put("start_time", TimestampUtils.Timestamp2Str(activity.getStartdate()));
            jsonObject.put("end_time_key", activity.getEnddate());
            jsonObject.put("end_time", TimestampUtils.Timestamp2Str(activity.getEnddate()));
            jsonObject.put("end_seconds", activity.getEnddate() - now);

            jsonObject.put("car_id", record.getVid());

            ptResult = autoService.getCarInfo(record.getVid());
            if(!ptResult.isSucceed()) {
                return ptResult;
            }
            CarParam carInfo = ptResult.getObject();
            jsonObject.put("image", CarShopUtils.trimPhoto(carInfo.getImage()));
            jsonObject.put("zhijiang", "全城抄底");
            if(record.getCarsetid() == 574) {
                jsonObject.put("zhiiang", "金融钜惠");
            }
            jsonObject.put("zhijiang_key", record.getLessprice());
            jsonObject.put("title", carInfo.getFullname());
            // 指导价
            jsonObject.put("guide_price", carInfo.getPrices() + "万");
            jsonObject.put("save_money", "已节省" + record.getLessprice().intValue() + "元");
            // 促销价
            jsonObject.put("cuxiaojia", CarShopUtils.caculate(Double.parseDouble(String.valueOf(carInfo.getPrices())), record.getLessprice()) + "万");
            // 可选颜色
            jsonObject.put("colors", "可选颜色" + CarShopUtils.rebuildColor(record.getColor()));

            jsonObject.put("end", "热销中");
            jsonObject.put("end_key", false);
            jsonObject.put("comment1", "数量有限，售完即止");
            jsonObject.put("comment2", "");
            jsonArray.add(jsonObject);
        }
        ret.put("size", jsonArray.size());
        ret.put("cars", jsonArray);

        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, ret);
    }

    /**用户晒单接口*/
    @ResponseBody
    @RequestMapping(value = "/biz/user/showorders.do")
    public PtResult showOrders(@RequestParam(required = true) String appId) {
        JSONObject jsonObject = this.getShaidanJson(appId);
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonObject);
    }

    /**app banner*/
    public JSONArray getBannerJson(String appId) {
        PtResult ptResult = sysBannerService.findByAppId(appId);
        if(!ptResult.isSucceed()) {
            return null;
        }
        List<SysBanner> list = ptResult.<List>getObject();
        JSONArray ret = new JSONArray(list.size());
        JSONObject jsonObject = null;
        for (SysBanner record : list) {
            jsonObject = new JSONObject();
            jsonObject.put("title", record.getDesc());
            jsonObject.put("image", record.getImage());
            jsonObject.put("url", record.getUrl());
            jsonObject.put("key", record.getAid());
            ret.add(jsonObject);
        }

        return ret;
    }

    /**
     * 限时购
     * @param appId
     * @return
     */
    private JSONObject getTimelimitJson(String appId) {
        int now = new Long(new Date().getTime()/1000).intValue();
        JSONObject ret = new JSONObject();
        /*SysItem param1 = new SysItem();
        param1.setStid(appId);
        param1.setItemcode("xianshigou");
        param1.setStatus(Boolean.TRUE);
        PtResult ptResult = sysItemService.findByParam(param1);
        if(!ptResult.isSucceed()) {
            logger.warn("业务中心 - 限时购item为空");
            return null;
        }
        List<SysItem> list1 = ptResult.<List>getObject();
        SysItem record1 = list1.get(0);
        ret.put("title", record1.getName());*/
        ret.put("title", ResourceUtil.bundle.getString("index.xianshigou.item"));

        // step 1,查询活动 - 限时购
        Activities param2 = new Activities();
        param2.setStid(appId);
        param2.setType(EnumConstant.ActType.TIME_LIMIT.getValue());
        PtResult ptResult = activitiesService.findByParam(param2, Boolean.TRUE);

        if(!ptResult.isSucceed()) {
            logger.warn("业务中心 - 限时购活动为空或已过期");
            ret.put("size", 0);
            ret.put("brands", null);
            return ret;
        }
        List<Activities> list2 = ptResult.<List>getObject();
        //rebuild list
        List<Map> dataList = new ArrayList<Map>();
        Map<String, Object> objectMap = null;
        for (Activities activity : list2) {

            // step 2,查询限时购活动车辆信息
            SalesVehicle param3 = new SalesVehicle();
            param3.setStid(appId);
            param3.setAcid(activity.getAcid());
            ptResult = salesVehicleService.findByParam(param3);

            if(!ptResult.isSucceed()) {
                logger.warn("业务中心 - 限时购活动车辆为空，actId=" + activity.getAcid());
                continue;
            }
            List<SalesVehicle> list3 = ptResult.<List>getObject();
            SalesVehicle _record = list3.get(0);

            // 品牌
            ptResult = autoService.getBrandInfo(_record.getCarbrandid());
            if(!ptResult.isSucceed()) {
                logger.warn("车型库服务 - 品牌为空");
                return null;
            }
            List<Vehicle> brands = ptResult.getObject();
            Vehicle brand = brands.get(0);
            // 车型
            ptResult = autoService.getCarsetInfo(_record.getCarsetid());
            if(!ptResult.isSucceed()) {
                logger.warn("车型库服务 - 车型为空");
                return null;
            }
            List<Vehicle> carsets = ptResult.getObject();
            Vehicle carset = carsets.get(0);

            // 名称、直降
            String title = "";
            Double lessprice = 0D;
            for (SalesVehicle vehicle : list3) {
                title += vehicle.getCarset() + "/";

                if(vehicle.getLessprice() > lessprice) {
                    lessprice = vehicle.getLessprice();
                }
            }
            if(!title.isEmpty()) {
                title = title.substring(0, title.length()-1);
            }

            objectMap = new HashMap<String, Object>();
            objectMap.put("huodong_id", activity.getAcid());
            objectMap.put("id", brand.getId());
            objectMap.put("icon", CarShopUtils.trimPhoto(brand.getImage()));
            objectMap.put("title", title);
            if(StringUtils.isNotEmpty(activity.getImage())) {
                objectMap.put("image", activity.getImage());
            }
            else {
                objectMap.put("image", CarShopUtils.trimPhoto(carset.getImage()));
            }

            objectMap.put("youhui_key", 90000);
            objectMap.put("youhui", "全系7折");
            objectMap.put("youhui_ext", "全系7折");

            objectMap.put("start_time_key", activity.getStartdate());
            objectMap.put("start_time", TimestampUtils.Timestamp2Str(activity.getStartdate()));
            objectMap.put("end_time_key", activity.getEnddate());
            objectMap.put("end_time", TimestampUtils.Timestamp2Str(activity.getEnddate()));
            objectMap.put("end_seconds", activity.getEnddate() - now);
            dataList.add(objectMap);
        }

        ret.put("size ", dataList.size());
        ret.put("brands", dataList);

        return ret;
    }

    /**
     * 促销车
     * @param appId
     * @return
     */
    private JSONObject getSalesJson(String appId) {
        int now = new Long(new Date().getTime()/1000).intValue();
        JSONObject ret = new JSONObject();
        /*SysItem param1 = new SysItem();
        param1.setStid(appId);
        param1.setItemcode("cuxiaoche");
        param1.setStatus(Boolean.TRUE);
        PtResult ptResult = sysItemService.findByParam(param1);
        if(!ptResult.isSucceed()) {
            logger.warn("业务中心 - 促销车item为空");
            return null;
        }
        List<SysItem> list1 = ptResult.<List>getObject();
        SysItem record1 = list1.get(0);
        ret.put("title", record1.getName());*/
        ret.put("title", ResourceUtil.bundle.getString("index.cuxiaoche.item"));

        // step 1,查询活动 - 促销车
        Activities param2 = new Activities();
        param2.setStid(appId);
        param2.setType(EnumConstant.ActType.PROMOTIONAL.getValue());
        PtResult ptResult = activitiesService.findByParam(param2, Boolean.TRUE);

        if(!ptResult.isSucceed()) {
            logger.warn("业务中心 - 促销车活动为空或已过期");
            ret.put("size", 0);
            ret.put("carsets", null);
            return ret;
        }
        List<Activities> list2 = ptResult.<List>getObject();

        //rebuild list
        List<Map> dataList = new ArrayList<Map>();
        for (Activities activity : list2) {

            // step 2,查询促销车活动车辆信息
            SalesVehicle param3 = new SalesVehicle();
            param3.setStid(appId);
            param3.setAcid(activity.getAcid());
            ptResult = salesVehicleService.findByParam(param3);
            if(!ptResult.isSucceed()) {
                logger.warn("业务中心 - 促销车活动车辆为空，actId=" + activity.getAcid());
                continue;
            }

            List<SalesVehicle> list = ptResult.getObject();

            Map<String, Object> objectMap = null;
            Boolean isExist = Boolean.FALSE;
            Map<String, Object> existData = null;
            for (SalesVehicle record : list) {
                for (Map<String, Object> dataMap : dataList) {
                    if(dataMap.<Integer>get("carset_id").equals(record.getCarsetid())
                            && dataMap.<Long>get("huodong_id").equals(record.getAcid())) {
                        isExist = Boolean.TRUE;
                        if(Double.parseDouble(dataMap.get("zhiiang_key").toString()) < record.getLessprice()) {
                            dataMap.put("zhiiang", "全城抄底");
                            if(record.getCarsetid() == 574) {
                                dataMap.put("zhiiang", "金融钜惠");
                            }
                            dataMap.put("zhiiang_key", record.getLessprice());
                        }
                        break;
                    }
                }
                if(!isExist) {
                    objectMap = new HashMap<String, Object>();
                    objectMap.put("huodong_id", activity.getAcid());
                    objectMap.put("carset_id", record.getCarsetid());
                    ptResult = autoService.getCarsetInfo(record.getCarsetid());
                    if(!ptResult.isSucceed()) {
                        return null;
                    }
                    List<Vehicle> carsets = ptResult.getObject();
                    Vehicle carset = carsets.get(0);
                    objectMap.put("title", carset.getTitle());
                    if(StringUtils.isNotEmpty(activity.getImage())) {
                        objectMap.put("image", activity.getImage());
                    }
                    else {
                        objectMap.put("image", CarShopUtils.trimPhoto(carset.getImage()));
                    }

                    objectMap.put("zhiiang", "全城抄底");
                    if(record.getCarsetid() == 574) {
                        objectMap.put("zhiiang", "金融钜惠");
                    }
                    objectMap.put("zhiiang_key", record.getLessprice());
                    objectMap.put("start_time_key", activity.getStartdate());
                    objectMap.put("start_time", TimestampUtils.Timestamp2Str(activity.getStartdate()));
                    objectMap.put("end_time_key", activity.getEnddate());
                    objectMap.put("end_time", TimestampUtils.Timestamp2Str(activity.getEnddate()));
                    objectMap.put("end_seconds", activity.getEnddate() - now);
                    dataList.add(objectMap);
                }

            }

        }

        ret.put("size", dataList.size());
        ret.put("carsets", dataList);

        return ret;
    }

    /**
     * 用户晒单
     * @return
     */
    private JSONObject getShaidanJson(String appId) {
        JSONObject ret = new JSONObject();
       /* SysItem param1 = new SysItem();
        param1.setStid(appId);
        param1.setItemcode("show_orders");
        param1.setStatus(Boolean.TRUE);
        PtResult ptResult = sysItemService.findByParam(param1);
        if(!ptResult.isSucceed()) {
            logger.warn("业务中心 - 用户晒单item为空");
            return null;
        }
        List<SysItem> list1 = ptResult.<List>getObject();
        SysItem record1 = list1.get(0);
        ret.put("title", record1.getName());*/
        ret.put("title", ResourceUtil.bundle.getString("index.show_orders.item"));

        List<DocHeaderInfo> list = infoService.getColumnDocHeaders(13, Integer.parseInt(appId));
        if(list == null || list.size() == 0) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(list.size());
        JSONObject jsonObject = null;
        for (DocHeaderInfo record : list) {
            jsonObject = new JSONObject();
            jsonObject.put("id", record.getId());
            jsonObject.put("title", record.getTitle());
            jsonObject.put("image", record.getPicUrl());
            jsonObject.put("bottom", record.getAuthor());
            jsonObject.put("time", record.getDate());
            jsonArray.add(jsonObject);
        }

        ret.put("size", jsonArray.size());
        ret.put("orders", jsonArray);

        return ret;

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
