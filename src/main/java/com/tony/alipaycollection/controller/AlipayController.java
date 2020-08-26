package com.tony.alipaycollection.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import com.tony.alipaycollection.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tony
 * @date 2020/8/26 16:55
 */
@Slf4j
@RestController
public class AlipayController {

    /**支付宝应用设置本地公钥后生成对应的支付宝公钥（非本地生成的公钥）*/
    private static String alipayPublicKey = "ABCD123";;

    @Resource
    private AlipayService alipayService;

    /**生成支付宝二维码*/
    @RequestMapping(value = "qrcode", method = RequestMethod.GET)
    public String qrCode() throws Exception {
        return alipayService.newAliOrder();
    }

    /**支付宝回调接口*/
    /**不返回success，支付宝会在25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）才会结束通知发送。*/
    @RequestMapping(value = "alinotify")
    public String aliNotify(HttpServletRequest request) throws Exception {
        try {
            log.info("进入支付宝回调地址");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            log.info("支付宝验签参数：{}", JSON.toJSONString(requestParams));
            for (String name : requestParams.keySet()) {
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            boolean flag = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
            if (flag) {
                alipayService.aliNotify(params);
                log.info("支付宝通知更改状态成功！");
                return "success";
            }
        } catch (Throwable e) {
            log.error("exception: ", e);
        }
        return "failure";
    }

}
