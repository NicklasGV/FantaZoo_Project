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
import com.example.fantazoo_app.Adapter.CageAdapter;
import com.example.fantazoo_app.Models.CageModel;
import com.example.fantazoo_app.R;
import com.example.fantazoo_app.Secrets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CagesFragment extends Fragment {
    private GridView gridView;
    private CageAdapter adapter;
    private ArrayList<CageModel> cmodel;

    public CagesFragment() {
        // Required empty public constructor
    }
    public static CagesFragment newInstance(String param1, String param2) {
        CagesFragment fragment = new CagesFragment();
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
        View view = inflater.inflate(R.layout.fragment_cages, container, false);

        gridView = view.findViewById(R.id.gv_cage_list);
        cmodel = new ArrayList<>();
        adapter = new CageAdapter(getContext(), cmodel);
        gridView.setAdapter(adapter);

        getCages();

        return view;
    }
    public void getCages() {
        String url = Secrets.host + "/api/cc";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            Gson gson = new Gson();
            String json = response;
            Type listType = new TypeToken<ArrayList<CageModel>>(){}.getType();
            ArrayList<CageModel> cage = gson.fromJson(json, listType);

            if (cage != null) {
                // Add new data to the existing list
                cmodel.addAll(cage);
                adapter.notifyDataSetChanged(); // Notify adapter of data change
            }


        }, error -> Log.e("Volley", error.toString()));
        rq.add(request);
    }

}