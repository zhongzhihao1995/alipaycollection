package com.tony.alipaycollection.config;

public enum RetEnum {
    /**成功返回码*/
    Succeeded(0, "成功"),

    /**失败返回码*/
    Failed(1, "服务繁忙（1），请稍后重试"),


    MachineOrderAlipayException(7003, "支付宝接口调用成功，但返回错误"),
    MachineOrderAliUnPay(7004,"支付宝订单状态错误"),



    ;


    private int code;
    private String message;


    RetEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int asCode() {
        return code;
    }

    public String asMessage() {
        return message;
    }

    public static RetEnum get(int code) {
        for (RetEnum a : RetEnum.values()) {
            if (a.code == code) {
                return a;
            }
        }
        return RetEnum.Failed;
    }

}
