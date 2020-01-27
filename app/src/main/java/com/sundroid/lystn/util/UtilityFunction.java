package com.sundroid.lystn.util;

import android.content.Context;

import com.google.gson.Gson;
import com.sundroid.lystn.pojo.UserPOJO;

public class UtilityFunction {

    public static UserPOJO getUserPOJO(Context context){
        try{

            UserPOJO userPOJO=new Gson().fromJson(Pref.GetStringPref(context,StringUtils.PROFILE_DATA,""),UserPOJO.class);
            return userPOJO;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
