/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.example.home.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class commonSearchViewAdapter extends RecyclerView
        .Adapter<commonSearchViewAdapter
        .DataObjectHolder> {
    private ArrayList<commonSearchDataObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        ImageView searchrestaurantimage;
        TextView searchrestaurantname, searchrestaurantarea, searchrestaurantcate, searchrestauranttime;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            searchrestaurantimage = (ImageView) itemView.findViewById(R.id.searchrestaurantimage);
            searchrestaurantname = (TextView) itemView.findViewById(R.id.searchrestaurantname);
            searchrestaurantarea = (TextView) itemView.findViewById(R.id.searchrestaurantarea);
            searchrestaurantcate = (TextView) itemView.findViewById(R.id.searchrestaurantcate);
            searchrestauranttime = (TextView) itemView.findViewById(R.id.searchrestauranttime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public commonSearchViewAdapter(ArrayList<commonSearchDataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.common_search_card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        try {
            URL aryURI = new URL(mDataset.get(position).getmImageUrl());
            URLConnection conn = aryURI.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bm = BitmapFactory.decodeStream(is);
            is.close();
            holder.searchrestaurantimage.setImageBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.searchrestaurantname.setText(mDataset.get(position).getmText1());
        holder.searchrestaurantarea.setText(mDataset.get(position).getmText2());
        holder.searchrestaurantcate.setText(mDataset.get(position).getmText3());
        holder.searchrestauranttime.setText(mDataset.get(position).getmText4());
    }

    public void addItem(commonSearchDataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}