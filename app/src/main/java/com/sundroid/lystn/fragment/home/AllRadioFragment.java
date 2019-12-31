package com.sundroid.lystn.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sundroid.lystn.R;
import com.sundroid.lystn.adapter.RadioAdapter;
import com.sundroid.lystn.fragmentcontroller.FragmentController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AllRadioFragment extends FragmentController {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_radio)
    RecyclerView rv_radio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_all_radio,container,false);
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

        attachArtistAdapter();


    }

    RadioAdapter homeArtistesAdapter;
    List<String> artistPojos = new ArrayList<>();

    public void attachArtistAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_radio.setHasFixedSize(true);
        rv_radio.setLayoutManager(linearLayoutManager);
        homeArtistesAdapter = new RadioAdapter(getActivity(), this, artistPojos);
        rv_radio.setAdapter(homeArtistesAdapter);
        rv_radio.setNestedScrollingEnabled(false);
        rv_radio.setItemAnimator(new DefaultItemAnimator());
    }
}
