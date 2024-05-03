package com.example.fantazoo_app.Adapter.AdminAdapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.fantazoo_app.Models.AnimModel;
import com.example.fantazoo_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminAnimAdapter extends ArrayAdapter<AnimModel> {

    private Context aContext;
    private ArrayList<AnimModel> aResults;

    private EditButtonClickListener editButtonClickListener;
    private DeleteButtonClickListener deleteButtonClickListener;

    public AdminAnimAdapter(Context context, ArrayList<AnimModel> results) {
        super(context, 0, results);
        aContext = context;
        aResults = results;
    }

    // Interface for handling delete button clicks
    public interface DeleteButtonClickListener {
        void onDeleteButtonClick(AnimModel selectedItem);
    }

    // Setter for delete button click listener
    public void setDeleteButtonClickListener(DeleteButtonClickListener listener) {
        this.deleteButtonClickListener = listener;
    }

    // Interface for handling edit button clicks
    public interface EditButtonClickListener {
        void onEditButtonClick(AnimModel selectedItem);
    }

    // Setter for edit button click listener
    public void setEditButtonClickListener(EditButtonClickListener listener) {
        this.editButtonClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        ViewHolder viewHolder;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            viewHolder = new ViewHolder();

            // Inflate the layout for the list item
            listItemView = inflater.inflate(R.layout.admin_anim_grid_list, parent, false);

            // Initialize views
            viewHolder.tv_name = listItemView.findViewById(R.id.tv_name_Show);
            viewHolder.tv_age = listItemView.findViewById(R.id.tv_age_Show);
            viewHolder.tv_gender = listItemView.findViewById(R.id.fab_gender);
            viewHolder.tv_damage = listItemView.findViewById(R.id.tv_damage_Show);
            viewHolder.tv_cage = listItemView.findViewById(R.id.tv_cage_Show);
            viewHolder.imageView = listItemView.findViewById(R.id.iv_img);
            viewHolder.imageButton_edit = listItemView.findViewById(R.id.ib_edit);
            viewHolder.imageButton_delete = listItemView.findViewById(R.id.ib_delete);

            // Set tag for ViewHolder to reuse views
            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        // Set OnClickListener to the edit button
        viewHolder.imageButton_edit.setOnClickListener(v -> {
            if (editButtonClickListener != null) {
                AnimModel selectedItem = aResults.get(position);
                editButtonClickListener.onEditButtonClick(selectedItem);
            }
        });

        // Set OnClickListener to the delete button
        viewHolder.imageButton_delete.setOnClickListener(v -> {
            if (deleteButtonClickListener != null) {
                AnimModel selectedItem = aResults.get(position);
                deleteButtonClickListener.onDeleteButtonClick(selectedItem);
            }
        });

        // Bind data to views
        AnimModel anim = aResults.get(position);
        int animAge = anim.getAge();

        if (anim.getName() != null) {
            viewHolder.tv_name.setText(anim.getName());
        }

        if (animAge > 0) {
            viewHolder.tv_age.setText(String.valueOf(animAge));
        }

        if (anim.getGender() != null) {
            switch (anim.getGender()) {
                case "MALE":
                    viewHolder.tv_gender.setImageResource(R.drawable.baseline_male_24);
                    viewHolder.tv_gender.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(aContext, R.color.see_through_blue)));
                    break;
                case "FEMALE":
                    viewHolder.tv_gender.setImageResource(R.drawable.baseline_female_24);
                    viewHolder.tv_gender.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(aContext, R.color.see_through_red)));
                    break;
            }
        }

        if (anim.getCage() != null && anim.getCage().getName() != null) {
            viewHolder.tv_cage.setText(anim.getCage().getName());
        }
        else {
            viewHolder.tv_cage.setText("In the wild");
        }

        // Load image using Picasso library
        String imageUrl = "https://i.imgur.com/p21dFsJ.jpeg";
        Picasso.get().load(imageUrl).into(viewHolder.imageView);

        return listItemView;
    }

    // ViewHolder pattern for efficient view reuse
    static class ViewHolder {
        TextView tv_name;
        TextView tv_age;
        FloatingActionButton tv_gender;
        TextView tv_damage;
        TextView tv_cage;
        ImageButton imageButton_edit;
        ImageButton imageButton_delete;
        ImageView imageView;
    }
}
