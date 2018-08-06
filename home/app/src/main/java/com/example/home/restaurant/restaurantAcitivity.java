package com.example.home.restaurant;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.home.R;
import com.example.home.common.commonInfo;
import com.example.home.visiter.visiterAcitivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import library.UserFunctions;
import com.example.home.common.ImageProcessClass;

import org.json.JSONObject;

public class restaurantAcitivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, View.OnClickListener, View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {

    Button restaurant, restaurantIntro, restaurantPromotions, restaurantMessage, restaurantSetting, restaurantDelete, restaurantSignout;
    Button addPromotions_btn, addButton, release, send;
    Button language, character, suggestion;
    EditText enterrestaurantname, enteraddress, entertransportation, entertelephone, entertime, entersit, enterremark, enterabout, addmenuText, enterrecommend;
    EditText enterdateline, enterpromotioncontent, enterstartdate;
    EditText entername, entercellphone, enteremail, enteropinion2;
    Spinner areaselect, regionselect, categoryselect;
    LinearLayout addmenuPic;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;

    View viewLayout;

    Bitmap bitmap;
    ImageButton addrestaurantpicture, addmenuimg1, addmenuimg2, addmenuimg3, addmealimg1, addmealimg2, addmealimg3, addmealimg4, addmealimg5, addmealimg6;
    ImageButton outdoorimg1, outdoorimg2, outdoorimg3, indoorimg1, indoorimg2, indoorimg3;
    RadioButton textButton, picButton;
    String menuType="";
    int doorCount=1;
    String ImageUploadPath ="https://h10271808.000webhostapp.com/img_upload_to_server.php";
    //String ImageUploadPath ="http://www.chiens.idv.tw/test/img_upload_to_server.php";

