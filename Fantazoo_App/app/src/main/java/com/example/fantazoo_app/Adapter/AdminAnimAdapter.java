package com.example.fantazoo_app.Adapter;

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

    public AdminAnimAdapter( Context context, ArrayList<AnimModel> results){
        super(context,0, results);
        aContext = context;
        aResults = results;
    }

    public interface EditButtonClickListener {
        void onEditButtonClick(AnimModel selectedItem);
    }

    public void setEditButtonClickListener(EditButtonClickListener listener) {
        this.editButtonClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        AdminAnimAdapter.ViewHolder viewHolder;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            viewHolder = new AdminAnimAdapter.ViewHolder();

            listItemView = inflater.inflate(R.layout.admin_anim_grid_list, parent, false);

            viewHolder.tv_name = listItemView.findViewById(R.id.tv_name_Show);
            viewHolder.tv_age = listItemView.findViewById(R.id.tv_age_Show);
            viewHolder.tv_gender = listItemView.findViewById(R.id.fab_gender);
            viewHolder.tv_damage = listItemView.findViewById(R.id.tv_damage_Show);
            viewHolder.tv_cage = listItemView.findViewById(R.id.tv_cage_Show);
            viewHolder.imageView = listItemView.findViewById(R.id.iv_img);
            viewHolder.imageButton = listItemView.findViewById(R.id.ib_edit);


            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (AdminAnimAdapter.ViewHolder) listItemView.getTag();
        }

        // Set OnClickListener to the edit button
        viewHolder.imageButton.setOnClickListener(v -> {
            if (editButtonClickListener != null) {
                AnimModel selectedItem = aResults.get(position);
                editButtonClickListener.onEditButtonClick(selectedItem);
            }
        });

        // Bind data to your grid item layout here
        AnimModel anim = aResults.get(position);
        int animAge = anim.getAge();

        if (anim.getName() != null) {
            viewHolder.tv_name.setText(anim.getName());
        }
        if (animAge > 0) {
            viewHolder.tv_age.setText(String.valueOf(animAge));
        }
        if (anim.getGender() != null) {
            if (anim.getGender().equals("MALE"))
            {
                viewHolder.tv_gender.setImageResource(R.drawable.baseline_male_24);
                viewHolder.tv_gender.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(aContext, R.color.see_through_blue)));
            }
            else if (anim.getGender().equals("FEMALE")) {
                viewHolder.tv_gender.setImageResource(R.drawable.baseline_female_24);
                viewHolder.tv_gender.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(aContext, R.color.see_through_red)));
            }
        }
        if (anim.getCage() != null) {
            viewHolder.tv_cage.setText(anim.getCage().getName());
        }

        String imageUrl = "https://i.imgur.com/p21dFsJ.jpeg";
        Picasso.get().load(imageUrl).into(viewHolder.imageView);

        return listItemView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_age;
        FloatingActionButton tv_gender;
        TextView tv_damage;
        TextView tv_cage;
        ImageButton imageButton;
        ImageView imageView;
    }
}
