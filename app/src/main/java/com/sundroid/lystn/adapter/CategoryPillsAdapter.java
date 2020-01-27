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
import com.sundroid.lystn.pojo.home.HomeContentPOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryPillsAdapter extends RecyclerView.Adapter<CategoryPillsAdapter.ViewHolder> {

    private List<HomeContentPOJO> items;
    Activity activity;
    Fragment fragment;


    public CategoryPillsAdapter(Activity activity, Fragment fragment, List<HomeContentPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_category_pills, parent, false);
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