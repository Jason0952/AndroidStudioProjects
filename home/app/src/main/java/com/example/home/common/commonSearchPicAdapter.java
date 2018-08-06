package com.example.home.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.home.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class commonSearchPicAdapter extends PagerAdapter {
    Context c;
    String imgURL;

    public commonSearchPicAdapter(Context c) {
        this.c=c;
    }


    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        ImageView im=new ImageView(c);
        imgURL="https://h10271808.000webhostapp.com/img/"+commonInfo.storePosition+"/";
            try {
                URL aryURI = new URL(imgURL+"t0"+(position+1)+".jpg");
                URLConnection conn = aryURI.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                Bitmap bm = BitmapFactory.decodeStream(is);
                is.close();
                im.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        container.addView(im);
        return im;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object o){
        //container.removeViewAt(position);
    }
}
