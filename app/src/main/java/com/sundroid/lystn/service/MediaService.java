package com.sundroid.lystn.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.session.MediaSessionManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.sundroid.lystn.R;
import com.sundroid.lystn.pojo.home.HomeContentPOJO;
import com.sundroid.lystn.util.Pref;
import com.sundroid.lystn.util.StringUtils;
import com.sundroid.lystn.util.TagUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MediaService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    MediaPlayer mediaPlayer;
    boolean isMediaPlayerSetOnce = false;
    int play_progress;
    HomeContentPOJO homeContentPOJO;
//    String media_type = "";

    //MediaSession
    private MediaSessionManager mediaSessionManager;
    private MediaSessionCompat mediaSession;
    private MediaControllerCompat.TransportControls transportControls;

    private static final int NOTIFICATION_ID = 101;

    public static final String ACTION_PLAY = "com.sundroid.lystn.ACTION_PLAY";
    public static final String ACTION_PAUSE = "com.sundroid.lystn.ACTION_PAUSE";
    public static final String ACTION_PREVIOUS = "com.sundroid.lystn.ACTION_PREVIOUS";
    public static final String ACTION_NEXT = "com.sundroid.lystn.ACTION_NEXT";
    public static final String ACTION_STOP = "com.sundroid.lystn.ACTION_STOP";

    NotificationCompat.Builder mBuilder;
    NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TagUtils.getTag(), "oncreate service");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TagUtils.getTag(), "onstart service");
    }

    public void sendMessageToHome(String command, Object object) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(StringUtils.COMMAND, command);
            if (object != null) {
                jsonObject.put(StringUtils.AUDIO_DATA, new Gson().toJson(object));
            }
            Intent intent = new Intent(StringUtils.UPDATE_HOME_ACTIVITY);
            intent.putExtra("message", jsonObject.toString());
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TagUtils.getTag(), "onstartcommand service");

        //Handle Intent action from MediaSession.TransportControls

