package com.example.fantazoo_app.Fragments.AdminFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fantazoo_app.R;

public class AdminFragment extends Fragment {

    public AdminFragment() {
        // Required empty public constructor
    }
    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_admin, container, false);

        rootView.findViewById(R.id.btn_anim).setOnClickListener(v -> fragmentChanger(AdminAnimalFragment.class));
        rootView.findViewById(R.id.btn_zk).setOnClickListener(v -> fragmentChanger(AdminKeeperFragment.class));
        rootView.findViewById(R.id.btn_cage).setOnClickListener(v -> fragmentChanger(AdminCageFragment.class));
        rootView.findViewById(R.id.btn_weap).setOnClickListener(v -> fragmentChanger(AdminWeaponFragment.class));



        return rootView;
    }

    private void fragmentChanger(Class c) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.admin_fragment_container, c, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }
}