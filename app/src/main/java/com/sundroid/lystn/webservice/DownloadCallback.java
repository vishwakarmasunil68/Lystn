package com.sundroid.lystn.webservice;

public interface DownloadCallback {
    public void onSuccessDownload(String podcast_id);

    public void onDownloadFailed(String error);
}
