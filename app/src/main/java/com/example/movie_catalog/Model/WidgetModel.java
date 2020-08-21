package com.example.movie_catalog.Model;

public class WidgetModel {

    private String posterPath;

    public WidgetModel(String posterPath){
        this.posterPath=posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
