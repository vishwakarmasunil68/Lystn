package com.sundroid.lystn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sundroid.lystn.R;
import com.sundroid.lystn.fragment.home.ArtisteDetailFragment;
import com.sundroid.lystn.pojo.artiste.PodcastEpisodeDetailsPOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenrePlayListAdapter extends RecyclerView.Adapter<GenrePlayListAdapter.ViewHolder> {

    private List<PodcastEpisodeDetailsPOJO> items;
    Activity activity;
    Fragment fragment;


    public GenrePlayListAdapter(Activity activity, Fragment fragment, List<PodcastEpisodeDetailsPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_genre_playlist_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_episode_name.setText(items.get(position).getTitle());
        holder.tv_date.setText(items.get(position).getAddedOn());
        holder.tv_duration.setText(items.get(position).getDuration());
        holder.tv_sequence.setText(items.get(position).getEpisodeSeq());
//        Glide.with(activity)
//                .load(items.get(position).getImgLocalUri())
//                .placeholder(R.drawable.ll_disc)
//                .error(R.drawable.ll_disc)
//                .dontAnimate()
//                .into(holder.iv_episode);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment !=null && fragment instanceof ArtisteDetailFragment){
                    ArtisteDetailFragment artisteDetailFragment= (ArtisteDetailFragment) fragment;
                    artisteDetailFragment.playEpisode(position);
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

//        @BindView(R.id.iv_episode)
//        ImageView iv_episode;
        @BindView(R.id.tv_episode_name)
        TextView tv_episode_name;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_duration)
        TextView tv_duration;
        @BindView(R.id.tv_sequence)
        TextView tv_sequence;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}