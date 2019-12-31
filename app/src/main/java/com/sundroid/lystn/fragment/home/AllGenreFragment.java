package com.sundroid.lystn.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sundroid.lystn.R;
import com.sundroid.lystn.adapter.ArtistAdapter;
import com.sundroid.lystn.adapter.GenreAdapter;
import com.sundroid.lystn.fragmentcontroller.FragmentController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AllGenreFragment extends FragmentController {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_artist)
    RecyclerView rv_artist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_all_genre,container,false);
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

        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");
        artistPojos.add("");

        attachArtistAdapter();


    }

    GenreAdapter homeArtistesAdapter;
    List<String> artistPojos = new ArrayList<>();

    public void attachArtistAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        rv_artist.setHasFixedSize(true);
        rv_artist.setLayoutManager(gridLayoutManager);
        homeArtistesAdapter = new GenreAdapter(getActivity(), this, artistPojos);
        rv_artist.setAdapter(homeArtistesAdapter);
        rv_artist.setNestedScrollingEnabled(false);
        rv_artist.setItemAnimator(new DefaultItemAnimator());
    }
}
