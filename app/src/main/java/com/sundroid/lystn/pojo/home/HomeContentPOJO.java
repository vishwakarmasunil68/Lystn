package com.sundroid.lystn.pojo.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeContentPOJO implements Serializable {
    @SerializedName("conId")
    @Expose
    private String conId;
    @SerializedName("conName")
    @Expose
    private String conName;
    @SerializedName("imgIrl")
    @Expose
    private String imgIrl;
    @SerializedName("cotDeepLink")
    @Expose
    private String cotDeepLink;
    private String description;
    private String subtitle;

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getImgIrl() {
        return imgIrl;
    }

    public void setImgIrl(String imgIrl) {
        this.imgIrl = imgIrl;
    }

    public String getCotDeepLink() {
        return cotDeepLink;
    }

    public void setCotDeepLink(String cotDeepLink) {
        this.cotDeepLink = cotDeepLink;
    }

    @Override
    public String toString() {
        return "HomeContentPOJO{" +
                "conId='" + conId + '\'' +
                ", conName='" + conName + '\'' +
                ", imgIrl='" + imgIrl + '\'' +
                ", cotDeepLink='" + cotDeepLink + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