//        media_type=intent.getStringExtra(StringUtils.MEDIA_TYPE);
        Log.d(TagUtils.getTag(), "media_type:-" + intent.getStringExtra(StringUtils.MEDIA_TYPE));

        if (intent.getStringExtra(StringUtils.AUDIO_DATA) != null) {
            try {
                homeContentPOJO = new Gson().fromJson(intent.getStringExtra(StringUtils.AUDIO_DATA), HomeContentPOJO.class);
                Log.d(TagUtils.getTag(), "home content string:-" + homeContentPOJO.toString());
                if (mediaSessionManager == null) {
                    try {
                        initMediaSession();
                        initMediaPlayer();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        stopSelf();
                    }
                    getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.UPDATE_SERVICE));
                    buildNotification(PlaybackStatus.PLAYING);
                }

                startMediaPlayer(homeContentPOJO.getCotDeepLink());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        handleIncomingActions(intent);
        return START_STICKY;
    }

    public void initMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
        }
    }

    private void initMediaSession() throws RemoteException {
        if (mediaSessionManager != null) return; //mediaSessionManager exists

        Log.d(TagUtils.getTag(), "media player session initialized");

        mediaSessionManager = (MediaSessionManager) getSystemService(Context.MEDIA_SESSION_SERVICE);
        // Create a new MediaSession
        mediaSession = new MediaSessionCompat(getApplicationContext(), "AudioPlayer");
        //Get MediaSessions transport controls
        transportControls = mediaSession.getController().getTransportControls();
        //set MediaSession -> ready to receive media commands
        mediaSession.setActive(true);
        //indicate that the MediaSession handles transport control commands
        // through its MediaSessionCompat.Callback.
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        //Set mediaSession's MetaData
        updateMetaData();

        // Attach Callback to receive MediaSession updates
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            // Implement callbacks
            @Override
            public void onPlay() {
                super.onPlay();
//                resumeMedia();
                Log.d(TagUtils.getTag(), "media onPlay");
                buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onPause() {
                super.onPause();
                Log.d(TagUtils.getTag(), "media onPause");
                pauseMedia();
                buildNotification(PlaybackStatus.PAUSED);
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
                Log.d(TagUtils.getTag(), "media skiptoNext");
//                skipToNext();
                updateMetaData();
                buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                Log.d(TagUtils.getTag(), "media Previous");
//                skipToPrevious();
                updateMetaData();
                buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onStop() {
                super.onStop();
                Log.d(TagUtils.getTag(), "media remove notification");
                removeNotification();
                //Stop the service
                stopSelf();
            }

            @Override
            public void onSeekTo(long position) {
                super.onSeekTo(position);
            }
        });
    }

    private void updateMetaData() {
//        Log.d(TagUtils.getTag(), "metadata updated");
//        try {
//            new AsyncTask<Void, Void, Void>() {
//                Bitmap theBitmap;
//
//                @Override
//                protected Void doInBackground(Void... voids) {
//                    try {
//                        theBitmap = Glide.
//                                with(MediaService.this).
//                                load(homeContentPOJO.getImgIrl()).
//                                asBitmap().
//                                into(100, 100).get();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return null;
//                }
//
//                @Override
//                protected void onPostExecute(Void aVoid) {
//                    super.onPostExecute(aVoid);
//                    if (theBitmap != null) {
//                        Log.d(TagUtils.getTag(), "setting notification metadata");
//                        mediaSession.setMetadata(new MediaMetadataCompat.Builder()
//                                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, theBitmap)
//                                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, homeContentPOJO.getConName())
//                                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, homeContentPOJO.getConName())
//                                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, homeContentPOJO.getConName())
//                                .build());
//                    }
//                }
//            }.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        uodateNotificationData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeNotification();
        Log.d(TagUtils.getTag(), "ondestroy service");
        getApplicationContext().unregisterReceiver(mMessageReceiver);
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {

                String type = intent.getStringExtra("type");
                if (type.equalsIgnoreCase(StringUtils.PLAY_SONG)) {
                    homeContentPOJO = (HomeContentPOJO) intent.getSerializableExtra(StringUtils.AUDIO_DATA);
//                    media_type = intent.getStringExtra(StringUtils.MEDIA_TYPE);
                    Log.d(TagUtils.getTag(), "media_type:-" + intent.getStringExtra(StringUtils.MEDIA_TYPE));
                    Log.d(TagUtils.getTag(), "home content:-" + homeContentPOJO.toString());
                    startMediaPlayer(homeContentPOJO.getCotDeepLink());
                    updateMetaData();
                } else if (type.equalsIgnoreCase(StringUtils.PLAY_PAUSE_MEDIA)) {
                    playPauseMediaPlayer();
                } else if (type.equalsIgnoreCase(StringUtils.CHECK_PLAYER)) {
                    if (mediaPlayer != null) {
                        playingStatusInHome(mediaPlayer.isPlaying());
                    } else {
                        playingStatusInHome(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void startMediaPlayer(String url) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url.trim());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playMedia() {
        if (mediaPlayer != null && (!mediaPlayer.isPlaying())) {
            mediaPlayer.start();
            playingStatusInHome(true);
            mBuilder.setOngoing(true);
        }
    }

    public void pauseMedia() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playingStatusInHome(false);
            if (mBuilder != null) {
                mBuilder.setOngoing(false);
            }
        }
    }

    public void updateMediaTiming() {
        if (!Pref.GetStringPref(getApplicationContext(), StringUtils.MEDIA_TYPE, "").equals("radio")) {
            Intent intent = new Intent(StringUtils.UPDATE_HOME_ACTIVITY);
            intent.putExtra("type", StringUtils.NEXT_SONG);
            sendBroadcast(intent);
        }
    }

    public void nextMedia() {
        if (!Pref.GetStringPref(getApplicationContext(), StringUtils.MEDIA_TYPE, "").equals("radio")) {
            Intent intent = new Intent(StringUtils.UPDATE_HOME_ACTIVITY);
            intent.putExtra("type", StringUtils.NEXT_SONG);
            sendBroadcast(intent);
        }
    }

    public void updateTiminginMusicPlayer() {
        if(mediaPlayer!=null){
            Intent intent = new Intent(StringUtils.UPDATE_HOME_ACTIVITY);
            intent.putExtra("type", StringUtils.NEXT_SONG);
            sendBroadcast(intent);
        }
    }

    public void previousMedia() {
        if (!Pref.GetStringPref(getApplicationContext(), StringUtils.MEDIA_TYPE, "").equals("radio")) {
            Intent intent = new Intent(StringUtils.UPDATE_HOME_ACTIVITY);
            intent.putExtra("type", StringUtils.PREVIOUS_SONG);
            sendBroadcast(intent);
        }
    }

    public void playPauseMediaPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                play_progress = mediaPlayer.getCurrentPosition();
                pauseMedia();
                buildNotification(PlaybackStatus.PAUSED);
            } else {
                playMedia();
                buildNotification(PlaybackStatus.PLAYING);
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TagUtils.getTag(), "Song Completed");
        sendMessageToHome(StringUtils.PLAY_COMPLETED, null);

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        dismissHomeProgressBar();
        isMediaPlayerSetOnce = true;
        playMedia();
        if (!Pref.GetStringPref(getApplicationContext(), StringUtils.MEDIA_TYPE, "").equals("radio")) {

        }
    }

    private void dismissHomeProgressBar() {
        Intent intent = new Intent(StringUtils.UPDATE_HOME_ACTIVITY);
        intent.putExtra("type", "dismiss_progress");
        sendBroadcast(intent);
    }

    private void playingStatusInHome(boolean is_playing) {
        Intent intent = new Intent(StringUtils.UPDATE_HOME_ACTIVITY);
        intent.putExtra("type", StringUtils.MUSIC_PLAYING_STATUS);
        intent.putExtra("status", is_playing);
        sendBroadcast(intent);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.d("MediaPlayer Error", "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.d("MediaPlayer Error", "MEDIA ERROR SERVER DIED " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.d("MediaPlayer Error", "MEDIA ERROR UNKNOWN " + extra);
                break;
        }
        return false;
    }

    private void buildNotification(PlaybackStatus playbackStatus) {

        Log.d(TagUtils.getTag(), "building notifications");

        int notificationAction = android.R.drawable.ic_media_pause;//needs to be initialized
        PendingIntent play_pauseAction = null;

        if (playbackStatus == PlaybackStatus.PLAYING) {
            notificationAction = R.drawable.ic_pause_not;
            play_pauseAction = playbackAction(1);
        } else if (playbackStatus == PlaybackStatus.PAUSED) {
            notificationAction = R.drawable.ic_play_not;
            play_pauseAction = playbackAction(0);
        }

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher); //replace with your own image


        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setShowWhen(false)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.getSessionToken()))
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setLargeIcon(largeIcon)
                .setOngoing(true)
                .setSmallIcon(android.R.drawable.stat_sys_headset)
                .addAction(R.drawable.ic_repeat_one, "previous", playbackAction(4))
                .addAction(R.drawable.ic_backward, "previous", playbackAction(3))
                .addAction(notificationAction, "pause", play_pauseAction)
                .addAction(R.drawable.ic_next, "next", playbackAction(2))
                .addAction(R.drawable.ic_queue, "next", playbackAction(5))
                .setPriority(Notification.PRIORITY_MAX);

        if (homeContentPOJO != null) {
            uodateNotificationData();
        }

        mBuilder.setContentIntent(play_pauseAction);

