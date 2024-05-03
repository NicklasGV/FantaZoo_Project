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

import com.example.fantazoo_app.Models.CageModel;
import com.example.fantazoo_app.Models.WeapModel;
import com.example.fantazoo_app.R;

import java.util.ArrayList;

public class AdminWeaponAdapter extends ArrayAdapter<WeapModel> {

    private Context aContext;
    private ArrayList<WeapModel> cResults;

    private AdminWeaponAdapter.EditButtonClickListener editButtonClickListener;
    private AdminWeaponAdapter.DeleteButtonClickListener deleteButtonClickListener;

    public AdminWeaponAdapter(Context context, ArrayList<WeapModel> results) {
        super(context, 0, results);
        aContext = context;
        cResults = results;
    }

    // Interface for handling delete button clicks
    public interface DeleteButtonClickListener {
        void onDeleteButtonClick(WeapModel selectedItem);
    }

    // Setter for delete button click listener
    public void setDeleteButtonClickListener(AdminWeaponAdapter.DeleteButtonClickListener listener) {
        this.deleteButtonClickListener = listener;
    }

    // Interface for handling edit button clicks
    public interface EditButtonClickListener {
        void onEditButtonClick(WeapModel selectedItem);
    }

    // Setter for edit button click listener
    public void setEditButtonClickListener(AdminWeaponAdapter.EditButtonClickListener listener) {
        this.editButtonClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        AdminWeaponAdapter.ViewHolder viewHolder;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            viewHolder = new AdminWeaponAdapter.ViewHolder();

            // Inflate the layout for the list item
            listItemView = inflater.inflate(R.layout.admin_weapon_grid_list, parent, false);

            // Initialize views
            viewHolder.tv_name = listItemView.findViewById(R.id.tv_name_Show);
            viewHolder.tv_damage = listItemView.findViewById(R.id.tv_damage_Show);
            viewHolder.tv_zk = listItemView.findViewById(R.id.tv_zk_Show);
            viewHolder.imageButton_edit = listItemView.findViewById(R.id.ib_edit);
            viewHolder.imageButton_delete = listItemView.findViewById(R.id.ib_delete);

            // Set tag for ViewHolder to reuse views
            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (AdminWeaponAdapter.ViewHolder) listItemView.getTag();
        }

        // Set OnClickListener to the edit button
        viewHolder.imageButton_edit.setOnClickListener(v -> {
            if (editButtonClickListener != null) {
                WeapModel selectedItem = cResults.get(position);
                editButtonClickListener.onEditButtonClick(selectedItem);
            }
        });

        // Set OnClickListener to the delete button
        viewHolder.imageButton_delete.setOnClickListener(v -> {
            if (deleteButtonClickListener != null) {
                WeapModel selectedItem = cResults.get(position);
                deleteButtonClickListener.onDeleteButtonClick(selectedItem);
            }
        });

        // Bind data to views
        WeapModel weapon = cResults.get(position);


        if (weapon.getName() != null) {
            viewHolder.tv_name.setText(weapon.getName());
        }

        if (weapon.getZookeepers() != null)
        {
            int animCount = weapon.getZookeepers().size();
            if (animCount != 0){
                viewHolder.tv_zk.setText(String.valueOf(animCount));
            }
        }
        else {
            viewHolder.tv_zk.setText("0");
        }

        if (weapon.getDamage() != null)
        {
            viewHolder.tv_damage.setText(weapon.getDamage());
        }

        return listItemView;
    }

    // ViewHolder pattern for efficient view reuse
    static class ViewHolder {
        TextView tv_name;
        TextView tv_zk;
        TextView tv_damage;
        ImageButton imageButton_edit;
        ImageButton imageButton_delete;
    }
}
