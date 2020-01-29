package com.sundroid.lystn.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.sundroid.lystn.R;
import com.sundroid.lystn.activity.HomeActivity;
import com.sundroid.lystn.adapter.HomeAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends FragmentController {

    @BindView(R.id.frame_search)
    FrameLayout frame_search;
    @BindView(R.id.ll_home_content)
    LinearLayout ll_home_content;
    @BindView(R.id.ll_home_default)
    LinearLayout ll_home_default;
    //    @BindView(R.id.ll_small_player)
//    LinearLayout ll_small_player;
//    @BindView(R.id.iv_player)
//    ImageView iv_player;
//    @BindView(R.id.tv_name)
//    TextView tv_name;
//    @BindView(R.id.iv_play)
//    ImageView iv_play;
//    @BindView(R.id.ll_play)
//    LinearLayout ll_play;
    @BindView(R.id.rv_home)
    RecyclerView rv_home;
    @BindView(R.id.et_search)
    EditText et_search;


    @BindView(R.id.shimmer_text)
    ShimmerFrameLayout shimmerText;

    HomeContentPOJO homeContentPOJO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    public void setHomeContentPOJO(HomeContentPOJO homeContentPOJO) {
        this.homeContentPOJO = homeContentPOJO;



        shimmerText.startShimmer();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ll_home_content.setVisibility(View.GONE);
        ll_home_default.setVisibility(View.VISIBLE);

        frame_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    homeActivity.setViewPagerIndex(1);
                }
            }
        });

        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame_search.callOnClick();
            }
        });

//        new CountDownTimer(3000, 1000) {
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//                shimmerText.stopShimmer();
//                ll_home_content.setVisibility(View.VISIBLE);
//                ll_home_default.setVisibility(View.GONE);
//            }
//        }.start();

        attachHomeAdapter();




        getHomeData();
    }




    public void getHomeData() {
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
                shimmerText.stopShimmer();
                ll_home_content.setVisibility(View.VISIBLE);
                ll_home_default.setVisibility(View.GONE);
                homePOJOS.clear();

                try {
                    String object = new String(response);
                    JSONObject jsonObject = new JSONObject(object);
                    JSONObject responseObject = jsonObject.optJSONObject("response");
                    if (responseObject.optBoolean("status")) {
//                        if(getActivity() instanceof LoginActivity){

                        JSONObject homeJsonObject = responseObject.optJSONObject("home");

                        Iterator<String> keys = homeJsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();

                            JSONArray innerArray = homeJsonObject.optJSONArray(key);

                            for (int i = 0; i < innerArray.length(); i++) {
                                HomePOJO homePOJO = new Gson().fromJson(innerArray.optJSONObject(i).toString(), HomePOJO.class);
                                homePOJOS.add(homePOJO);
                            }
                        }

                        Log.d(TagUtils.getTag(), "homepojos:-" + homePOJOS.size());
                        homeAdapter.notifyDataSetChanged();

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
                dismissProgressBar();
            }
        }, "GET_HOME").makeApiCall(WebServicesUrls.GET_HOME, jsonObject);
    }


    HomeAdapter homeAdapter;
    List<HomePOJO> homePOJOS = new ArrayList<>();

    public void attachHomeAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_home.setHasFixedSize(true);
        rv_home.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(getActivity(), this, homePOJOS);
        rv_home.setAdapter(homeAdapter);
        rv_home.setNestedScrollingEnabled(true);
        rv_home.setItemAnimator(new DefaultItemAnimator());
    }


}
