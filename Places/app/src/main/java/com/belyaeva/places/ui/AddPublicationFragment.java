package com.belyaeva.places.ui;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.belyaeva.places.R;
import com.belyaeva.places.databinding.FragmentAddPublicationBinding;
import com.belyaeva.places.domain.Publication;
import com.belyaeva.places.rest.PlacesApiImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class AddPublicationFragment extends Fragment {

    private static int keyImage = 9;
    androidx.appcompat.widget.Toolbar toolbar;
    private FragmentAddPublicationBinding binding;
    private final int PICKER = 1;
    private Bitmap pic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddPublicationBinding.inflate(inflater, container, false);

        /*View view = inflater.inflate(R.layout.fragment_add_publication, container, false);*/
        View root = binding.getRoot();

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_cancel);
        toolbar.setTitleTextAppearance(getContext(), R.style.ToolbarTitleStyle);
        Menu menu = toolbar.getMenu();
        menu.getItem(0).setIcon(R.drawable.ic_accept);

        binding.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectImage().doInBackground();
            }
        });

        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new AddNewPublication().doInBackground();
                onDestroy();
                return false;
            }
        });

        // Inflate the layout for this fragment
        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //check if we are returning from picture selection
            if (requestCode == PICKER) {
                //полный путь до картинки
                Uri pickedUri = data.getData();
                //declare the bitmap (расровое изображение)
                try {
                    pic = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedUri);
                    Bitmap pic2 = pic;
                    binding.ivImage.setImageBitmap(pic2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private class SelectImage extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            Intent pickIntent = new Intent();
            pickIntent.setType("image/*");
            pickIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICKER);
            return null;
        }
    }

    private class AddNewPublication extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bytes = stream.toByteArray();
            String str_image=Base64.encodeToString(bytes, Base64.DEFAULT);
            /* String str_image = java.util.Base64.getEncoder().encodeToString(bytes);*/

            String nameAuthor = String.valueOf(binding.etUserName.getText());
            String info = String.valueOf(binding.etInfo.getText());

            Publication publication = new Publication(str_image, info, nameAuthor, null);
            PlacesApiImpl placesApi = new PlacesApiImpl(getContext());
            placesApi.createNewPublication(publication);
            return null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        toolbar.setTitleTextAppearance(getContext(), R.style.Theme_Places_AppBarOverlay);
        Menu menu = toolbar.getMenu();
        menu.getItem(0).setIcon(R.drawable.ic_plus);
    }
}