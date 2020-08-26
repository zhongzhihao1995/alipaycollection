package com.tony.alipaycollection.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.tony.alipaycollection.config.ApiException;
import com.tony.alipaycollection.config.RetEnum;
import com.tony.alipaycollection.consts.AlipayConsts;
import com.tony.alipaycollection.service.AlipayService;
import com.tony.alipaycollection.vo.AlipayBean;
import com.tony.alipaycollection.vo.AlipayJsonRootBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author tony
 * @date 2020/8/19 13:42
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    /**支付宝请求地址*/
    private static String aliUrl = "https://openapi.alipay.com/gateway.do";
    /**支付宝应用ID*/
    private static String aliAppId = "11223344";
    /**本地通过"支付宝开放平台开发助手"生成的私钥*/
    private static String aliAppPrivateKey = "ABCD123";
    /**支付宝应用设置本地公钥后生成对应的支付宝公钥（非本地生成的公钥）*/
    private static String alipayPublicKey = "ABCD123";;
    /**支付宝回调的接口地址*/
    private static String aliNotifyUrl = "http://localhost:8080/alinotify";

    @Override
    public String newAliOrder() throws Exception {
        log.info("开始调用支付宝生成支付二维码...");
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(aliUrl, aliAppId, aliAppPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
        //设置请求参数
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo("20200826888888888888888");
        model.setTotalAmount("1");
        model.setSubject("充值");
        //如果没有店铺号可不设置
        model.setStoreId("9527");
        model.setQrCodeTimeoutExpress("10m");
        request.setBizModel(model);
        //支付宝异步通知地址
        request.setNotifyUrl(aliNotifyUrl);
        log.info("创建支付宝订单，请求参数：{} ", JSONObject.toJSONString(request));
        //调用接口
        AlipayTradePrecreateResponse response = alipayClient.execute(request);

        log.info("创建支付宝订单，返回值：{} ", JSONObject.toJSONString(response));
        if (!response.isSuccess()) {
            throw new ApiException(RetEnum.MachineOrderAlipayException);
        }
        AlipayJsonRootBean alipayJsonRootBean = JSONObject.parseObject(response.getBody(), AlipayJsonRootBean.class);
        if(!AlipayConsts.SuccessCode.equals(alipayJsonRootBean.getAlipay_trade_precreate_response().getCode())){
            throw new ApiException(RetEnum.MachineOrderAlipayException);
        }
        log.info("交易订单号outTradeNo：{} ", response.getOutTradeNo());
        log.info("支付二维码qrCode：{} ", response.getQrCode());
        return response.getQrCode();
    }

    @Override
    public void aliNotify(Map<String, String> param) throws Exception {
        log.info("支付宝异步回调接口数据处理");
        //只有支付成功后，支付宝才会回调应用接口，可直接获取支付宝响应的参数
        String order_id = param.get(AlipayConsts.AliOutTradeNo);
        //出于安全考虑，通过支付宝回传的订单号查询支付宝交易信息
        AlipayTradeQueryResponse aliResp = queryOrder(order_id);
        if (!AlipayConsts.SuccessCode.equals(aliResp.getCode())) {
            //返回值非10000
            throw new ApiException(RetEnum.MachineOrderAlipayException, aliResp.getSubMsg());
        }
        if (!AlipayConsts.AliTradeSuccess.equals(aliResp.getTradeStatus()) && !AlipayConsts.AliTradeFinished.equals(aliResp.getTradeStatus())) {
            //支付宝订单状态不是支付成功
            throw new ApiException(RetEnum.MachineOrderAliUnPay);
        }
        //可对支付宝响应参数AlipayTradeQueryResponse进行处理

    }

    @Override
    public AlipayTradeQueryResponse queryOrder(String orderId) throws Exception {
        log.info("查询支付宝订单，订单编号为：{}", orderId);
        AlipayClient alipayClient = new DefaultAlipayClient(aliUrl, aliAppId, aliAppPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(orderId);
        request.setBizModel(model);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        log.info("查询支付宝订单，返回数据：{}", response);
        return response;
    }


}
