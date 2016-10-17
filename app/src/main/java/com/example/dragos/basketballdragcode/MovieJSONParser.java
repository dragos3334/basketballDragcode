package com.example.dragos.basketballdragcode;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MovieJSONParser {

    public static ArrayList<Player> parseFeed (String content){
        try {

            JSONObject jb= new JSONObject(content);
            JSONArray ar= jb.getJSONArray("all_Players");


            ArrayList<Player> movieList= new ArrayList<>();

            for( int i= 0; i < ar.length(); i ++){

                JSONObject obj= ar.getJSONObject(i);
                Player movie = new Player();


                movie.setName(obj.getString("uuname"));
                movie.setphoneNumber(obj.getString("uuphoneNumber"));



                movieList.add(movie);

            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}

