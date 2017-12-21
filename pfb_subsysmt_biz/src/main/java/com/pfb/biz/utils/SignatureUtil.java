package com.pfb.biz.utils;

import com.pfb.biz.common.SHA256Utils;
import com.pfb.common.util.JSONUtil;
import com.pfb.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名工具类
 *
 * @author zhang.hui@pufubao.net
 * @version v1.0
 * @date 2016年10月20日 上午10:58:24
 */
public class SignatureUtil {

    private static Logger logger = LoggerFactory.getLogger(SignatureUtil.class);

    /**
     * 签名算法
     *
     * @param map
     * @param key
     * @param log
     * @return
     * @author zhang.hui@pufubao.net
     * @date 2016年11月11日 下午2:53:33
     */
    public static String getSign(Map<String, Object> map, String key) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
        	if("sign".equals(entry.getKey())){
        		continue;
        	}
            if (entry.getValue() != null && StringUtil.isNotBlank(String.valueOf(entry.getValue()))) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result = result.substring(0, result.length() - 1);
        result += key;
        logger.info("Sign Before MD5: {}", result);
        result = MD5Util.MD5Encode(result).toUpperCase();
        logger.info("Sign Result: {}", result);
        return result;
    }

    public static String getSignByStringMap(Map<String, String> map, String key) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null && StringUtil.isNotBlank(String.valueOf(entry.getValue()))) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result = result.substring(0, result.length() - 1);
        result += key;
        logger.info("Sign Before MD5: {}", result);
        result = MD5Util.MD5Encode(result).toUpperCase();
        logger.info("Sign Result: {}", result);
        return result;
    }

    /**
     * 从API返回的JSON数据里面重新计算一次签名
     *
     * @param responseString API返回的JSON数据
     * @return 新鲜出炉的签名
     */
    public static String getSignFromResponseString(String responseString, String key) {
        Map<String, Object> map = JSONUtil.parseObject(responseString, Map.class);
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        return SignatureUtil.getSign(map, key);
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param responseString API返回的JSON数据字符串
     * @return API签名是否合法
     */
    public static boolean checkSign(String responseString, String key) {
        Map<String, Object> map = JSONUtil.parseObject(responseString, HashMap.class);
        logger.info("校验签名的数据:{}", map.toString());

        String signFromAPIResponse = map.get("sign").toString();
        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
        	logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("服务器回包里面的签名是:{}", signFromAPIResponse);
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = SignatureUtil.getSign(map, key);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {
            // 签名验不过，表示这个API返回的数据有可能已经被篡改了
        	logger.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }

    /**
     * 校验签名
     *
     * @param map
     * @param key
     * @param log
     * @return
     */
    public static boolean checkSign(Map<String, Object> map, String key) {
    	logger.info("校验签名的数据:{}", map.toString());
        String signFromAPIResponse = map.get("sign").toString();
        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
        	logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("服务器回包里面的签名是:{}", signFromAPIResponse);
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.remove("sign");
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = SignatureUtil.getSign(map, key);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {
            // 签名验不过，表示这个API返回的数据有可能已经被篡改了
        	logger.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }
    /**
     * 校验签名-美团验证
     *
     * @param map
     * @param key
     * @param log
     * @return
     */
    public static boolean checkSignToMt(Map<String, Object> map, String key) {
    	logger.info("校验签名的数据:{}", map.toString());
        String signFromAPIResponse = map.get("sign").toString();
        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
        	logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("服务器回包里面的签名是:{}", signFromAPIResponse);
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.remove("sign");
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = SHA256Utils.getSHA256Str(map, key);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {
            // 签名验不过，表示这个API返回的数据有可能已经被篡改了
        	logger.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        logger.info("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }
    
}
