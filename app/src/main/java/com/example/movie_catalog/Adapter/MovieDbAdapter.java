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
import com.example.movie_catalog.Model.MovieDB.Result;
import com.example.movie_catalog.R;

import java.util.ArrayList;


public class MovieDbAdapter extends RecyclerView.Adapter<MovieDbAdapter.ListViewHolder> {
    private ArrayList<Result> listMovieDb;

    public MovieDbAdapter(ArrayList<Result> list){
        this.listMovieDb=list;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie,viewGroup,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Result moviedb= listMovieDb.get(position);
        String path="https://image.tmdb.org/t/p/w185"+moviedb.getPosterPath();
        Glide.with(holder.itemView.getContext())
                .load(path)
                .apply(new RequestOptions().override(200,200))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imgPhoto);

        Glide.with(holder.itemView.getContext())
                .load(path)
                .apply(new RequestOptions().override(25,25))
                .into(holder.imgBackground);
        holder.mvName.setText(moviedb.getTitle());
        holder.mvRating.setText(moviedb.getVoteAverage().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listMovieDb.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovieDb.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto,imgBackground;
        TextView mvName,mvRating;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBackground=itemView.findViewById(R.id.item_photo_backdrop);
            imgPhoto=itemView.findViewById(R.id.img_photo);
            mvName = itemView.findViewById(R.id.txt_name);
            mvRating = itemView.findViewById(R.id.txt_overview);

        }


    }

    private MovieDbAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(MovieDbAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Result result);
    }

    public void setData(ArrayList<Result> result) {
        listMovieDb.clear();
        listMovieDb.addAll(result);
        notifyDataSetChanged();
    }
}