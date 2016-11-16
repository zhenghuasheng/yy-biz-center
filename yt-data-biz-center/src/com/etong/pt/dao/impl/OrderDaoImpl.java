package com.etong.pt.dao.impl;

import com.etong.pt.dao.OrderDao;
import com.etong.pt.dao.codegenerate.CodeUtil;
import com.etong.pt.data.order.Order;
import com.etong.pt.data.order.OrderExample;
import com.etong.pt.data.order.OrderMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenlinyang on 2015/10/19.
 */
public class OrderDaoImpl implements OrderDao {
    public static final String ORDER_INDEX = "biz_center_order";
    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);
    private DbIndexNum dbIndexNum;
    private DbManager dbManager;

    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public PtResult getById(Long id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        OrderMapper mapper = dbProxy.getMapper(OrderMapper.class);
        try {
            Order record = mapper.selectByPrimaryKey(id);

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
    public PtResult getListByUserId(Integer userId) {
        OrderExample example = new OrderExample();
        example.or().andF_midEqualTo(new Long(userId));
        DbProxy dbProxy = dbManager.getDbProxy(true);
        OrderMapper mapper = dbProxy.getMapper(OrderMapper.class);

        try {
            List<Order> list = mapper.selectByExample(example);

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
    public PtResult add(Order record) {
        if (dbIndexNum == null) {
            return new PtResult(PtCommonError.PT_ERROR_INVALID_SERVICE
                    , "数据库索引服务为空", null);
        }

        PtResult ptResult = dbIndexNum.generateIndexNum(ORDER_INDEX);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        Long id = ptResult.getObject();
        record.setF_ofid(id);
        record.setF_ofcode(CodeUtil.getOrderCodeSN(id));

        DbProxy dbProxy = dbManager.getDbProxy(true);
        OrderMapper mapper = dbProxy.getMapper(OrderMapper.class);

        try {
            int result = mapper.insertSelective(record);

            if (result > 0) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getF_ofid());
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
    public PtResult edit(Order record) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        OrderMapper mapper = dbProxy.getMapper(OrderMapper.class);

        try {
            int result = mapper.updateByPrimaryKeySelective(record);

            if (result == 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, record.getF_ofid());
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
    public PtResult findByParam(Order param,Integer page, Integer pageSize) {
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        if (param.getF_cartypeid() != null) {
            criteria.andF_cartypeidEqualTo(param.getF_cartypeid());
        }
        if (param.getF_name() != null && !param.getF_name().isEmpty()) {
            criteria.andF_nameEqualTo(param.getF_name());
        }
        if (param.getF_phone() != null && !param.getF_phone().isEmpty()) {
            criteria.andF_phoneEqualTo(param.getF_phone());
        }
        if (param.getF_mid() != null) {
            criteria.andF_midEqualTo(param.getF_mid());
        }
        if (param.getF_stid() != null && !param.getF_stid().isEmpty()) {
            criteria.andF_stidEqualTo(param.getF_stid());
        }
        if (param.getF_status() != null) {
            criteria.andF_statusEqualTo(param.getF_status());
        }
        if (param.getF_type() != null) {
            criteria.andF_typeEqualTo(param.getF_type());
        }

        DbProxy dbProxy = dbManager.getDbProxy(true);
        OrderMapper mapper = dbProxy.getMapper(OrderMapper.class);

        try {
            int total = mapper.countByExample(example);

            if (page != null && pageSize != null) {
                example.setStart((page - 1) * pageSize);
                example.setLimit(pageSize);
            }

            example.setOrderByClause("f_createtime desc");
            List<Order> list = mapper.selectByExampleWithBLOBs(example);

            if (list.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> objectMap = new HashMap<String, Object>();
            objectMap.put("total", total);
            objectMap.put("list", list);

            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, objectMap);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            dbProxy.close();
        }
    }

    @Override
    public PtResult remove( Long id) {
        DbProxy dbProxy = dbManager.getDbProxy(true);
        OrderMapper mapper = dbProxy.getMapper(OrderMapper.class);

        try {
            int result = mapper.deleteByPrimaryKey(id);

            if (result == 1) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            }
            {
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
    public PtResult getOrderListRequest(Integer status,Integer type, String name, String phone,
                                        String appId,Integer start,Integer limit) {
        DbProxy dbProxy = null;
        try {
            dbProxy=dbManager.getDbProxy(true);
            OrderMapper mapper = dbProxy.getMapper(OrderMapper.class);
            OrderExample example = new OrderExample();
            OrderExample.Criteria criteria = example.createCriteria();
            if (status != null) {
                criteria.andF_statusEqualTo(status);
            }
            if (type != null) {
                criteria.andF_typeEqualTo(type);
            }
            if (StringUtils.isNotEmpty(appId)) {
                criteria.andF_stidEqualTo(appId);
            }
            if(StringUtils.isNotEmpty(name)) {
                criteria.andF_nameEqualTo(name);
            }
            if(StringUtils.isNotEmpty(phone)) {
                criteria.andF_phoneEqualTo(phone);
            }
            Integer total =mapper.countByExample(example);

            example.setOrderByClause("f_createtime desc");
            example.setStart(start);
            example.setLimit(limit);
            List<Order> orderList = mapper.selectByExampleWithBLOBs(example);
            if (orderList.isEmpty()) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, total.toString(), orderList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        } finally {
            if (dbProxy != null) {
                dbProxy.close();
            }
        }
    }

    @Override
    public PtResult getCarorderListStatus(Long userId, String appId, List<Integer> status) {

        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);

            OrderExample example=new OrderExample();
            OrderExample.Criteria criteria=example.createCriteria();
            criteria.andF_midEqualTo(userId);
            criteria.andF_stidEqualTo(appId);
            if (!status.isEmpty()){
                criteria.andF_statusIn(status);
            }

            OrderMapper orderMapper=dbProxy.getMapper(OrderMapper.class);
            List<Order>orderList=orderMapper.selectByExampleWithBLOBs(example);

            if (orderList.isEmpty()){
                return new PtResult(PtCommonError.PT_ERROR_NODATA,null,null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,orderList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_DB,e.getMessage(),null);
        } finally {
            if (dbProxy!=null){
                dbProxy.close();
            }
        }
    }

}
