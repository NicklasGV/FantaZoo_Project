package com.example.fantazoo_app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantazoo_app.Models.CageModel;
import com.example.fantazoo_app.Models.ZKModel;
import com.example.fantazoo_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class CageAdapter extends ArrayAdapter<CageModel> {
    private Context cContext;
    private ArrayList<CageModel> cResults;

    public  CageAdapter( Context context, ArrayList<CageModel> results){
        super(context,0, results);
        cContext = context;
        cResults = results;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        CageAdapter.ViewHolder viewHolder;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            viewHolder = new ViewHolder();

            listItemView = inflater.inflate(R.layout.cage_grid_list, parent, false);

            viewHolder.tv_name = listItemView.findViewById(R.id.tv_name);
            viewHolder.tv_zk = listItemView.findViewById(R.id.tv_zk_Show);
            viewHolder.tv_anim = listItemView.findViewById(R.id.tv_anim_Show);

            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (CageAdapter.ViewHolder) listItemView.getTag();
        }

        // Bind data to your grid item layout here
        CageModel cage = cResults.get(position);

        if (cage.getName() != null) {
            viewHolder.tv_name.setText(cage.getName());
        }
        if (cage.getZookeepers() != null) {
            int zkCount = cage.getZookeepers().size();
            viewHolder.tv_zk.setText(String.valueOf(zkCount));
        }
        if (cage.getAnimals() != null) {
            int animCount = cage.getAnimals().size();
            viewHolder.tv_anim.setText(String.valueOf(animCount));
        }

        return listItemView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_zk;
        TextView tv_anim;

        ImageView imageView;
    }
}
