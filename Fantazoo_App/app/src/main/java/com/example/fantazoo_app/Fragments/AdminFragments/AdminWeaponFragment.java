package com.example.fantazoo_app.Fragments.AdminFragments;

import static com.example.fantazoo_app.MainActivity.rq;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fantazoo_app.Adapter.AdminAdapters.AdminWeaponAdapter;
import com.example.fantazoo_app.Models.WeapModel;
import com.example.fantazoo_app.R;
import com.example.fantazoo_app.Secrets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminWeaponFragment extends Fragment implements AdminWeaponAdapter.EditButtonClickListener, AdminWeaponAdapter.DeleteButtonClickListener{
    private GridView gridView;
    private ArrayList<WeapModel> wmodel;
    private AdminWeaponAdapter adapter;
    private EditText weaponNameEditText;
    private EditText weaponDamageEditText;
    private int selectedWeaponId;

    public AdminWeaponFragment() {
        // Required empty public constructor
    }

    public static AdminWeaponFragment newInstance(String param1, String param2) {
        AdminWeaponFragment fragment = new AdminWeaponFragment();
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
        View view = inflater.inflate(R.layout.admin_fragment_weapon, container, false);

        weaponNameEditText = view.findViewById(R.id.editor_weapon_name);
        weaponDamageEditText = view.findViewById(R.id.editor_damage_name);

        gridView = view.findViewById(R.id.gv_weapon_list);
        wmodel = new ArrayList<>();
        adapter = new AdminWeaponAdapter(getContext(), wmodel);
        gridView.setAdapter(adapter);

        getWeapons();

        Button saveButton = view.findViewById(R.id.btn_admin_weapon_save);
        adapter.setEditButtonClickListener(this);
        adapter.setDeleteButtonClickListener(this);

        // Set OnClickListener to the save button
        saveButton.setOnClickListener(v -> saveWeapon());

        return view;
    }

    // Overrides the method to populate edit views,
    // with data from selectedItem and set the selected weapon ID.
    @Override
    public void onEditButtonClick(WeapModel selectedItem) {
        // Populate the edit views with data from selectedItem
        populateEditViews(selectedItem);

        // Set the selected weapon ID
        selectedWeaponId = selectedItem.getId();
    }
    // Overrides the method to set the selected weapon ID and delete the corresponding weapon.
    @Override
    public void onDeleteButtonClick(WeapModel selectedItem) {
        // Set the selected weapon ID
        selectedWeaponId = selectedItem.getId();
        deleteWeapon(selectedWeaponId);
    }

    private void populateEditViews(WeapModel selectedItem) {
        weaponNameEditText.setText(selectedItem.getName());
        weaponDamageEditText.setText(selectedItem.getDamage());
    }

    // Gets all weapons
    public void getWeapons() {
        String url = Secrets.host + "/api/wpc";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            String json = response;
            Gson gson = new Gson();

            // Specify the type of the list elements
            Type listType = new TypeToken<ArrayList<WeapModel>>(){}.getType();
            ArrayList<WeapModel> weapModel = gson.fromJson(json, listType);

            if (weapModel != null) {
                // Add new data to the existing list
                wmodel.addAll(weapModel);
                adapter.notifyDataSetChanged(); // Notify adapter of data change
            }
        }, error -> Log.e("Volley", error.toString()))
        {
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String>  params = new HashMap<>();
                return params;
            }
        };
        rq.add(request);
    }

    // Saves weapons as a standard, but if it detects an id it edits the id with the data,
    // from editViews
    private void saveWeapon() {
        // Retrieve values from views
        String name = weaponNameEditText.getText().toString();
        String damage = weaponDamageEditText.getText().toString();

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(weaponNameEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(weaponDamageEditText.getWindowToken(), 0);

        // Create JSON object with the weapon data
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
            requestBody.put("damage", damage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Determine the URL and request method based on whether the user is editing an existing weapon or creating a new one
        String url;
        int method;

        if (selectedWeaponId != 0) {
            // Editing existing weapon: use PUT request
            url = Secrets.host + "/api/wpc/id/" + selectedWeaponId;
            method = Request.Method.PUT;
        } else {
            // Creating new weapon: use POST request
            url = Secrets.host + "/api/wpc";
            method = Request.Method.POST;
        }

        // Create a request
        JsonObjectRequest request = new JsonObjectRequest(method, url, requestBody,
                response -> {
                    // Handle response
                    Log.d("SaveWeapon", "Response: " + response.toString());
                    // Handle success response from server
                },
                error -> {
                    // Handle error
                    Log.e("SaveWeapon", "Error: " + error.toString());
                    // Handle error response from server
                });

        // Add the request to the
        reloadFragment();
        rq.add(request);
    }

    void deleteWeapon(int weapId) {
        String url = Secrets.host + "/api/wpc/id/" + weapId;
        StringRequest request = new StringRequest(Request.Method.DELETE, url, response -> {
            // Response can be added when delete is succesful
        }, error -> Log.e("Volley", error.toString()));
        reloadFragment();
        rq.add(request);
    }

    private void reloadFragment() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.admin_fragment_container, new AdminWeaponFragment());
        fragmentTransaction.commit();
    }
}