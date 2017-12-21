package com.pfb.biz.common;

/**
 * 配置信息
 *
 * @author zhanghui
 * @version 1.0
 * @date: 26/09/2017 10:27
 */

public class CommonConfig {

    private String developMode;
    private String signKey;
    private String ossUploadUrl;
    private String ossUploadKey;
    private String ossUploadSource;

    public String getDevelopMode() {
        return developMode;
    }

    public void setDevelopMode(String developMode) {
        this.developMode = developMode;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getOssUploadUrl() {
        return ossUploadUrl;
    }

    public void setOssUploadUrl(String ossUploadUrl) {
        this.ossUploadUrl = ossUploadUrl;
    }

    public String getOssUploadKey() {
        return ossUploadKey;
    }

    public void setOssUploadKey(String ossUploadKey) {
        this.ossUploadKey = ossUploadKey;
    }

    public String getOssUploadSource() {
        return ossUploadSource;
    }

    public void setOssUploadSource(String ossUploadSource) {
        this.ossUploadSource = ossUploadSource;
    }
}
