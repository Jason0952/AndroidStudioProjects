package com.example.home.common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import library.UserFunctions;

public class commonInfo {
    public static int theme;
    public static String KEY_SUCCESS = "success";
    public static String jsonStatus="";
    public static String[] areaitems = { "全選", "台北市", "新北市" };
    public static String[] taipeiitems = { "全選", "松山區","大安區","大同區","中山區","內湖區","南港區","士林區","北投區","信義區","中正區","萬華區","文山區" };
    public static String[] newtaipeiitems = {"全選", "板橋區","三重區","中和區","新莊區","永和區","新店區","土城區","蘆洲區","樹林區","汐止區","三峽區","淡水區","鶯歌區","五股區","泰山區","林口區","瑞芳區","深坑區","石碇區","坪林區","三芝區","石門區","八里區","平溪區","雙溪區","貢寮區","金山區","萬里區","烏來區"};

    public static String[] addareaitems = { "台北市", "新北市" };
    public static String[] addtaipeiitems = { "松山區","大安區","大同區","中山區","內湖區","南港區","士林區","北投區","信義區","中正區","萬華區","文山區" };
    public static String[] addnewtaipeiitems = { "板橋區","三重區","中和區","新莊區","永和區","新店區","土城區","蘆洲區","樹林區","汐止區","三峽區","淡水區","鶯歌區","五股區","泰山區","林口區","瑞芳區","深坑區","石碇區","坪林區","三芝區","石門區","八里區","平溪區","雙溪區","貢寮區","金山區","萬里區","烏來區"};

    public static String[] categoryitems = { "美式餐廳", "日式料理", "韓式料理", "港式餐廳", "義式餐廳", "中式餐廳", "甜點", "冰品" };
    public static JSONObject json_restaurant, json_search_restaurant;
    public static JSONObject json_promotion;
    public static JSONObject json_mycard;
    public static ArrayList<String> searchRestaurantId= new ArrayList<String>();
    public static String searchArea="", searchRegion="", searchCategory="";
    public static String searchKeyword="";

    public static String restaurantId;
    public static String updateRestaurantId="";
    public static String storePosition;

    //member_information
    public static String memberId;
    public static String memberName;
    public static String memberNickname;
    public static String memberAccount;
    public static String memberTelephone;
    public static String memberEmail;

    //restaurant_information
    public static String restaurantName;
    public static String restaurantAccount;
    public static String restaurantTelephone;
    public static String restaurantArea;
    public static String restaurantAddress;
    public static String restaurantCategory;
    public static String restaurantEmail;

    //user_information
    public static String languageSetting;

    public static void getRestaurantInformation(int id){
        restaurantId=id+"";
        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.restaurantinformation(restaurantId);
        try {
            if (json.getString(commonInfo.KEY_SUCCESS) != null)
                json_restaurant = json.getJSONObject("restaurant");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getSearchRestaurantInformation(boolean keywordSearch){
        UserFunctions userFunction = new UserFunctions();
        JSONObject json;
        if(keywordSearch)
            json = userFunction.searchRestaurantInformationKeyword(searchKeyword);
        else
            json = userFunction.searchRestaurantInformation(searchArea, searchRegion, searchCategory);

        try {
            if (json.getString(commonInfo.KEY_SUCCESS) != null) {
                jsonStatus="SUCCESS";
                json_search_restaurant = json.getJSONObject("restaurantSearch");
            }
        } catch (JSONException e) {
            jsonStatus="FAIL";
            e.printStackTrace();
        }
    }

    public static void getPromotions(String promotionID){
        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.getPromotions(promotionID);
        try {
            if (json.getString(commonInfo.KEY_SUCCESS) != null)
                json_promotion = json.getJSONObject("promotions");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getmycard(String restaurantID){
        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.getmycard(restaurantID);
        try {
            if (json.getString(commonInfo.KEY_SUCCESS) != null)
                json_mycard = json.getJSONObject("restaurant");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
