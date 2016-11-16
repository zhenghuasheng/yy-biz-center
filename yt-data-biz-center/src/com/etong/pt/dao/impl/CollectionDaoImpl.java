package com.etong.pt.dao.impl;

import com.etong.pt.dao.CollectionDao;
import com.etong.pt.data.collection.CollectionVehicle;
import com.etong.pt.data.collection.CollectionVehicleExample;
import com.etong.pt.data.collection.CollectionVehicleMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public class CollectionDaoImpl implements CollectionDao {
    public static final String COLLECTION_INDEX = "biz_center_collection";
    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    @Autowired
    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    @Autowired
    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult addCollectionRequest(CollectionVehicle record) {
        PtResult ptResult=dbIndexNum.generateIndexNum(COLLECTION_INDEX);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        record.setF_clid((Long) ptResult.getObject());

        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);
            CollectionVehicleMapper mapper=dbProxy.getMapper(CollectionVehicleMapper.class);
            int result=mapper.insertSelective(record);
            if (result<1){
                return new PtResult(PtCommonError.PT_ERROR_SUBMIT,"添加收藏失败",null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,record.getF_clid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB,e.getMessage(),null);
        } finally {
            if (dbProxy!=null){
                dbProxy.close();
            }
        }
    }

    @Override
    public PtResult deleteCollectionRequest(Long f_clid) {
        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);

            CollectionVehicleMapper mapper=dbProxy.getMapper(CollectionVehicleMapper.class);
            int result=mapper.deleteByPrimaryKey(f_clid);
            if (result<1){
                return new PtResult(PtCommonError.PT_ERROR_SUBMIT,"删除操作失败",null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,f_clid);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB,e.getMessage(),null);
        } finally {
            if (dbProxy!=null){
                dbProxy.close();
            }
        }
    }

    @Override
    public PtResult modifyCollectionRequest(CollectionVehicle record) {

        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);
            CollectionVehicleMapper mapper=dbProxy.getMapper(CollectionVehicleMapper.class);
            int result=mapper.updateByPrimaryKeySelective(record);
            if (result<1){
                return new PtResult(PtCommonError.PT_ERROR_SUBMIT,"修改记录失败",null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,record.getF_clid());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB,e.getMessage(),null);
        } finally {
            if (dbProxy!=null){
                dbProxy.close();
            }
        }

    }

    @Override
    public PtResult getCollectionRequest(Long f_clid) {
        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);
            CollectionVehicleMapper mapper=dbProxy.getMapper(CollectionVehicleMapper.class);

            CollectionVehicle collectionVehicle=mapper.selectByPrimaryKey(f_clid);
            if (collectionVehicle==null){
                return new PtResult(PtCommonError.PT_ERROR_NODATA,"数据为空",null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,collectionVehicle);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB,e.getMessage(),null);
        } finally {
            if (dbProxy!=null){
                dbProxy.close();
            }
        }
    }

    @Override
    public PtResult getCollectionListRequest(Long f_mid, String f_stid) {

        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);
            CollectionVehicleMapper mapper=dbProxy.getMapper(CollectionVehicleMapper.class);

            CollectionVehicleExample example=new CollectionVehicleExample();
            CollectionVehicleExample.Criteria criteria=example.createCriteria();
            criteria.andF_midEqualTo(f_mid);
            criteria.andF_stidEqualTo(f_stid);

            List<CollectionVehicle>list=mapper.selectByExample(example);

            if (list.isEmpty()){
                return new PtResult(PtCommonError.PT_ERROR_NODATA,null,null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB,e.getMessage(),null);
        } finally {
            if (dbProxy!=null){
                dbProxy.close();
            }
        }
    }

    @Override
    public PtResult findCollectionsRequest(Long f_mid, String f_stid, Integer f_vid) {
        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);

            CollectionVehicleExample example=new CollectionVehicleExample();
            CollectionVehicleExample.Criteria criteria=example.createCriteria();
            if (f_mid!=null){
                criteria.andF_midEqualTo(f_mid);
            }
            if (f_stid!=null){
                criteria.andF_stidEqualTo(f_stid);
            }
            if (f_vid!=null){
                criteria.andF_vidEqualTo(f_vid);
            }
            CollectionVehicleMapper mapper=dbProxy.getMapper(CollectionVehicleMapper.class);

            List<CollectionVehicle>list=mapper.selectByExample(example);
            if (!list.isEmpty()){
                return new PtResult(PtCommonError.PT_ERROR_RECORD_REDUPLICATED,null,list);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,null);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB,e.getMessage(),null);
        } finally {
            if (dbProxy!=null){
                dbProxy.close();
            }
        }
    }
}
