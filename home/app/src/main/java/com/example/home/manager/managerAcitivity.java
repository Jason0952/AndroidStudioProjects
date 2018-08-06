package com.example.home.manager;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.home.R;
import com.example.home.common.commonInfo;
import com.example.home.common.commonSearchDataObject;
import com.example.home.common.commonSearchPicAdapter;
import com.example.home.common.commonSearchViewAdapter;
import com.example.home.visiter.visiterAcitivity;

import org.json.JSONException;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import library.UserFunctions;

public class managerAcitivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, View.OnClickListener{

    Button manager, managerAudit, managerSearch, managerRecommed, managerAdvice, managerSetting, managerSignout;
    Button language, character, addComplete, send, sendreply;
    EditText enterplacename, enterplaceaddress, enterplacetelephone, enterdistance, entertime, enterreply;

    boolean keywordSearch=false;
    Button search;
    Spinner areaselect, regionselect;
    EditText searchname;
    ToggleButton american, japan,korea,hongkong,italy,chinese,dessert,ice;
    managerAcitivity.searchItemClick searchClick=new managerAcitivity.searchItemClick();
    private ArrayList<String> searchCatetoryList = new ArrayList<String>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Toolbar toolbar;

    private TabLayout mTabLayout;
    private ViewPager mViewPager, managerViewPicPager;
    LinearLayout tipsBox;

    private managerSearchFragmentAdapter myFragmentPagerAdapter;

