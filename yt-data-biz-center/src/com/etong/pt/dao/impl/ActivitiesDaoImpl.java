package com.etong.pt.dao.impl;

import com.etong.pt.dao.ActivitiesDao;
import com.etong.pt.data.activities.Activities;
import com.etong.pt.data.activities.ActivitiesExample;
import com.etong.pt.data.activities.ActivitiesMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by chenlinyang on 2015/11/30.
 */
public class ActivitiesDaoImpl implements ActivitiesDao {
    public static final String SysItem_INDEX = "biz_center_activities";
    private static final Logger logger = LoggerFactory.getLogger(ActivitiesDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult findByParam(Activities param, Boolean status) {
        ActivitiesExample example = new ActivitiesExample();
        ActivitiesExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        ActivitiesMapper mapper = dbProxy.getMapper(ActivitiesMapper.class);

        int now = new Long(new Date().getTime()/1000).intValue();

        try {
            if(param.getStid() != null) {
                criteria.andStidEqualTo(param.getStid());
            }
            if(param.getType() != null) {
                criteria.andTypeEqualTo(param.getType());
            }
            if(status != null && status == Boolean.TRUE) { //实时活动
                criteria.andEnddateGreaterThan(now);
            }
            if(status != null && status == Boolean.FALSE) { //过期活动
                criteria.andEnddateLessThan(now);
            }

            List<Activities> list = mapper.selectByExampleWithBLOBs(example);

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
    public PtResult getById(Long id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        ActivitiesMapper mapper = dbProxy.getMapper(ActivitiesMapper.class);

        try {
            Activities record = mapper.selectByPrimaryKey(id);

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
    public PtResult add(Activities record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(SysItem_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setAcid(id);

        DbProxy dbProxy = dbManager.getDbProxy(true);
        ActivitiesMapper mapper = dbProxy.getMapper(ActivitiesMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getAcid());
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
    public PtResult update(Activities record) {
        ActivitiesExample example = new ActivitiesExample();
        example.or().andAcidEqualTo(record.getAcid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        ActivitiesMapper mapper = dbProxy.getMapper(ActivitiesMapper.class);

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
    public PtResult delete(Long id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        ActivitiesMapper mapper = dbProxy.getMapper(ActivitiesMapper.class);

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
