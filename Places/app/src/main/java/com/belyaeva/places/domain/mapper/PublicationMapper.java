package com.belyaeva.places.domain.mapper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.belyaeva.places.MainActivity;
import com.belyaeva.places.R;
import com.belyaeva.places.domain.Publication;
import com.belyaeva.places.ui.home.HomeFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

public class PublicationMapper {

    private Context context;

    public PublicationMapper(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Publication publicationFromJson(JSONObject jsonObject){

        Publication publication = null;

        try {
            String icon = jsonObject.getString("icon");
            publication = new Publication(
                    jsonObject.getLong("id"),
                    new CreateBitmap(icon).doInBackground(),
                    icon,
                    jsonObject.getString("info"),
                    jsonObject.getString("nameAuthor"),
                    null
            );

        } catch (JSONException e){
            e.printStackTrace();
        }

        return publication;

    }

    private class CreateBitmap extends AsyncTask<Void, String, Bitmap> {

        private String icon;
        private Bitmap bitmap;

        public CreateBitmap(String icon) {
            this.icon = icon;
        }

        @SuppressLint("CheckResult")
        @Override
        protected Bitmap doInBackground(Void... voids) {
            Log.d("TEST_BITMAP", "Bitmap is get");
            byte[] bytes = Base64.decode(icon,Base64.DEFAULT);
            Glide.with(context)
                    .load(bytes)
                    .placeholder(R.drawable.ic_cancel)
                    .load(bitmap);
            return bitmap;
        }

    }
}
