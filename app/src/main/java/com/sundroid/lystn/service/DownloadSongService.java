package com.sundroid.lystn.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ixuea.android.downloader.DownloadService;
import com.ixuea.android.downloader.callback.DownloadListener;
import com.ixuea.android.downloader.callback.DownloadManager;
import com.ixuea.android.downloader.domain.DownloadInfo;
import com.ixuea.android.downloader.exception.DownloadException;
import com.sundroid.lystn.R;
import com.sundroid.lystn.util.TagUtils;
import com.sundroid.lystn.util.UtilityFunction;

import java.io.File;

public class DownloadSongService extends Service {

    DownloadManager downloadManager;
    String conId;
    String url;
    String file_name;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    int id = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        downloadManager = DownloadService.getDownloadManager(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TagUtils.getTag(), "on start");
        conId = intent.getStringExtra("conId");
        url = intent.getStringExtra("url");

        Log.d(TagUtils.getTag(), "onstart command");

        file_name = UtilityFunction.createTestingDir(getApplicationContext()) + File.separator +
                conId + ".mp3";

        Log.d(TagUtils.getTag(), "file name:-" + file_name);

        startDownload();

        return START_NOT_STICKY;
    }

    private void startDownload() {
        final DownloadInfo[] downloadInfo = {new DownloadInfo.Builder().setUrl(url.trim())
                .setPath(file_name)
                .build()};

        downloadInfo[0].setDownloadListener(new DownloadListener() {

            @Override
            public void onStart() {
                Log.d(TagUtils.getTag(), "Prepare downloading");
                mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                int notificationId = 1;
                String channelId = "channel-01";
                String channelName = "Channel Name";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, channelName, importance);
                    mNotifyManager.createNotificationChannel(mChannel);
                }

                mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId);
                mBuilder.setContentTitle("File Download")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.mipmap.ic_launcher);
            }

            @Override
            public void onWaited() {
                Log.d(TagUtils.getTag(), "Waiting");
                Log.d(TagUtils.getTag(), "Pause");
            }

            @Override
            public void onPaused() {
                Log.d(TagUtils.getTag(), "Continue");
                Log.d(TagUtils.getTag(), "Paused");
            }

            @Override
            public void onDownloading(long progress, long size) {

                mBuilder.setProgress((int) size, (int) progress, false);
                mNotifyManager.notify(id, mBuilder.build());

                Log.d(TagUtils.getTag(), "progress:-" + progress + "/size:-" + size);
                Log.d(TagUtils.getTag(), "Pause");
            }

            @Override
            public void onRemoved() {
                Log.d(TagUtils.getTag(), "Download");
                Log.d(TagUtils.getTag(), "");
                downloadInfo[0] = null;
            }

            @Override
            public void onDownloadSuccess() {
                Log.d(TagUtils.getTag(), "Delete");
                Log.d(TagUtils.getTag(), "Download success");
                mBuilder.setContentText("Download completed")
                        // Removes the progress bar
                        .setProgress(0, 0, false);
                mNotifyManager.notify(id, mBuilder.build());
                stopSelf();
            }

            @Override
            public void onDownloadFailed(DownloadException e) {
                e.printStackTrace();
                Log.d(TagUtils.getTag(), "Download fail:" + e.getMessage());
            }
        });

        downloadManager.download(downloadInfo[0]);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TagUtils.getTag(), "on destroy service");
    }
}
