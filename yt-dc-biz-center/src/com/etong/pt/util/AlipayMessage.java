package com.etong.pt.util;

import java.io.Serializable;

public class AlipayMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;//订单ID
    private Double txnAmt; //交易金额
    private Integer payType;//支付方式（1-支付 4-储蓄卡）
    private Integer payItem;//支付项目
    private String payCode;//支付编码

    public AlipayMessage() {

    }

    public AlipayMessage(Long orderId, Double txnAmt, Integer payType,
                         Integer payItem, String payCode) {
        super();
        this.orderId = orderId;
        this.txnAmt = txnAmt;
        this.payType = payType;
        this.payItem = payItem;
        this.payCode = payCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(Double txnAmt) {
        this.txnAmt = txnAmt;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPayItem() {
        return payItem;
    }

    public void setPayItem(Integer payItem) {
        this.payItem = payItem;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    @Override
    public String toString() {
        return "AlipayMessage{" +
                "orderId=" + orderId +
                ", txnAmt=" + txnAmt +
                ", payType=" + payType +
                ", payItem=" + payItem +
                ", payCode='" + payCode + '\'' +
                '}';
    }
}
