package com.sundroid.lystn.fragment.home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sundroid.lystn.R;
import com.sundroid.lystn.activity.HomeActivity;
import com.sundroid.lystn.adapter.HomeArtistesAdapter;
import com.sundroid.lystn.adapter.HomeCategoryAdapter;
import com.sundroid.lystn.adapter.HomeGenreAdapter;
import com.sundroid.lystn.adapter.HomeRadioAdapter;
import com.sundroid.lystn.adapter.SpotlightAdapter;
import com.sundroid.lystn.fragmentcontroller.FragmentController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends FragmentController {

    @BindView(R.id.rv_spotlight)
    RecyclerView rv_spotlight;
    @BindView(R.id.rv_categories)
    RecyclerView rv_categories;
    @BindView(R.id.rv_artists)
    RecyclerView rv_artists;
    @BindView(R.id.rv_radio)
    RecyclerView rv_radio;
    @BindView(R.id.rv_genre)
    RecyclerView rv_genre;
    @BindView(R.id.tv_see_all_artist)
    TextView tv_see_all_artist;
    @BindView(R.id.tv_all_genre)
    TextView tv_all_genre;
    @BindView(R.id.tv_all_radio)
    TextView tv_all_radio;
    @BindView(R.id.ll_home_content)
    LinearLayout ll_home_content;
    @BindView(R.id.ll_home_default)
    LinearLayout ll_home_default;
    @BindView(R.id.ll_small_player)
    LinearLayout ll_small_player;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_home,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_home_content.setVisibility(View.GONE);
        ll_home_default.setVisibility(View.VISIBLE);

        new CountDownTimer(5000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                ll_home_content.setVisibility(View.VISIBLE);
                ll_home_default.setVisibility(View.GONE);
            }
        }.start();

        spotLightPojos.add("");
        spotLightPojos.add("");
        spotLightPojos.add("");
        spotLightPojos.add("");
        spotLightPojos.add("");
        spotLightPojos.add("");

        categoryPOJOs.add("");
        categoryPOJOs.add("");
        categoryPOJOs.add("");
        categoryPOJOs.add("");
        categoryPOJOs.add("");
        categoryPOJOs.add("");
        categoryPOJOs.add("");
        categoryPOJOs.add("");
        categoryPOJOs.add("");
        categoryPOJOs.add("");

        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");

        radioPOJOs.add("");
        radioPOJOs.add("");
        radioPOJOs.add("");
        radioPOJOs.add("");
        radioPOJOs.add("");
        radioPOJOs.add("");
        radioPOJOs.add("");
        radioPOJOs.add("");

        genrePOJOS.add("");
        genrePOJOS.add("");
        genrePOJOS.add("");
        genrePOJOS.add("");
        genrePOJOS.add("");
        genrePOJOS.add("");

        attachAdapter();
        attachCategoryAdapter();
        attachArtistAdapter();
        attachRadioAdapter();
        attachGenreAdapter();

        tv_see_all_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.startAllArtistFragment();
                }
            }
        });
        tv_all_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.startAllGenreFragment();
                }
            }
        });
        tv_all_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.startAllRadio();
                }
            }
        });

        ll_small_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.startMusicPlayerFragment();
                }
            }
        });
    }


    SpotlightAdapter spotlightAdapter;
    List<String> spotLightPojos = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_spotlight.setHasFixedSize(true);
        rv_spotlight.setLayoutManager(linearLayoutManager);
        spotlightAdapter = new SpotlightAdapter(getActivity(), this, spotLightPojos);
        rv_spotlight.setAdapter(spotlightAdapter);
        rv_spotlight.setNestedScrollingEnabled(false);
        rv_spotlight.setItemAnimator(new DefaultItemAnimator());
    }

    HomeCategoryAdapter homeCategoryAdapter;
    List<String> categoryPOJOs = new ArrayList<>();

    public void attachCategoryAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_categories.setHasFixedSize(true);
        rv_categories.setLayoutManager(linearLayoutManager);
        homeCategoryAdapter = new HomeCategoryAdapter(getActivity(), this, categoryPOJOs);
        rv_categories.setAdapter(homeCategoryAdapter);
        rv_categories.setNestedScrollingEnabled(false);
        rv_categories.setItemAnimator(new DefaultItemAnimator());
    }

    HomeArtistesAdapter homeArtistesAdapter;
    List<String> artistPojos = new ArrayList<>();

    public void attachArtistAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_artists.setHasFixedSize(true);
        rv_artists.setLayoutManager(linearLayoutManager);
        homeArtistesAdapter = new HomeArtistesAdapter(getActivity(), this, artistPojos);
        rv_artists.setAdapter(homeArtistesAdapter);
        rv_artists.setNestedScrollingEnabled(false);
        rv_artists.setItemAnimator(new DefaultItemAnimator());
    }

    HomeRadioAdapter homeRadioAdapter;
    List<String> radioPOJOs = new ArrayList<>();

    public void attachRadioAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_radio.setHasFixedSize(true);
        rv_radio.setLayoutManager(linearLayoutManager);
        homeRadioAdapter = new HomeRadioAdapter(getActivity(), this, radioPOJOs);
        rv_radio.setAdapter(homeRadioAdapter);
        rv_radio.setNestedScrollingEnabled(false);
        rv_radio.setItemAnimator(new DefaultItemAnimator());
    }

    HomeGenreAdapter homeGenreAdapter;
    List<String> genrePOJOS = new ArrayList<>();

    public void attachGenreAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_genre.setHasFixedSize(true);
        rv_genre.setLayoutManager(linearLayoutManager);
        homeGenreAdapter = new HomeGenreAdapter(getActivity(), this, genrePOJOS);
        rv_genre.setAdapter(homeGenreAdapter);
        rv_genre.setNestedScrollingEnabled(false);
        rv_genre.setItemAnimator(new DefaultItemAnimator());
    }

}
