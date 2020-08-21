package com.example.favoriteapplication.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.favoriteapplication.Movie;
import com.example.favoriteapplication.R;

import java.util.ArrayList;


public class    MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder>{
    private ArrayList<Movie> listMovieDb=new ArrayList<>();

    public MovieAdapter(){
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_layout,viewGroup,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Movie moviedb= listMovieDb.get(position);
        String path="https://image.tmdb.org/t/p/w185"+moviedb.getPosterPath();
        Glide.with(holder.itemView.getContext())
                .load(path)
                .apply(new RequestOptions().override(200,200))
                .into(holder.imgPhoto);

        holder.mvName.setText(moviedb.getTitle());
        holder.mvRating.setText(moviedb.getOverview());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCallback.onItemClicked(listMovieDb.get(holder.getAdapterPosition()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listMovieDb.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView mvName,mvRating;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto=itemView.findViewById(R.id.poster_fav);
            mvName = itemView.findViewById(R.id.title_fav);
            mvRating = itemView.findViewById(R.id.description_fav);

        }


    }

//    private MovieAdapter.OnItemClickCallback onItemClickCallback;
//
//    public void setOnItemClickCallback(MovieAdapter.OnItemClickCallback onItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback;
//    }
//
//    public interface OnItemClickCallback {
//        void onItemClicked(Movie result);
//    }

    public void setData(ArrayList<Movie> result) {
        if (listMovieDb.size()>0){
            listMovieDb.clear();
        }
        listMovieDb.addAll(result);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getListNotes(){
        return listMovieDb;
    }
}