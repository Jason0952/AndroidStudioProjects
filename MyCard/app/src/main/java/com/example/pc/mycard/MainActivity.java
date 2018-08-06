package com.example.pc.mycard;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn1, btn2;

    private List<String> rest;

    ArrayList LoadchooseList = new ArrayList(); //抓取有被使用者勾選的餐廳放入陣列裡

    LinearLayout LY;

    RelativeLayout.LayoutParams RL = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LY = (LinearLayout)findViewById(R.id.LY);

        choosedata();

        btn1 = (Button)findViewById(R.id.choose);
        btn2 = (Button)findViewById(R.id.getready);

        btn1.setOnClickListener(buttonListener);
        btn2.setOnClickListener(buttonListener );

    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.choose:
                    LY.removeAllViews();
                    LoadchooseList.clear();
                    multiDialogEvent();
                    break;
                case R.id.getready:
                    LY.removeAllViews();
                    btn1.setEnabled(false);
                    ran();
                    break;
            }
        }
    };

    public void choosedata(){
        rest = new ArrayList<>();
        rest.add(getString(R.string.choose1));
        rest.add(getString(R.string.choose2));
        rest.add(getString(R.string.choose3));
        rest.add(getString(R.string.choose4));
        rest.add(getString(R.string.choose6));
        rest.add(getString(R.string.choose7));
        rest.add(getString(R.string.choose8));
        rest.add(getString(R.string.choose9));
        rest.add(getString(R.string.choose10));
        rest.add(getString(R.string.choose11));
        rest.add(getString(R.string.choose12));
        rest.add(getString(R.string.choose13));
        rest.add(getString(R.string.choose14));
        rest.add(getString(R.string.choose15));
        rest.add(getString(R.string.choose16));
        rest.add(getString(R.string.choose17));
        rest.add(getString(R.string.choose18));
        rest.add(getString(R.string.choose19));
        rest.add(getString(R.string.choose20));
        rest.add(getString(R.string.choose21));
        rest.add(getString(R.string.choose22));
        rest.add(getString(R.string.choose23));
        rest.add(getString(R.string.choose24));
        rest.add(getString(R.string.choose25));
        rest.add(getString(R.string.choose26));
        rest.add(getString(R.string.choose27));
        rest.add(getString(R.string.choose28));
        rest.add(getString(R.string.choose29));
        rest.add(getString(R.string.choose30));

    }

    //讓系統能動態加入Imagebutton
    public  void addimagebtn(){
        RL.width = 100;
        RL.height = 100;
        RL.leftMargin = 10;
        for (int i = 0;i < LoadchooseList.size();i++){
            ImageButton choosebtn = new ImageButton(this); //在這個object裡面會自動create imagebutton(多個)
            choosebtn.setLayoutParams(RL);
            choosebtn.setImageResource(R.drawable.cardback);
            choosebtn.setId(i);
            LY.addView(choosebtn); //將choosebtn放到View上
        }
    }

    public void ran(){
        ArrayList NR = new ArrayList(); //存放亂數排序完的LoadchooseList
        ArrayList tempList = new ArrayList(LoadchooseList);
        RL.width = 100;
        RL.height = 100;
        RL.leftMargin = 10;
        while (tempList.size() > 0) {
            int i = (int) (Math.random() * tempList.size()); //讓LoadchooseList裡面的選項亂數排序
            NR.add(tempList.get(i)); //將亂數排序LoadchooseList裡面的值放進NR裡面
            tempList.remove(i); //清除tempList裡的資料
        }
        for (int j = 0;j < NR.size();j++){
            ArrayList<ImageButton> BtnList = new ArrayList<ImageButton>();
            ImageButton randombtn = new ImageButton(this); //在這個object裡面會自動create imagebutton(多個)
            if (j != 0 && (j+1)%3 == 0){
                //每5個則自動換行
                System.out.print("\n");
                LinearLayout ly = new LinearLayout(this);
                ly.setOrientation(LinearLayout.HORIZONTAL);

                for (ImageButton addIMGBTN:BtnList){
                    ly.addView(addIMGBTN);
                }

                LY.addView(ly);
                BtnList.clear();
            }
            randombtn.setLayoutParams(RL);
            randombtn.setImageResource(R.drawable.cardback);
            randombtn.setOnClickListener(clickHandler); //觸發imagebutton之後的事件
            randombtn.setTag(j); //添加額外的View(不會一直創新的View)
            randombtn.setId(j);
            BtnList.add(randombtn);
            LY.addView(randombtn); //將choosebtn放到View上
        }
    }

    public void multiDialogEvent(){
        final List<Boolean> checkedStatusList = new ArrayList<>();
        for(String s : rest/*取出所有rest陣列裡的值*/){
            checkedStatusList.add(false); //預設所有值皆為"取消選擇"
        }

        new AlertDialog.Builder(MainActivity.this).setMultiChoiceItems(rest.toArray(new String[rest.size()]), new boolean[rest.size()], new DialogInterface.OnMultiChoiceClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which, boolean ischecked) {
                checkedStatusList.set(which, ischecked);
            }
        }).setPositiveButton(R.string.ok /*按下確認觸發後面OnClickListener事件*/, new DialogInterface.OnClickListener() {
            //判斷使用者選擇的項目.並且一次性的傳送
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isEmpty = true; //一開始的選項皆"不是打勾"
                for (int i = 0;i < checkedStatusList.size();i++){
                        if (checkedStatusList.get(i)/*多選*/){
                            isEmpty = false; //是否有打勾 fasle = 有打勾
                            //放入抓資料庫的程式碼
                            //LoadchooseList [0] = 資料庫.getstring(資料數量);
                            LoadchooseList.add(rest.get(i));//從餐廳資料裡抓取已經被勾選的項目(i)，並且放在新的空陣列裡(從0開始排序)
                        }
                }

                addimagebtn();

                //判斷是否有選擇項目
                if (!isEmpty/*isEmpty不等於true*/){
                    Toast.makeText(MainActivity.this, "加入選擇成功", Toast.LENGTH_SHORT).show();
                    //重新取出所有值並讓值皆為"取消選擇"(回到原本的狀態)
                    for (String s : rest){
                        checkedStatusList.add(false);
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "請勾選項目", Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
    }

    @Override
    public void onClick(View view) {

    }

    private View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}
