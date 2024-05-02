package com.example.fantazoo_app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantazoo_app.Models.ZKModel;
import com.example.fantazoo_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ZKAdapter extends ArrayAdapter<ZKModel> {
    private Context zkContext;
    private ArrayList<ZKModel> zkResults;

    public  ZKAdapter( Context context, ArrayList<ZKModel> results){
        super(context,0, results);
        zkContext = context;
        zkResults = results;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        ViewHolder viewHolder;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            viewHolder = new ViewHolder();

            listItemView = inflater.inflate(R.layout.zk_grid_item, parent, false);

            viewHolder.tv_name = listItemView.findViewById(R.id.tv_name_Show);
            viewHolder.tv_weapon = listItemView.findViewById(R.id.tv_weapon_Show);
            viewHolder.tv_damage = listItemView.findViewById(R.id.tv_damage_Show);
            viewHolder.tv_cage = listItemView.findViewById(R.id.tv_cage_Show);
            viewHolder.imageView = listItemView.findViewById(R.id.iv_img);

            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        // Bind data to your grid item layout here
        ZKModel zkm = zkResults.get(position);

        if (zkm.getName() != null) {
            viewHolder.tv_name.setText(zkm.getName());
        }
        if (zkm.getWeapon() != null) {
            viewHolder.tv_weapon.setText(zkm.getWeapon().getName());
        }
        if (zkm.getWeapon() != null) {
            viewHolder.tv_damage.setText(zkm.getWeapon().getDamage());
        }
        if (zkm.getCage() != null) {
            viewHolder.tv_cage.setText(zkm.getCage().getName());
        }

        String imageUrl = "https://i.imgur.com/TcoSw9a.jpeg";
        Picasso.get().load(imageUrl).into(viewHolder.imageView);

        return listItemView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_weapon;
        TextView tv_damage;
        TextView tv_cage;
        ImageView imageView;
    }
}

//Load backdrop image using Picasso
//        if (zkm.getBackdrop_path() != null) {
//            String imageUrl = "https://image.tmdb.org/t/p/original/" + result.getPoster_path();
//            Picasso.get().load(imageUrl).into(viewHolder.imageView);
//        }


//        viewHolder.imageView.setOnClickListener(v -> {
//            // Pass the selected movie directly to DetailedMovieFragment.newInstance()
//            DetailedMovieFragment fragment = DetailedMovieFragment.newInstance(result);
//
//            // Get the fragment manager and start the transaction
//            FragmentManager fragmentManager = ((AppCompatActivity) this.mContext).getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, fragment)
//                    .addToBackStack("name")
//                    .commit();
//        });
