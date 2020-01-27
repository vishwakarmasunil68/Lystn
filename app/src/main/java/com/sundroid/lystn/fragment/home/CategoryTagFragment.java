package com.sundroid.lystn.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.Gson;
import com.sundroid.lystn.R;
import com.sundroid.lystn.adapter.CategoryPillsAdapter;
import com.sundroid.lystn.fragmentcontroller.FragmentController;
import com.sundroid.lystn.pojo.home.HomeContentPOJO;
import com.sundroid.lystn.util.Pref;
import com.sundroid.lystn.util.StringUtils;
import com.sundroid.lystn.util.TagUtils;
import com.sundroid.lystn.util.ToastClass;
import com.sundroid.lystn.webservice.ApiCallBase;
import com.sundroid.lystn.webservice.WebServicesCallBack;
import com.sundroid.lystn.webservice.WebServicesUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CategoryTagFragment extends FragmentController {

    @BindView(R.id.rv_categories)
    RecyclerView rv_categories;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    List<HomeContentPOJO> categoryTagPOJOS = new ArrayList<>();

    String bk_id="";

    public CategoryTagFragment(String bk_id){
        this.bk_id=bk_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_category_tag, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAllCategories();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    CategoryPillsAdapter homePillsAdapter;

    public void attachGenreCategoryAdapter() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity().getApplicationContext());
        rv_categories.setLayoutManager(layoutManager);
        homePillsAdapter = new CategoryPillsAdapter(getActivity(), this, categoryTagPOJOS);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        rv_categories.setAdapter(homePillsAdapter);
    }


    public void getAllCategories() {

        JSONObject jsonObject = new JSONObject();

        showProgressBar();

        try {
            jsonObject.put("userId", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.USER_ID, ""));
            jsonObject.put("deviceId", "91");
            jsonObject.put("langCode", "en");
            jsonObject.put("bkId", bk_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new ApiCallBase(getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                dismissProgressBar();
                categoryTagPOJOS.clear();
                try {
                    String object = new String(response);
                    JSONObject jsonObject = new JSONObject(object);
                    JSONObject responseObject = jsonObject.optJSONObject("response");
                    if (responseObject.optBoolean("status")) {

                        JSONObject dataObject=responseObject.optJSONObject("data");
                        JSONArray jsonArray = dataObject.optJSONArray("contents");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            categoryTagPOJOS.add(new Gson().fromJson(jsonArray.optJSONObject(i).toString(), HomeContentPOJO.class));
                        }

                    } else {
                        ToastClass.showShortToast(getActivity(), "Something went wrong");
                    }
                    Log.d(TagUtils.getTag(), jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TagUtils.getTag(),"size:-"+categoryTagPOJOS.size());
                attachGenreCategoryAdapter();
            }

            @Override
            public void onErrorMsg(String status_code, String response) {
                dismissProgressBar();
            }
        }, "POST_GENRE").makeApiCall(WebServicesUrls.GET_SEE_ALL_BUCKET, jsonObject);


    }


}
