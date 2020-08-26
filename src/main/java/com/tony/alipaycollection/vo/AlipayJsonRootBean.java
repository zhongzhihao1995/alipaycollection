package com.tony.alipaycollection.vo;

/**
 * @author tony
 * @date 2020/8/24 11:10
 */
public class AlipayJsonRootBean {
    private Alipay_trade_precreate_response alipay_trade_precreate_response;
    private String sign;
    public void setAlipay_trade_precreate_response(Alipay_trade_precreate_response alipay_trade_precreate_response) {
        this.alipay_trade_precreate_response = alipay_trade_precreate_response;
    }
    public Alipay_trade_precreate_response getAlipay_trade_precreate_response() {
        return alipay_trade_precreate_response;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSign() {
        return sign;
    }
}
