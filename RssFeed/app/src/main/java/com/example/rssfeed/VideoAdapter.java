package com.example.rssfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends ArrayAdapter<Video> {
    private final Context context;
    private final ArrayList<Video> values;

    public VideoAdapter(@NonNull Context context, ArrayList<Video> list) {
        super(context, R.layout.row_layout,list);
        this.context = context;
        this.values = list;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // to make the connexion with the row_layout :

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView  = inflater.inflate(R.layout.row_layout,parent,false);

        TextView tvTitleVideo = (TextView) rowView.findViewById(R.id.tvTitleVideo);
        TextView tvDescripVideo = (TextView) rowView.findViewById(R.id.tvDescripVideo);
        TextView tvDatePublished = (TextView) rowView.findViewById(R.id.tvDatePublished);

        ImageView ivVideo = (ImageView) rowView.findViewById(R.id.ivVideo);

        tvTitleVideo.setText("Title : " + values.get(position).getTitle());
        tvDescripVideo.setText("Description : " + values.get(position).getDescription());
        tvDatePublished.setText("Published on : " + values.get(position).getDatePublished());

        // for the picture :
        String imageUrl = values.get(position).getPicture();
        picassoPicture.downloadImage(context,imageUrl,ivVideo);

        return rowView;
    }

}
