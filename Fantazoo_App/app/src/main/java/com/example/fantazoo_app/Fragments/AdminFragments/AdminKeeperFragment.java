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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fantazoo_app.Adapter.AdminAdapters.AdminAnimAdapter;
import com.example.fantazoo_app.Adapter.AdminAdapters.AdminZKAdapter;
import com.example.fantazoo_app.Adapter.SpinnerAdapters.CageSpinnerAdapter;
import com.example.fantazoo_app.Adapter.SpinnerAdapters.WeaponSpinnerAdapter;
import com.example.fantazoo_app.Extra.Gender;
import com.example.fantazoo_app.Models.AnimModel;
import com.example.fantazoo_app.Models.CageModel;
import com.example.fantazoo_app.Models.WeapModel;
import com.example.fantazoo_app.Models.ZKModel;
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

public class AdminKeeperFragment extends Fragment implements AdminZKAdapter.EditButtonClickListener, AdminZKAdapter.DeleteButtonClickListener{
    private GridView gridView;
    private ArrayList<ZKModel> zkmodel;
    private ArrayList<WeapModel> weapons;
    private ArrayList<CageModel> cages;
    private CageSpinnerAdapter cageAdapter;
    private WeaponSpinnerAdapter weapAdapter;
    private AdminZKAdapter adapter;
    private EditText keeperNameEditText;
    private Spinner zkWeaponSpinner;
    private Spinner zkCageSpinner;
    private int selectedKeeperId;

    public AdminKeeperFragment() {
        // Required empty public constructor
    }

    public static AdminKeeperFragment newInstance(String param1, String param2) {
        AdminKeeperFragment fragment = new AdminKeeperFragment();
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
        View view = inflater.inflate(R.layout.admin_fragment_keeper, container, false);

        zkCageSpinner = view.findViewById(R.id.editor_keeper_cage);
        cages = new ArrayList<>();
        cageAdapter = new CageSpinnerAdapter(getContext(), cages);
        zkCageSpinner.setAdapter(cageAdapter);

        zkWeaponSpinner = view.findViewById(R.id.editor_keeper_weapon);
        weapons = new ArrayList<>();
        weapAdapter = new WeaponSpinnerAdapter(getContext(), weapons);
        zkWeaponSpinner.setAdapter(weapAdapter);

        keeperNameEditText = view.findViewById(R.id.editor_keeper_name);

        gridView = view.findViewById(R.id.gv_keeper_list);
        zkmodel = new ArrayList<>();
        adapter = new AdminZKAdapter(getContext(), zkmodel);
        gridView.setAdapter(adapter);

        getWeapons();
        getZKs();
        getCages();

        Button saveButton = view.findViewById(R.id.btn_admin_keeper_save);
        adapter.setEditButtonClickListener(this);
        adapter.setDeleteButtonClickListener(this);

        // Set OnClickListener to the save button
        saveButton.setOnClickListener(v -> saveKeeper());

        return view;
    }

    // Overrides the method to populate edit views,
    // with data from selectedItem and set the selected animal ID.
    @Override
    public void onEditButtonClick(ZKModel selectedItem) {
        // Populate the edit views with data from selectedItem
        populateEditViews(selectedItem);

        // Set the selected animal ID
        selectedKeeperId = selectedItem.getId();
    }
    // Overrides the method to set the selected animal ID and delete the corresponding animal.
    @Override
    public void onDeleteButtonClick(ZKModel selectedItem) {
        // Set the selected animal ID
        selectedKeeperId = selectedItem.getId();
        deleteKeeper(selectedKeeperId);
    }

    private void populateEditViews(ZKModel selectedItem) {

        keeperNameEditText.setText(selectedItem.getName());

        // Find the position of the gender in the Gender enum
        WeapModel selectedWeapon = selectedItem.getWeapon();
        int weaponPosition = getWeaponPosition(selectedWeapon);

        // Set the cage spinner to the position of the selected cage
        zkWeaponSpinner.setSelection(weaponPosition);

        // Find the position of the cage in the list of cages
        CageModel selectedCage = selectedItem.getCage();
        int cagePosition = getCagePosition(selectedCage);

        // Set the cage spinner to the position of the selected cage
        zkCageSpinner.setSelection(cagePosition);
    }

