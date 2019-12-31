package com.sundroid.lystn.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.sundroid.lystn.R;
import com.sundroid.lystn.adapter.ViewPagerAdapter;
import com.sundroid.lystn.fragment.download.DownloadListFragment;
import com.sundroid.lystn.fragment.home.AllArtistFragment;
import com.sundroid.lystn.fragment.home.AllGenreFragment;
import com.sundroid.lystn.fragment.home.AllRadioFragment;
import com.sundroid.lystn.fragment.home.HomeFragment;
import com.sundroid.lystn.fragment.home.LibraryFragment;
import com.sundroid.lystn.fragment.home.ManageSubscriptionFragment;
import com.sundroid.lystn.fragment.home.MeFragment;
import com.sundroid.lystn.fragment.home.MeSubscriptionFragment;
import com.sundroid.lystn.fragment.home.SearchFragment;
import com.sundroid.lystn.fragment.home.UpdateFragment;
import com.sundroid.lystn.fragment.login.LoginSelectLanguageFragment;
import com.sundroid.lystn.fragment.playlist.PlayListFragment;
import com.sundroid.lystn.fragmentcontroller.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends ActivityManager implements View.OnClickListener {

    @BindView(R.id.ll_home)
    LinearLayout ll_home;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.tv_home)
    TextView tv_home;

    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.tv_search)
    TextView tv_search;

    @BindView(R.id.ll_update)
    LinearLayout ll_update;
    @BindView(R.id.iv_update)
    ImageView iv_update;
    @BindView(R.id.tv_update)
    TextView tv_update;

    @BindView(R.id.ll_library)
    LinearLayout ll_library;
    @BindView(R.id.iv_library)
    ImageView iv_library;
    @BindView(R.id.tv_library)
    TextView tv_library;

    @BindView(R.id.ll_me)
    LinearLayout ll_me;
    @BindView(R.id.iv_me)
    ImageView iv_me;
    @BindView(R.id.tv_me)
    TextView tv_me;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.frame_main)
    FrameLayout frame_main;

    List<LinearLayout> linearLayouts = new ArrayList<>();
    List<ImageView> imageViews = new ArrayList<>();
    List<TextView> textViews = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        linearLayouts.add(ll_home);
        linearLayouts.add(ll_search);
        linearLayouts.add(ll_update);
        linearLayouts.add(ll_library);
        linearLayouts.add(ll_me);

        imageViews.add(iv_home);
        imageViews.add(iv_search);
        imageViews.add(iv_update);
        imageViews.add(iv_library);
        imageViews.add(iv_me);

        textViews.add(tv_home);
        textViews.add(tv_search);
        textViews.add(tv_update);
        textViews.add(tv_library);
        textViews.add(tv_me);

        setupViewPager(viewPager);
        makeTabSelected(0);

        ll_home.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        ll_update.setOnClickListener(this);
        ll_library.setOnClickListener(this);
        ll_me.setOnClickListener(this);

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "Monitor");
        adapter.addFrag(new SearchFragment(), "Monitor");
        adapter.addFrag(new UpdateFragment(), "Monitor");
        adapter.addFrag(new LibraryFragment(), "Monitor");
        adapter.addFrag(new MeFragment(), "Monitor");

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

        makeAllUnselected();

        textViews.get(position).setTextColor(Color.parseColor("#FF9C37"));

        switch (position) {
            case 0:
                iv_home.setImageResource(R.drawable.ic_home_selected);
                break;
            case 1:
                iv_search.setImageResource(R.drawable.ic_search_selected);
                break;
            case 2:
                iv_update.setImageResource(R.drawable.ic_update_selected);
                break;
            case 3:
                iv_library.setImageResource(R.drawable.ic_library_selected);
                break;
            case 4:
                iv_me.setImageResource(R.drawable.ic_me_selected);
                break;
        }
    }

    public void makeAllUnselected() {
        iv_home.setImageResource(R.drawable.ic_home_nonselected);
        iv_search.setImageResource(R.drawable.ic_search_nonselected);
        iv_update.setImageResource(R.drawable.ic_update_nonselected);
        iv_library.setImageResource(R.drawable.ic_library_nonselected);
        iv_me.setImageResource(R.drawable.ic_me_nonselected);

        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).setTextColor(Color.parseColor("#BDBDBD"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_search:
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_update:
                viewPager.setCurrentItem(2);
                break;
            case R.id.ll_library:
                viewPager.setCurrentItem(3);
                break;
            case R.id.ll_me:
                viewPager.setCurrentItem(4);
                break;

        }
    }

    public void startAllArtistFragment() {
        startFragment(R.id.frame_main, new AllArtistFragment());
    }

    public void startAllGenreFragment() {
        startFragment(R.id.frame_main, new AllGenreFragment());
    }

    public void startAllRadio() {
        startFragment(R.id.frame_main, new AllRadioFragment());
    }


    public void showUnLockPremiumFragment() {
        startFragment(R.id.frame_main,new MeSubscriptionFragment());
    }

    public void startManageSubscription() {
        startFragment(R.id.frame_main,new ManageSubscriptionFragment());
    }

    public void startSelectLangageFragment() {
        startFragment(R.id.frame_main,new LoginSelectLanguageFragment());
    }

    public void startMusicPlayerFragment() {

    }

    public void showDownloadFragment() {
        startFragment(R.id.frame_main,new DownloadListFragment());
    }

    public void showPlayListFragment() {
        startFragment(R.id.frame_main,new PlayListFragment());
    }
}
