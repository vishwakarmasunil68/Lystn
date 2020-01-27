package com.sundroid.lystn.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sundroid.lystn.R;
import com.sundroid.lystn.activity.HomeActivity;
import com.sundroid.lystn.fragmentcontroller.FragmentController;
import com.sundroid.lystn.pojo.home.HomeContentPOJO;
import com.sundroid.lystn.util.Pref;
import com.sundroid.lystn.util.StringUtils;
import com.sundroid.lystn.util.TagUtils;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MusicPlayerFragment extends FragmentController {

    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.iv_player)
    ImageView iv_player;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_small_desc)
    TextView tv_small_desc;
    @BindView(R.id.ll_minimize)
    LinearLayout ll_minimize;
    @BindView(R.id.iv_next)
    ImageView iv_next;
    @BindView(R.id.iv_previous)
    ImageView iv_previous;
    @BindView(R.id.iv_player_background)
    ImageView iv_player_background;
    @BindView(R.id.tv_description)
    TextView tv_description;

    HomeContentPOJO homeContentPOJO;

    public MusicPlayerFragment(HomeContentPOJO homeContentPOJO) {
        this.homeContentPOJO = homeContentPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_music_player, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ll_minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//
//        // ------------- custom thumb
//        //----- using resources
//
//        seekBar.setThumb(new BitmapDrawable(BitmapFactory.decodeResource(
//                getActivity().getResources(), R.drawable.ic_seek_circle)));
//        //----- or using shape drawable

        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    homeActivity.playPausePlayer();
                }
            }
        });

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.MEDIA_TYPE, "").equals("radio")) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        homeActivity.nextSong();
                    }
                }
            }
        });

        iv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.MEDIA_TYPE, "").equals("radio")) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        homeActivity.previousSong();
                    }
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        setHomeContentPOJO(homeContentPOJO);
    }

    @Override
    public void onDestroy() {
        if (getActivity() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            homeActivity.onMusicPlayerClosed();
        }
        super.onDestroy();
    }

    public void setHomeContentPOJO(HomeContentPOJO homeContentPOJO) {
        this.homeContentPOJO = homeContentPOJO;
        Glide.with(getActivity())
                .load(homeContentPOJO.getImgIrl())
                .placeholder(R.drawable.ll_square)
                .error(R.drawable.ll_square)
                .dontAnimate()
                .into(iv_player);

//        Glide.with(getActivity())
//                .load(homeContentPOJO.getImgIrl())
//                .placeholder(R.drawable.ll_square)
//                .error(R.drawable.ll_square)
//                .dontAnimate()
//                .into(iv_player_background);

        Glide.with(this).load(homeContentPOJO.getImgIrl())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(50, 5)))
                .into(iv_player_background);

//        Glide.with(getActivity()).load(homeContentPOJO.getImgIrl())
//                .biu(new BlurTransformation(context))
//                .into((ImageView) findViewById(R.id.image));

        tv_name.setText(homeContentPOJO.getConName());

        if (homeContentPOJO.getDescription() != null && !(homeContentPOJO.getDescription().equalsIgnoreCase(""))) {
            tv_description.setText(homeContentPOJO.getDescription());
        }

        if (getActivity() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            homeActivity.checkPlayerRunning();
        }
    }

    public void setPlayImage(boolean is_playing) {
        Log.d(TagUtils.getTag(), "is_playing:-" + is_playing);
        if (is_playing) {
            iv_play.setImageResource(R.drawable.ic_pause);
        } else {
            iv_play.setImageResource(R.drawable.ic_play);
        }
    }
}
