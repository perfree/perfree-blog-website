package org.perfree.model;

import org.apache.commons.lang3.StringUtils;

public class Article extends com.perfree.model.Article {
    private String creator;
    private Integer payType;
    private String previewUrl;
    private String docUrl;
    private String downloadUrl;
    private String url;
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUrl() {
        String pre = "/" + getType() + "/";
        if (StringUtils.isBlank(getSlug())) {
            return pre + getId();
        }
        return pre + getSlug();
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
