package com.sundroid.lystn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sundroid.lystn.R;
import com.sundroid.lystn.activity.WelcomeActivity;
import com.sundroid.lystn.fragmentcontroller.FragmentController;

import butterknife.BindView;

public class Welcome2Fragment extends FragmentController {

    @BindView(R.id.iv_next)
    ImageView iv_next;
    @BindView(R.id.tv_skip)
    TextView tv_skip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_welcome_2,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof WelcomeActivity){
                    WelcomeActivity welcomeActivity= (WelcomeActivity) getActivity();
                    welcomeActivity.showNext();
                }
            }
        });


        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof WelcomeActivity){
                    WelcomeActivity welcomeActivity= (WelcomeActivity) getActivity();
                    welcomeActivity.skipToLogin();
                }
            }
        });

    }
}
