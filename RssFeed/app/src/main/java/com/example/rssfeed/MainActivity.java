package com.example.rssfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv_rss;
    Toolbar tb_fix;

    ArrayList<Video> videos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_rss = (ListView) findViewById(R.id.lv_rss);






        lv_rss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // When ever we click on an item, we will go the link specefied :
                Uri uri = Uri.parse(videos.get(i).getLink());
                // an implicit intent to start the website :
                Intent in = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(in);
            }
        });

        // Now let's Call our function ProcessInBackground :
        new ProcessInBackground().execute();

    }

    // Now we are going into the background and a new thread
    // to use the ASYNC TASK to start reading from that document

    // the middle parameter used for example to a progresBar :
    public class ProcessInBackground extends AsyncTask<Integer,Void,Exception>{

        // progressBar
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        // make an exception :
        Exception exception = null;

        // this method is before we going to the backGround  :
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Busy Loading rss feed ... Please WAIT. ");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {


            try{
                // let's start :
                URL url = new URL("https://www.youtube.com/feeds/videos.xml?channel_id=UC95bEkaIgwhxSjSsdMFXYGg");
                // XmlpullParser will help us to retrieve the data from XML :
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                // this just specifies the parts that are produced by this factory to provide support for XML name :
                factory.setNamespaceAware(false);
                // so we are gonna use the factory instance to create an XmlPullParser
                XmlPullParser xpp = factory.newPullParser();
                // we use xpp to read from the inputStream that we Created by the url passed ;
                xpp.setInput(url.openConnection().getInputStream(),"UTF_8");
                // variable to tell me if i m inside aan entry :
                boolean insideEntry = false;
                // return the type of the curent event : START_TAG, END_TAG, START_DOCUMENT
                int evenType = xpp.getEventType();

                Video video = new Video();

                while(evenType != XmlPullParser.END_DOCUMENT)
                {
                    if(evenType == XmlPullParser.START_TAG)
                    {
                        if(xpp.getName().equalsIgnoreCase("entry"))
                        {
                            video = new Video();
                            insideEntry = true;
                        }
                        else if(xpp.getName().equalsIgnoreCase("title")){
                            if(insideEntry)
                            {
                                video.setTitle(xpp.nextText());
                            }
                        } else if(xpp.getName().equalsIgnoreCase("media:description"))
                        {
                            if(insideEntry)
                            {
                                video.setDescription(xpp.nextText().substring(1,20));
                            }
                        } else if(xpp.getName().equalsIgnoreCase("published")){
                            if(insideEntry)
                            {
                                video.setDatePublished(xpp.nextText());
                            }

                        } else if(xpp.getName().equalsIgnoreCase("link"))
                        {
                            if(insideEntry)
                            {
                                String link = xpp.getAttributeValue(null,"href");
                                video.setLink(link);
                            }
                        } else if(xpp.getName().equalsIgnoreCase("media:thumbnail"))
                        {
                            if(insideEntry)
                            {
                                String imageUrl = xpp.getAttributeValue(null,"url");
                                video.setPicture(imageUrl);
                            }
                        }
                    }
                    else if(evenType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("entry"))
                    {
                        videos.add(video);
                        insideEntry =false;
                    }
                    evenType = xpp.next();
                }




            }catch (MalformedURLException e ) {
                    exception = e;
            }catch(XmlPullParserException e)
            {
                exception = e;
            }catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            lv_rss.setAdapter(new VideoAdapter(MainActivity.this,videos));

            // we have to dismiss the progress dialog :
            progressDialog.dismiss();
        }
    }

}