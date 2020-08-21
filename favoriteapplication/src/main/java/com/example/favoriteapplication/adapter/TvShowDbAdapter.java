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
import com.example.favoriteapplication.R;
import com.example.favoriteapplication.TvShow;

import java.util.ArrayList;

public class TvShowDbAdapter extends RecyclerView.Adapter<TvShowDbAdapter.ListViewHolder> {
    private ArrayList<TvShow> listTvShow=new ArrayList<>();

//    private TvShowDbAdapter.OnItemClickCallback onItemClickCallback;
//
//    public void setOnItemClickCallback(TvShowDbAdapter.OnItemClickCallback onItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback;
//    }

    public TvShowDbAdapter(){
    }
    @NonNull
    @Override
    public TvShowDbAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_layout,viewGroup,false);
        return new TvShowDbAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        TvShow result= listTvShow.get(position);
        String path="https://image.tmdb.org/t/p/w185"+result.getPosterPath();

        Glide.with(holder.itemView.getContext())
                .load(path)
                .apply(new RequestOptions().override(200,200))
                .into(holder.imgPhoto);

        holder.tvName.setText(result.getName());
        holder.tvRating.setText(result.getOverview());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCallback.onItemClicked(listTvShow.get(holder.getAdapterPosition()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }



    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName,tvRating;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto=itemView.findViewById(R.id.poster_fav);
            tvName = itemView.findViewById(R.id.title_fav);
            tvRating = itemView.findViewById(R.id.description_fav);

        }
    }
//
//    public interface OnItemClickCallback {
//        void onItemClicked(TvShow result);
//    }
    public void setData(ArrayList<TvShow> result) {
        if (listTvShow.size()>0){
            listTvShow.clear();
        }
        listTvShow.addAll(result);
        notifyDataSetChanged();
    }

    public ArrayList<TvShow> getListNotes(){
        return listTvShow;
    }

}
