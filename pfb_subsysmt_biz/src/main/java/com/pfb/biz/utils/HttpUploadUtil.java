package com.pfb.biz.utils;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * http工具类
 *
 * @author zhang.lei@pufubao.net
 * @date 2017年4月28日 下午4:35:48
 */
public class HttpUploadUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUploadUtil.class);
    private static org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();

    /**
     * post 可上传文件
     *
     * @param url
     * @param params 参数 文件的话存放路径
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        MultipartPostMethod filePost = new MultipartPostMethod(url);
        try {

            // 通过以下方法可以模拟页面参数提交
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue() + "";
                if (isPath(value)) {
//                    if (isPic(value) && isPic(value)) {
                    File file = new File(value);
                    filePost.addPart(new FilePart(key, file));
//                    } else {
//                        filePost.addParameter(key, value + "");
//                    }
                } else {
                    filePost.addParameter(key, value + "");
                }
                log.info("post参数,name:{},value:{}", key, value + "");
            }

            // 由于要上传的文件可能比较大 , 因此在此设置最大的连接超时时间
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                return filePost.getResponseBodyAsString();
            }
        } catch (Exception e) {
            log.error("上传文件到oss出错{}", e);
        } finally {
            if (filePost != null) {
                filePost.releaseConnection();
            }
        }
        return "";
    }

    /**
     * 判断是否是路径 只针对图片 格式：JPEG，PNG
     *
     * @param content
     * @return
     */
    public static boolean isPath(String content) {
        if (content.contains(".jpg") || content.contains(".jpeg") || content.contains(".png"))
            return true;
        else
            return false;
    }


}