//        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void uodateNotificationData() {
        new AsyncTask<Void, Void, Void>() {
            Bitmap theBitmap;

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(homeContentPOJO.getImgIrl());
                    theBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (theBitmap != null) {
                    Log.d(TagUtils.getTag(), "setting notification metadata");
                    mBuilder.setLargeIcon(theBitmap);
                    mBuilder.setContentText(homeContentPOJO.getConName());
                    mBuilder.setContentTitle(Pref.GetStringPref(getApplicationContext(), StringUtils.MEDIA_TYPE, ""));
                    mBuilder.setContentInfo(Pref.GetStringPref(getApplicationContext(), StringUtils.MEDIA_TYPE, ""));
                    notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                }
            }
        }.execute();
    }

    private void removeNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private PendingIntent playbackAction(int actionNumber) {
        Intent playbackAction = new Intent(this, MediaService.class);
        switch (actionNumber) {
            case 0:
                // Play
                Log.d(TagUtils.getTag(), "action play");
                playbackAction.setAction(ACTION_PLAY);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case 1:
                // Pause
                Log.d(TagUtils.getTag(), "action pause");
                playbackAction.setAction(ACTION_PAUSE);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case 2:
                // Next track
                Log.d(TagUtils.getTag(), "action next");
                playbackAction.setAction(ACTION_NEXT);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case 3:
                // Previous track
                Log.d(TagUtils.getTag(), "action previous");
                playbackAction.setAction(ACTION_PREVIOUS);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            default:
                break;
        }
        return null;
    }

    private void handleIncomingActions(Intent playbackAction) {
        if (playbackAction == null || playbackAction.getAction() == null) return;

        String actionString = playbackAction.getAction();
        if (actionString.equalsIgnoreCase(ACTION_PLAY)) {
            Log.d(TagUtils.getTag(), "handling play");
            playMedia();
            transportControls.play();
        } else if (actionString.equalsIgnoreCase(ACTION_PAUSE)) {
            Log.d(TagUtils.getTag(), "handling pause");
            pauseMedia();
            transportControls.pause();
        } else if (actionString.equalsIgnoreCase(ACTION_NEXT)) {
            Log.d(TagUtils.getTag(), "handling next");
            nextMedia();
            transportControls.skipToNext();
        } else if (actionString.equalsIgnoreCase(ACTION_PREVIOUS)) {
            Log.d(TagUtils.getTag(), "handling previous");
            previousMedia();
            transportControls.skipToPrevious();
        } else if (actionString.equalsIgnoreCase(ACTION_STOP)) {
            Log.d(TagUtils.getTag(), "handling stop");
            transportControls.stop();
        }
    }
}