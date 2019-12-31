package com.sundroid.lystn.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.sundroid.lystn.R;
import com.sundroid.lystn.fragment.login.LoginDefaultFragment;
import com.sundroid.lystn.fragment.login.LoginSelectLanguageFragment;
import com.sundroid.lystn.fragment.login.LoginTagFragment;
import com.sundroid.lystn.fragment.login.LoginWithPhoneFragment;
import com.sundroid.lystn.fragmentcontroller.ActivityManager;

import butterknife.ButterKnife;

public class LoginActivity extends ActivityManager {

//    @BindView(R.id.iv_login_mobile)
//    ImageView iv_login_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

//        iv_login_mobile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this,LoginMobileActivity.class));
//            }
//        });

        startSelectLangageFragment();

    }

    public void startSelectLangageFragment() {
        startFragment(R.id.frame_base,new LoginSelectLanguageFragment());
    }
    public void startLoginDefaultFragment(){
        startFragment(R.id.frame_base,new LoginDefaultFragment());
    }
    public void startLoginWithPhoneFragment(){
        startFragment(R.id.frame_base,new LoginWithPhoneFragment());
    }
    public void startLoginTagFragment(){
        startFragment(R.id.frame_base,new LoginTagFragment());
    }

    public void startHome() {
        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
    }

    public void skipFragment() {
        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
    }
}
