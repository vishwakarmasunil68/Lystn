package com.sundroid.lystn.fragment.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sundroid.lystn.R;
import com.sundroid.lystn.activity.LoginActivity;
import com.sundroid.lystn.adapter.LanguageSelectAdapter;
import com.sundroid.lystn.fragmentcontroller.FragmentController;
import com.sundroid.lystn.pojo.LanguagePOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LoginSelectLanguageFragment extends FragmentController {

    @BindView(R.id.rv_select_language)
    RecyclerView rv_select_language;
    @BindView(R.id.frame_continue)
    FrameLayout frame_continue;
    @BindView(R.id.tv_skip)
    TextView tv_skip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_login_select_language,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        languagePOJOS.add(new LanguagePOJO(R.drawable.icon_country_usa,"English",false));
        languagePOJOS.add(new LanguagePOJO(R.drawable.icon_country_india,"Hindi",false));
        languagePOJOS.add(new LanguagePOJO(R.drawable.icon_country_fran,"Francis",false));
        languagePOJOS.add(new LanguagePOJO(R.drawable.icon_country_china,"Chinese",false));
        languagePOJOS.add(new LanguagePOJO(R.drawable.icon_country_vie,"Vitenamese",false));
        languagePOJOS.add(new LanguagePOJO(R.drawable.icon_country_japan,"Japenese",false));
        languagePOJOS.add(new LanguagePOJO(R.drawable.icon_country_deutsch,"Deutsch",false));

        attachAdapter();

        frame_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof LoginActivity){
                    LoginActivity loginActivity= (LoginActivity) getActivity();
                    loginActivity.startLoginDefaultFragment();
                }
            }
        });

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof LoginActivity){
                    LoginActivity loginActivity= (LoginActivity) getActivity();
                    loginActivity.skipFragment();
                }
            }
        });

    }

    LanguageSelectAdapter languageSelectAdapter;
    List<LanguagePOJO> languagePOJOS = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_select_language.setHasFixedSize(true);
        rv_select_language.setLayoutManager(linearLayoutManager);
        languageSelectAdapter = new LanguageSelectAdapter(getActivity(), this, languagePOJOS);
        rv_select_language.setAdapter(languageSelectAdapter);
        rv_select_language.setNestedScrollingEnabled(false);
        rv_select_language.setItemAnimator(new DefaultItemAnimator());
    }
}
