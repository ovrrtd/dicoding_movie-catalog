package com.example.movie_catalog.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.movie_catalog.Activity.DetailTvActivity;
import com.example.movie_catalog.Adapter.TvShowDbAdapter;
import com.example.movie_catalog.Controller.Controller;
import com.example.movie_catalog.Interface.MovieDbInterface;


import com.example.movie_catalog.Model.TvShowDB.Result;
import com.example.movie_catalog.Model.TvShowDB.TvShowDb;
import com.example.movie_catalog.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private ArrayList<Result> listDb=new ArrayList<>();

    private RecyclerView rvMoviesDb;

    public final String API_KEY="fd141ba30ab693bd721d88bd9c88ca66";


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tv_show, container, false);
        rvMoviesDb = view.findViewById(R.id.rv_tvShow);
        rvMoviesDb.setHasFixedSize(true);

        if (savedInstanceState != null) {
            listDb = savedInstanceState.getParcelableArrayList("ParcelableTV");
            showRecyclerListDb();
        }
        else {
            prepareDB();
        }
        // Inflate the layout for this fragment
        return view;
    }

    private void prepareDB(){
        MovieDbInterface movieDbInterface= Controller.getRetrofitInstance().create(MovieDbInterface.class);
        Call<TvShowDb> call= movieDbInterface.getTvShowDb(API_KEY,"en-US");
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setMessage("It's loading....");
        progressDialog.setTitle("Please waiting");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        call.enqueue(new Callback<TvShowDb>() {
            @Override
            public void onResponse(Call<TvShowDb> call, Response<TvShowDb> response) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "it works", Toast.LENGTH_SHORT).show();
                listDb.addAll(response.body().getResults());
                showRecyclerListDb();
            }

            @Override
            public void onFailure(Call<TvShowDb> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showRecyclerListDb(){
        rvMoviesDb.setLayoutManager(new LinearLayoutManager(getContext()));
        TvShowDbAdapter tvShowDbAdapter = new TvShowDbAdapter(listDb);
        rvMoviesDb.setAdapter(tvShowDbAdapter);

        tvShowDbAdapter.setOnItemClickCallback(new TvShowDbAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Result result) {
                Toast.makeText(getContext(), result.getOriginalName(), Toast.LENGTH_SHORT).show();
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailTvActivity.class);
                moveWithObjectIntent.putExtra(DetailTvActivity.DETAIL_TV, result);
                startActivity(moveWithObjectIntent);
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("ParcelableTV",listDb);
        super.onSaveInstanceState(outState);
    }


}
