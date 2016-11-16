package com.etong.pt.service.impl;

import com.etong.pt.dao.SysItemDao;
import com.etong.pt.data.sysitem.SysItem;
import com.etong.pt.service.SysItemService;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.pt.view.model.TreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenlinyang on 2015/11/26.
 */
public class SysItemServiceImpl implements SysItemService {
    private SysItemDao sysItemDao;

    public void setSysItemDao(SysItemDao sysItemDao) {
        this.sysItemDao = sysItemDao;
    }

    @Override
    public PtResult findTreeByAppId(String appId) {
        SysItem param = new SysItem();
        param.setStid(appId);
        param.setStatus(Boolean.TRUE);
        param.setPitemid(null);
        PtResult ptResult = sysItemDao.findByParam(param);
        if(!ptResult.isSucceed()) {
            return ptResult;
        }

        List<SysItem> list = ptResult.<List>getObject();
        SysItem record = list.get(0);

        List<TreeModel> tree = new ArrayList<TreeModel>();
        TreeModel<SysItem> node = new TreeModel<SysItem>();
        node.setId(Integer.toString(record.getItemid()));
        node.setPid("root");
        node.setCode(record.getItemcode());
        node.setText(record.getName());
        node.setIcon(record.getIco());
        node.setDescription(record.getDesc());
        //node.setData(record);

        param.setPitemid(record.getItemid());
        ptResult = sysItemDao.findByParam(param);
        if(!ptResult.isSucceed()) {
            node.setChildren(null);
        }
        else {
            node.setChildren(getChildren(record.getItemid()));
        }

        tree.add(node);
        return  new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, tree);
    }

    @Override
    public PtResult findByParam(SysItem param) {
        return sysItemDao.findByParam(param);
    }

    @Override
    public PtResult add(SysItem record) {
        return sysItemDao.add(record);
    }

    @Override
    public PtResult update(SysItem record) {
        return sysItemDao.update(record);
    }

    // 递归调用
    private List<TreeModel> getChildren(Integer pid) {
        List<TreeModel> tree = new ArrayList<TreeModel>();
        SysItem param = new SysItem();
        param.setStatus(Boolean.TRUE);
        param.setPitemid(pid);
        PtResult ptResult = sysItemDao.findByParam(param);
        if(!ptResult.isSucceed()) {
            return null;
        }

        List<SysItem> list = ptResult.<List>getObject();
        TreeModel<SysItem> node = null;
        for (SysItem record : list) {
            node = new TreeModel<SysItem>();
            node.setId(Integer.toString(record.getItemid()));
            node.setPid(Integer.toString(pid));
            node.setCode(record.getItemcode());
            node.setText(record.getName());
            node.setIcon(record.getIco());
            node.setDescription(record.getDesc());
            //node.setData(record);

            param.setPitemid(record.getItemid());
            ptResult = sysItemDao.findByParam(param);
            if(!ptResult.isSucceed()) {
                node.setChildren(null);
            }
            else {
                node.setChildren(getChildren(record.getItemid()));
            }

            tree.add(node);
        }
        return tree;
    }
}
