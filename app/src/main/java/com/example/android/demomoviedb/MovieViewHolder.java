package com.example.android.demomoviedb;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.demomoviedb.model.Movie;

import static com.example.android.demomoviedb.MovieAdapter.mClickHandler;
import static com.example.android.demomoviedb.MovieAdapter.mMovies;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView moviePoster;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        moviePoster = itemView.findViewById(R.id.iv_movie_poster);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int adapterPosition = getAdapterPosition();
        Movie movie = mMovies.get(adapterPosition);
        mClickHandler.onClick(movie);
    }
}
