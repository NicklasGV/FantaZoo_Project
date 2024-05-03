package com.example.fantazoo_app.Adapter.AdminAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fantazoo_app.Models.AnimModel;
import com.example.fantazoo_app.Models.CageModel;
import com.example.fantazoo_app.R;

import java.util.ArrayList;

public class AdminCageAdapter extends ArrayAdapter<CageModel> {

    private Context aContext;
    private ArrayList<CageModel> cResults;

    private AdminCageAdapter.EditButtonClickListener editButtonClickListener;
    private AdminCageAdapter.DeleteButtonClickListener deleteButtonClickListener;

    public AdminCageAdapter(Context context, ArrayList<CageModel> results) {
        super(context, 0, results);
        aContext = context;
        cResults = results;
    }

    // Interface for handling delete button clicks
    public interface DeleteButtonClickListener {
        void onDeleteButtonClick(CageModel selectedItem);
    }

    // Setter for delete button click listener
    public void setDeleteButtonClickListener(AdminCageAdapter.DeleteButtonClickListener listener) {
        this.deleteButtonClickListener = listener;
    }

    // Interface for handling edit button clicks
    public interface EditButtonClickListener {
        void onEditButtonClick(CageModel selectedItem);
    }

    // Setter for edit button click listener
    public void setEditButtonClickListener(AdminCageAdapter.EditButtonClickListener listener) {
        this.editButtonClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        AdminCageAdapter.ViewHolder viewHolder;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            viewHolder = new AdminCageAdapter.ViewHolder();

            // Inflate the layout for the list item
            listItemView = inflater.inflate(R.layout.admin_cage_grid_list, parent, false);

            // Initialize views
            viewHolder.tv_name = listItemView.findViewById(R.id.tv_name_Show);
            viewHolder.tv_anim = listItemView.findViewById(R.id.tv_anim_Show);
            viewHolder.tv_zk = listItemView.findViewById(R.id.tv_zk_Show);
            viewHolder.imageButton_edit = listItemView.findViewById(R.id.ib_edit);
            viewHolder.imageButton_delete = listItemView.findViewById(R.id.ib_delete);

            // Set tag for ViewHolder to reuse views
            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (AdminCageAdapter.ViewHolder) listItemView.getTag();
        }

        // Set OnClickListener to the edit button
        viewHolder.imageButton_edit.setOnClickListener(v -> {
            if (editButtonClickListener != null) {
                CageModel selectedItem = cResults.get(position);
                editButtonClickListener.onEditButtonClick(selectedItem);
            }
        });

        // Set OnClickListener to the delete button
        viewHolder.imageButton_delete.setOnClickListener(v -> {
            if (deleteButtonClickListener != null) {
                CageModel selectedItem = cResults.get(position);
                deleteButtonClickListener.onDeleteButtonClick(selectedItem);
            }
        });

        // Bind data to views
        CageModel cage = cResults.get(position);


        if (cage.getName() != null) {
            viewHolder.tv_name.setText(cage.getName());
        }

        if (cage.getAnimals() != null)
        {
            int animCount = cage.getAnimals().size();
            if (animCount != 0){
                viewHolder.tv_anim.setText(String.valueOf(animCount));
            }
        }
        else {
            viewHolder.tv_anim.setText("0");
        }

        if (cage.getZookeepers() != null)
        {
            int zkCount = cage.getZookeepers().size();
            if (zkCount != 0)
            {
                viewHolder.tv_zk.setText(String.valueOf(zkCount));
            }
        }
        else {
            viewHolder.tv_zk.setText("0");
        }

        return listItemView;
    }

    // ViewHolder pattern for efficient view reuse
    static class ViewHolder {
        TextView tv_name;
        TextView tv_zk;
        TextView tv_anim;
        ImageButton imageButton_edit;
        ImageButton imageButton_delete;
    }
}
