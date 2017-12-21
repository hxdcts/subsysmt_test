package com.pfb.biz.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.pfb.common.util.DateUtil;

/**
 * 工具类
 *
 * zhang.mingmeng@pufubao.net
 * 
 */
public class GZCMBCUtil {

	/**
	 * 生成yyyyMMdd订单号
	 *
	 * @param prefix
	 *            前缀
	 * @return
	 * @author zhang.hui@pufubao.net
	 * @date 2016年10月13日 下午5:57:40
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
	 * @param prefix
	 *            前缀
	 * @return
	 * @author zhang.hui@pufubao.net
	 * @date 2016年10月13日 下午6:49:44
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
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 * @author zhang.hui@pufubao.net
	 * @date 2016年10月13日 下午8:50:16
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

	private static final String DATE_BEGIN="23:30:00";
	private static final String DATE_END="00:30:00";

	/**
	 * 判断date 是否在 DATE_BEGIN 与 DATE_END 之间
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static boolean isInDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		try {
			long now = sdf.parse(sdf.format(date)).getTime();
			long start = sdf.parse(DATE_BEGIN).getTime();
			long end = sdf.parse(DATE_END).getTime();
			if (end < start) {
				if (now >= end && now < start) {
					return false;
				} else {
					return true;
				}
			} else {
				if (now >= start && now < end) {
					return true;
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

    /**
     * 支付卡类型：
     * 1.借记卡（DEBIT）<br/>
     * 2.信用卡（CREDIT）<br/>
     * 3.钱包（WALLET）<br/>
     * 4.花呗（PCREDIT）<br/>
     * 5.储值卡（MCARD）<br/>
     * 6.红包，优惠券（COUPON）<br/>
     *
     * @param bankType 支付卡类型
     * @return
     */
//    public static String convertFundType(String bankType) {
//		Map<String, String> map = new HashMap<>();
//		map.put("CFT", CommonConstant.ORDER_FUND_TYPE_WALLET);//微信
//		map.put("ALIPAYACCOUNT", CommonConstant.ORDER_FUND_TYPE_WALLET);//支付宝
//		map.put("PCREDIT", CommonConstant.ORDER_FUND_TYPE_PCREDIT);//支付宝
//		map.put("MCARD", CommonConstant.ORDER_FUND_TYPE_MCARD);//支付宝
//		map.put("COUPON", CommonConstant.ORDER_FUND_TYPE_COUPON);//支付宝
//
//		// 支付卡类型
//		boolean b = map.containsKey(bankType);
//		if (b) {
//			String value = map.get(bankType);
//			return value;
//		} else {
//			String credit = CommonConstant.ORDER_FUND_TYPE_CREDIT;//信用卡
//			String debit = CommonConstant.ORDER_FUND_TYPE_DEBIT;//借记卡
//			int isCredit = bankType.indexOf(credit);
//			int isDebit = bankType.indexOf(debit);
//			if (isCredit != -1) {
//				return credit;
//			}
//			if (isDebit != -1) {
//				return debit;
//			}
//			return bankType;
//		}
//	}

}
