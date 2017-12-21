package com.pfb.biz.utils;

import com.pfb.common.util.DateUtil;

import java.util.Random;

/**
 * 工具类
 *
 * @author zhang.hui@pufubao.net
 * @version v1.0
 * @date 2016年10月13日 下午5:54:41
 */
public class BRCBUtil {

    /**
     * 生成yyyyMMdd订单号
     *
     * @param prefix 前缀
     * @return
     */
    public static String getOrderNum(String prefix) {
        Random random = new Random();
        String merOrderId = prefix
                + DateUtil.getFormatTime("yyyyMMddHHmmssSSS")
                + (random.nextInt((int) Math.pow(10, 4)) + (int) Math
                .pow(10, 5));
        return merOrderId;
    }

    /**
     * 生成
     *
     * @param prefix 前缀
     * @return
     */
    public static String getTimeMillisNum(String prefix) {
        Random random = new Random();
        String merOrderId = prefix
                + String.valueOf(System.currentTimeMillis())
                + (random.nextInt((int) Math.pow(10, 3)) + (int) Math
                .pow(10, 4));
        return merOrderId;
    }

    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
