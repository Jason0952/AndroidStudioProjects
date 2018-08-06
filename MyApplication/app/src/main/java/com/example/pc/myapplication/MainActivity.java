package com.example.pc.myapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SEND_SMS;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @OnClick({R.id.btnSendCode, R.id.btnVerify})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnSendCode:
                    EditText txtPhoneNumber = (EditText)findViewById(R.id.txtPhoneNumber)
                    if (!TextUtils.isEmpty(txtPhoneNumber.getText().toString())){
                        int permission = ActivityCompat.checkSelfPermission(this, SEND_SMS);
                        if (permission != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(
                                    this,
                                    new String[]{READ_SMS, SEND_SMS,},
                                    REQUEST_SMS
                            );
                        }else{
                            sendSMS();
                        }
                    }
                    break;
            }
        }
    }

    private void sendSMS() {
        EditText txtPhoneNumber = (EditText)findViewById(R.id.txtPhoneNumber);
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(), 0);
        smsManager.sendTextMessage(
                txtPhoneNumber.getText().toString(),
                null,
                "456799",
                pendingIntent,
                null
        );
    }
}
