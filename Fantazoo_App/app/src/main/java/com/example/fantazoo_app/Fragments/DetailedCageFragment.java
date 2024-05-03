package com.example.fantazoo_app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.fantazoo_app.Adapter.ZKAdapter;
import com.example.fantazoo_app.Models.AnimModel;
import com.example.fantazoo_app.Models.ZKModel;
import com.example.fantazoo_app.R;

import java.util.ArrayList;

public class DetailedCageFragment extends Fragment {
    private GridView gridView;
//    private DetailedCageAdapter adapter;
    private ArrayList<ZKModel> zkmodel;
    private ArrayList<AnimModel> amodel;

    public DetailedCageFragment() {
        // Required empty public constructor
    }

    public static DetailedCageFragment newInstance(String param1, String param2) {
        DetailedCageFragment fragment = new DetailedCageFragment();
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

//        gridView = view.findViewById(R.id.gv_zk_list);
//        zkmodel = new ArrayList<>();
//        adapter = new ZKAdapter(getContext(), zkmodel);
//        gridView.setAdapter(adapter);

        return view;
    }
}