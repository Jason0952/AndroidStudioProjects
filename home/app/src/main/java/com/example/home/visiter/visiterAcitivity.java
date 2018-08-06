package com.example.home.visiter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.home.R;
import com.example.home.common.commonInfo;
import com.example.home.common.commonSearchPicAdapter;
import com.example.home.common.commonSearchViewAdapter;
import com.example.home.member.memberAcitivity;
import com.example.home.common.commonSearchDataObject;
import com.example.home.common.commonSearchFragmentAdapter;
import com.example.home.restaurant.restaurantAcitivity;
import com.example.home.manager.managerAcitivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import library.UserFunctions;

public class visiterAcitivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, View.OnClickListener,
        ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener{
    Button visiter, login, search1, setting, signup1, signup2, signin, forgetpassword;
    EditText account, password;
    Button language, character, suggestion, send;
    EditText entername, entercellphone, enteremail, enteropinion2;


    boolean keywordSearch=false;
    Button search;
    Spinner areaselect, regionselect;
    EditText searchname;
    ToggleButton american, japan, korea, hongkong, italy, chinese, dessert, ice;
    searchItemClick searchClick=new searchItemClick();
    private ArrayList<String> searchCatetoryList = new ArrayList<String>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //註冊頁面
    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private Class fragmentArray[] = { memberRegisterFragment.class, restraurantRegisterFragment.class };
    private int imageViewArray[] = { R.drawable.tab_home_btn, R.drawable.tab_view_btn };
    private String textViewArray[] = { "會員註冊", "餐廳註冊"};
    private List<Fragment> list = new ArrayList<Fragment>();
    private ViewPager vp;
    Toolbar toolbar;

    private TabLayout mTabLayout;
    private ViewPager mViewPager, visiterViewPicPager;
    LinearLayout tipsBox;
    private commonSearchFragmentAdapter myFragmentPagerAdapter;
    private TabLayout.Tab one, two, three, four;

    View viewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(commonInfo.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initMenuContent(R.id.visiter_menu,R.id.visiter_layout);

        //memu button
        visiter=(Button) findViewById(R.id.visiter);
        login=  (Button) findViewById(R.id.loginin);
        search1=(Button) findViewById(R.id.search1);
        setting=(Button) findViewById(R.id.setting);
        signup2=(Button) findViewById(R.id.signup2);
        signup1=(Button) findViewById(R.id.signup1);
        forgetpassword=(Button) findViewById(R.id.forgetpassword);

        tabInitView();//初始化控件
        tabInitPage();//初始化页面

        visiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.visiterContentMain);
                onBackPressed();
                toolbar.setTitle("訪客");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.visiterLogin);

                signin=(Button) findViewById(R.id.signin);
                account=(EditText) findViewById(R.id.accountnumber);
                password=(EditText) findViewById(R.id.passwordnumber);

                signin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            String acc = account.getText().toString();
                            String pass = password.getText().toString();
                            UserFunctions userFunction = new UserFunctions();
                            JSONObject json = userFunction.loginUser(acc, pass);

                            try {
                                if (json.getString(commonInfo.KEY_SUCCESS) != null) {
                                    String res = json.getString(commonInfo.KEY_SUCCESS);
                                    JSONObject json_user = json.getJSONObject("user");
                                    String type = json_user.getString("type");
                                    if(Integer.parseInt(res) == 1 && Integer.parseInt(type) == 1) {
                                        commonInfo.memberId = json_user.getString("uid");
                                        JSONObject json_member = json.getJSONObject("member");
                                        commonInfo.memberName =json_member.getString("name");
                                        commonInfo.memberNickname = json_member.getString("nickname");
                                        commonInfo.memberAccount = json_member.getString("account");
                                        commonInfo.memberTelephone = json_member.getString("telephone");
                                        commonInfo.memberEmail = json_member.getString("email");

                                        Toast.makeText(getApplicationContext(), "會員登入成功", Toast.LENGTH_SHORT).show();
                                        Intent login = new Intent(visiterAcitivity.this, memberAcitivity.class);
                                        startActivity(login);
                                        finish();
                                    }else if(Integer.parseInt(res) == 1 && Integer.parseInt(type) == 2) {
                                        commonInfo.restaurantId = json_user.getString("uid");
                                        JSONObject json_restaurant = json.getJSONObject("restaurant");
                                        commonInfo.restaurantName =json_restaurant.getString("name");
                                        commonInfo.restaurantAccount = json_restaurant.getString("account");
                                        commonInfo.restaurantTelephone = json_restaurant.getString("telephone");
                                        commonInfo.restaurantArea = json_restaurant.getString("area");
                                        commonInfo.restaurantAddress = json_restaurant.getString("address");
                                        commonInfo.restaurantCategory = json_restaurant.getString("category");
                                        commonInfo.restaurantEmail = json_restaurant.getString("email");

                                        Toast.makeText(getApplicationContext(), "餐廳登入成功", Toast.LENGTH_SHORT).show();
                                        Intent login = new Intent(visiterAcitivity.this, restaurantAcitivity.class);
                                        startActivity(login);
                                        finish();
                                    }else if(Integer.parseInt(res) == 1 && Integer.parseInt(type) == 0) {
                                        Toast.makeText(getApplicationContext(), "管理者登入成功", Toast.LENGTH_SHORT).show();
                                        Intent login = new Intent(visiterAcitivity.this, managerAcitivity.class);
                                        startActivity(login);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "登入失敗", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                });
                onBackPressed();
                toolbar.setTitle("登入");
            }
        });

        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.visiterSearch);
                initSearchContent();
                onBackPressed();
                toolbar.setTitle("訪客搜尋");
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.visiterSetting);

                language=(Button)   viewLayout.findViewById(R.id.Language_btn);
                character=(Button)  viewLayout.findViewById(R.id.Character_btn);
                suggestion=(Button) viewLayout.findViewById(R.id.Suggestion_btn);
                send = (Button) viewLayout.findViewById(R.id.send);
                entername = (EditText) viewLayout.findViewById(R.id.entername);
                entercellphone = (EditText) viewLayout.findViewById(R.id.entercellphone);
                enteremail = (EditText) viewLayout.findViewById(R.id.enteremail);
                enteropinion2 = (EditText) viewLayout.findViewById(R.id.enteropinion2);

                send.setOnClickListener(visiterAcitivity.this);
                language.setOnClickListener(visiterAcitivity.this);
                character.setOnClickListener(visiterAcitivity.this);
                suggestion.setOnClickListener(visiterAcitivity.this);

                onBackPressed();
                toolbar.setTitle("訪客設定");

            }
        });

        signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.visiterSignup);
                onBackPressed();
                toolbar.setTitle("訪客註冊");
            }
        });

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.visiterSignup);
                toolbar.setTitle("訪客註冊");
            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContent(R.id.visiterForgetpassword);
                toolbar.setTitle("");
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

        toolbar = (Toolbar) findViewById(R.id.visiter_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.visiter_fab);
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
        findViewById(R.id.visiterContentMain).setVisibility(View.GONE);
        findViewById(R.id.visiterLogin).setVisibility(View.GONE);
        findViewById(R.id.visiterSearch).setVisibility(View.GONE);
        findViewById(R.id.visiterSearchResult).setVisibility(View.GONE);
        findViewById(R.id.visiterSearchDetail).setVisibility(View.GONE);
        findViewById(R.id.visiterSetting).setVisibility(View.GONE);
        findViewById(R.id.visiterOpinion).setVisibility(View.GONE);
        findViewById(R.id.visiterSignup).setVisibility(View.GONE);
        findViewById(R.id.visiterForgetpassword).setVisibility(View.GONE);
        //onBackPressed();
        findViewById(showLayout).setVisibility(View.VISIBLE);
    }

    public void showContent1(int showLayout){
        findViewById(R.id.visiterContentMain).setVisibility(View.GONE);
        findViewById(R.id.visiterLogin).setVisibility(View.GONE);
        findViewById(R.id.visiterSearch).setVisibility(View.GONE);
        findViewById(R.id.visiterSearchResult).setVisibility(View.GONE);
        findViewById(R.id.visiterSearchDetail).setVisibility(View.GONE);
        findViewById(R.id.visiterSetting).setVisibility(View.GONE);
        findViewById(R.id.visiterOpinion).setVisibility(View.GONE);
        findViewById(R.id.visiterSignup).setVisibility(View.GONE);
        findViewById(R.id.visiterForgetpassword).setVisibility(View.GONE);
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
        search.setOnClickListener(visiterAcitivity.this);

        areaselect.setOnItemSelectedListener(visiterAcitivity.this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                visiterAcitivity.this, android.R.layout.simple_spinner_item, commonInfo.areaitems);
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

    private ArrayList<commonSearchDataObject> getSearchDataSet()  {
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
            if (commonInfo.jsonStatus.equals("FAIL"))
                Toast.makeText(this,"搜尋不到餐廳",Toast.LENGTH_SHORT).show();
            else {
                for (int index = 0; index < Integer.parseInt(commonInfo.json_search_restaurant.getString("restaurantcount")); index++) {
                    searchIndex = Integer.parseInt(commonInfo.json_search_restaurant.getJSONArray("restaurantid").getString(index));
                    commonInfo.getRestaurantInformation(searchIndex);
                    commonSearchDataObject obj = null;
                    obj = new commonSearchDataObject(
                            "https://h10271808.000webhostapp.com/img/" + (searchIndex) + "/h01.jpg",
                            commonInfo.json_restaurant.getString("restaurantname"),
                            commonInfo.json_restaurant.getString("area") + commonInfo.json_restaurant.getString("region"),
                            commonInfo.json_restaurant.getString("category"),
                            commonInfo.json_restaurant.getString("time"));
                    results.add(index, obj);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }

    //    控件初始化控件
    private void tabInitView() {
        vp = (ViewPager) findViewById(R.id.pager);

        vp.addOnPageChangeListener(this);//设置页面切换时的监听器
        layoutInflater = LayoutInflater.from(this);//加载布局管理器

        /*实例化FragmentTabHost对象并进行绑定*/
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);//绑定tahost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.pager);//绑定viewpager

        mTabHost.setOnTabChangedListener(this);

        int count = textViewArray.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(textViewArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.setTag(i);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    private void tabInitPage() {
        memberRegisterFragment memberRegisterFragment = new memberRegisterFragment();
        restraurantRegisterFragment restraurantRegisterFragment = new restraurantRegisterFragment();

        list.add(memberRegisterFragment);
        list.add(restraurantRegisterFragment);

        vp.setAdapter(new registerFragmentAdapter(getSupportFragmentManager(), list));
        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    private View getTabItemView(int i) {
        View view = layoutInflater.inflate(R.layout.visiter_registered_tab_content, null);
        ImageView mImageView = (ImageView) view.findViewById(R.id.tab_imageview);
        TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
        mImageView.setBackgroundResource(imageViewArray[i]);
        mTextView.setText(textViewArray[i]);
        return view;
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

    private void searchInitViews() {
        visiterViewPicPager=(ViewPager)findViewById(R.id.visiterViewPicPager);
        tipsBox=(LinearLayout)findViewById(R.id.visiterTipsBox);
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

        visiterViewPicPager.setAdapter(new commonSearchPicAdapter(this));
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.visiterViewPager);
        myFragmentPagerAdapter = new commonSearchFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.visiterTabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        four = mTabLayout.getTabAt(3);
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
    public void onNothingSelected(AdapterView<?> adapterView) { }


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
                    Intent refresh = new Intent(visiterAcitivity.this, visiterAcitivity.class);
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
                    Intent refresh = new Intent(visiterAcitivity.this, visiterAcitivity.class);
                    startActivity(refresh);
                    finish();
                    Toast.makeText(getBaseContext(),items[item],Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        } else if(v.getId()==R.id.Suggestion_btn){
            findViewById(R.id.visiterSetting).setVisibility(View.GONE);
            findViewById(R.id.visiterOpinion).setVisibility(View.VISIBLE);

        } else if(v.getId()==R.id.search_btn) {
                mAdapter = new commonSearchViewAdapter(getSearchDataSet());
            if (!commonInfo.jsonStatus.equals("FAIL")) {
                findViewById(R.id.visiterSearch).setVisibility(View.GONE);
                findViewById(R.id.visiterSearchResult).setVisibility(View.VISIBLE);

                mRecyclerView = (RecyclerView) viewLayout.findViewById(R.id.common_search_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(visiterAcitivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mRecyclerView.setAdapter(mAdapter);
                ((commonSearchViewAdapter) mAdapter).setOnItemClickListener(new commonSearchViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {

                        findViewById(R.id.visiterSearchResult).setVisibility(View.GONE);
                        findViewById(R.id.visiterSearchDetail).setVisibility(View.VISIBLE);

                        try {
                            int searchID = Integer.parseInt(commonInfo.json_search_restaurant.getJSONArray("restaurantid").getString(position));
                            commonInfo.getRestaurantInformation(searchID);
                            toolbar.setTitle(commonInfo.json_restaurant.getString("restaurantname"));
                            commonInfo.storePosition = searchID + "";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        searchInitViews();
                    }
                });
            }
        }else if (v.getId() == R.id.send) {
            String name = entername.getText().toString();
            String cellphone = entercellphone.getText().toString();
            String mail = enteremail.getText().toString();
            String opinioncontent = enteropinion2.getText().toString();

            UserFunctions userFunction = new UserFunctions();
            userFunction.addOpinion(name, cellphone, mail, opinioncontent);

            Toast.makeText(getBaseContext(), "送出成功", Toast.LENGTH_SHORT).show();
            showContent1(R.id.visiterContentMain);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {  }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mTabHost.setCurrentTab(position);
        widget.setDescendantFocusability(oldFocusability);
    }

    @Override
    public void onPageScrollStateChanged(int state) { }

    @Override
    public void onTabChanged(String s) {
        int position = mTabHost.getCurrentTab();
        vp.setCurrentItem(position);//把选中的Tab的位置赋给适配器，让它控制页面切换
    }
}



