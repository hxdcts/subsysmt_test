package com.pfb.biz.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.pfb.annotation.PropertyBindingAnnotation;

/**
 * 华融微信通道转换工具类
 *
 * @author zhang.hui@pufubao.net
 * @version v1.0
 * @date 2016年8月25日 下午6:55:28
 */
public class ConvertUtil {

	private static final Logger logger = LoggerFactory.getLogger(ConvertUtil.class);
    /***
     * 将对象转换为map对象
     * @param thisObj 对象
     * @return
     */
    public static Map<String, Object> objectToMap(Object thisObj, String[] ignores) {
        Map<String, Object> map = new HashMap<String, Object> ();
        Class c;
        try {
            c = Class.forName(thisObj.getClass().getName());
            //获取所有的方法
            Method[] m = c.getMethods();
            for (int i = 0; i < m.length; i++) {   //获取方法名
                String method = m[i].getName();
                //获取get开始的方法名
                if (method.startsWith("get") && !method.contains("getClass")) {
                    try {
                        //获取对应对应get方法的value值
                        Object value = m[i].invoke(thisObj);
                        if (value != null) {
                            //截取get方法除get意外的字符 如getUserName-->UserName
                            String key = method.substring(3);
                            //将属性的第一个值转为小写
                            key = key.substring(0, 1).toLowerCase() + key.substring(1);
                            //将属性key,value放入对象
                            if (null != ignores && ignores.length > 0) {
                                boolean existOfIgnores = isExistOfIgnores(key, ignores);
                                if (!existOfIgnores) {
                                    map.put(key, value);
                                }
                            } else {
                                map.put(key, value);
                            }
                        }
                    } catch (Exception e) {
                        logger.info("error: {}", method);
                    }
                }
            }
        } catch (Exception e) {
            logger.info("对象转换map出错");
        }

        map.remove("serialVersionUID");
        return map;
    }


    /**
     * 判断fieldName是否是ignores中排除的
     *
     * @param fieldName
     * @param ignores
     * @return
     * @author zhang.hui@pufubao.net
     * @date 2016年10月17日 下午8:44:11
     */
    private static boolean isExistOfIgnores(String fieldName, String[] ignores) {
        boolean flag = false;
        for (String str : ignores) {
            if (str.equals(fieldName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    /**
     * map<String, String[]>转换map<String, String>
     *
     * @param parameterMap
     * @return
     * @author zhang.hui@pufubao.net
     * @date 2016年12月26日 下午4:47:51
     */
    public static Map<String, String> convertToMap(Map<String, String[]> parameterMap) {
        if (parameterMap != null) {
            Map<String, String> map = new HashMap<String, String>();
            Set<Map.Entry<String, String[]>> ps = parameterMap.entrySet();
            for (Map.Entry<String, String[]> e : ps) {
                map.put(e.getKey(), e.getValue()[0]);
            }
            return map;
        }
        return null;
    }


    /**
     * 字符串拼接转换成Map
     *
     * @param str
     * @return
     */
    public static Map<String, String> strCover2StringMap(String str) {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit = null;
        if (str == null) {
            return mapRequest;
        }
        // 每个键值为一组
        arrSplit = str.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]", 2);

            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 字符串拼接转换成Map
     *
     * @param str
     * @return
     */
    public static Map<String, Object> strCover2ObjectMap(String str) {
        Map<String, Object> mapRequest = new HashMap<String, Object>();

        String[] arrSplit = null;
        if (str == null) {
            return mapRequest;
        }
        // 每个键值为一组
        arrSplit = str.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]", 2);

            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param clazz 要转化的类型
     * @param map   包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static <T> T convertMap(Class<T> clazz, Map<String, String> map) {
        T rep = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
            Object obj = clazz.newInstance(); // 创建 JavaBean 对象
            rep = (T) obj;
            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();

                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);

                    Object[] args = new Object[1];
                    args[0] = value;

                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return rep;
    }

    /**
     * 获取所有request请求参数key-value
     *
     * @param request
     * @return
     */
    public static Map<String, String> getRequestParams(HttpServletRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        if (null != request) {
            Set<String> paramsKey = request.getParameterMap().keySet();
            for (String key : paramsKey) {
                params.put(key, request.getParameter(key));
            }
        }
        return params;
    }

    public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {

        Map<String, Object> params = new HashMap<String, Object>();
        if (null != request) {
            Set<String> paramsKey = request.getParameterMap().keySet();
            for (String key : paramsKey) {
                params.put(key, request.getParameter(key));
            }
        }
        return params;
    }

    /**
     * 解析请求参数为JSON字符串
     *
     * @param request 请求
     * @return
     * @throws Exception
     * @author zhang.hui@pufubao.net
     * @date 2016年10月17日 下午9:17:35
     */
    public static String parseRequest2JsonStr(HttpServletRequest request) throws Exception {
        // 读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        String reqStr = sb.toString();

        return reqStr;
    }

    public static <T, E> T convertEntity(E fromInstance, Class<T> toClazz) throws Exception 
    {
    	if(null == fromInstance){
    		return null;
    	}
    	T target = toClazz.newInstance();
    	Class<? extends Object> fromClazz = fromInstance.getClass();
    	Field[] fields = fromClazz.getDeclaredFields();
    	for(Field f : fields){
    		PropertyBindingAnnotation anno = f.getAnnotation(PropertyBindingAnnotation.class);
    		if(null == anno){
    			continue;
    		}
    		f.setAccessible(true);
    		if(null == f.get(fromInstance)){
    			continue;
    		}
    		String propertyName = anno.propertyName();
    		String proVal = f.get(fromInstance).toString();
    		String methodName = "set"+propertyName.substring(0, 1).toUpperCase()+propertyName.substring(1);
    		if(anno.autoWired()){
    			String[] propertyFrom = anno.propertyFrom();
        		String[] propertyTarget = anno.propertyTarget();
        		if(propertyFrom.length==0 && StringUtils.isEmpty(propertyFrom[0])){
        			continue;
        		}
        		for(int i=0 ; i<propertyFrom.length; i++){
        			if(proVal.equals(propertyFrom[i])){
        				proVal = propertyTarget[i];
        				break;
        			}
        		}
    		}
    		
    		Method m = toClazz.getMethod(methodName,String.class);
    		m.invoke(target, proVal);
    	}
    	return target;
    }
    public static void main(String[] args) throws Exception {

    }
}
