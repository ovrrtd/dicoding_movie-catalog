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

import com.example.movie_catalog.Activity.DetailMovieActivity;
import com.example.movie_catalog.Adapter.MovieDbAdapter;
import com.example.movie_catalog.Controller.Controller;
import com.example.movie_catalog.Interface.MovieDbInterface;
import com.example.movie_catalog.Model.MovieDB.MovieDb;
import com.example.movie_catalog.Model.MovieDB.Result;
import com.example.movie_catalog.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private ArrayList<Result> listDb=new ArrayList<>();


    private RecyclerView rvMoviesDb;


    public final String API_KEY="fd141ba30ab693bd721d88bd9c88ca66";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        rvMoviesDb = view.findViewById(R.id.rv_movies);
        rvMoviesDb.setHasFixedSize(true);
        if (savedInstanceState != null) {
            listDb = savedInstanceState.getParcelableArrayList("ParcelableMovies");
            showRecyclerListDb();
        }
        else{
            prepareDB();
        }




        // Inflate the layout for this fragment
        return view;
    }

    private void prepareDB(){
        MovieDbInterface movieDbInterface= Controller.getRetrofitInstance().create(MovieDbInterface.class);
        Call<MovieDb> call= movieDbInterface.getMovieDb(API_KEY,"en-US");
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("Please waiting");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<MovieDb>() {
            @Override
            public void onResponse(Call<MovieDb> call, Response<MovieDb> response) {
                progressDialog.dismiss();
                listDb.addAll(response.body().getResults());
                showRecyclerListDb();
            }

            @Override
            public void onFailure(Call<MovieDb> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showRecyclerListDb(){
        rvMoviesDb.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieDbAdapter movieDbAdapter = new MovieDbAdapter(listDb);
        rvMoviesDb.setAdapter(movieDbAdapter);

        movieDbAdapter.setOnItemClickCallback(new MovieDbAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Result result) {
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailMovieActivity.class);
                moveWithObjectIntent.putExtra(DetailMovieActivity.DETAIL_MOVIE, result);
                startActivity(moveWithObjectIntent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("ParcelableMovies",listDb);
        super.onSaveInstanceState(outState);
    }

}