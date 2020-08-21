package com.example.movie_catalog.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movie_catalog.Model.TvShowDB.Result;
import com.example.movie_catalog.R;

import java.util.ArrayList;

public class TvShowDbAdapter extends RecyclerView.Adapter<TvShowDbAdapter.ListViewHolder> {
    private ArrayList<Result> listTvShow;

    private TvShowDbAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(TvShowDbAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public TvShowDbAdapter(ArrayList<Result> list){
        this.listTvShow=list;
    }
    @NonNull
    @Override
    public TvShowDbAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie,viewGroup,false);
        return new TvShowDbAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Result result= listTvShow.get(position);
        String path="https://image.tmdb.org/t/p/w185"+result.getPosterPath();

        Glide.with(holder.itemView.getContext())
                .load(path)
                .apply(new RequestOptions().override(200,200))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imgPhoto);

        Glide.with(holder.itemView.getContext())
                .load(path)
                .apply(new RequestOptions().override(25,25))
                .into(holder.imgBackground);

        holder.tvName.setText(result.getName());
        holder.tvRating.setText(result.getVoteAverage().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listTvShow.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }



    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto,imgBackground;
        TextView tvName,tvRating;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBackground=itemView.findViewById(R.id.item_photo_backdrop);
            imgPhoto=itemView.findViewById(R.id.img_photo);
            tvName = itemView.findViewById(R.id.txt_name);
            tvRating = itemView.findViewById(R.id.txt_overview);

        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Result result);
    }
    public void setData(ArrayList<Result> result) {
        listTvShow.clear();
        listTvShow.addAll(result);
        notifyDataSetChanged();
    }

}
