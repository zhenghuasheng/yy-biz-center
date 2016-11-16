package com.etong.pt.dao.impl;

import com.etong.pt.dao.BargainDao;
import com.etong.pt.data.bargain.BargainInfo;
import com.etong.pt.data.bargain.BargainInfoExample;
import com.etong.pt.data.bargain.BargainInfoMapper;
import com.etong.pt.db.DbIndexNum;
import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Administrator on 2015/10/30.
 */
public class BargainDaoImpl implements BargainDao {
    private DbManager dbManager;
    private DbIndexNum dbIndexNum;

    private Logger logger=Logger.getLogger(BargainDaoImpl.class);
    public static final String BARGAIN_INDEX = "biz_center_app";

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }
    public void setDbIndexNum(DbIndexNum dbIndexNum) {
        this.dbIndexNum = dbIndexNum;
    }

    @Override
    public PtResult addBargainInfoRequest(BargainInfo bargainInfo) {
        PtResult ptResult=dbIndexNum.generateIndexNum(BARGAIN_INDEX);
        if (!ptResult.isSucceed()){
            return ptResult;
        }
        bargainInfo.setF_bgid((Long) ptResult.getObject());

        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);
            BargainInfoMapper bargainInfoMapper=dbProxy.getMapper(BargainInfoMapper.class);
            int result= bargainInfoMapper.insertSelective(bargainInfo);
            if (result<1){
                return new PtResult(PtCommonError.PT_ERROR_SUBMIT,"插入砍价流水失败",null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS,null,bargainInfo.getF_bgid());
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
    public PtResult getBargainInfoListRequest(Long ofid, String ofcode,Integer start,Integer limit) {

        DbProxy dbProxy=null;
        try {
            dbProxy=dbManager.getDbProxy(true);

            BargainInfoExample example=new BargainInfoExample();
            BargainInfoExample.Criteria criteria= example.createCriteria();
            example.setOrderByClause("f_operatetime desc");
            if (ofcode!=null){
                criteria.andF_devicecodeEqualTo(ofcode);
            }
            if (ofid!=null){
                criteria.andF_ofidEqualTo(ofid);
            }

            BargainInfoMapper mapper=dbProxy.getMapper(BargainInfoMapper.class);
            List<BargainInfo>list= mapper.selectByExample(example);
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
}
