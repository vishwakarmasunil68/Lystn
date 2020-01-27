package com.sundroid.lystn.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sundroid.lystn.R;
import com.sundroid.lystn.activity.HomeActivity;
import com.sundroid.lystn.adapter.ViewPagerAdapter;
import com.sundroid.lystn.fragment.playlist.PodcastPlayListFragment;
import com.sundroid.lystn.fragment.playlist.RecommendListFragment;
import com.sundroid.lystn.fragmentcontroller.FragmentController;
import com.sundroid.lystn.pojo.artiste.PodcastPOJO;
import com.sundroid.lystn.pojo.home.HomeContentPOJO;
import com.sundroid.lystn.util.Pref;
import com.sundroid.lystn.util.StringUtils;
import com.sundroid.lystn.util.ToastClass;
import com.sundroid.lystn.webservice.ApiCallBase;
import com.sundroid.lystn.webservice.WebServicesCallBack;
import com.sundroid.lystn.webservice.WebServicesUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GenreDetailFragment extends FragmentController {


    @BindView(R.id.iv_podcast_image)
    ImageView iv_podcast_image;
    @BindView(R.id.tv_podcast_name)
    TextView tv_podcast_name;
    @BindView(R.id.tv_podcast_description)
    TextView tv_podcast_description;
    @BindView(R.id.tv_podcast_copy_right)
    TextView tv_podcast_copy_right;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_frag_1)
    TextView tv_frag_1;
    @BindView(R.id.tv_frag_2)
    TextView tv_frag_2;
    @BindView(R.id.view_frag_1)
    View view_frag_1;
    @BindView(R.id.view_frag_2)
    View view_frag_2;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.btn_follow)
    Button btn_follow;

    List<TextView> textViews = new ArrayList<>();
    List<View> views = new ArrayList<>();

    String conId;

    public GenreDetailFragment(String conId) {
        this.conId = conId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_genre_detail, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViews.add(tv_frag_1);
        textViews.add(tv_frag_2);


        views.add(view_frag_1);
        views.add(view_frag_2);

        setupViewPager(viewPager);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getPodcastDetails();

        changeFollowText();

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    if (homeActivity.getArtisteFollowUpList().contains(conId)) {
                        homeActivity.showProgressBar();
                        JSONObject jsonObject = new JSONObject();

                        try {
                            jsonObject.put("userId", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.USER_ID, ""));
                            jsonObject.put("deviceId", "91");
                            jsonObject.put("followType", "artiste");
                            jsonObject.put("followId", conId);
                            jsonObject.put("langCode", "en");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        new ApiCallBase(getActivity(), new WebServicesCallBack() {
                            @Override
                            public void onGetMsg(String apicall, String response) {
                                homeActivity.dismissProgressBar();
                                try {
                                    String object = new String(response);
                                    JSONObject jsonObject = new JSONObject(object);
                                    JSONObject responseObject = jsonObject.optJSONObject("response");
//                                    ArtisteFollowUtil.removeArtisteFromList(activity.getApplicationContext(),String.valueOf(items.get(position).getConId()));
//                                    homeActivity.getArtisteFollowUpList().remove(items.get(position).getConId());
                                    homeActivity.removeFollowUP(StringUtils.ARTISTE_FOLLOW_UP_STRING, conId);
//                                    Log.d(TagUtils.getTag(),"artiste list:-"+ArtisteFollowUtil.getArtisteList(activity.getApplicationContext()));

                                    changeFollowText();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorMsg(String status_code, String response) {

                            }
                        }, "UNFOLLOW_API").makeApiCall(WebServicesUrls.UNFOLLOW_API, jsonObject);
                    } else {
                        JSONObject jsonObject = new JSONObject();
                        homeActivity.showProgressBar();
                        try {
                            jsonObject.put("userId", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.USER_ID, ""));
                            jsonObject.put("deviceId", "91");
                            jsonObject.put("followType", "artiste");
                            jsonObject.put("followId", conId);
                            jsonObject.put("langCode", "en");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        new ApiCallBase(getActivity(), new WebServicesCallBack() {
                            @Override
                            public void onGetMsg(String apicall, String response) {
                                homeActivity.dismissProgressBar();
                                try {
                                    String object = new String(response);
                                    JSONObject jsonObject = new JSONObject(object);
                                    JSONObject responseObject = jsonObject.optJSONObject("response");
//                                    ArtisteFollowUtil.addFollowArtiste(activity.getApplicationContext(), String.valueOf(items.get(position).getConId()));
//                                    Log.d(TagUtils.getTag(), jsonObject.toString());
//                                    Log.d(TagUtils.getTag(),"artiste list:-"+ArtisteFollowUtil.getArtisteList(activity.getApplicationContext()));

                                    homeActivity.addFollowUpData(StringUtils.ARTISTE_FOLLOW_UP_STRING, conId);

//                                    homeActivity.getArtisteFollowUpList().add(items.get(position).getConId());

                                    changeFollowText();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorMsg(String status_code, String response) {

                            }
                        }, "FOLLOW_API").makeApiCall(WebServicesUrls.FOLLOW_API, jsonObject);
                    }
                }
            }
        });

    }

    public void changeFollowText(){
        if (getActivity() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            if (homeActivity.getArtisteFollowUpList().contains(conId)) {
                btn_follow.setText("unfollow");
            } else {
                btn_follow.setText("follow");
            }
        }
    }

    public void getPodcastDetails() {
        JSONObject jsonObject = new JSONObject();

        showProgressBar();

        try {
            jsonObject.put("userId", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.USER_ID, ""));
            jsonObject.put("deviceId", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.DEVICE_ID, ""));
