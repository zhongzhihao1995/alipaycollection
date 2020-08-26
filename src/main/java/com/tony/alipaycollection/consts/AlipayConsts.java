package com.tony.alipaycollection.consts;

/**
 * @author tony
 * @date 2020/8/21 13:42
 */
public class AlipayConsts {
    public final static String AliTradeSuccess = "TRADE_SUCCESS";
    public final static String AliTradeFinished = "TRADE_FINISHED";

    public final static String AliOutTradeNo = "out_trade_no";
    public final static String TradeStatus = "trade_status";

    public final static String SuccessCode = "10000";

    public static class Status {
        public final static int MachineOrderInit = 0;        //未支付
        public final static int MachineOrderPaySucc = 1;    //充值成功
    }

}
