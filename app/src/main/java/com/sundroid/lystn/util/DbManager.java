package com.sundroid.lystn.util;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.sundroid.lystn.pojo.artiste.PodcastEpisodeDetailsPOJO;
import com.sundroid.lystn.pojo.home.HomeContentPOJO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class DbManager {

    Context context;
    EasyDB easyDB;

    private DbManager(Context context) {
        this.context = context;
    }

    public static DbManager initDB(Context context) {
        DbManager dbManager = new DbManager(context);
        dbManager.easyDB = initTable(context);
        return dbManager;
    }

    public static EasyDB initTable(Context context) {
        EasyDB easyDB = EasyDB.init(context, "Lystn")
                .setTableName("downloads")
                .addColumn(new Column("podcast_id", new String[]{"text", "unique"}))
                .addColumn(new Column("type", new String[]{"text", "not null"}))
                .addColumn(new Column("pojo", new String[]{"text", "not null"}))
                .doneTableColumn();

        return easyDB;
    }

    public void saveDownloadedSong(String id, String type, String pojo) {
        if (!checkSongInDB(id)) {
            boolean done = easyDB
                    .addData("podcast_id", id)
                    .addData("type", type)
                    .addData("pojo", pojo)
                    .doneDataAdding();

            Log.d(TagUtils.getTag(), "song saved in db" + done);
        } else {
            Log.d(TagUtils.getTag(), "song in db");
        }
    }

    public boolean checkSongInDB(String podcast_id) {
        Cursor res = easyDB.searchInColumn("podcast_id", podcast_id, -1); // Here we passed limit = -1. Thus it will return all the rows with the matched column values
        if (res != null) {
            while (res.moveToNext()) {
                return true;
            }
        }
        return false;
    }

    public List<HomeContentPOJO> readAllSongs() {
        Cursor res = easyDB.getAllData();
        List<HomeContentPOJO> homeContentPOJOS = new ArrayList<>();
        while (res.moveToNext()) {
            Log.d(TagUtils.getTag(), "data reading:- col1:-" + res.getString(0) + ",col2:-" + res.getString(1) + ",col3:-" + res.getString(2));
            PodcastEpisodeDetailsPOJO podcastEpisodeDetailsPOJO = new Gson().fromJson(res.getString(3), PodcastEpisodeDetailsPOJO.class);
            HomeContentPOJO homeContentPOJO = new HomeContentPOJO();
            homeContentPOJO.setConId(podcastEpisodeDetailsPOJO.getEpisodeId());
            homeContentPOJO.setConName(podcastEpisodeDetailsPOJO.getTitle());
            homeContentPOJO.setImgIrl(podcastEpisodeDetailsPOJO.getImgLocalUri().toString());
            homeContentPOJO.setCotDeepLink(UtilityFunction.createTestingDir(context) + File.separator + podcastEpisodeDetailsPOJO.getEpisodeId() + ".mp3");
            homeContentPOJO.setDescription(podcastEpisodeDetailsPOJO.getDescription());
            homeContentPOJO.setSubtitle(podcastEpisodeDetailsPOJO.getSubtitle());
            homeContentPOJOS.add(homeContentPOJO);
        }
        return homeContentPOJOS;
    }

    public boolean removeSong(String podcast_id){
        boolean deleted = easyDB.deleteRow("podcast_id", podcast_id);
        return deleted;
    }


}
