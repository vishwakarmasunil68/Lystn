package com.sundroid.lystn.fragment.home;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.sundroid.lystn.R;
import com.sundroid.lystn.adapter.SearchRankingAdapter;
import com.sundroid.lystn.adapter.ViewPagerAdapter;
import com.sundroid.lystn.fragment.search.AllSearchResultFragment;
import com.sundroid.lystn.fragment.search.SearchArtistFragment;
import com.sundroid.lystn.fragment.search.SearchEpisodesFragment;
import com.sundroid.lystn.fragment.search.SearchMyDownloadFragment;
import com.sundroid.lystn.fragment.search.SearchPodcastsFragment;
import com.sundroid.lystn.fragmentcontroller.FragmentController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchFragment extends FragmentController {

    @BindView(R.id.rv_search_ranking)
    RecyclerView rv_search_ranking;
    @BindView(R.id.rv_popular)
    RecyclerView rv_popular;
    @BindView(R.id.ll_search_default)
    LinearLayout ll_search_default;
    @BindView(R.id.ll_search_layout)
    LinearLayout ll_search_layout;
    @BindView(R.id.ll_no_results)
    LinearLayout ll_no_results;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.searchViewPager)
    ViewPager searchViewPager;

    @BindView(R.id.ll_all_results)
    LinearLayout ll_all_results;
    @BindView(R.id.ll_episodes)
    LinearLayout ll_episodes;
    @BindView(R.id.ll_artists)
    LinearLayout ll_artists;
//    @BindView(R.id.ll_my_downloads)
//    LinearLayout ll_my_downloads;
    @BindView(R.id.ll_podcasts)
    LinearLayout ll_podcasts;

    @BindView(R.id.tv_all_results)
    TextView tv_all_results;
    @BindView(R.id.tv_podcasts)
    TextView tv_podcasts;
    @BindView(R.id.tv_episodes)
    TextView tv_episodes;
    @BindView(R.id.tv_artists)
    TextView tv_artists;
//    @BindView(R.id.tv_my_downloads)
//    TextView tv_my_downloads;

    List<LinearLayout> linearLayouts=new ArrayList<>();
    List<TextView> textViews=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");
        searchPOJOs.add("");

        attachSearchAdapter();
        attachPopularAdapter();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_search.getText().toString().length() == 0) {
                    ll_search_default.setVisibility(View.VISIBLE);
                    ll_search_layout.setVisibility(View.GONE);
                    ll_no_results.setVisibility(View.GONE);
                } else if(et_search.getText().toString().length()>6){
                    ll_search_default.setVisibility(View.GONE);
                    ll_search_layout.setVisibility(View.GONE);
                    ll_no_results.setVisibility(View.VISIBLE);
                }else{
                    ll_search_default.setVisibility(View.GONE);
                    ll_search_layout.setVisibility(View.VISIBLE);
                    ll_no_results.setVisibility(View.GONE);
                }
            }
        });


        linearLayouts.add(ll_all_results);
        linearLayouts.add(ll_podcasts);
        linearLayouts.add(ll_episodes);
        linearLayouts.add(ll_artists);
//        linearLayouts.add(ll_my_downloads);

        textViews.add(tv_all_results);
        textViews.add(tv_podcasts);
        textViews.add(tv_episodes);
        textViews.add(tv_artists);
//        textViews.add(tv_my_downloads);

        setupViewPager(searchViewPager);
        makeTabSelected(0);

    }

    SearchRankingAdapter searchRankingAdapter;
    List<String> searchPOJOs = new ArrayList<>();

    public void attachSearchAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        rv_search_ranking.setHasFixedSize(true);
        rv_search_ranking.setLayoutManager(gridLayoutManager);
        searchRankingAdapter = new SearchRankingAdapter(getActivity(), this, searchPOJOs);
        rv_search_ranking.setAdapter(searchRankingAdapter);
        rv_search_ranking.setNestedScrollingEnabled(false);
        rv_search_ranking.setItemAnimator(new DefaultItemAnimator());
    }

    SearchRankingAdapter popularSearchRankingAdapter;

    public void attachPopularAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        rv_popular.setHasFixedSize(true);
        rv_popular.setLayoutManager(gridLayoutManager);
        popularSearchRankingAdapter = new SearchRankingAdapter(getActivity(), this, searchPOJOs);
        rv_popular.setAdapter(popularSearchRankingAdapter);
        rv_popular.setNestedScrollingEnabled(false);
        rv_popular.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new AllSearchResultFragment(), "Monitor");
        adapter.addFrag(new SearchPodcastsFragment(), "Monitor");
        adapter.addFrag(new SearchEpisodesFragment(), "Monitor");
        adapter.addFrag(new SearchArtistFragment(), "Monitor");
//        adapter.addFrag(new SearchMyDownloadFragment(), "Monitor");

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
        for(int i=0;i<linearLayouts.size();i++){
            linearLayouts.get(i).setBackgroundResource(0);
        }

        for(int i=0;i<textViews.size();i++){
            textViews.get(i).setTextColor(Color.parseColor("#757575"));
        }

        linearLayouts.get(position).setBackgroundResource(R.drawable.ic_tab_selected);
        textViews.get(position).setTextColor(Color.parseColor("#FFFFFF"));

    }
}
