package com.etong.pt.dao.impl;

import com.etong.pt.dao.DealerDao;
import com.etong.pt.data.dealer.Dealer;
import com.etong.pt.data.dealer.DealerExample;
import com.etong.pt.data.dealer.DealerMapper;
import com.etong.pt.data.dealer.DealerWithBLOBs;
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
public class DealerDaoImpl implements DealerDao {
    public static final String Dealer_INDEX = "biz_center_dealer";
    private static final Logger logger = LoggerFactory.getLogger(DealerDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult findByParam(Dealer param) {
        DealerExample example = new DealerExample();
        DealerExample.Criteria criteria = example.createCriteria();

        DbProxy dbProxy = dbManager.getDbProxy(true);
        DealerMapper mapper = dbProxy.getMapper(DealerMapper.class);

        try {
            if(param.getStid() != null) {
                criteria.andStidEqualTo(param.getStid());
            }

            List<Dealer> list = mapper.selectByExample(example);

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
        DealerMapper mapper = dbProxy.getMapper(DealerMapper.class);

        try {
            Dealer record = mapper.selectByPrimaryKey(id);

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
    public PtResult add(DealerWithBLOBs record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(Dealer_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setNcid(id.intValue());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        DealerMapper mapper = dbProxy.getMapper(DealerMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getNcid());
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
    public PtResult update(DealerWithBLOBs record) {
        DealerExample example = new DealerExample();
        example.or().andNcidEqualTo(record.getNcid());

        DbProxy dbProxy = dbManager.getDbProxy(true);
        DealerMapper mapper = dbProxy.getMapper(DealerMapper.class);

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
        DealerMapper mapper = dbProxy.getMapper(DealerMapper.class);

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
