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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fantazoo_app.Adapter.AdminAnimAdapter;
import com.example.fantazoo_app.Adapter.AnimAdapter;
import com.example.fantazoo_app.Adapter.CageAdapter;
import com.example.fantazoo_app.Adapter.CageSpinnerAdapter;
import com.example.fantazoo_app.Extra.Gender;
import com.example.fantazoo_app.Models.AnimModel;
import com.example.fantazoo_app.Models.CageModel;
import com.example.fantazoo_app.R;
import com.example.fantazoo_app.Secrets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminAnimalFragment extends Fragment implements AdminAnimAdapter.EditButtonClickListener, AdminAnimAdapter.DeleteButtonClickListener{
    private Spinner spinner;
    private GridView gridView;
    private ArrayList<AnimModel> amodel;
    private ArrayList<CageModel> cages;
    private CageSpinnerAdapter adapter;
    private AdminAnimAdapter animAdapter;
    private EditText animalNameEditText;
    private EditText animalAgeEditText;
    private Spinner animalGenderSpinner;
    private Spinner animalCageSpinner;
    private int selectedAnimalId;

    public AdminAnimalFragment() {
        // Required empty public constructor
    }
    public static AdminAnimalFragment newInstance(String param1, String param2) {
        AdminAnimalFragment fragment = new AdminAnimalFragment();
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
        View view = inflater.inflate(R.layout.admin_fragment_animal, container, false);

        spinner = view.findViewById(R.id.editor_animal_cage);
        cages = new ArrayList<>();
        adapter = new CageSpinnerAdapter(getContext(), cages);
        spinner.setAdapter(adapter);

        animalNameEditText = view.findViewById(R.id.editor_animal_name);
        animalAgeEditText = view.findViewById(R.id.editor_animal_age);
        animalGenderSpinner = view.findViewById(R.id.editor_animal_gender);
        animalCageSpinner = view.findViewById(R.id.editor_animal_cage);

        gridView = view.findViewById(R.id.gv_anim_list);
        amodel = new ArrayList<>();
        animAdapter = new AdminAnimAdapter(getContext(), amodel);
        gridView.setAdapter(animAdapter);

        getAnimals();

        getCages();

        Button saveButton = view.findViewById(R.id.btn_admin_animal_save);
        animAdapter.setEditButtonClickListener(this);
        animAdapter.setDeleteButtonClickListener(this);

        // Set OnClickListener to the save button
        saveButton.setOnClickListener(v -> saveAnimal());

        ArrayAdapter<Gender> genderAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Gender.values());
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalGenderSpinner.setAdapter(genderAdapter);

        return view;
    }
    // Overrides the method to populate edit views,
    // with data from selectedItem and set the selected animal ID.
    @Override
    public void onEditButtonClick(AnimModel selectedItem) {
        // Populate the edit views with data from selectedItem
        populateEditViews(selectedItem);

        // Set the selected animal ID
        selectedAnimalId = selectedItem.getId();
    }
    // Overrides the method to set the selected animal ID and delete the corresponding animal.
    @Override
    public void onDeleteButtonClick(AnimModel selectedItem) {
        // Set the selected animal ID
        selectedAnimalId = selectedItem.getId();
        deleteAnimal(selectedAnimalId);
    }

    private void populateEditViews(AnimModel selectedItem) {

        animalNameEditText.setText(selectedItem.getName());
        animalAgeEditText.setText(String.valueOf(selectedItem.getAge()));

        // Find the position of the gender in the Gender enum
        Gender gender = Gender.valueOf(selectedItem.getGender());
        int genderPosition = getGenderPosition(gender);

        // Set the spinner to the position of the gender
        animalGenderSpinner.setSelection(genderPosition);

        // Find the position of the cage in the list of cages
        CageModel selectedCage = selectedItem.getCage();
        int cagePosition = getCagePosition(selectedCage);

        // Set the cage spinner to the position of the selected cage
        animalCageSpinner.setSelection(cagePosition);
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
    // Gets gender position for edited animal
    private int getGenderPosition(Gender gender) {
        Gender[] genders = Gender.values();
        for (int i = 0; i < genders.length; i++) {
            if (genders[i] == gender) {
                return i;
            }
        }
        return 0; // Default to the first position if not found
    }
    // Gets all animals
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
                cages.addAll(cageModel);
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
    // Saves animals as a standard, but if it detects an id it edits the id with the data,
    // from editViews
    private void saveAnimal() {
        // Retrieve values from views
        String name = animalNameEditText.getText().toString();
        int age = Integer.parseInt(animalAgeEditText.getText().toString());
        Gender gender = (Gender) animalGenderSpinner.getSelectedItem();
        CageModel selectedCage = (CageModel) animalCageSpinner.getSelectedItem();

        int cageId = selectedCage.getId();

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(animalNameEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(animalAgeEditText.getWindowToken(), 0);

        // Create JSON object with the animal data
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
            requestBody.put("age", age);
            requestBody.put("gender", gender.toString()); // Convert enum to string
            JSONObject cageObject = new JSONObject();
            cageObject.put("id", cageId);
            requestBody.put("cage", cageObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Determine the URL and request method based on whether the user is editing an existing animal or creating a new one
        String url;
        int method;

        if (selectedAnimalId != 0) {
            // Editing existing animal: use PUT request
            url = Secrets.host + "/api/ac/id/" + selectedAnimalId;
            method = Request.Method.PUT;
        } else {
            // Creating new animal: use POST request
            url = Secrets.host + "/api/ac";
            method = Request.Method.POST;
        }

        // Create a request
        JsonObjectRequest request = new JsonObjectRequest(method, url, requestBody,
                response -> {
                    // Handle response
                    Log.d("SaveAnimal", "Response: " + response.toString());
                    // Handle success response from server
                },
                error -> {
                    // Handle error
                    Log.e("SaveAnimal", "Error: " + error.toString());
                    // Handle error response from server
                });

        // Add the request to the
        reloadFragment();
        rq.add(request);
    }

    void deleteAnimal(int animId) {
        String url = Secrets.host + "/api/ac/id/" + animId;
        StringRequest request = new StringRequest(Request.Method.DELETE, url, response -> {
            Toast.makeText(getContext(), "Animal Deleted", Toast.LENGTH_SHORT).show();
        }, error -> Log.e("Volley", error.toString()));
        reloadFragment();
        rq.add(request);
    }

    private void reloadFragment() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.admin_fragment_container, new AdminAnimalFragment());
        fragmentTransaction.commit();
    }
}