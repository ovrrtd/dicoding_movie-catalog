package com.example.movie_catalog.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movie_catalog.Activity.DetailMovieActivity;
import com.example.movie_catalog.Adapter.MovieDbAdapter;
import com.example.movie_catalog.Database.MovieHelper;
import com.example.movie_catalog.Model.MovieDB.Result;
import com.example.movie_catalog.R;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritMovieFragment extends Fragment {

    private ArrayList<Result> listDb=new ArrayList<>();

    private MovieHelper movieHelper;

    private RecyclerView rvMoviesDb;

    private MovieDbAdapter movieDbAdapter;

    public FavoritMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_favorit_movie, container, false);
        movieHelper = MovieHelper.getInstance(view.getContext());
        movieHelper.open();

        rvMoviesDb = view.findViewById(R.id.rv_movies_fav);
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
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("Please waiting");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        listDb.addAll(movieHelper.getAllMovies());
        Log.i(TAG, "prepareDB: ");
        showRecyclerListDb();
        progressDialog.dismiss();
    }
    private void showRecyclerListDb(){
        rvMoviesDb.setLayoutManager(new LinearLayoutManager(getContext()));
        movieDbAdapter = new MovieDbAdapter(listDb);
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

    @Override
    public void onResume() {
        super.onResume();
        listDb = movieHelper.getAllMovies();
        movieDbAdapter.setData(listDb);
    }
}
