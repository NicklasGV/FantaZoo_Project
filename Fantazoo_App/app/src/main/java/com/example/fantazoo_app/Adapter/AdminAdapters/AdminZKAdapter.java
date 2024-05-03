package com.example.fantazoo_app.Adapter.AdminAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantazoo_app.Models.ZKModel;
import com.example.fantazoo_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminZKAdapter extends ArrayAdapter<ZKModel> {
    private Context aContext;
    private ArrayList<ZKModel> zkResults;

    private AdminZKAdapter.EditButtonClickListener editButtonClickListener;
    private AdminZKAdapter.DeleteButtonClickListener deleteButtonClickListener;

    public AdminZKAdapter(Context context, ArrayList<ZKModel> results) {
        super(context, 0, results);
        aContext = context;
        zkResults = results;
    }

    // Interface for handling delete button clicks
    public interface DeleteButtonClickListener {
        void onDeleteButtonClick(ZKModel selectedItem);
    }

    // Setter for delete button click listener
    public void setDeleteButtonClickListener(AdminZKAdapter.DeleteButtonClickListener listener) {
        this.deleteButtonClickListener = listener;
    }

    // Interface for handling edit button clicks
    public interface EditButtonClickListener {
        void onEditButtonClick(ZKModel selectedItem);
    }

    // Setter for edit button click listener
    public void setEditButtonClickListener(AdminZKAdapter.EditButtonClickListener listener) {
        this.editButtonClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        AdminZKAdapter.ViewHolder viewHolder;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            viewHolder = new AdminZKAdapter.ViewHolder();

            // Inflate the layout for the list item
            listItemView = inflater.inflate(R.layout.admin_keeper_grid_list, parent, false);

            // Initialize views
            viewHolder.imageView = listItemView.findViewById(R.id.iv_img);
            viewHolder.tv_name = listItemView.findViewById(R.id.tv_name_Show);
            viewHolder.tv_zk = listItemView.findViewById(R.id.tv_zk_Show);
            viewHolder.tv_cage = listItemView.findViewById(R.id.tv_cage_Show);
            viewHolder.imageView = listItemView.findViewById(R.id.iv_img);
            viewHolder.imageButton_edit = listItemView.findViewById(R.id.ib_edit);
            viewHolder.imageButton_delete = listItemView.findViewById(R.id.ib_delete);

            // Set tag for ViewHolder to reuse views
            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (AdminZKAdapter.ViewHolder) listItemView.getTag();
        }

        // Set OnClickListener to the edit button
        viewHolder.imageButton_edit.setOnClickListener(v -> {
            if (editButtonClickListener != null) {
                ZKModel selectedItem = zkResults.get(position);
                editButtonClickListener.onEditButtonClick(selectedItem);
            }
        });

        // Set OnClickListener to the delete button
        viewHolder.imageButton_delete.setOnClickListener(v -> {
            if (deleteButtonClickListener != null) {
                ZKModel selectedItem = zkResults.get(position);
                deleteButtonClickListener.onDeleteButtonClick(selectedItem);
            }
        });

        // Bind data to views
        ZKModel zk = zkResults.get(position);

        if (zk.getName() != null) {
            viewHolder.tv_name.setText(zk.getName());
        }

        if (zk.getCage() != null && zk.getCage().getName() != null) {
            viewHolder.tv_cage.setText(zk.getCage().getName());
        }
        else {
            viewHolder.tv_cage.setText("Not assigned");
        }

        if (zk.getWeapon() != null && zk.getWeapon().getName() != null) {
            viewHolder.tv_cage.setText(zk.getWeapon().getName());
        }
        else {
            viewHolder.tv_cage.setText("No Weapon");
        }

        // Load image using Picasso library
        String imageUrl = "https://i.imgur.com/TcoSw9a.jpeg";
        Picasso.get().load(imageUrl).into(viewHolder.imageView);

        return listItemView;
    }

    // ViewHolder pattern for efficient view reuse
    static class ViewHolder {
        TextView tv_name;
        TextView tv_zk;
        TextView tv_cage;
        ImageButton imageButton_edit;
        ImageButton imageButton_delete;
        ImageView imageView;
    }
}
