package com.example.android.demomoviedb;

import android.os.AsyncTask;

import com.example.android.demomoviedb.Utilities.JsonUtils;
import com.example.android.demomoviedb.Utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MovieDetails extends AsyncTask<URL, Void, String> {
    @Override
    protected String doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        String jsonResult = null;
        try {
            jsonResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        MainActivity.currentPage ++;
        JsonUtils.parseMovieJson(s);
        MainActivity.mAdapter.notifyDataSetChanged();

    }
}
