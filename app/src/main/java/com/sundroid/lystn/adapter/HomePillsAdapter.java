package com.sundroid.lystn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sundroid.lystn.R;
import com.sundroid.lystn.activity.HomeActivity;
import com.sundroid.lystn.fragment.home.MusicPlayerFragment;
import com.sundroid.lystn.fragment.playlist.PlayListFragment;
import com.sundroid.lystn.pojo.home.HomeContentPOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePillsAdapter extends RecyclerView.Adapter<HomePillsAdapter.ViewHolder> {

    private List<HomeContentPOJO> items;
    Activity activity;
    Fragment fragment;
    String type;


    public HomePillsAdapter(Activity activity, Fragment fragment, List<HomeContentPOJO> items,String type) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
        this.type=type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_home_pill_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        switch (position%6){
            case 0:
                holder.ll_pill.setBackgroundResource(R.drawable.ll_home_category_1);
                break;
            case 1:
                holder.ll_pill.setBackgroundResource(R.drawable.ll_home_category_2);
                break;
            case 2:
                holder.ll_pill.setBackgroundResource(R.drawable.ll_home_category_3);
                break;
            case 3:
                holder.ll_pill.setBackgroundResource(R.drawable.ll_home_category_4);
                break;
            case 4:
                holder.ll_pill.setBackgroundResource(R.drawable.ll_home_category_5);
                break;
            case 5:
                holder.ll_pill.setBackgroundResource(R.drawable.ll_home_category_6);
                break;
            default:
                holder.ll_pill.setBackgroundResource(R.drawable.ll_home_category_1);
                break;
        }

        Glide.with(activity)
                .load(items.get(position).getImgIrl())
                .placeholder(R.drawable.ll_disc)
                .error(R.drawable.ll_disc)
                .dontAnimate()
                .into(holder.iv_item);

        holder.tv_cat.setText(items.get(position).getConName());

        holder.iv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equalsIgnoreCase("genre")){
                    if(activity instanceof HomeActivity){
                        HomeActivity homeActivity= (HomeActivity) activity;
                        homeActivity.startFragment(R.id.frame_main,new PlayListFragment());
                    }
                }else if(type.equalsIgnoreCase("radio")){
                    if(activity instanceof HomeActivity){
                        HomeActivity homeActivity= (HomeActivity) activity;
                        homeActivity.playAudio(items,position,"radio");
                    }
                }else if(type.equalsIgnoreCase("artiste")){
                    if(activity instanceof HomeActivity){
                        HomeActivity homeActivity= (HomeActivity) activity;
                        homeActivity.startFragment(R.id.frame_main,new MusicPlayerFragment(items.get(position)));
                    }
                }
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_item)
        ImageView iv_item;
        @BindView(R.id.tv_cat)
        TextView tv_cat;
        @BindView(R.id.ll_pill)
        LinearLayout ll_pill;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}