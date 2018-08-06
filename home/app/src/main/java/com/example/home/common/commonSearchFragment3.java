package com.example.home.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home.R;

import org.json.JSONException;

import library.UserFunctions;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class commonSearchFragment3 extends Fragment {
    View mainView;
    Button addMessage;
    EditText entermember_message;
    Button message_verify_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.common_search_info3, container, false);
        return mainView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addMessage=(Button) getView().findViewById(R.id.addMessage_btn);
        addMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addMessageView=inflater.inflate(R.layout.common_add_message, null, false);
                ViewGroup rootView=(ViewGroup)getView();
                rootView.removeAllViews();
                rootView.addView(addMessageView);

                entermember_message = (EditText) addMessageView.findViewById(R.id.entermember_message);
                message_verify_btn = (Button) addMessageView.findViewById(R.id.message_verify_btn);
                message_verify_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String member_message = entermember_message.getText().toString();

                        UserFunctions userFunction = new UserFunctions();
                        userFunction.addMessage(member_message);
                        Toast.makeText(getActivity(), "留言成功", Toast.LENGTH_SHORT).show();
                        //showContent1(R.id.memberSearchDetail);
                    }
                });


            }
        });
    }
}
