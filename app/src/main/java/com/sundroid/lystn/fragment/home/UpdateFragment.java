package com.sundroid.lystn.fragment.home;

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
import com.sundroid.lystn.adapter.UpdateAdapter;
import com.sundroid.lystn.fragmentcontroller.FragmentController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UpdateFragment extends FragmentController {

    @BindView(R.id.rv_follow)
    RecyclerView rv_follow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_update,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        libraryPOJOs.add("");
        libraryPOJOs.add("");
        libraryPOJOs.add("");
        libraryPOJOs.add("");
        libraryPOJOs.add("");
        libraryPOJOs.add("");
        libraryPOJOs.add("");

        attachAdapter();

    }

    UpdateAdapter homeArtistesAdapter;
    List<String> libraryPOJOs = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_follow.setHasFixedSize(true);
        rv_follow.setLayoutManager(linearLayoutManager);
        homeArtistesAdapter = new UpdateAdapter(getActivity(), this, libraryPOJOs);
        rv_follow.setAdapter(homeArtistesAdapter);
        rv_follow.setNestedScrollingEnabled(false);
        rv_follow.setItemAnimator(new DefaultItemAnimator());
    }

}
