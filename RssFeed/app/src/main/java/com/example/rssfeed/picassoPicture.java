package com.example.rssfeed;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class picassoPicture {

    public static void downloadImage(Context c, String ImageUrl, ImageView img)
    {
        if(ImageUrl != null && ImageUrl.length() >0)
        {
            Picasso.with(c).load(ImageUrl).placeholder(R.mipmap.ic_launcher).into(img);
        }else {
            Picasso.with(c).load(R.mipmap.ic_launcher).into(img);
        }
    }
}