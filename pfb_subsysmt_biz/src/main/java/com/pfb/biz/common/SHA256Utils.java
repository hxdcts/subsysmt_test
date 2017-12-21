package com.pfb.biz.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pfb.common.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * SHA256加密
 * @author zhang.mingmeng@pufubao.net
 */
public class SHA256Utils {

	 private static Logger logger = LoggerFactory.getLogger(SHA256Utils.class);
    /**
     *  利用java原生的摘要实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(Map<String, Object> map, String key){
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
        sb.append("key=").append(key);
        String result = sb.toString();
        logger.info("Sign Before SHA256: {}", result);
        String encodeStr = "";
        try {
        	encodeStr=DigestUtils.sha256Hex(result);
        } catch (Exception e) {
            logger.error("SHA256加密异常 {}",e.getMessage());;
        } 
        logger.info("Sign Result: {}", encodeStr);
        return encodeStr;
    }
    
}
