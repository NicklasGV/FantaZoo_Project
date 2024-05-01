package com.example.fantazoo_app.Adapter;

import android.content.Context;
import android.content.res.Configuration;
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

import java.util.ArrayList;

public class ZKAdapter extends ArrayAdapter<ZKModel> {
    private Context mContext;
    private ArrayList<ZKModel> mResults;

    public  ZKAdapter( Context context, ArrayList<ZKModel> results){
        super(context,0, results);
        mContext = context;
        mResults = results;
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

            viewHolder.textView = listItemView.findViewById(R.id.tv_name);
            // viewHolder.imageView = listItemView.findViewById(R.id.img_card);

            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        // Bind data to your grid item layout here
        ZKModel zkm = mResults.get(position);

        if (zkm.getName() != null) {
            viewHolder.textView.setText(zkm.getName());
        }
        // Load backdrop image using Picasso
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


        return listItemView;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
