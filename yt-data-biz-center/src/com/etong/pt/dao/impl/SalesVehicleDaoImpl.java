package com.etong.pt.dao.impl;

import com.etong.pt.dao.SalesVehicleDao;
import com.etong.pt.data.salesvehicle.SalesVehicle;
import com.etong.pt.data.salesvehicle.SalesVehicleExample;
import com.etong.pt.data.salesvehicle.SalesVehicleMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by chenlinyang on 2015/10/28.
 */
public class SalesVehicleDaoImpl implements SalesVehicleDao {
    public static final String SALES_VEHICLE_INDEX = "biz_center_salesvehicle";
    private static final Logger logger = LoggerFactory.getLogger(SalesVehicleDaoImpl.class);
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
        SalesVehicleExample example = new SalesVehicleExample();
        SalesVehicleExample.Criteria criteria = example.createCriteria();


        DbProxy dbProxy = dbManager.getDbProxy(true);
        SalesVehicleMapper mapper = dbProxy.getMapper(SalesVehicleMapper.class);

        try {
            example.setOrderByClause("f_order asc");
            List<SalesVehicle> list = mapper.selectByExample(example);

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
    public PtResult findByParam(SalesVehicle param) {
        SalesVehicleExample example = new SalesVehicleExample();
        SalesVehicleExample.Criteria criteria = example.createCriteria();


        DbProxy dbProxy = dbManager.getDbProxy(true);
        SalesVehicleMapper mapper = dbProxy.getMapper(SalesVehicleMapper.class);

        try {
            if(param.getStid() != null && !param.getStid().isEmpty()) {
                criteria.andStidEqualTo(param.getStid());
            }
            if(param.getAcid() != null) {
                criteria.andAcidEqualTo(param.getAcid());
            }
            if(param.getCarbrandid() != null) {
                criteria.andCarbrandidEqualTo(param.getCarbrandid());
            }
            if(param.getCarsetid() != null) {
                criteria.andCarsetidEqualTo(param.getCarsetid());
            }

            example.setOrderByClause("f_order asc");
            List<SalesVehicle> list = mapper.selectByExample(example);

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
    public PtResult getById(Integer id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        SalesVehicleMapper mapper = dbProxy.getMapper(SalesVehicleMapper.class);

        try {
            SalesVehicle record = mapper.selectByPrimaryKey(id);

            if (record == null) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult add(SalesVehicle record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(SALES_VEHICLE_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setSvid(id.intValue());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        SalesVehicleMapper mapper = dbProxy.getMapper(SalesVehicleMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getSvid());
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
    public PtResult update(SalesVehicle record) {
        SalesVehicleExample example = new SalesVehicleExample();
        example.or().andSvidEqualTo(record.getSvid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        SalesVehicleMapper mapper = dbProxy.getMapper(SalesVehicleMapper.class);

        try {
            int result = mapper.updateByExampleSelective(record, example);

            if (result == 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_DB_RESULT, null, null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult delete(Integer id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        SalesVehicleMapper mapper = dbProxy.getMapper(SalesVehicleMapper.class);

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
