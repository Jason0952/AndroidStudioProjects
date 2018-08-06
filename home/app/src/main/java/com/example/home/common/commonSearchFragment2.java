package com.example.home.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.home.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import library.UserFunctions;


public class commonSearchFragment2 extends Fragment {
    TextView menu_txt,recommend;
    LinearLayout menu_pic;
    ImageView[] mealsimg=new ImageView [6] ;
    ImageView[] menuimg=new ImageView [3] ;
    int[] mealsimgid={R.id.mealsimg1, R.id.mealsimg2, R.id.mealsimg3, R.id.mealsimg4, R.id.mealsimg5, R.id.mealsimg6};
    int[] menuimgid={R.id.menuimg1, R.id.menuimg2, R.id.menuimg3};
    String imgURL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_search_info2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            getrestaurantinformation(commonInfo.json_restaurant);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getrestaurantinformation(JSONObject json_restaurant) throws JSONException {
        imgURL="https://h10271808.000webhostapp.com/img/"+commonInfo.storePosition+"/";
        for(int i=0;i<=5;i++){
            mealsimg[i]=(ImageView) getView().findViewById(mealsimgid[i]);
            try {
                //Log.d("aaa",imgURL+"0"+(i+1)+".jpg");
                URL aryURI = new URL(imgURL+"0"+(i+1)+".jpg");
                URLConnection conn = aryURI.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                Bitmap bm = BitmapFactory.decodeStream(is);
                is.close();
                BitmapDrawable ob = new BitmapDrawable(getResources(),bm);
                mealsimg[i].setBackgroundDrawable(ob);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        menu_txt=(TextView) getView().findViewById(R.id.menu_txt);
        menu_pic=(LinearLayout) getView().findViewById(R.id.menu_pic);

        if(json_restaurant.getString("menu").equals("")) {
            menu_txt.setVisibility(View.INVISIBLE);
            menu_pic.setVisibility(View.VISIBLE);
            for(int i=0;i<=2;i++){
                menuimg[i]=(ImageView) getView().findViewById(menuimgid[i]);
                try {
                    URL aryURI = new URL(imgURL+"v0"+(i+1)+".jpg");
                    URLConnection conn = aryURI.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bm = BitmapFactory.decodeStream(is);
                    is.close();
                    BitmapDrawable ob = new BitmapDrawable(getResources(),bm);
                    menuimg[i].setBackgroundDrawable(ob);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            menu_txt.setVisibility(View.VISIBLE);
            menu_pic.setVisibility(View.INVISIBLE);
            menu_txt.setText(json_restaurant.getString("menu"));
        }
        recommend=(TextView) getView().findViewById(R.id.recommend_txt);
        recommend.setText(json_restaurant.getString("recommend"));
    }


}
