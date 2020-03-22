package com.example.android.demomoviedb.Utilities;

import com.example.android.demomoviedb.MainActivity;
import com.example.android.demomoviedb.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String JSON_KEY_RESULTS = "results";
    private static final String JSON_KEY_ORIGINAL_TITLE = "original_title";
    private static final String JSON_KEY_POSTER_PATH = "poster_path";
    private static final String JSON_KEY_OVERVIEW = "overview";
    private static final String JSON_KEY_VOTE_AVERAGE = "vote_average";
    private static final String JSON_KEY_RELEASE_DATE = "release_date";

    public static void parseMovieJson(String json) {

        ArrayList<JSONObject> movies = new ArrayList<>();
        List<String> originalTitles = new ArrayList<>();
        List<String> posterPaths = new ArrayList<>();
        List<String> overviews = new ArrayList<>();
        List<Integer> voteAverages = new ArrayList<>();
        List<String> releaseDates = new ArrayList<>();

        try{

            JSONObject moviesDetails = new JSONObject(json);
            JSONArray resultsArray = moviesDetails.optJSONArray(JSON_KEY_RESULTS);

            if (resultsArray != null)
                for (int i = 0; i < resultsArray.length(); i++)
                    movies.add(resultsArray.getJSONObject(i));

            if (movies.size() > 0) {
                for (int i = 0; i < movies.size(); i++) {
                    originalTitles.add(movies.get(i).optString(JSON_KEY_ORIGINAL_TITLE));
                    posterPaths.add(movies.get(i).optString(JSON_KEY_POSTER_PATH));
                    overviews.add(movies.get(i).optString(JSON_KEY_OVERVIEW));
                    voteAverages.add(movies.get(i).optInt(JSON_KEY_VOTE_AVERAGE));
                    releaseDates.add(movies.get(i).optString(JSON_KEY_RELEASE_DATE));

                }

                for (int i = 0; i < movies.size(); i++) {
                    Movie movie = new Movie(originalTitles.get(i), posterPaths.get(i), overviews.get(i), voteAverages.get(i), releaseDates.get(i));
                    MainActivity.movieArrayList.add(movie);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