    // Gets cage position for edited animal
    private int getCagePosition(CageModel selectedCage) {
        if (selectedCage != null) {
            for (int i = 0; i < cages.size(); i++) {
                CageModel cage = cages.get(i);
                if (cage != null && cage.getId() == selectedCage.getId()) {
                    return i;
                }
            }
        }
        return 0; // Default to the first position if not found
    }
    // Gets weapon position for edited animal
    private int getWeaponPosition(WeapModel selectedWeapon) {
        if (selectedWeapon != null) {
            for (int i = 0; i < cages.size(); i++) {
                CageModel cage = cages.get(i);
                if (cage != null && cage.getId() == selectedWeapon.getId()) {
                    return i;
                }
            }
        }
        return 0; // Default to the first position if not found
    }
    // Gets all cages
    public void getCages() {
        String url = Secrets.host + "/api/cc";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            String json = response;
            Gson gson = new Gson();

            // Specify the type of the list elements
            Type listType = new TypeToken<ArrayList<CageModel>>(){}.getType();
            ArrayList<CageModel> cageModel = gson.fromJson(json, listType);

            // Add "No cage" option
            cages.add(new CageModel(0, "No cage"));

            if (cageModel != null) {
                // Add new data to the existing list
                cages.addAll(cageModel);
                cageAdapter.notifyDataSetChanged(); // Notify adapter of data change
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
    // Gets all weapons
    public void getWeapons() {
        String url = Secrets.host + "/api/wpc";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            String json = response;
            Gson gson = new Gson();

            // Specify the type of the list elements
            Type listType = new TypeToken<ArrayList<WeapModel>>(){}.getType();
            ArrayList<WeapModel> weapModel = gson.fromJson(json, listType);

            // Add "No Weapon" option
            weapons.add(new WeapModel(0, "No Weapon"));

            if (weapModel != null) {
                // Add new data to the existing list
                weapons.addAll(weapModel);
                weapAdapter.notifyDataSetChanged(); // Notify adapter of data change
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
    public void getZKs() {
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
    // Saves animals as a standard, but if it detects an id it edits the id with the data,
    // from editViews
    private void saveKeeper() {
        // Retrieve values from views
        String name = keeperNameEditText.getText().toString();
        WeapModel selectedWeapon = (WeapModel) zkWeaponSpinner.getSelectedItem();
        CageModel selectedCage = (CageModel) zkCageSpinner.getSelectedItem();

        int cageId;

        // Check if selected cage is "No cage"
        if (selectedCage != null && selectedCage.getName().equals("No cage")) {
            cageId = 0; // Set cage ID to 0 for "No cage"
        } else {
            cageId = selectedCage != null ? selectedCage.getId() : 0;
        }

        int weapId;

        // Check if selected cage is "No cage"
        if (selectedWeapon != null && selectedWeapon.getName().equals("No Weapon")) {
            weapId = 0; // Set cage ID to 0 for "No cage"
        } else {
            weapId = selectedWeapon != null ? selectedWeapon.getId() : 0;
        }

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(keeperNameEditText.getWindowToken(), 0);

        // Create JSON object with the animal data
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
            // Add cage data only if the cage is not "No cage"
            if (cageId != 0) {
                JSONObject cageObject = new JSONObject();
                cageObject.put("id", cageId);
                requestBody.put("cage", cageObject);
            }
            // Add weapon data only if the cage is not "No Weapon"
            if (weapId != 0) {
                JSONObject weapObject = new JSONObject();
                weapObject.put("id", weapId);
                requestBody.put("weapon", weapObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Determine the URL and request method based on whether the user is editing an existing animal or creating a new one
        String url;
        int method;

        if (selectedKeeperId != 0) {
            // Editing existing animal: use PUT request
            url = Secrets.host + "/api/zkc/id/" + selectedKeeperId;
            method = Request.Method.PUT;
        } else {
            // Creating new animal: use POST request
            url = Secrets.host + "/api/zkc";
            method = Request.Method.POST;
        }

        // Create a request
        JsonObjectRequest request = new JsonObjectRequest(method, url, requestBody,
                response -> {
                    // Handle response
                    Log.d("SaveKeeper", "Response: " + response.toString());
                    // Handle success response from server
                },
                error -> {
                    // Handle error
                    Log.e("SaveKeeper", "Error: " + error.toString());
                    // Handle error response from server
                });

        // Add the request to the
        reloadFragment();
        rq.add(request);
    }
    void deleteKeeper(int animId) {
        String url = Secrets.host + "/api/zkc/id/" + animId;
        StringRequest request = new StringRequest(Request.Method.DELETE, url, response -> {
            // Response can be added when delete is succesful
        }, error -> Log.e("Volley", error.toString()));
        reloadFragment();
        rq.add(request);
    }

    private void reloadFragment() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.admin_fragment_container, new AdminKeeperFragment());
        fragmentTransaction.commit();
    }
}