package com.sundroid.lystn.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.sundroid.lystn.pojo.UserPOJO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilityFunction {

    public static UserPOJO getUserPOJO(Context context) {
        try {
            UserPOJO userPOJO = new Gson().fromJson(Pref.GetStringPref(context, StringUtils.PROFILE_DATA, ""), UserPOJO.class);
            return userPOJO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAppDirectory(Context context) {
        PackageManager m = context.getPackageManager();
        String s = context.getPackageName();
        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            s = p.applicationInfo.dataDir;
            Log.d(TagUtils.getTag(), "data directory:-" + s);
            return s;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("yourtag", "Error Package name not found ", e);
            return "";
        }
    }

    public static String createAppSongDir(Context context) {
        File file = new File(getAppDirectory(context) + File.separator + "downsong");
        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getPath();
    }

    public static String createTestingDir(Context context) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "downsong");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    public static String parseDT(String dt){
        try{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d=simpleDateFormat.parse(dt);
            return new SimpleDateFormat("dd MMM, YYYY").format(d);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
