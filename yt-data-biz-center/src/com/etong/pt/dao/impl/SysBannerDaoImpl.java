package com.etong.pt.dao.impl;

import com.etong.pt.dao.SysBannerDao;
import com.etong.pt.data.sysbanner.SysBanner;
import com.etong.pt.data.sysbanner.SysBannerExample;
import com.etong.pt.data.sysbanner.SysBannerMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by chenlinyang on 2015/11/30.
 */
public class SysBannerDaoImpl implements SysBannerDao {
    public static final String SysItem_INDEX = "biz_center_sysbanner";
    private static final Logger logger = LoggerFactory.getLogger(SysBannerDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult findByParam(SysBanner param) {
        SysBannerExample example = new SysBannerExample();
        SysBannerExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        SysBannerMapper mapper = dbProxy.getMapper(SysBannerMapper.class);

        try {
            if(param.getStid() != null) {
                criteria.andStidEqualTo(param.getStid());
            }

            List<SysBanner> list = mapper.selectByExampleWithBLOBs(example);

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
        SysBannerMapper mapper = dbProxy.getMapper(SysBannerMapper.class);

        try {
            SysBanner record = mapper.selectByPrimaryKey(id);

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
    public PtResult add(SysBanner record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(SysItem_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setBnid(id);

        DbProxy dbProxy = dbManager.getDbProxy(true);
        SysBannerMapper mapper = dbProxy.getMapper(SysBannerMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getBnid());
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
    public PtResult update(SysBanner record) {
        SysBannerExample example = new SysBannerExample();
        example.or().andBnidEqualTo(record.getBnid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        SysBannerMapper mapper = dbProxy.getMapper(SysBannerMapper.class);

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
        SysBannerMapper mapper = dbProxy.getMapper(SysBannerMapper.class);

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
