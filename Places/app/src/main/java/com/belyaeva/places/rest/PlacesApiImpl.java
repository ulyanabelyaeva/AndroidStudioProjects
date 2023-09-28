package com.belyaeva.places.rest;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.belyaeva.places.db.Db;
import com.belyaeva.places.domain.Publication;
import com.belyaeva.places.domain.mapper.PublicationMapper;
import com.belyaeva.places.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacesApiImpl implements PlacesApi{

    public static final String API_TEST = "API_TEST";
    public static final String BASE_URL = "http://192.168.43.230:8082";
    private Response.ErrorListener errorListener;
    private final Context context;


    public PlacesApiImpl(Context context) {
        this.context = context;
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(API_TEST, error.toString());
            }
        };
    }

    @Override
    public void fillPublications() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = BASE_URL + "/publication";

        PublicationMapper publicationMapper = new PublicationMapper(context);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {
                        Db.PUBLICATION_LIST.clear();
                        try{
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Publication publication = publicationMapper.publicationFromJson(jsonObject);
                                Db.PUBLICATION_LIST.add(publication);
                            }

                            List<Publication> publications = new ArrayList<>(); //чтобы наверху списка были недавно добавленные публикации
                            for (int i = Db.PUBLICATION_LIST.size() - 1; i >= 0 ; i--) {
                                publications.add(Db.PUBLICATION_LIST.get(i));
                            }
                            Db.PUBLICATION_LIST.clear();
                            Db.PUBLICATION_LIST.addAll(publications);

                            if (HomeFragment.publicationAdapter != null)
                                HomeFragment.publicationAdapter.notifyDataSetChanged();

                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                errorListener
        );

        requestQueue.add(arrayRequest);

    }

    @Override
    public void createNewPublication(Publication publication) {

        Log.d(API_TEST, "try to create new publication");

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = BASE_URL + "/publication";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        fillPublications();
                    }
                },
                errorListener
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("icon", publication.getIcon());
                params.put("info", publication.getInfo());
                params.put("nameAuthor", publication.getNameAuthor());

                return params;
            }
        };

        requestQueue.add(request);

    }
}
