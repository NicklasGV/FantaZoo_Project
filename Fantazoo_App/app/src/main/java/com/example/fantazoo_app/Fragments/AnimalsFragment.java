package com.example.fantazoo_app.Fragments;

import static com.example.fantazoo_app.MainActivity.rq;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.fantazoo_app.Adapter.AnimAdapter;
import com.example.fantazoo_app.Models.AnimModel;
import com.example.fantazoo_app.R;
import com.example.fantazoo_app.Secrets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AnimalsFragment extends Fragment {
    private GridView gridView;
    private AnimAdapter adapter;
    private ArrayList<AnimModel> amodel;

    public AnimalsFragment() {
        // Required empty public constructor
    }

    public static AnimalsFragment newInstance(String param1, String param2) {
        AnimalsFragment fragment = new AnimalsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animals, container, false);

        gridView = view.findViewById(R.id.gv_anim_list);
        amodel = new ArrayList<>();
        adapter = new AnimAdapter(getContext(), amodel);
        gridView.setAdapter(adapter);

        getAnimals();

        return view;
    }

    public void getAnimals() {
        String url = Secrets.host + "/api/ac";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            Gson gson = new Gson();
            String json = response;
            Type listType = new TypeToken<ArrayList<AnimModel>>(){}.getType();
            ArrayList<AnimModel> anim = gson.fromJson(json, listType);

            if (anim != null) {
                // Add new data to the existing list
                amodel.addAll(anim);
                adapter.notifyDataSetChanged(); // Notify adapter of data change
            }


        }, error -> Log.e("Volley", error.toString()));
        rq.add(request);
    }
}