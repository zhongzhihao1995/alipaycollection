package com.tony.alipaycollection.vo;

/**
 * @author tony
 * @date 2020/8/24 11:10
 */
public class Alipay_trade_precreate_response {
    private String code;
    private String msg;
    private String out_trade_no;
    private String qr_code;
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }
    public String getQr_code() {
        return qr_code;
    }

}
