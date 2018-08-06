package com.example.home.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.home.R;

import org.json.JSONException;
import org.json.JSONObject;

import library.UserFunctions;

public class commonSearchFragment1 extends Fragment {
    TextView restaurantname, category,  area, address, transport, telephone, time, sit, remarks, about, recommend;

    View viewlayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_search_info1, container, false);
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
        //TextView menu, recommend;
        category=(TextView) getView().findViewById(R.id.category_txt);
        area=(TextView) getView().findViewById(R.id.area_txt);
        address=(TextView) getView().findViewById(R.id.address_txt);
        transport=(TextView) getView().findViewById(R.id.transport_txt);
        telephone=(TextView) getView().findViewById(R.id.telephone_txt);
        time=(TextView) getView().findViewById(R.id.time_txt);
        sit=(TextView) getView().findViewById(R.id.sit_txt);
        remarks=(TextView) getView().findViewById(R.id.remarks_txt);
        about=(TextView) getView().findViewById(R.id.about_txt);

        category.setText(json_restaurant.getString("category"));
        area.setText(json_restaurant.getString("area"));
        address.setText(json_restaurant.getString("address"));
        transport.setText(json_restaurant.getString("transport"));
        telephone.setText(json_restaurant.getString("telephone"));
        time.setText(json_restaurant.getString("time"));
        sit.setText(json_restaurant.getString("sit"));
        remarks.setText(json_restaurant.getString("remarks"));
        about.setText(json_restaurant.getString("about"));
    }
}
