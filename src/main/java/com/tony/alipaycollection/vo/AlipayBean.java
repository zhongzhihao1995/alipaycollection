package com.tony.alipaycollection.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author tony
 * @date 2020/8/19 13:48
 */
@Data
public class AlipayBean {
    /**商户订单号，必填*/
    private String outTradeNo;
    /**订单名称，必填*/
    private String subject;
    /**付款金额，必填*/
    private String totalAmount;
    /**商品描述，可空*/
    private String body;
    /**支付渠道*/
    private String payChannel;
    /**支付平台ios、android、web*/
    private String payPlatform;


}
