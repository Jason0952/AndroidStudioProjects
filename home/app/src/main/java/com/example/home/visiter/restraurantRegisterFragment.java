package com.example.home.visiter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.home.R;
import com.example.home.common.commonInfo;

import org.json.JSONException;
import org.json.JSONObject;

import library.UserFunctions;

public class restraurantRegisterFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener

{
    Button restaurantsignin;
    EditText enterrestaurantname, enterrestaurantaccount, enterrestaurantpassword, entermakesurerestaurantpassword, entertelephone, enteraddress, enterrestaurantemail;
    Spinner areaselect, regionselect, categoryselect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.visiter_restaurant_register, null);

        restaurantsignin = (Button) view.findViewById(R.id.restaurantsignin);

        enterrestaurantname = (EditText) view.findViewById(R.id.enterrestaurantname);
        enterrestaurantaccount = (EditText) view.findViewById(R.id.enterrestaurantaccount);
        enterrestaurantpassword = (EditText) view.findViewById(R.id.enterrestaurantpassword);
        entermakesurerestaurantpassword = (EditText) view.findViewById(R.id.entermakesurerestaurantpassword);
        entertelephone = (EditText) view.findViewById(R.id.entertelephone);
        enteraddress = (EditText) view.findViewById(R.id.enteraddress);
        enterrestaurantemail = (EditText) view.findViewById(R.id.enterrestaurantemail);

        areaselect = (Spinner) view.findViewById(R.id.areaselect);
        regionselect = (Spinner) view.findViewById(R.id.regionselect);
        categoryselect = (Spinner) view.findViewById(R.id.categoryselect);

        areaselect.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, commonInfo.areaitems);
        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        areaselect.setAdapter(aa);

        categoryselect.setOnItemSelectedListener(this);
        ArrayAdapter<String> bb = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, commonInfo.categoryitems);
        bb.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        categoryselect.setAdapter(bb);
        restaurantsignin.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.restaurantsignin) {
            if (enterrestaurantpassword.getText().toString().equals(entermakesurerestaurantpassword.getText().toString())) {
                String restaurantname = enterrestaurantname.getText().toString();
                String account = enterrestaurantaccount.getText().toString();
                String password = enterrestaurantpassword.getText().toString();
                String telephone = entertelephone.getText().toString();
                String area = areaselect.getSelectedItem().toString();
                String address = regionselect.getSelectedItem().toString();
                String category = categoryselect.getSelectedItem().toString();
                String email = enterrestaurantemail.getText().toString();

                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.registerUser(restaurantname, account, password,"2");
                JSONObject jsonRestaurant = userFunction.registerRestaurant(restaurantname, account, password, telephone, area, address, category, email);
                try {
                    if (json.getString(commonInfo.KEY_SUCCESS) != null && jsonRestaurant.getString(commonInfo.KEY_SUCCESS) != null) {
                        String res = json.getString(commonInfo.KEY_SUCCESS);
                        String resRestaurant = jsonRestaurant.getString(commonInfo.KEY_SUCCESS);
                        if (Integer.parseInt(res) == 1 && Integer.parseInt(resRestaurant) == 1) {
                            Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "兩次密碼輸入不同,請重新輸入", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if(parent.getId()==R.id.areaselect) {
            if (position == 0) {
                regionselect.setOnItemSelectedListener(this);
                ArrayAdapter<String> aa = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_spinner_item, commonInfo.taipeiitems);
                aa.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                regionselect.setAdapter(aa);
            }
            else {
                regionselect.setOnItemSelectedListener(this);
                ArrayAdapter<String> aa = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_spinner_item, commonInfo.newtaipeiitems);
                aa.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                regionselect.setAdapter(aa);

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
