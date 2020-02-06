package com.sundroid.lystn.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sundroid.lystn.R;
import com.sundroid.lystn.adapter.ArtistAdapter;
import com.sundroid.lystn.fragmentcontroller.FragmentController;
import com.sundroid.lystn.pojo.home.HomeContentPOJO;
import com.sundroid.lystn.pojo.home.HomePOJO;
import com.sundroid.lystn.util.Pref;
import com.sundroid.lystn.util.StringUtils;
import com.sundroid.lystn.util.TagUtils;
import com.sundroid.lystn.util.ToastClass;
import com.sundroid.lystn.webservice.ApiCallBase;
import com.sundroid.lystn.webservice.WebServicesCallBack;
import com.sundroid.lystn.webservice.WebServicesUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AllArtistFragment extends FragmentController {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_artist)
    RecyclerView rv_artist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_all_artist,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        attachArtistAdapter();
        getArtistData();
    }

    public void getArtistData() {
        JSONObject jsonObject = new JSONObject();

        showProgressBar();

        try {
            jsonObject.put("userId", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.USER_ID, ""));
            jsonObject.put("deviceId", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.DEVICE_ID, ""));
            jsonObject.put("langCode", "en");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new ApiCallBase(getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                dismissProgressBar();
                artistPojos.clear();
                try {
                    String object = new String(response);
                    JSONObject jsonObject = new JSONObject(object);
                    JSONObject responseObject = jsonObject.optJSONObject("response");
                    if (responseObject.optBoolean("status")) {
//                        if(getActivity() instanceof LoginActivity){

                        HomePOJO homePOJO=new Gson().fromJson(responseObject.optJSONObject("data").toString(),HomePOJO.class);
                        for(HomeContentPOJO homeContentPOJO:homePOJO.getHomeContentPOJOS()){
                            artistPojos.add(homeContentPOJO);
                        }

                        homeArtistesAdapter.notifyDataSetChanged();

                    } else {
                        ToastClass.showShortToast(getActivity(), "Something went wrong");
                    }
                    Log.d(TagUtils.getTag(), jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorMsg(String status_code, String response) {
                ToastClass.showShortToast(getActivity().getApplicationContext(),"Server Down");
                dismissProgressBar();
            }
        }, "GET_HOME").makeApiCall(WebServicesUrls.GET_ARTIST_BUCKET, jsonObject);
    }

    ArtistAdapter homeArtistesAdapter;
    List<HomeContentPOJO> artistPojos = new ArrayList<>();

    public void attachArtistAdapter() {
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
//        rv_artist.setHasFixedSize(true);
//        rv_artist.setLayoutManager(gridLayoutManager);
//        homeArtistesAdapter = new ArtistAdapter(getActivity(), this, artistPojos);
//        rv_artist.setAdapter(homeArtistesAdapter);
//        rv_artist.setNestedScrollingEnabled(false);
//        rv_artist.setItemAnimator(new DefaultItemAnimator());
    }
}
