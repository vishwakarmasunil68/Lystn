package com.sundroid.lystn.fragment.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sundroid.lystn.R;
import com.sundroid.lystn.activity.HomeActivity;
import com.sundroid.lystn.adapter.GenrePlayListAdapter;
import com.sundroid.lystn.fragmentcontroller.FragmentController;
import com.sundroid.lystn.pojo.artiste.PodcastDetailPOJO;
import com.sundroid.lystn.pojo.artiste.PodcastEpisodeDetailsPOJO;
import com.sundroid.lystn.pojo.home.HomeContentPOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PodcastPlayListFragment extends FragmentController {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<PodcastEpisodeDetailsPOJO> podcastEpisodeDetailsPOJOS=new ArrayList<>();
    PodcastDetailPOJO podcastDetailPOJO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_podcast_playlist, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachGenericAdapter(recyclerView);
    }

    public void setPodcastList(PodcastDetailPOJO podcastDetailPOJO,List<PodcastEpisodeDetailsPOJO> podcastEpisodeDetailsPOJOS){
        this.podcastEpisodeDetailsPOJOS.clear();
        this.homeContentPOJOS.clear();
        this.podcastDetailPOJO=podcastDetailPOJO;
        this.podcastEpisodeDetailsPOJOS.addAll(podcastEpisodeDetailsPOJOS);
        for(PodcastEpisodeDetailsPOJO podcastEpisodeDetailsPOJO:podcastEpisodeDetailsPOJOS){
            HomeContentPOJO homeContentPOJO=new HomeContentPOJO();

            homeContentPOJO.setConId("");
            homeContentPOJO.setConName(podcastEpisodeDetailsPOJO.getTitle());
            homeContentPOJO.setImgIrl(podcastEpisodeDetailsPOJO.getImgLocalUri().toString());
            homeContentPOJO.setCotDeepLink(podcastEpisodeDetailsPOJO.getStreamUri());
            homeContentPOJO.setDescription(podcastEpisodeDetailsPOJO.getDescription());
            homeContentPOJO.setSubtitle(podcastEpisodeDetailsPOJO.getSubtitle());

            homeContentPOJOS.add(homeContentPOJO);

        }
        podcastEpisodeAdapter.notifyDataSetChanged();
    }


    List<HomeContentPOJO> homeContentPOJOS = new ArrayList<>();
    GenrePlayListAdapter podcastEpisodeAdapter;

    public void attachGenericAdapter(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        podcastEpisodeAdapter = new GenrePlayListAdapter(getActivity(), this, podcastEpisodeDetailsPOJOS);
        recyclerView.setAdapter(podcastEpisodeAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void playAudio(int position){
        if(getActivity() instanceof HomeActivity){
            HomeActivity homeActivity= (HomeActivity) getActivity();
            homeActivity.playAudio(homeContentPOJOS,position,"Genre",podcastDetailPOJO);
        }
    }

}
