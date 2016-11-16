package com.etong.pt.http.controller;

import com.etong.pt.data.collection.CollectionVehicle;
import com.etong.pt.service.CollectionService;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2015/10/28.
 */
@Controller
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @ResponseBody
    @RequestMapping(value = "/biz/collection/add.do")
    public PtResult addCollectionRequest(CollectionVehicle collectionVehicle){
        if (collectionVehicle.getF_stid()==null||collectionVehicle.getF_mid()==null||collectionVehicle.getF_vid()==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (collectionService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        PtResult ptResult=collectionService.addCollectionRequest(collectionVehicle);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/collection/del.do")
    public PtResult deleteCollectionRequest(Long clid){
        if (clid==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (collectionService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        PtResult ptResult=collectionService.deleteCollectionRequest(clid);
        return ptResult;
    }

    @ResponseBody
    @RequestMapping(value = "/biz/collection/get.do")
    public PtResult getCollectionRequst(Long clid){
        if (clid==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (collectionService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        PtResult ptResult=collectionService.getCollectionRequest(clid);
        return ptResult;
   }

    @ResponseBody
    @RequestMapping(value = "/biz/collection/list.do")
    public PtResult getCollectionListRequest(Long mid,String stid){
        if (mid==null||stid==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (collectionService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        PtResult ptResult=collectionService.getCollectionListRequest(mid, stid);
        return ptResult;
    }


    @ResponseBody
    @RequestMapping("/biz/vehicle/colvalidate.do")
    public PtResult validateVehicleRequest(Long userId,Integer vid ,String stid){
        if (userId==null||vid==null||stid==null){
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER,"缺少必要参数",null);
        }
        if (collectionService==null){
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE,"业务服务无效",null);
        }
        PtResult ptResult=collectionService.findCollectionsRequest(userId, stid, vid);
        return ptResult;
    }

}
