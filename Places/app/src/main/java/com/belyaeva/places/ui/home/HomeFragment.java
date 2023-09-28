package com.belyaeva.places.ui.home;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.belyaeva.places.MainActivity;
import com.belyaeva.places.R;
import com.belyaeva.places.adapter.PublicationAdapter;
import com.belyaeva.places.databinding.FragmentHomeBinding;
import com.belyaeva.places.db.Db;
import com.belyaeva.places.rest.PlacesApiImpl;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public static PublicationAdapter publicationAdapter;

    @SuppressLint("WrongThread")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /*HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);*/

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        new LoadPublications().doInBackground();

        publicationAdapter = new PublicationAdapter(getContext(), Db.PUBLICATION_LIST);
        binding.rvPublications.setAdapter(publicationAdapter);

        return root;
    }

    private class LoadPublications extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            PlacesApiImpl placesApi = new PlacesApiImpl(getContext());
            placesApi.fillPublications();
            return null;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}