//            jsonObject.put("podcastId", "525");
            jsonObject.put("podcastId", conId);
            jsonObject.put("langCode", "en");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new ApiCallBase(getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                dismissProgressBar();
                try {
                    String object = new String(response);
                    JSONObject jsonObject = new JSONObject(object);
                    JSONObject responseObject = jsonObject.optJSONObject("response");
                    if (responseObject.optBoolean("status")) {

                        PodcastPOJO podcastPOJO = new Gson().fromJson(responseObject.optJSONObject("podcast").toString(), PodcastPOJO.class);


                        tv_podcast_name.setText(podcastPOJO.getPodcastDetailPOJO().getTitle());
                        tv_podcast_description.setText(podcastPOJO.getPodcastDetailPOJO().getDescription());
                        tv_podcast_copy_right.setText(podcastPOJO.getPodcastDetailPOJO().getCopyright());

                        Glide.with(getActivity())
                                .load(podcastPOJO.getPodcastDetailPOJO().getImgLocalUri())
                                .placeholder(R.drawable.ll_disc)
                                .error(R.drawable.ll_disc)
                                .dontAnimate()
                                .into(iv_podcast_image);

//                        podcastEpisodeDetailsPOJOS.addAll(podcastPOJO.getPodcastEpisodeDetailsPOJOS());
                            podcastPlayListFragment.setPodcastList(podcastPOJO.getPodcastEpisodeDetailsPOJOS());

                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
                    }
//                    Log.d(TagUtils.getTag(), jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorMsg(String status_code, String response) {
                dismissProgressBar();
            }
        }, "GET_PODCAST_DETAILS").makeApiCall(WebServicesUrls.GET_PODCAST_DETAILS, jsonObject);
    }


    PodcastPlayListFragment podcastPlayListFragment;

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(podcastPlayListFragment = new PodcastPlayListFragment(), "Monitor");
        adapter.addFrag(new RecommendListFragment(), "Monitor");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                makeTabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void makeTabSelected(int position) {

        for (int i = 0; i < views.size(); i++) {
            views.get(i).setVisibility(View.INVISIBLE);
        }
        views.get(position).setVisibility(View.VISIBLE);

//        for (int i = 0; i < textViews.size(); i++) {
//            textViews.get(i).setTextSize(14);
//        }
//        textViews.get(position).setTextSize(16);
    }


    public void playEpisode(List<HomeContentPOJO> homeContentPOJOS, int index) {
        if (getActivity() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            homeActivity.playAudio(homeContentPOJOS, index, "genre");
        }
    }

}
