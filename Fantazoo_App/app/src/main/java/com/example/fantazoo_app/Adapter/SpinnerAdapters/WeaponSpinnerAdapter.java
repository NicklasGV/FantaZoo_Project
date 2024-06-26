package com.example.fantazoo_app.Adapter.SpinnerAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fantazoo_app.Models.WeapModel;
import com.example.fantazoo_app.R;

import java.util.ArrayList;

public class WeaponSpinnerAdapter extends ArrayAdapter<WeapModel> {

    public WeaponSpinnerAdapter(Context context, ArrayList<WeapModel> weapons) {
        super(context, 0, weapons);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        WeapModel weapon = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.spinner_text);

        // Populate the data into the template view using the data object
        if (weapon != null) {
            tvName.setText(weapon.getName());
        } else {
            // If weapon is null, set "No weapon" as the text
            tvName.setText("No Weapon");
        }

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