    private TabLayout.Tab one, two, three, four;
    View viewLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(commonInfo.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initMenuContent(R.id.manager_menu,R.id.manager_layout);

        //memu button
        manager=(Button) findViewById(R.id.manager);
        managerAudit=(Button) findViewById(R.id.managerAudit_btn);
        managerSearch=(Button) findViewById(R.id.managerSearch_btn);
        managerRecommed=(Button) findViewById(R.id.manager_recommed_btn);
        managerAdvice=(Button) findViewById(R.id.managerAdvice_btn);
        managerAdvice=(Button) findViewById(R.id.managerAdvice_btn);
        managerSetting=(Button) findViewById(R.id.managerSetting_btn);
        managerSignout=(Button) findViewById(R.id.managerSignout_btn);

        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.managerContentMain);
                toolbar.setTitle("管理者");
            }
        });

        managerAudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.managerAudit);

                mRecyclerView = (RecyclerView) findViewById(R.id.manager_audit_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new GridLayoutManager(managerAcitivity.this, 2) ;
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new managerAuditViewAdapter(getAuditDataSet());
                mRecyclerView.setAdapter(mAdapter);
                ((managerAuditViewAdapter) mAdapter).setOnItemClickListener(new managerAuditViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Toast.makeText(getApplicationContext()," Clicked on Item " + position,Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder auditConfirm = new AlertDialog.Builder(managerAcitivity.this,android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);

                        LayoutInflater inflater = getLayoutInflater();
                        View dialoglayout = inflater.inflate(R.layout.manager_audit_confirm, (ViewGroup) findViewById(R.id.confirmDialogLayout));

                        auditConfirm.setView(dialoglayout);
                        final AlertDialog ad = auditConfirm.show();

                        Button Approved = (Button) dialoglayout.findViewById(R.id.Approved);
                        Approved.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(getApplicationContext()," Approved ",Toast.LENGTH_SHORT).show();
                                AlertDialog b = new AlertDialog.Builder(managerAcitivity.this)
                                        .setTitle("核准再次確認")
                                        .setMessage("是否核准此店家成為合作餐聽?")
                                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                Toast.makeText(managerAcitivity.this, "核准取消", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                Toast.makeText(managerAcitivity.this, "核准成功", Toast.LENGTH_LONG).show();
                                                ad.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        });

                        Button notApproved = (Button) dialoglayout.findViewById(R.id.notApproved);
                        notApproved.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext()," notApproved ",Toast.LENGTH_SHORT).show();
                                //ad.dismiss();
                                AlertDialog.Builder notApprovedConfirm = new AlertDialog.Builder(managerAcitivity.this,android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);

                                LayoutInflater inflater = getLayoutInflater();
                                View dialoglayout = inflater.inflate(R.layout.manager_audit_confirm_no, (ViewGroup) findViewById(R.id.confirmNoDialogLayout));

                                notApprovedConfirm.setView(dialoglayout);
                                final AlertDialog add = notApprovedConfirm.show();
                                Button trans = (Button) dialoglayout.findViewById(R.id.sendemail);
                                trans.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext()," trans ",Toast.LENGTH_SHORT).show();
                                        add.dismiss();
                                        ad.dismiss();
                                    }
                                });

                                Button cancel = (Button) dialoglayout.findViewById(R.id.cencel_btn);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext()," cancle ",Toast.LENGTH_SHORT).show();
                                        add.dismiss();
                                    }
                                });
                            }
                        });
                    }
                });
                toolbar.setTitle("審核餐廳");
            }
        });

        managerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.managerSearch);
                initSearchContent();
                toolbar.setTitle("搜尋餐廳");
            }
        });

        managerRecommed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.managerRecommed);

                mRecyclerView = (RecyclerView) findViewById(R.id.manager_recommed_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new GridLayoutManager(managerAcitivity.this, 2) ;
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new managerRecommedViewAdapter(getRecommedDataSet());
                mRecyclerView.setAdapter(mAdapter);
                ((managerRecommedViewAdapter) mAdapter).setOnItemClickListener(new managerRecommedViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        findViewById(R.id.managerRecommed).setVisibility(View.GONE);
                        findViewById(R.id.managerRecommedContent).setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext()," Clicked on Item " + position,Toast.LENGTH_SHORT).show();
                        mRecyclerView = (RecyclerView) findViewById(R.id.manager_recommed_content_recycler_view);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(managerAcitivity.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new managerRecommedContentViewAdapter(getRecommedContentDataSet());
                        mRecyclerView.setAdapter(mAdapter);
                        ((managerRecommedContentViewAdapter) mAdapter).setOnItemClickListener(new managerRecommedContentViewAdapter.MyClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                findViewById(R.id.managerRecommedContent).setVisibility(View.GONE);
                                findViewById(R.id.managerRecommedModify).setVisibility(View.VISIBLE);

                                Button modifyComplete = (Button) findViewById(R.id.modifyComplete);
                                modifyComplete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getApplicationContext()," modifyComplete ",Toast.LENGTH_SHORT).show();
                                        findViewById(R.id.managerRecommedContent).setVisibility(View.VISIBLE);
                                        findViewById(R.id.managerRecommedModify).setVisibility(View.GONE);
                                    }
                                });
                            }
                        });

                        Button add = (Button) findViewById(R.id.addRecommend_btn);
                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                findViewById(R.id.managerRecommedContent).setVisibility(View.GONE);
                                findViewById(R.id.managerRecommedAdd).setVisibility(View.VISIBLE);
                                enterplacename = (EditText) viewLayout.findViewById(R.id.enterplacename);
                                enterplaceaddress = (EditText) viewLayout.findViewById(R.id.enterplaceaddress);
                                enterplacetelephone = (EditText) viewLayout.findViewById(R.id.enterplacetelephone);
                                enterdistance = (EditText) viewLayout.findViewById(R.id.enterdistance);
                                entertime = (EditText) viewLayout.findViewById(R.id.entertime);
                                Button addComplete = (Button) findViewById(R.id.addComplete);
                                addComplete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String placename = enterplacename.getText().toString();
                                        String placeaddress = enterplaceaddress.getText().toString();
                                        String placetelephone = enterplacetelephone.getText().toString();
                                        String distance = enterdistance.getText().toString();
                                        String time = entertime.getText().toString();

                                        UserFunctions userFunction = new UserFunctions();
                                        userFunction.addPlaceinformation(placename, placeaddress, placetelephone, distance, time);

                                        showContent1(R.id.managerRecommedContent);

                                        Toast.makeText(getApplicationContext()," 新增成功 ",Toast.LENGTH_SHORT).show();
                                        findViewById(R.id.managerRecommedContent).setVisibility(View.VISIBLE);
                                        findViewById(R.id.managerRecommedAdd).setVisibility(View.GONE);

                                    }
                                });
                            }
                        });

                    }
                });
                toolbar.setTitle("推薦旅遊景點");
            }
        });


        managerAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.managerAdvice);

                enterreply = (EditText) viewLayout.findViewById(R.id.enterreply);

                sendreply = (Button) findViewById(R.id.sendreply);
                sendreply.setOnClickListener(managerAcitivity.this);

                mRecyclerView = (RecyclerView) findViewById(R.id.manager_advice_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(managerAcitivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new managerAdviceViewAdapter(getAdviceDataSet());
                mRecyclerView.setAdapter(mAdapter);
                ((managerAdviceViewAdapter) mAdapter).setOnItemClickListener(new managerAdviceViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        findViewById(R.id.managerAdvice).setVisibility(View.GONE);
                        findViewById(R.id.managerAdviceContent).setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext()," Clicked on Item " + position,Toast.LENGTH_SHORT).show();
                    }
                });

                toolbar.setTitle("意見回饋");
            }
        });

        managerSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.managerSetting);

                language=(Button) findViewById(R.id.managerLanguage_btn);
                character=(Button) findViewById(R.id.managerCharacter_btn);

                language.setOnClickListener(managerAcitivity.this);
                character.setOnClickListener(managerAcitivity.this);

                toolbar.setTitle("管理者設定");
            }
        });


        managerSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "管理者登出", Toast.LENGTH_SHORT).show();
                Intent login = new Intent(managerAcitivity.this, visiterAcitivity.class);
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

        toolbar = (Toolbar) findViewById(R.id.manager_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.manager_fab);
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
        findViewById(R.id.managerContentMain).setVisibility(View.GONE);
        findViewById(R.id.managerAudit).setVisibility(View.GONE);
        findViewById(R.id.managerSearch).setVisibility(View.GONE);
        findViewById(R.id.managerSearchResult).setVisibility(View.GONE);
        findViewById(R.id.managerSearchDetail).setVisibility(View.GONE);
        findViewById(R.id.managerRecommed).setVisibility(View.GONE);
        findViewById(R.id.managerRecommedContent).setVisibility(View.GONE);
        findViewById(R.id.managerRecommedAdd).setVisibility(View.GONE);
        findViewById(R.id.managerRecommedModify).setVisibility(View.GONE);
        findViewById(R.id.managerAdvice).setVisibility(View.GONE);
        findViewById(R.id.managerAdviceContent).setVisibility(View.GONE);
        findViewById(R.id.managerSetting).setVisibility(View.GONE);
        onBackPressed();
        findViewById(showLayout).setVisibility(View.VISIBLE);
    }

    public void showContent1(int showLayout){
        findViewById(R.id.managerContentMain).setVisibility(View.GONE);
        findViewById(R.id.managerAudit).setVisibility(View.GONE);
        findViewById(R.id.managerSearch).setVisibility(View.GONE);
        findViewById(R.id.managerSearchResult).setVisibility(View.GONE);
        findViewById(R.id.managerSearchDetail).setVisibility(View.GONE);
        findViewById(R.id.managerRecommed).setVisibility(View.GONE);
        findViewById(R.id.managerRecommedContent).setVisibility(View.GONE);
        findViewById(R.id.managerRecommedAdd).setVisibility(View.GONE);
        findViewById(R.id.managerRecommedModify).setVisibility(View.GONE);
        findViewById(R.id.managerAdvice).setVisibility(View.GONE);
        findViewById(R.id.managerAdviceContent).setVisibility(View.GONE);
        findViewById(R.id.managerSetting).setVisibility(View.GONE);
        findViewById(showLayout).setVisibility(View.VISIBLE);
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
        search.setOnClickListener(managerAcitivity.this);

        areaselect.setOnItemSelectedListener(managerAcitivity.this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                managerAcitivity.this, android.R.layout.simple_spinner_item, commonInfo.areaitems);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaselect.setAdapter(aa);
        searchname.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

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

    private ArrayList<managerAuditDataObject> getAuditDataSet() {
        ArrayList results = new ArrayList<managerAuditDataObject>();
        for (int index = 0; index < 20; index++) {
            managerAuditDataObject obj = new managerAuditDataObject("店名 " + index, "申請時間 " + index);
            results.add(index, obj);
        }
        return results;
    }

    private ArrayList<managerRecommedDataObject> getRecommedDataSet() {
        ArrayList results = new ArrayList<managerRecommedDataObject>();
        for (int index = 0; index < 10; index++) {
            managerRecommedDataObject obj = new managerRecommedDataObject("店名 " + index, "申請時間 " + index);
            results.add(index, obj);
        }
        return results;
    }

    private ArrayList<managerRecommedContentDataObject> getRecommedContentDataSet() {
        ArrayList results = new ArrayList<managerRecommedContentDataObject>();
        for (int index = 0; index < 15; index++) {
            managerRecommedContentDataObject obj = new managerRecommedContentDataObject("景點 " + index, "地址" + index);
            results.add(index, obj);
        }
        return results;
    }

   private ArrayList<managerAdviceDataObject> getAdviceDataSet() {
        ArrayList results = new ArrayList<managerAdviceDataObject>();
        for (int index = 0; index < 5; index++) {
            managerAdviceDataObject obj = new managerAdviceDataObject("帳號 " + index, "意見內容 " + index);
            results.add(index, obj);
        }
        return results;
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        managerViewPicPager=(ViewPager)findViewById(R.id.managerViewPicPager);
        tipsBox=(LinearLayout)findViewById(R.id.managerTipsBox);

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

        managerViewPicPager.setAdapter(new commonSearchPicAdapter(this));

        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.managerViewPager);
        myFragmentPagerAdapter = new managerSearchFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.managerTabLayout);
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
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.managerLanguage_btn) {
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
                    Intent refresh = new Intent(managerAcitivity.this, managerAcitivity.class);
                    startActivity(refresh);
                    finish();
                    Toast.makeText(getBaseContext(), items[item], Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if(v.getId()==R.id.managerCharacter_btn){
            final CharSequence[] items = { "大", "適中" };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("字體設定");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if(items[item].equals("適中"))
                        commonInfo.theme = R.style.AppTheme_NoActionBar;
                    else
                        commonInfo.theme = R.style.AppTheme_Big_NoActionBar;
                    Intent refresh = new Intent(managerAcitivity.this, managerAcitivity.class);
                    startActivity(refresh);
                    finish();
                    Toast.makeText(getBaseContext(),items[item],Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else if(v.getId()==R.id.search_btn){
            findViewById(R.id.managerSearch).setVisibility(View.GONE);
            findViewById(R.id.managerSearchResult).setVisibility(View.VISIBLE);

            mRecyclerView = (RecyclerView) viewLayout.findViewById(R.id.common_search_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(managerAcitivity.this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new commonSearchViewAdapter(getSearchDataSet());
            mRecyclerView.setAdapter(mAdapter);
            ((commonSearchViewAdapter) mAdapter).setOnItemClickListener(new commonSearchViewAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    findViewById(R.id.managerSearchResult).setVisibility(View.GONE);
                    findViewById(R.id.managerSearchDetail).setVisibility(View.VISIBLE);
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
        }else if (v.getId() == R.id.sendreply) {
            String reply = enterreply.getText().toString();

            //UserFunctions userFunction = new UserFunctions();
            //userFunction.addReply(reply);

            sendMail("delicious47959@gmail.com", "首選好食道", "首選好食道 郵件測試");
            showContent1(R.id.managerAdvice);
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
            Toast.makeText(getApplicationContext(),searchCatetoryList.size()+"",Toast.LENGTH_SHORT).show();
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

    public void sendMail(String mailAddress, String mailSubject, String mailContent){
        if(!isOnline())
            Toast.makeText(getBaseContext(), "沒有網路", Toast.LENGTH_SHORT).show();
        else {
            final String username = "delicious47959@gmail.com";
            final String password = "delicious1215";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(username, password);
                }
            });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("delicious47959@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(mailAddress));
                message.setSubject(mailSubject);
                message.setText(mailContent);

                /*
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();
                messageBodyPart = new MimeBodyPart();
                String file = "path of file to be attached";
                String fileName = "attachmentName"
                DataSource source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fileName);
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                */

                Transport.send(message);
                Toast.makeText(getBaseContext(), "mail寄送完成", Toast.LENGTH_SHORT).show();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}