    Map<String, TreeMap> uploadImageList=new TreeMap<String, TreeMap>();
    TreeMap<String, String> uploadImageHeaderList=new TreeMap<String, String>();
    TreeMap<String, String> uploadImageMenuList=new TreeMap<String, String>();
    TreeMap<String, String> uploadImageMealList=new TreeMap<String, String>();
    TreeMap<String, String> uploadImageDoorList=new TreeMap<String, String>();
    int notificationId;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(commonInfo.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initMenuContent(R.id.restaurant_menu, R.id.restaurant_layout);

        //memu button
        restaurant = (Button) findViewById(R.id.restaurant);
        restaurantIntro = (Button) findViewById(R.id.restaurantInformation_btn);
        restaurantPromotions = (Button) findViewById(R.id.restaurantPromotions_btn);
        restaurantMessage = (Button) findViewById(R.id.restaurantMessage_btn);
        restaurantSetting = (Button) findViewById(R.id.restaurantSetting_btn);
        restaurantDelete = (Button) findViewById(R.id.restaurantDelete_btn);
        restaurantSignout = (Button) findViewById(R.id.restaurantSignout_btn);

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.restaurantContentMain);
                toolbar.setTitle("餐廳");
            }
        });

        restaurantIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.restaurantIntro);
                areaselect = (Spinner) viewLayout.findViewById(R.id.areaselect);
                regionselect = (Spinner) viewLayout.findViewById(R.id.regionselect);
                categoryselect = (Spinner) viewLayout.findViewById(R.id.categoryselect);
                enterrestaurantname = (EditText) viewLayout.findViewById(R.id.enterrestaurantname);
                enteraddress = (EditText) viewLayout.findViewById(R.id.enteraddress);
                entertransportation = (EditText) viewLayout.findViewById(R.id.entertransportation);
                entertelephone = (EditText) viewLayout.findViewById(R.id.entertelephone);
                entertime = (EditText) viewLayout.findViewById(R.id.entertime);
                entersit = (EditText) viewLayout.findViewById(R.id.entersit);
                enterremark = (EditText) viewLayout.findViewById(R.id.enterremark);
                enterabout = (EditText) viewLayout.findViewById(R.id.enterabout);
                addmenuText = (EditText) viewLayout.findViewById(R.id.addmenuText);
                addmenuPic = (LinearLayout) viewLayout.findViewById(R.id.addmenuPic);
                enterrecommend = (EditText) viewLayout.findViewById(R.id.enterrecommend);

                addButton = (Button) findViewById(R.id.addButton);
                addButton.setOnClickListener(restaurantAcitivity.this);


                textButton = (RadioButton) findViewById(R.id.textButton);
                picButton= (RadioButton) findViewById(R.id.picButton);
                textButton.setOnCheckedChangeListener(restaurantAcitivity.this);
                picButton.setOnCheckedChangeListener(restaurantAcitivity.this);

                //ImageUploadButton
                setImageButton();

                categoryselect.setOnItemSelectedListener(restaurantAcitivity.this);
                ArrayAdapter<String> bb = new ArrayAdapter<String>(
                        restaurantAcitivity.this, android.R.layout.simple_spinner_item, commonInfo.categoryitems);
                bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categoryselect.setAdapter(bb);

                areaselect.setOnItemSelectedListener(restaurantAcitivity.this);
                ArrayAdapter<String> aa = new ArrayAdapter<String>(
                        restaurantAcitivity.this, android.R.layout.simple_spinner_item, commonInfo.addareaitems);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaselect.setAdapter(aa);

                toolbar.setTitle("餐廳介紹");
            }
        });

        restaurantPromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.restaurantPromotions);
                addPromotions_btn = (Button) findViewById(R.id.addPromotions_btn);
                addPromotions_btn.setOnClickListener(restaurantAcitivity.this);

                /*mRecyclerView = (RecyclerView) findViewById(R.id.favorite_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(memberAcitivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new managerAdviceViewAdapter(getFavoriteDataSet());
                mRecyclerView.setAdapter(mAdapter);
                ((managerAdviceViewAdapter) mAdapter).setOnItemClickListener(new managerAdviceViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Toast.makeText(getApplicationContext()," Clicked on Item " + position,Toast.LENGTH_SHORT).show();
                    }
                });*/
                toolbar.setTitle("優惠促銷");
            }
        });

        restaurantMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.restaurantMessage);
                toolbar.setTitle("會員回饋");
            }
        });

        restaurantSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.restaurantSetting);

                language = (Button) viewLayout.findViewById(R.id.Language_btn);
                character = (Button) viewLayout.findViewById(R.id.Character_btn);

                suggestion = (Button) viewLayout.findViewById(R.id.Suggestion_btn);
                send = (Button) viewLayout.findViewById(R.id.send);
                entername = (EditText) viewLayout.findViewById(R.id.entername);
                entercellphone = (EditText) viewLayout.findViewById(R.id.entercellphone);
                enteremail = (EditText) viewLayout.findViewById(R.id.enteremail);
                enteropinion2 = (EditText) viewLayout.findViewById(R.id.enteropinion2);

                send.setOnClickListener(restaurantAcitivity.this);
                language.setOnClickListener(restaurantAcitivity.this);
                character.setOnClickListener(restaurantAcitivity.this);
                suggestion.setOnClickListener(restaurantAcitivity.this);

                toolbar.setTitle("餐廳設定");

            }
        });


        restaurantDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog b = new AlertDialog.Builder(restaurantAcitivity.this)
                        .setTitle("刪除餐廳資料")
                        .setMessage("你確定要刪除餐廳所有資料,解除合作關係?")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                Toast.makeText(restaurantAcitivity.this, "解除取消", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                        })
                        .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                Toast.makeText(restaurantAcitivity.this, "解除成功", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                        })
                        .show();
            }
        });

        restaurantSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(restaurantAcitivity.this, visiterAcitivity.class);
                startActivity(login);
                finish();
                commonInfo.restaurantId = "";
                commonInfo.restaurantName ="";
                commonInfo.restaurantAccount = "";
                commonInfo.restaurantTelephone = "";
                commonInfo.restaurantArea = "";
                commonInfo.restaurantAddress = "";
                commonInfo.restaurantCategory = "";
                commonInfo.restaurantEmail = "";
                Toast.makeText(getApplicationContext(), "餐廳登出", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initMenuContent(int initMenu, int initContent) {
        //show memu
        findViewById(R.id.visiter_menu).setVisibility(View.GONE);
        findViewById(R.id.member_menu).setVisibility(View.GONE);
        findViewById(R.id.manager_menu).setVisibility(View.GONE);
        findViewById(R.id.restaurant_menu).setVisibility(View.GONE);
        findViewById(initMenu).setVisibility(View.VISIBLE);

        //show content
        findViewById(R.id.visiter_layout).setVisibility(View.GONE);
        findViewById(R.id.member_layout).setVisibility(View.GONE);
        findViewById(R.id.restaurant_layout).setVisibility(View.GONE);
        findViewById(R.id.manager_layout).setVisibility(View.GONE);
        findViewById(initContent).setVisibility(View.VISIBLE);

        viewLayout = findViewById(initContent);

        toolbar = (Toolbar) findViewById(R.id.restaurant_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.restaurant_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    public void showContent(int showLayout) {
        findViewById(R.id.restaurantContentMain).setVisibility(View.GONE);
        findViewById(R.id.restaurantIntro).setVisibility(View.GONE);
        findViewById(R.id.restaurantPromotions).setVisibility(View.GONE);
        findViewById(R.id.restaurantAddPromotions).setVisibility(View.GONE);
        findViewById(R.id.restaurantMessage).setVisibility(View.GONE);
        findViewById(R.id.restaurantSetting).setVisibility(View.GONE);
        findViewById(R.id.restaurantrOpinion).setVisibility(View.GONE);
        onBackPressed();
        findViewById(showLayout).setVisibility(View.VISIBLE);
    }

    public void showContent1(int showLayout) {
        findViewById(R.id.restaurantContentMain).setVisibility(View.GONE);
        findViewById(R.id.restaurantIntro).setVisibility(View.GONE);
        findViewById(R.id.restaurantPromotions).setVisibility(View.GONE);
        findViewById(R.id.restaurantAddPromotions).setVisibility(View.GONE);
        findViewById(R.id.restaurantMessage).setVisibility(View.GONE);
        findViewById(R.id.restaurantSetting).setVisibility(View.GONE);
        findViewById(R.id.restaurantrOpinion).setVisibility(View.GONE);
        findViewById(showLayout).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

       /* ((memberRecordViewAdapter) mAdapter).setOnItemClickListener(new memberRecordViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Toast.makeText(this, id + "", Toast.LENGTH_SHORT).show();
        if (id == R.id.loginin) {
            Toast.makeText(this, "account1", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "account2", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "account3", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "account4", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "account5", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "account6", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        ArrayAdapter<String> aa;
        if (parent.getId() == R.id.areaselect) {
            if (position == 0) {
                regionselect.setOnItemSelectedListener(this);
                aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, commonInfo.addtaipeiitems);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                regionselect.setAdapter(aa);
            } else if (position == 1) {
                regionselect.setOnItemSelectedListener(this);
                aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, commonInfo.addnewtaipeiitems);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                regionselect.setAdapter(aa);
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addPromotions_btn) {
            findViewById(R.id.restaurantPromotions).setVisibility(View.GONE);
            findViewById(R.id.restaurantAddPromotions).setVisibility(View.VISIBLE);
            enterpromotioncontent = (EditText) viewLayout.findViewById(R.id.enterpromotioncontent);
            enterdateline = (EditText) viewLayout.findViewById(R.id.enterdateline);
            enterstartdate = (EditText) viewLayout.findViewById(R.id.enterstartdate);
            release = (Button) findViewById(R.id.release);
            release.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String promotioncontent = enterpromotioncontent.getText().toString();
                    String dateline = enterdateline.getText().toString();
                    String startdate = enterstartdate.getText().toString();

                    UserFunctions userFunction = new UserFunctions();
                    userFunction.addPromoiotn(promotioncontent, dateline, startdate);
                    Toast.makeText(getBaseContext(), "新增成功", Toast.LENGTH_SHORT).show();

                    enterpromotioncontent.setText("");
                    enterdateline.setText("");
                    enterstartdate.setText("");
                    showContent1(R.id.restaurantPromotions);
                }
            });
        }

        if (v.getId() == R.id.Language_btn) {
            final CharSequence[] items = {"中文", "英文"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("語言設定");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    Configuration c = new Configuration(getResources().getConfiguration());
                    if(items[item].equals("中文"))
                        c.locale = Locale.CHINESE;
                    else
                        c.locale = Locale.ENGLISH;
                    commonInfo.languageSetting=items[item].toString();
                    getResources().updateConfiguration(c, getResources().getDisplayMetrics());
                    Intent refresh = new Intent(restaurantAcitivity.this, restaurantAcitivity.class);
                    startActivity(refresh);
                    finish();
                    Toast.makeText(getBaseContext(), items[item], Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else if (v.getId() == R.id.Character_btn) {
            final CharSequence[] items = {"大", "適中"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("字體設定");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if(items[item].equals("適中"))
                        commonInfo.theme = R.style.AppTheme_NoActionBar;
                    else
                        commonInfo.theme = R.style.AppTheme_Big_NoActionBar;
                    Intent refresh = new Intent(restaurantAcitivity.this, restaurantAcitivity.class);
                    startActivity(refresh);
                    finish();
                    Toast.makeText(getBaseContext(),items[item],Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else if (v.getId() == R.id.Suggestion_btn) {
            findViewById(R.id.restaurantrOpinion).setVisibility(View.VISIBLE);
            findViewById(R.id.restaurantSetting).setVisibility(View.GONE);
        } else if (v.getId() == R.id.addButton) {
            String restaurantname = enterrestaurantname.getText().toString();
            String address = enteraddress.getText().toString();
            String area = areaselect.getSelectedItem().toString();
            String region = regionselect.getSelectedItem().toString();
            String category = categoryselect.getSelectedItem().toString();
            String transport = entertransportation.getText().toString();
            String telephone = entertelephone.getText().toString();
            String time = entertime.getText().toString();
            String sit = entersit.getText().toString();
            String remarks = enterremark.getText().toString();
            String about = enterabout.getText().toString();
            String recommend = enterrecommend.getText().toString();
            String menu = addmenuText.getText().toString();
            uploadImageList.put("h",uploadImageHeaderList);
            uploadImageList.put("m",uploadImageMenuList);
            uploadImageList.put("n",uploadImageMealList);
            uploadImageList.put("t",uploadImageDoorList);

            UserFunctions userFunction = new UserFunctions();
            userFunction.addRestaurant(restaurantname, address, area, region, category, transport, telephone, time, sit, remarks, about, menu, recommend);
            if(commonInfo.updateRestaurantId!="" && commonInfo.updateRestaurantId!=null) {
                AsyncTaskUploadClass AsyncTaskUploadImage = new AsyncTaskUploadClass();
                AsyncTaskUploadImage.execute();
            }
        }else if (v.getId() == R.id.send) {
            String name = entername.getText().toString();
            String cellphone = entercellphone.getText().toString();
            String mail = enteremail.getText().toString();
            String opinioncontent = enteropinion2.getText().toString();

            UserFunctions userFunction = new UserFunctions();
            userFunction.addOpinion(name, cellphone, mail, opinioncontent);
            Toast.makeText(getBaseContext(), "送出成功", Toast.LENGTH_SHORT).show();
            showContent1(R.id.restaurantContentMain);
        }
    }


    public void setImageButton() {
        addrestaurantpicture = (ImageButton) viewLayout.findViewById(R.id.addrestaurantpicture);
        addmenuimg1 = (ImageButton) viewLayout.findViewById(R.id.addmenuimg1);
        addmenuimg2 = (ImageButton) viewLayout.findViewById(R.id.addmenuimg2);
        addmenuimg3 = (ImageButton) viewLayout.findViewById(R.id.addmenuimg3);
        addmealimg1 = (ImageButton) viewLayout.findViewById(R.id.addmealimg1);
        addmealimg2 = (ImageButton) viewLayout.findViewById(R.id.addmealimg2);
        addmealimg3 = (ImageButton) viewLayout.findViewById(R.id.addmealimg3);
        addmealimg4 = (ImageButton) viewLayout.findViewById(R.id.addmealimg4);
        addmealimg5 = (ImageButton) viewLayout.findViewById(R.id.addmealimg5);
        addmealimg6 = (ImageButton) viewLayout.findViewById(R.id.addmealimg6);
        outdoorimg1 = (ImageButton) viewLayout.findViewById(R.id.outdoorimg1);
        outdoorimg2 = (ImageButton) viewLayout.findViewById(R.id.outdoorimg2);
        outdoorimg3 = (ImageButton) viewLayout.findViewById(R.id.outdoorimg3);
        indoorimg1 = (ImageButton) viewLayout.findViewById(R.id.indoorimg1);
        indoorimg2 = (ImageButton) viewLayout.findViewById(R.id.indoorimg2);
        indoorimg3 = (ImageButton) viewLayout.findViewById(R.id.indoorimg3);

        addrestaurantpicture.setOnClickListener(new ImageUploadButtonClick());
        addmenuimg1.setOnClickListener(new ImageUploadButtonClick());
        addmenuimg2.setOnClickListener(new ImageUploadButtonClick());
        addmenuimg3.setOnClickListener(new ImageUploadButtonClick());
        addmealimg1.setOnClickListener(new ImageUploadButtonClick());
        addmealimg2.setOnClickListener(new ImageUploadButtonClick());
        addmealimg3.setOnClickListener(new ImageUploadButtonClick());
        addmealimg4.setOnClickListener(new ImageUploadButtonClick());
        addmealimg5.setOnClickListener(new ImageUploadButtonClick());
        addmealimg6.setOnClickListener(new ImageUploadButtonClick());
        outdoorimg1.setOnClickListener(new ImageUploadButtonClick());
        outdoorimg2.setOnClickListener(new ImageUploadButtonClick());
        outdoorimg3.setOnClickListener(new ImageUploadButtonClick());
        indoorimg1.setOnClickListener(new ImageUploadButtonClick());
        indoorimg2.setOnClickListener(new ImageUploadButtonClick());
        indoorimg3.setOnClickListener(new ImageUploadButtonClick());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton.getId()==R.id.textButton) {
            if (compoundButton.isChecked()) {
                addmenuText.setVisibility(View.VISIBLE);
                addmenuPic.setVisibility(View.INVISIBLE);
                menuType="文字";
            }
        }else{
            if(compoundButton.isChecked()) {
                addmenuText.setVisibility(View.INVISIBLE);
                addmenuPic.setVisibility(View.VISIBLE);
                menuType="照片";
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    public class ImageUploadButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            switch (view.getId()) {
                case R.id.addrestaurantpicture:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
                    break;
                case R.id.addmenuimg1:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 2);
                    break;
                case R.id.addmenuimg2:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 3);
                    break;
                case R.id.addmenuimg3:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 4);
                    break;
                case R.id.addmealimg1:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 5);
                    break;
                case R.id.addmealimg2:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 6);
                    break;
                case R.id.addmealimg3:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 7);
                    break;
                case R.id.addmealimg4:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 8);
                    break;
                case R.id.addmealimg5:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 9);
                    break;
                case R.id.addmealimg6:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 10);
                    break;
                case R.id.outdoorimg1:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 11);
                    break;
                case R.id.outdoorimg2:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 12);
                    break;
                case R.id.outdoorimg3:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 13);
                    break;
                case R.id.indoorimg1:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 14);
                    break;
                case R.id.indoorimg2:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 15);
                    break;
                case R.id.indoorimg3:
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 16);
                    break;
            }
        }
    }

    public  class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String serName = Context.NOTIFICATION_SERVICE;
            String extendedTitle = "1. 餐廳資料上傳中";
            String extendedText  = "2. 餐廳資料上傳中...";
            long when = System.currentTimeMillis();

            notificationManager = (NotificationManager)getSystemService(serName);
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setAutoCancel(false)
                    .setContentTitle(extendedTitle)
                    .setContentText(extendedText)
                    .setSmallIcon(android.R.drawable.star_on)
                    .setWhen(when)
                    .build();

            //trigger notification
            notificationId = 1;
            notificationManager.notify(notificationId, notification);
        }

        @Override
        protected void onPostExecute(String string1) {
            super.onPostExecute(string1);
            notificationId = 1;
            notificationManager.cancel(notificationId);

            //reset
            doorCount=0;
            commonInfo.updateRestaurantId="";
            Toast.makeText(restaurantAcitivity.this, "資料上傳成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... params) {

            ImageProcessClass imageProcessClass = new ImageProcessClass();
            HashMap<String,String> HashMapParams;

            for(int i=0;i<uploadImageList.get("h").keySet().size();i++){
                HashMapParams = new HashMap<String,String>();
                //Log.d("h","h"+i+"="+uploadImageList.get("h").keySet().toArray()[i].toString());
                HashMapParams.put("restaurant_id", commonInfo.updateRestaurantId);
                HashMapParams.put("image_name", uploadImageList.get("h").keySet().toArray()[i].toString());
                HashMapParams.put("image_path", uploadImageList.get("h").values().toArray()[i].toString());
                imageProcessClass.ImageHttpRequest(ImageUploadPath, HashMapParams);
            }

            for(int i=0;i<uploadImageList.get("m").keySet().size();i++){
                HashMapParams = new HashMap<String,String>();
                //Log.d("m","m"+i+"="+uploadImageList.get("m").keySet().toArray()[i].toString());
                HashMapParams.put("restaurant_id", commonInfo.updateRestaurantId);
                HashMapParams.put("image_name", uploadImageList.get("m").keySet().toArray()[i].toString());
                HashMapParams.put("image_path", uploadImageList.get("m").values().toArray()[i].toString());
                imageProcessClass.ImageHttpRequest(ImageUploadPath, HashMapParams);
            }

            for(int i=0;i<uploadImageList.get("n").keySet().size();i++){
                HashMapParams = new HashMap<String,String>();
                //Log.d("n","n"+i+"="+uploadImageList.get("n").keySet().toArray()[i].toString());
                HashMapParams.put("restaurant_id", commonInfo.updateRestaurantId);
                HashMapParams.put("image_name", uploadImageList.get("n").keySet().toArray()[i].toString());
                HashMapParams.put("image_path", uploadImageList.get("n").values().toArray()[i].toString());
                imageProcessClass.ImageHttpRequest(ImageUploadPath, HashMapParams);
            }

            for(int i=0;i<uploadImageList.get("t").keySet().size();i++){
                HashMapParams = new HashMap<String,String>();
                //Log.d("t","t"+i+"="+uploadImageList.get("t").keySet().toArray()[i].toString());
                HashMapParams.put("restaurant_id", commonInfo.updateRestaurantId);
                HashMapParams.put("image_name", uploadImageList.get("t").keySet().toArray()[i].toString());
                HashMapParams.put("image_path", uploadImageList.get("t").values().toArray()[i].toString());
                imageProcessClass.ImageHttpRequest(ImageUploadPath, HashMapParams);
            }
            return "";
        }
    }


    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        ImageButton showImage = null;
        if (RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(viewLayout.getContext().getContentResolver(), uri);
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);

                ByteArrayOutputStream byteArrayOutputStreamObject ;
                byteArrayOutputStreamObject = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
                byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
                final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

                switch (RC) {
                    case 1:
                        showImage = viewLayout.findViewById(R.id.addrestaurantpicture);
                        uploadImageHeaderList.put("h01.jpg", ConvertImage);
                        break;
                    case 2:
                        showImage = viewLayout.findViewById(R.id.addmenuimg1);
                        uploadImageMenuList.put("m01.jpg", ConvertImage);
                        viewLayout.findViewById(R.id.addmenuimg2).setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        showImage = viewLayout.findViewById(R.id.addmenuimg2);
                        uploadImageMenuList.put("m02.jpg", ConvertImage);
                        viewLayout.findViewById(R.id.addmenuimg3).setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        showImage = viewLayout.findViewById(R.id.addmenuimg3);
                        uploadImageMenuList.put("m03.jpg", ConvertImage);
                        break;
                    case 5:
                        showImage = viewLayout.findViewById(R.id.addmealimg1);
                        uploadImageMealList.put("01.jpg", ConvertImage);
                        viewLayout.findViewById(R.id.addmealimg2).setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        showImage = viewLayout.findViewById(R.id.addmealimg2);
                        uploadImageMealList.put("02.jpg", ConvertImage);
                        viewLayout.findViewById(R.id.addmealimg3).setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        showImage = viewLayout.findViewById(R.id.addmealimg3);
                        uploadImageMealList.put("03.jpg", ConvertImage);
                        viewLayout.findViewById(R.id.addmealimg4).setVisibility(View.VISIBLE);
                        break;
                    case 8:
                        showImage = viewLayout.findViewById(R.id.addmealimg4);
                        uploadImageMealList.put("04.jpg", ConvertImage);
                        viewLayout.findViewById(R.id.addmealimg5).setVisibility(View.VISIBLE);
                        break;
                    case 9:
                        showImage = viewLayout.findViewById(R.id.addmealimg5);
                        uploadImageMealList.put("05.jpg", ConvertImage);
                        viewLayout.findViewById(R.id.addmealimg6).setVisibility(View.VISIBLE);
                        break;
                    case 10:
                        showImage = viewLayout.findViewById(R.id.addmealimg6);
                        uploadImageMealList.put("06.jpg", ConvertImage);
                        break;
                    case 11:
                        showImage = viewLayout.findViewById(R.id.outdoorimg1);
                        uploadImageDoorList.put("t0"+ doorCount++ +".jpg", ConvertImage);
                        viewLayout.findViewById(R.id.outdoorimg2).setVisibility(View.VISIBLE);
                        break;
                    case 12:
                        showImage = viewLayout.findViewById(R.id.outdoorimg2);
                        uploadImageDoorList.put("t0" +doorCount++ +".jpg", ConvertImage);
                        viewLayout.findViewById(R.id.outdoorimg3).setVisibility(View.VISIBLE);
                        break;
                    case 13:
                        showImage = viewLayout.findViewById(R.id.outdoorimg3);
                        uploadImageDoorList.put("t0"+ doorCount++ +".jpg", ConvertImage);
                        break;
                    case 14:
                        showImage = viewLayout.findViewById(R.id.indoorimg1);
                        uploadImageDoorList.put("t0"+ doorCount++ +".jpg", ConvertImage);
                        viewLayout.findViewById(R.id.indoorimg2).setVisibility(View.VISIBLE);
                        break;
                    case 15:
                        showImage = viewLayout.findViewById(R.id.indoorimg2);
                        uploadImageDoorList.put("t0"+ doorCount++ +".jpg", ConvertImage);
                        viewLayout.findViewById(R.id.indoorimg3).setVisibility(View.VISIBLE);
                        break;
                    case 16:
                        showImage = viewLayout.findViewById(R.id.indoorimg3);
                        uploadImageDoorList.put("t0"+ doorCount++ +".jpg", ConvertImage);
                        break;
                }
                showImage.setBackground(ob);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



