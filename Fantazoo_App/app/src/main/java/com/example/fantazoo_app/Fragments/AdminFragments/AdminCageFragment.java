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
import com.example.fantazoo_app.Adapter.AdminAdapters.AdminCageAdapter;
import com.example.fantazoo_app.Models.CageModel;
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

public class AdminCageFragment extends Fragment implements AdminCageAdapter.EditButtonClickListener, AdminCageAdapter.DeleteButtonClickListener{
    private GridView gridView;
    private ArrayList<CageModel> cmodel;
    private AdminCageAdapter cageAdapter;
    private EditText cageNameEditText;
    private int selectedCageId;

    public AdminCageFragment() {
        // Required empty public constructor
    }
    public static AdminCageFragment newInstance(String param1, String param2) {
        AdminCageFragment fragment = new AdminCageFragment();
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
        View view = inflater.inflate(R.layout.admin_fragment_cage, container, false);

        cageNameEditText = view.findViewById(R.id.editor_cage_name);

        gridView = view.findViewById(R.id.gv_cage_list);
        cmodel = new ArrayList<>();
        cageAdapter = new AdminCageAdapter(getContext(), cmodel);
        gridView.setAdapter(cageAdapter);

        getCages();

        Button saveButton = view.findViewById(R.id.btn_admin_cage_save);
        cageAdapter.setEditButtonClickListener(this);
        cageAdapter.setDeleteButtonClickListener(this);

        // Set OnClickListener to the save button
        saveButton.setOnClickListener(v -> saveCage());

        return view;
    }

    // Overrides the method to populate edit views,
    // with data from selectedItem and set the selected cage ID.
    @Override
    public void onEditButtonClick(CageModel selectedItem) {
        // Populate the edit views with data from selectedItem
        populateEditViews(selectedItem);

        // Set the selected cage ID
        selectedCageId = selectedItem.getId();
    }
    // Overrides the method to set the selected cage ID and delete the corresponding cage.
    @Override
    public void onDeleteButtonClick(CageModel selectedItem) {
        // Set the selected cage ID
        selectedCageId = selectedItem.getId();
        deleteCage(selectedCageId);
    }

    private void populateEditViews(CageModel selectedItem) {
        cageNameEditText.setText(selectedItem.getName());
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

            if (cageModel != null) {
                // Add new data to the existing list
                cmodel.addAll(cageModel);
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

    // Saves cages as a standard, but if it detects an id it edits the id with the data,
    // from editViews
    private void saveCage() {
        // Retrieve values from views
        String name = cageNameEditText.getText().toString();

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(cageNameEditText.getWindowToken(), 0);

        // Create JSON object with the cage data
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Determine the URL and request method based on whether the user is editing an existing cage or creating a new one
        String url;
        int method;

        if (selectedCageId != 0) {
            // Editing existing cage: use PUT request
            url = Secrets.host + "/api/cc/id/" + selectedCageId;
            method = Request.Method.PUT;
        } else {
            // Creating new cage: use POST request
            url = Secrets.host + "/api/cc";
            method = Request.Method.POST;
        }

        // Create a request
        JsonObjectRequest request = new JsonObjectRequest(method, url, requestBody,
                response -> {
                    // Handle response
                    Log.d("SaveCage", "Response: " + response.toString());
                    // Handle success response from server
                },
                error -> {
                    // Handle error
                    Log.e("SaveCage", "Error: " + error.toString());
                    // Handle error response from server
                });

        // Add the request to the
        reloadFragment();
        rq.add(request);
    }

    void deleteCage(int cageId) {
        String url = Secrets.host + "/api/cc/id/" + cageId;
        StringRequest request = new StringRequest(Request.Method.DELETE, url, response -> {
            // Response can be added when delete is succesful
        }, error -> Log.e("Volley", error.toString()));
        reloadFragment();
        rq.add(request);
    }

    private void reloadFragment() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.admin_fragment_container, new AdminCageFragment());
        fragmentTransaction.commit();
    }
}