package com.example.fantazoo_app.Fragments;

import static com.example.fantazoo_app.MainActivity.rq;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.fantazoo_app.Adapter.ZKAdapter;
import com.example.fantazoo_app.Models.ZKModel;
import com.example.fantazoo_app.R;
import com.example.fantazoo_app.Secrets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ZookeepersFragment extends Fragment {
    private GridView gridView;
    private ZKAdapter adapter;
    private ArrayList<ZKModel> zkmodel;

    public ZookeepersFragment() {
        // Required empty public constructor
    }
    public static ZookeepersFragment newInstance(String param1, String param2) {
        ZookeepersFragment fragment = new ZookeepersFragment();
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
        View view = inflater.inflate(R.layout.fragment_zookeepers, container, false);

        gridView = view.findViewById(R.id.gv_zk_list);
        zkmodel = new ArrayList<>();
        adapter = new ZKAdapter(getContext(), zkmodel);
        gridView.setAdapter(adapter);

        getZookeepers();

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Call updateGridViewColumns when configuration changes (e.g., device orientation)
        setRetainInstance(true);
        updateGridViewColumns();
    }

    private void updateGridViewColumns() {
        int columns;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidthPx = displayMetrics.widthPixels;
        int screenHeightPx = displayMetrics.heightPixels;
        float screenDensity = displayMetrics.density;

        // Define thresholds for different screen sizes
        int phoneScreenWidthThreshold = 1000; // in pixels
        int tabletScreenWidthThreshold = 1400; // in pixelsw

        // Calculate screen width in dp
        int screenWidthDp = (int) (screenWidthPx / screenDensity);

        // Determine number of columns based on screen size
        if (screenWidthDp < phoneScreenWidthThreshold) {
            columns = 2; // Small screens (phones)
        } else if (screenWidthDp < tabletScreenWidthThreshold) {
            columns = 3; // Medium screens (tablets)
        } else {
            columns = 4; // Large screens (PC screens)
        }

        gridView.setNumColumns(columns);
    }

    public void getZookeepers() {
        String url = Secrets.host + "/api/zkc";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            Gson gson = new Gson();
            String json = response;

            Log.v("Data", json);
            Type listType = new TypeToken<ArrayList<ZKModel>>(){}.getType();
            ArrayList<ZKModel> zkm = gson.fromJson(json, listType);

            if (zkm != null) {
                // Add new data to the existing list
                zkmodel.addAll(zkm);
                adapter.notifyDataSetChanged(); // Notify adapter of data change
            }


        }, error -> Log.e("Volley", error.toString()));
        rq.add(request);
    }
}