package com.sundroid.lystn.pojo.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sundroid.lystn.pojo.artiste.PodcastDetailPOJO;

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
    private boolean playing;
    private boolean showchecks;
    private boolean checked;
    private PodcastDetailPOJO podcastDetailPOJO;

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

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isShowchecks() {
        return showchecks;
    }

    public void setShowchecks(boolean showchecks) {
        this.showchecks = showchecks;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public PodcastDetailPOJO getPodcastDetailPOJO() {
        return podcastDetailPOJO;
    }

    public void setPodcastDetailPOJO(PodcastDetailPOJO podcastDetailPOJO) {
        this.podcastDetailPOJO = podcastDetailPOJO;
    }
}
