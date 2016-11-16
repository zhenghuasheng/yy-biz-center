package com.etong.pt.dao.impl;

import com.etong.pt.dao.HotVehicleDao;
import com.etong.pt.data.hotvehicle.HotVehicle;
import com.etong.pt.data.hotvehicle.HotVehicleExample;
import com.etong.pt.data.hotvehicle.HotVehicleMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by chenlinyang on 2015/10/20.
 */
public class HotVehicleDaoImpl implements HotVehicleDao {
    public static final String HOT_VEHICLE_INDEX = "biz_center_hotvehicle";
    private static final Logger logger = LoggerFactory.getLogger(HotVehicleDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult findAll() {
        HotVehicleExample example = new HotVehicleExample();
        HotVehicleExample.Criteria criteria = example.createCriteria();


        DbProxy dbProxy = dbManager.getDbProxy(true);
        HotVehicleMapper mapper = dbProxy.getMapper(HotVehicleMapper.class);

        try {
            example.setOrderByClause("f_order asc");
            List<HotVehicle> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult findByParam(HotVehicle param) {
        HotVehicleExample example = new HotVehicleExample();
        HotVehicleExample.Criteria criteria = example.createCriteria();


        DbProxy dbProxy = dbManager.getDbProxy(true);
        HotVehicleMapper mapper = dbProxy.getMapper(HotVehicleMapper.class);

        try {
            if(param.getF_type() != null) {
                criteria.andF_typeEqualTo(param.getF_type());
            }
            if(param.getF_stid() != null && !param.getF_stid().isEmpty()) {
                criteria.andF_stidEqualTo(param.getF_stid());
            }
            if(param.getF_vid() != null) {
                criteria.andF_vidEqualTo(param.getF_vid());
            }

            example.setOrderByClause("f_order asc");
            List<HotVehicle> list = mapper.selectByExample(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult add(HotVehicle record) {
        PtResult ptResult = this.findByParam(record);
        if(ptResult.isSucceed()) {
            return new PtResult(PtCommonError.PT_ERROR_RECORD_REDUPLICATED, null, null);
        }

        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        ptResult = dbIndexNum.generateIndexNum(HOT_VEHICLE_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setF_hvid(id.intValue());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        HotVehicleMapper mapper = dbProxy.getMapper(HotVehicleMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getF_hvid());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
        return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
    }

    @Override
    public PtResult delete(Integer id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        HotVehicleMapper mapper = dbProxy.getMapper(HotVehicleMapper.class);

        try {
            int result = mapper.deleteByPrimaryKey(id);

            if (result == 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            }{
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }
}
