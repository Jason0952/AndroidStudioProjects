package com.example.home.member;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.home.R;
import com.example.home.common.commonInfo;
import com.example.home.common.commonSearchDataObject;
import com.example.home.common.commonSearchFragmentAdapter;
import com.example.home.common.commonSearchPicAdapter;
import com.example.home.common.commonSearchViewAdapter;
import com.example.home.restaurant.restaurantAcitivity;
import com.example.home.visiter.visiterAcitivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import library.UserFunctions;

public class memberAcitivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, View.OnClickListener{

    Button member, memberSearch ,memberPromotions, memberTurntable, watchtherecord, myfavourite, memberSetting, memberSignout;
    Button language, character, suggestion, send;
    EditText entername, entercellphone, enteremail, enteropinion2;

    View viewlayout;
    TextView promotioncontent, startdate, dateline;


    boolean keywordSearch=false;
    Button search, searchTurntable, message_verify_btn;
    Spinner areaselect,regionselect;
    EditText searchname, entermember_message;
    ToggleButton american, japan,korea,hongkong,italy,chinese,dessert,ice;
    memberAcitivity.searchItemClick searchClick=new memberAcitivity.searchItemClick();
    private ArrayList<String> searchCatetoryList = new ArrayList<String>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Toolbar toolbar;

    private TabLayout mTabLayout;
    private ViewPager mViewPager, memberViewPicPager;
    LinearLayout tipsBox;

    private commonSearchFragmentAdapter myFragmentPagerAdapter;

    private TabLayout.Tab one, two, three, four;
    View viewLayout;

