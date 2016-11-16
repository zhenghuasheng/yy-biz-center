package com.etong.pt.http.controller;

import com.etong.pt.data.activities.Activities;
import com.etong.pt.data.appvehicle.AppVehicle;
import com.etong.pt.data.dealer.DealerWithBLOBs;
import com.etong.pt.data.salesvehicle.SalesVehicle;
import com.etong.pt.data.sysbanner.SysBanner;
import com.etong.pt.service.*;
import com.etong.pt.utility.PtResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chenlinyang on 2015/11/30.
 */
@Controller
public class ConfigController {
    @Autowired
    private AppVehicleService appVehicleService;
    @Autowired
    private HotVehicleService hotVehicleService;
    @Autowired
    private SysBannerService sysBannerService;
    @Autowired
    private ActivitiesService activitiesService;
    @Autowired
    private SalesVehicleService salesVehicleService;

    @Autowired
    private DealerService dealerService;

    @ResponseBody
    @RequestMapping(value = "/biz/app/brand/get.do")
    public PtResult getAppBrand(@RequestParam(required = true)String appId, @RequestParam(required = true)Boolean status) {
        return appVehicleService.getAppBrand(appId, status);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/brand/edit.do")
    public PtResult editAppBrand(@RequestParam(required = true)String appId, @RequestParam(required = true)Integer brandId, @RequestParam(required = true)Boolean status) {
        return appVehicleService.editAppBrand(appId, brandId, status);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/car/list.do")
    public PtResult getAppCar(@RequestParam(required = true)String appId, @RequestParam(required = true)Integer brandId, @RequestParam(required = true)Integer carsetId, Boolean status,
                              Integer page, Integer pageSize) {
        return appVehicleService.getAppCar(appId, brandId, carsetId, status,
                page, pageSize);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/car/edit.do")
    public PtResult editAppCar(@RequestParam(required = true)String appId, @RequestParam(required = true)Integer brandId, @RequestParam(required = true)Integer carsetId, @RequestParam(required = true)Integer carId,
                               @RequestParam(required = true)Boolean status) {
        return appVehicleService.editAppCar(appId,  brandId, carsetId, carId, status);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/hot/brand/add.do")
    public PtResult addHotBrand(@RequestParam(required = true)String appId, @RequestParam(required = true)Integer brandId) {
        return hotVehicleService.addHotBrand(appId, brandId);

    }

    @ResponseBody
    @RequestMapping(value = "/biz/hot/carset/add.do")
    public PtResult addHotCarset(@RequestParam(required = true)String appId, @RequestParam(required = true)Integer carsetId) {
        return hotVehicleService.addHotCarset(appId, carsetId);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/hot/delete.do")
    public PtResult deleteHot(@RequestParam(required = true)Integer id) {
        return hotVehicleService.deleteHot(id);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/banner/get.do")
    public PtResult getAppBanner(@RequestParam(required = true)String appId) {
        return sysBannerService.findByAppId(appId);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/banner/add.do")
    public PtResult addAppBanner(@RequestBody SysBanner record) {
        return sysBannerService.add(record);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/banner/update.do")
    public PtResult updateAppBanner(@RequestBody SysBanner record) {
        return sysBannerService.update(record);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/banner/delete.do")
    public PtResult deleteAppBanner(@RequestParam(required = true)Long id) {
        return sysBannerService.delete(id);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/activities/get.do")
    public PtResult getAppActivities(@RequestParam(required = true)String appId) {
        return activitiesService.findByAppId(appId);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/activities/add.do")
    public PtResult addAppActivities(@RequestBody Activities record) {
        return activitiesService.add(record);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/activities/update.do")
    public PtResult updateAppActivities(@RequestBody Activities record) {
        return activitiesService.update(record);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/activities/delete.do")
    public PtResult deleteAppActivities(@RequestParam(required = true)Long id) {
        return activitiesService.delete(id);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/salesvehicle/get.do")
    public PtResult getSalesVehicle(@RequestParam(required = true)String appId, @RequestParam(required = true)Long actId) {
        SalesVehicle param = new SalesVehicle();
        param.setStid(appId);
        param.setAcid(actId);
        return salesVehicleService.findByParam(param);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/salesvehicle/add.do")
    public PtResult addSalesvehicle(@RequestBody SalesVehicle record) {
        return salesVehicleService.add(record);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/salesvehicle/update.do")
    public PtResult updateSalesvehicle(@RequestBody SalesVehicle record) {
        return salesVehicleService.update(record);
    }

    @ResponseBody
    @RequestMapping(value = "/biz/app/salesvehicle/delete.do")
    public PtResult deleteSalesvehicle(@RequestParam(required = true)Integer id) {
        return salesVehicleService.delete(id);
    }

    @RequestMapping(value = "/biz/app/dealer/list.do")
    public @ResponseBody PtResult listDealer(@RequestParam(required = true)String appId) {
        return dealerService.findByAppId(appId);
    }

    @RequestMapping(value = "/biz/app/dealer/add.do")
    public @ResponseBody PtResult addDealer(@RequestBody DealerWithBLOBs record) {
        return dealerService.add(record);
    }

    @RequestMapping(value = "/biz/app/dealer/update.do")
    public @ResponseBody PtResult updateDealer(@RequestBody DealerWithBLOBs record) {
        return dealerService.update(record);
    }

    @RequestMapping(value = "/biz/app/dealer/delete.do")
    public @ResponseBody PtResult deleteDealer(@RequestParam(required = true)Integer id) {
        return dealerService.delete(id);
    }

    @RequestMapping(value = "/biz/app/dealer/get.do")
    public @ResponseBody PtResult getDealer(@RequestParam(required = true)Integer id) {
        return dealerService.getById(id);
    }

 }
