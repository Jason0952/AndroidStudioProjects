package com.example.home.visiter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.home.R;
import com.example.home.common.commonInfo;

import library.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class memberRegisterFragment extends Fragment implements  DatePickerDialog.OnDateSetListener, View.OnClickListener {
    Button enterbirthday,membersignin;
    EditText entermembername,enternickname,entermemberaccout,entermemberpassword,entermakesurememberpassword,entermemberphone,entermemberemail;
    Calendar c = Calendar.getInstance();
    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.visiter_membe_register, null);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        enterbirthday=(Button) view.findViewById(R.id.enterbirthday);
        membersignin=(Button) view.findViewById(R.id.membersignin);

        entermembername=(EditText) view.findViewById(R.id.entermembername);
        enternickname=(EditText) view.findViewById(R.id.enternickname);
        entermemberpassword=(EditText) view.findViewById(R.id.entermemberpassword);
        entermemberaccout=(EditText) view.findViewById(R.id.entermemberaccout);
        entermakesurememberpassword=(EditText) view.findViewById(R.id.entermakesurememberpassword);
        entermemberphone=(EditText) view.findViewById(R.id.entermemberphone);
        entermemberemail=(EditText) view.findViewById(R.id.entermemberemail);

        enterbirthday.setOnClickListener(this);
        membersignin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDateSet(DatePicker v, int y, int m, int d)  {
        enterbirthday.setText(y + "/" + (m+1) + "/" + d); // 將選定日期顯示在螢幕上
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.membersignin){
            if(entermemberpassword.getText().toString().equals(entermakesurememberpassword.getText().toString())){
                String name = entermembername.getText().toString();
                String nickname = enternickname.getText().toString();
                String account = entermemberaccout.getText().toString();
                String password = entermemberpassword.getText().toString();
                String birthday = enterbirthday.getText().toString();
                String telephone = entermemberphone.getText().toString();
                String email = entermemberemail.getText().toString();

                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.registerUser(name, account, password, "1");
                JSONObject jsonMember = userFunction.registerMember(name, nickname, account, password, birthday, telephone, email);
                try {
                    if (json.getString(commonInfo.KEY_SUCCESS) != null && jsonMember.getString(commonInfo.KEY_SUCCESS) != null ) {
                        String res = json.getString(commonInfo.KEY_SUCCESS);
                        String resMember = jsonMember.getString(commonInfo.KEY_SUCCESS);
                        if (Integer.parseInt(res) == 1 && Integer.parseInt(resMember) == 1) {
                            Toast.makeText(getActivity(),"success",Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getActivity(),"兩次密碼輸入不同,請重新輸入",Toast.LENGTH_LONG).show();
            }
        }else {
            new DatePickerDialog(getActivity(), this, // 由活動物件監聽事件
                    c.get(Calendar.YEAR),  //由Calendar物件取得目前的西元年
                    c.get(Calendar.MONTH),        //取得目前月 (由 0 算起)
                    c.get(Calendar.DAY_OF_MONTH)) //取得目前日
                       .show();  //顯示出來
        }
    }
}