    //add
    LinearLayout LY;
    List<String> rest;
    ArrayList LoadchooseList = new ArrayList(); //抓取有被使用者勾選的餐廳放入陣列裡
    RelativeLayout.LayoutParams RL = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(commonInfo.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initMenuContent(R.id.member_menu,R.id.member_layout);

        //memu button
        member=(Button) findViewById(R.id.member);
        memberSearch=(Button) findViewById(R.id.memberSearch_btn);
        memberPromotions=(Button) findViewById(R.id.memberPromotions_btn);
        //add
        memberTurntable=(Button) findViewById(R.id.turntable);

        myfavourite=(Button) findViewById(R.id.myfavourite);
        watchtherecord=(Button) findViewById(R.id.watchtherecord);
        memberSetting=(Button) findViewById(R.id.memberSetting_btn);
        memberSignout=(Button) findViewById(R.id.memberSignout_btn);

        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.memberContentMain);
                toolbar.setTitle("會員");
            }
        });

        memberSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.memberSearch);
                initSearchContent();
                toolbar.setTitle("餐廳搜尋");
            }
        });

        memberPromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.memberPromotions);

                mRecyclerView = (RecyclerView) findViewById(R.id.promotions_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(memberAcitivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new memberPromotionsViewAdapter(getPromotionsDataSet());
                mRecyclerView.setAdapter(mAdapter);
                ((memberPromotionsViewAdapter) mAdapter).setOnItemClickListener(new memberPromotionsViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {

                        Toast.makeText(getApplicationContext()," Clicked on Item " + position,Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder getPromotionsDialog = new AlertDialog.Builder(memberAcitivity.this,android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialoglayout = inflater.inflate(R.layout.member_promotions_information, (ViewGroup) findViewById(R.id.confirmDialogLayout));
                        final AlertDialog ad=  getPromotionsDialog.setView(dialoglayout).show();

                        Button getPromotions = (Button) dialoglayout.findViewById(R.id.getPromotions_btn);
                        getPromotions.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext()," getPromotions ",Toast.LENGTH_SHORT).show();
                                ad.dismiss();
                            }
                        });
                    }
                });

                toolbar.setTitle("優惠促銷");
            }
        });

        //add
        memberTurntable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.memberTurntable);
                initSearchTurntableContent();
                toolbar.setTitle("餐廳搜尋");

                mLayoutManager = new LinearLayoutManager(memberAcitivity.this);
                mAdapter = new cardSearchViewAdapter(cardSearchDataSet());
                ((cardSearchViewAdapter) mAdapter).setOnItemClickListener(new cardSearchViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {

                        LY = (LinearLayout)viewLayout.findViewById(R.id.LY);

                        btn1 = (Button)viewLayout.findViewById(R.id.choose);
                        btn2 = (Button)viewLayout.findViewById(R.id.getready);
                        btn1.setOnClickListener(buttonListener);
                        btn2.setOnClickListener(buttonListener);

                    }
                });
            }
        });


        myfavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.favorite);

                mRecyclerView = (RecyclerView) findViewById(R.id.favorite_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(memberAcitivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new memberFavoriteViewAdapter(getFavoriteDataSet());
                mRecyclerView.setAdapter(mAdapter);
                ((memberFavoriteViewAdapter) mAdapter).setOnItemClickListener(new memberFavoriteViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Toast.makeText(getApplicationContext()," Clicked on Item " + position,Toast.LENGTH_SHORT).show();
                    }
                });
                toolbar.setTitle("我的最愛");
            }
        });

        watchtherecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.record);

                mRecyclerView = (RecyclerView) findViewById(R.id.record_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(memberAcitivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new memberRecordViewAdapter(getRecordDataSet());
                mRecyclerView.setAdapter(mAdapter);
                ((memberRecordViewAdapter) mAdapter).setOnItemClickListener(new memberRecordViewAdapter.MyClickListener() {
                @Override
                    public void onItemClick(int position, View v) {
                        Toast.makeText(getApplicationContext()," Clicked on Item " + position,Toast.LENGTH_SHORT).show();
                    }
                });
                toolbar.setTitle("觀看紀錄");
            }
        });

        memberSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.memberSetting);

                language=(Button) viewLayout.findViewById(R.id.Language_btn);
                character=(Button) viewLayout.findViewById(R.id.Character_btn);
                suggestion=(Button) viewLayout.findViewById(R.id.Suggestion_btn);
                send = (Button) viewLayout.findViewById(R.id.send);
                entername = (EditText) viewLayout.findViewById(R.id.entername);
                entercellphone = (EditText) viewLayout.findViewById(R.id.entercellphone);
                enteremail = (EditText) viewLayout.findViewById(R.id.enteremail);
                enteropinion2 = (EditText) viewLayout.findViewById(R.id.enteropinion2);

                send.setOnClickListener(memberAcitivity.this);
                language.setOnClickListener(memberAcitivity.this);
                character.setOnClickListener(memberAcitivity.this);
                suggestion.setOnClickListener(memberAcitivity.this);

                toolbar.setTitle("會員設定");
            }
        });


        memberSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "會員登出", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(memberAcitivity.this, visiterAcitivity.class);
                startActivity(login);
                finish();
            }
        });
    }

    public void initMenuContent(int initMenu, int initContent){
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
        toolbar = (Toolbar) findViewById(R.id.member_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.member_fab);
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

    public void showContent(int showLayout){
        findViewById(R.id.memberContentMain).setVisibility(View.GONE);
        findViewById(R.id.memberSearch).setVisibility(View.GONE);
        findViewById(R.id.memberSearchResult).setVisibility(View.GONE);
        findViewById(R.id.memberSearchDetail).setVisibility(View.GONE);
        //add
        findViewById(R.id.memberTurntable).setVisibility(View.GONE);
        findViewById(R.id.memberTurntableResult).setVisibility(View.GONE);
        findViewById(R.id.memberTurntableDetail).setVisibility(View.GONE);

        findViewById(R.id.memberPromotions).setVisibility(View.GONE);
        findViewById(R.id.favorite).setVisibility(View.GONE);
        findViewById(R.id.record).setVisibility(View.GONE);
        findViewById(R.id.memberSetting).setVisibility(View.GONE);
        findViewById(R.id.memberOpinion).setVisibility(View.GONE);
        onBackPressed();
        findViewById(showLayout).setVisibility(View.VISIBLE);
    }

    public void showContent1(int showLayout){
        findViewById(R.id.memberContentMain).setVisibility(View.GONE);
        findViewById(R.id.memberSearch).setVisibility(View.GONE);
        findViewById(R.id.memberSearchResult).setVisibility(View.GONE);
        findViewById(R.id.memberSearchDetail).setVisibility(View.GONE);
        findViewById(R.id.memberPromotions).setVisibility(View.GONE);
        findViewById(R.id.favorite).setVisibility(View.GONE);
        findViewById(R.id.record).setVisibility(View.GONE);
        findViewById(R.id.memberSetting).setVisibility(View.GONE);
        findViewById(R.id.memberOpinion).setVisibility(View.GONE);
        findViewById(showLayout).setVisibility(View.VISIBLE);
    }

    //add
    public void initSearchTurntableContent(){
        areaselect=(Spinner) viewLayout.findViewById(R.id.areaselect);
        regionselect=(Spinner) viewLayout.findViewById(R.id.regionselect);
        searchname=(EditText) viewLayout.findViewById(R.id.searchname);
        american=(ToggleButton) viewLayout.findViewById(R.id.american);
        japan=(ToggleButton) viewLayout.findViewById(R.id.japan);
        korea=(ToggleButton) viewLayout.findViewById(R.id.korea);
        hongkong=(ToggleButton) viewLayout.findViewById(R.id.hongkong);
        italy=(ToggleButton) viewLayout.findViewById(R.id.italy);
        chinese=(ToggleButton) viewLayout.findViewById(R.id.chinese);
        dessert=(ToggleButton) viewLayout.findViewById(R.id.dessert);
        ice=(ToggleButton) viewLayout.findViewById(R.id.ice);

        american.setOnClickListener(searchClick);
        japan.setOnClickListener(searchClick);
        korea.setOnClickListener(searchClick);
        hongkong.setOnClickListener(searchClick);
        italy.setOnClickListener(searchClick);
        chinese.setOnClickListener(searchClick);
        dessert.setOnClickListener(searchClick);
        ice.setOnClickListener(searchClick);

        searchTurntable=(Button) viewLayout.findViewById(R.id.search_turntable_btn);
        searchTurntable.setOnClickListener(memberAcitivity.this);

        areaselect.setOnItemSelectedListener(memberAcitivity.this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                memberAcitivity.this, android.R.layout.simple_spinner_item, commonInfo.areaitems);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaselect.setAdapter(aa);
    }

    public void initSearchContent(){
        areaselect=(Spinner) viewLayout.findViewById(R.id.areaselect);
        regionselect=(Spinner) viewLayout.findViewById(R.id.regionselect);
        searchname=(EditText) viewLayout.findViewById(R.id.searchname);
        american=(ToggleButton) viewLayout.findViewById(R.id.american);
        japan=(ToggleButton) viewLayout.findViewById(R.id.japan);
        korea=(ToggleButton) viewLayout.findViewById(R.id.korea);
        hongkong=(ToggleButton) viewLayout.findViewById(R.id.hongkong);
        italy=(ToggleButton) viewLayout.findViewById(R.id.italy);
        chinese=(ToggleButton) viewLayout.findViewById(R.id.chinese);
        dessert=(ToggleButton) viewLayout.findViewById(R.id.dessert);
        ice=(ToggleButton) viewLayout.findViewById(R.id.ice);

        american.setOnClickListener(searchClick);
        japan.setOnClickListener(searchClick);
        korea.setOnClickListener(searchClick);
        hongkong.setOnClickListener(searchClick);
        italy.setOnClickListener(searchClick);
        chinese.setOnClickListener(searchClick);
        dessert.setOnClickListener(searchClick);
        ice.setOnClickListener(searchClick);

        search=(Button) viewLayout.findViewById(R.id.search_btn);
        search.setOnClickListener(memberAcitivity.this);

        areaselect.setOnItemSelectedListener(memberAcitivity.this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                memberAcitivity.this, android.R.layout.simple_spinner_item, commonInfo.areaitems);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaselect.setAdapter(aa);
        searchname.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    searchItemEnable(false);
                    commonInfo.searchKeyword=s.toString();
                    keywordSearch=true;
                }
                else{
                    searchItemEnable(true);
                    keywordSearch=false;
                }

            }
        });
    }

    private ArrayList<commonSearchDataObject> getSearchDataSet() {
        commonInfo.searchArea="";
        commonInfo.searchRegion="";
        commonInfo.searchCategory="";

        if(areaselect.getSelectedItem()!=null && areaselect.getSelectedItem().toString()!="全選") {
            commonInfo.searchArea = areaselect.getSelectedItem().toString();
            if (regionselect.getSelectedItem() != null && regionselect.getSelectedItem().toString() != "全選")
                commonInfo.searchRegion = regionselect.getSelectedItem().toString();
        }
        commonInfo.searchCategory="";
        for(int i=0;i<searchCatetoryList.size();i++) {
            commonInfo.searchCategory += searchCatetoryList.get(i);
            if(i!=searchCatetoryList.size()-1)
                commonInfo.searchCategory +=",";
        }
        commonInfo.getSearchRestaurantInformation(keywordSearch);
        ArrayList results = new ArrayList<commonSearchDataObject>();
        int searchIndex;

        try {
            for (int index = 0; index < Integer.parseInt(commonInfo.json_search_restaurant.getString("restaurantcount")); index++) {
                searchIndex = Integer.parseInt(commonInfo.json_search_restaurant.getJSONArray("restaurantid").getString(index));
                commonInfo.getRestaurantInformation(searchIndex);
                commonSearchDataObject obj = null;
                obj = new commonSearchDataObject(
                        "https://h10271808.000webhostapp.com/img/"+(searchIndex)+"/h01.jpg",
                        commonInfo.json_restaurant.getString("restaurantname"),
                        commonInfo.json_restaurant.getString("area")+commonInfo.json_restaurant.getString("region"),
                        commonInfo.json_restaurant.getString("category"),
                        commonInfo.json_restaurant.getString("time"));
                results.add(index, obj);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }

    private ArrayList<memberPromotionsDataObject> getPromotionsDataSet() {

        commonInfo.getPromotions("0");
        ArrayList results = new ArrayList<memberPromotionsDataObject>();
        try {
            if (commonInfo.jsonStatus.equals("FAIL"))
                Toast.makeText(this,"搜尋不到餐廳",Toast.LENGTH_SHORT).show();
            else {
                for (int index = 0; index < Integer.parseInt(commonInfo.json_promotion.getString("promotioncount")); index++) {
                    memberPromotionsDataObject obj = new memberPromotionsDataObject(
                            commonInfo.json_promotion.getJSONArray("restaurantid").getString(index),
                            commonInfo.json_promotion.getJSONArray("startdate").getString(index)+commonInfo.json_promotion.getJSONArray("dateline").getString(index)
                    );
                    results.add(index, obj);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

   private ArrayList<memberFavoriteDataObject> getFavoriteDataSet() {
        ArrayList results = new ArrayList<memberFavoriteDataObject>();
        for (int index = 0; index < 10; index++) {
            memberFavoriteDataObject obj = new memberFavoriteDataObject("餐廳名稱 " + index, "加入日期 " + index);
            results.add(index, obj);
        }
        return results;
    }

    private ArrayList<memberRecordDataObject> getRecordDataSet() {
        ArrayList results = new ArrayList<memberRecordDataObject>();
        for (int index = 0; index < 15; index++) {
            memberRecordDataObject obj = new memberRecordDataObject("餐廳名稱 " + index, "觀看日期 " + index);
            results.add(index, obj);
        }
        return results;
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
            if(tipsBox!=null)
                tipsBox.removeAllViews();
        } else {
            super.onBackPressed();
        }
    }

    private void searchInitViews() {
        memberViewPicPager=(ViewPager)findViewById(R.id.memberViewPicPager);
        tipsBox=(LinearLayout)findViewById(R.id.memberTipsBox);

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        params.leftMargin=5;
        params.rightMargin=5;

        TextView txt = new TextView(this);
        txt.setText(toolbar.getTitle());
        txt.setTextColor(Color.WHITE);
        tipsBox.addView(txt, params);

        ImageView img=new ImageView(this);
        img.setLayoutParams(new ViewGroup.LayoutParams(10,10));
        img.setBackgroundResource(R.mipmap.ic_launcher);
        tipsBox.addView(img, params);

        memberViewPicPager.setAdapter(new commonSearchPicAdapter(this));
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.memberViewPager);
        myFragmentPagerAdapter = new commonSearchFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.memberTabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        four = mTabLayout.getTabAt(3);
    }

       @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Toast.makeText(this,id+"",Toast.LENGTH_SHORT).show();
        if (id == R.id.loginin) {
            Toast.makeText(this,"account1",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this,"account2",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this,"account3",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(this,"account4",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this,"account5",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this,"account6",Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        ArrayAdapter<String> aa;
        if(parent.getId()==R.id.areaselect) {
            if (position == 0)
                regionselect.setEnabled(false);
            else if (position == 1) {
                regionselect.setEnabled(true);
                regionselect.setOnItemSelectedListener(this);
                aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, commonInfo.taipeiitems);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                regionselect.setAdapter(aa);
            }
            else if (position == 2) {
                regionselect.setEnabled(true);
                regionselect.setOnItemSelectedListener(this);
                aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, commonInfo.newtaipeiitems);
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
        if(v.getId()==R.id.Language_btn) {
            final CharSequence[] items = { "中文", "英文" };
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
                    Intent refresh = new Intent(memberAcitivity.this, memberAcitivity.class);
                    startActivity(refresh);
                    finish();
                    Toast.makeText(getBaseContext(), items[item], Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else if(v.getId()==R.id.Character_btn){
            final CharSequence[] items = { "大", "適中" };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("字體設定");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if(items[item].equals("適中"))
                        commonInfo.theme = R.style.AppTheme_NoActionBar;
                    else
                        commonInfo.theme = R.style.AppTheme_Big_NoActionBar;
                    Intent refresh = new Intent(memberAcitivity.this, memberAcitivity.class);
                    startActivity(refresh);
                    finish();
                    Toast.makeText(getBaseContext(),items[item],Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else if(v.getId()==R.id.Suggestion_btn){
            findViewById(R.id.memberSetting).setVisibility(View.GONE);
            findViewById(R.id.memberOpinion).setVisibility(View.VISIBLE);

        //add
        } else if(v.getId() == R.id.search_turntable_btn){
            findViewById(R.id.memberTurntable).setVisibility(View.GONE);
            findViewById(R.id.memberTurntableResult).setVisibility(View.VISIBLE);

        } else if(v.getId()==R.id.search_btn ){
            findViewById(R.id.memberSearch).setVisibility(View.GONE);
            findViewById(R.id.memberSearchResult).setVisibility(View.VISIBLE);

            mRecyclerView = (RecyclerView) viewLayout.findViewById(R.id.common_search_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(memberAcitivity.this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new commonSearchViewAdapter(getSearchDataSet());
            mRecyclerView.setAdapter(mAdapter);
            ((commonSearchViewAdapter) mAdapter).setOnItemClickListener(new commonSearchViewAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    findViewById(R.id.memberSearchResult).setVisibility(View.GONE);
                    findViewById(R.id.memberSearchDetail).setVisibility(View.VISIBLE);
                    commonInfo.getRestaurantInformation(position);
                    try {
                        toolbar.setTitle(commonInfo.json_restaurant.getString("restaurantname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    commonInfo.storePosition=position+1+"";
                    searchInitViews();//初始化控件
                }
            });

        }else if (v.getId() == R.id.send) {
            String name = entername.getText().toString();
            String cellphone = entercellphone.getText().toString();
            String mail = enteremail.getText().toString();
            String opinioncontent = enteropinion2.getText().toString();

            UserFunctions userFunction = new UserFunctions();
            userFunction.addOpinion(name, cellphone, mail, opinioncontent);

            Toast.makeText(getBaseContext(), "送出成功", Toast.LENGTH_SHORT).show();
            showContent1(R.id.memberContentMain);
        }
    }

    public class searchItemClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String addString="";
            switch (view.getId()) {
                case R.id.american:
                    addString="美式";
                    break;
                case R.id.japan:
                    addString="日式";
                    break;
                case R.id.korea:
                    addString="韓式";
                    break;
                case R.id.hongkong:
                    addString="港式";
                    break;
                case R.id.italy:
                    addString="義式";
                    break;
                case R.id.chinese:
                    addString="中式";
                    break;
                case R.id.dessert:
                    addString="甜點";
                    break;
                case R.id.ice:
                    addString="冰品";
                    break;
            }
            if(((ToggleButton)view).isChecked())
                searchCatetoryList.add(addString);
            else
                searchCatetoryList.remove(addString);
        }
    }

    public void searchItemEnable(boolean go){
        areaselect.setEnabled(go);
        regionselect.setEnabled(go);
        american.setEnabled(go);
        japan.setEnabled(go);
        korea.setEnabled(go);
        hongkong.setEnabled(go);
        italy.setEnabled(go);
        chinese.setEnabled(go);
        dessert.setEnabled(go);
        ice.setEnabled(go);
    }

    //add
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

    //抽卡的抓資料庫的東西
    private ArrayList<cardSearchDataObject> cardSearchDataSet() {
        commonInfo.searchArea="";     //縣市
        commonInfo.searchRegion="";   //地區
        commonInfo.searchCategory=""; //類别

        if(areaselect.getSelectedItem()!=null && areaselect.getSelectedItem().toString()!="全選") {
            commonInfo.searchArea = areaselect.getSelectedItem().toString();
            if (regionselect.getSelectedItem() != null && regionselect.getSelectedItem().toString() != "全選")
                commonInfo.searchRegion = regionselect.getSelectedItem().toString();
        }
        commonInfo.searchCategory="";
        for(int i=0;i<searchCatetoryList.size();i++) {
            commonInfo.searchCategory += searchCatetoryList.get(i);
            if(i!=searchCatetoryList.size()-1)
                commonInfo.searchCategory +=",";
        }

        ArrayList results = new ArrayList<cardSearchDataObject>();
        int searchIndex;

        try {
            for (int index = 0; index < Integer.parseInt(commonInfo.json_search_restaurant.getString("restaurantcount")); index++) {
                searchIndex = Integer.parseInt(commonInfo.json_search_restaurant.getJSONArray("restaurantid").getString(index));
                commonInfo.getRestaurantInformation(searchIndex);
                cardSearchDataObject obj = null;
                obj = new cardSearchDataObject(
                        "https://h10271808.000webhostapp.com/img/"+(searchIndex)+"/h01.jpg",
                        commonInfo.json_mycard.getString("restaurantname"));
                results.add(index, obj);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }

    //讓系統能動態加入Imagebutton
    public  void addimagebtn(){
        RL.width = 100;
        RL.height = 100;
        RL.leftMargin = 10;
        for (int i = 0;i < LoadchooseList.size();i++){
            ImageButton choosebtn = new ImageButton(this); //在這個object裡面會自動create imagebutton(多個)
            choosebtn.setLayoutParams(RL);
            choosebtn.setBackgroundResource(R.drawable.cardback);
            choosebtn.setId(i);
            LY.addView(choosebtn); //將choosebtn放到View上
        }
    }

    public void ran(){
        ArrayList NR = new ArrayList(); //存放亂數排序完的LoadchooseList
        ArrayList tempList = new ArrayList(LoadchooseList);
        RL.width = 500;
        RL.height = 500;
        RL.leftMargin = 20;
        while (tempList.size() > 0) {
            int i = (int) (Math.random() * tempList.size()); //讓LoadchooseList裡面的選項亂數排序
            NR.add(tempList.get(i)); //將亂數排序LoadchooseList裡面的值放進NR裡面
            tempList.remove(i); //清除tempList裡的資料
        }
        for (int j = 0;j < NR.size();j++){
            ArrayList<ImageButton> BtnList = new ArrayList<ImageButton>();
            ImageButton randombtn = new ImageButton(this); //在這個object裡面會自動create imagebutton(多個)
            if (j != 0 && (j+1)%3 == 0){
                //每3個則自動換行
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
            randombtn.setBackgroundResource(R.drawable.cardback);
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

        new android.support.v7.app.AlertDialog.Builder(memberAcitivity.this).setMultiChoiceItems(rest.toArray(new String[rest.size()]), new boolean[rest.size()], new DialogInterface.OnMultiChoiceClickListener(){
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
                        LoadchooseList.add(rest.get(i));//從餐廳資料裡抓取已經被勾選的項目(i)，並且放在新的空陣列裡(從0開始排序)
                    }
                }

                addimagebtn();

                //判斷是否有選擇項目
                if (!isEmpty/*isEmpty不等於true*/){
                    Toast.makeText(memberAcitivity.this, "加入選擇成功", Toast.LENGTH_SHORT).show();
                    //重新取出所有值並讓值皆為"取消選擇"(回到原本的狀態)
                    for (String s : rest){
                        checkedStatusList.add(false);
                    }
                }
                else {
                    Toast.makeText(memberAcitivity.this, "請勾選項目", Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
    }

    private View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}



