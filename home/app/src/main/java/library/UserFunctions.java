/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package library;

import android.content.Context;
import android.util.Log;

import com.example.home.common.commonInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFunctions {
	
	private JSONParser jsonParser;
	//http://www.chiens.idv.tw/test/
	//https://h10271808.000webhostapp.com
	private static String loginURL = "https://h10271808.000webhostapp.com/index.php";
	private static String registerURL = "https://h10271808.000webhostapp.com/index.php";
	private static String registerMemberURL = "https://h10271808.000webhostapp.com/member.php";
	private static String registerRestaurantURL = "https://h10271808.000webhostapp.com/restaurant.php";
	private static String restaurantURL = "https://h10271808.000webhostapp.com/index.php";
	private static String promotionURL = "https://h10271808.000webhostapp.com/index.php";
	//private static String restaurantURL = "http://www.chiens.idv.tw/test/index.php";

	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String restaurant_tag = "getrestaurant";
	private static String restaurantSearch_tag = "getrestaurantsearch";
	private static String restaurantSearchKeyword_tag = "getrestaurantsearchkeyword";

	private static String promotionGet_tag = "getpromotion";
	private static String mycardGet_tag = "getmycard";

	private static String restaurantaAdd_tag = "addrestautant";
    private static String opinionAdd_tag = "addOpinion";
    private static String promotionAdd_tag = "addPromotion";
    private static String placeinformationAdd_tag = "addPlaceinformation";
    private static String messageAdd_tag = "addMessage";


	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param account
	 * @param password
	 * */
	public JSONObject loginUser(String account, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

		//Log.e("JSON", json.toString());
		return json;
	}

	public JSONObject restaurantinformation(String restaurantid){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", restaurant_tag)); //送資料過去
		params.add(new BasicNameValuePair("restaurantid", restaurantid));
		JSONObject json = jsonParser.getJSONFromUrl(restaurantURL, params); //撈資料回來

		//Log.e("JSON", json.toString());
		return json;
	}

    public JSONObject getPromotions(String promotionsid){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", promotionGet_tag)); //送資料過去
        params.add(new BasicNameValuePair("promotionsid", promotionsid));
        JSONObject json = jsonParser.getJSONFromUrl(promotionURL, params); //撈資料回來

        //Log.e("JSON", json.toString());
        return json;
    }

	public JSONObject getmycard(String restaurantid){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", mycardGet_tag)); //送資料過去
		params.add(new BasicNameValuePair("restaurantid", restaurantid));
		JSONObject json = jsonParser.getJSONFromUrl(restaurantURL, params); //撈資料回來

		//Log.e("JSON", json.toString());
		return json;
	}

	public JSONObject searchRestaurantInformation(String searchArea, String searchRegion, String searchCategory){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", restaurantSearch_tag)); //送資料過去
		params.add(new BasicNameValuePair("searchArea", searchArea));
		params.add(new BasicNameValuePair("searchRegion", searchRegion));
		params.add(new BasicNameValuePair("searchCategory", searchCategory));
		JSONObject json = jsonParser.getJSONFromUrl(restaurantURL, params); //撈資料回來

		//Log.e("JSON", json.toString());
		return json;
	}

	public JSONObject searchRestaurantInformationKeyword(String keyword){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", restaurantSearchKeyword_tag)); //送資料過去
		params.add(new BasicNameValuePair("searchKeyword", keyword));
		JSONObject json = jsonParser.getJSONFromUrl(restaurantURL, params); //撈資料回來

		//Log.e("JSON", json.toString());
		return json;
	}

	public JSONObject addRestaurant(String restaurantname, String address, String areaselect, String regionselect, String categoryselect, String transport, String telephone, String time, String sit, String remarks, String about, String menu, String recommend){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", restaurantaAdd_tag)); //送資料過去
		params.add(new BasicNameValuePair("restaurantname", restaurantname));
		params.add(new BasicNameValuePair("address", address));
		params.add(new BasicNameValuePair("areaselect", areaselect));
		params.add(new BasicNameValuePair("regionselect", regionselect));
		params.add(new BasicNameValuePair("categoryselect", categoryselect));
		params.add(new BasicNameValuePair("transport", transport));
		params.add(new BasicNameValuePair("telephone", telephone));
		params.add(new BasicNameValuePair("time", time));
		params.add(new BasicNameValuePair("sit", sit));
		params.add(new BasicNameValuePair("remarks", remarks));
		params.add(new BasicNameValuePair("about", about));
		params.add(new BasicNameValuePair("menu", menu));
		params.add(new BasicNameValuePair("recommend", recommend));
		JSONObject json = jsonParser.getJSONFromUrl(restaurantURL, params); //撈資料回來

		//Log.e("JSON", json.toString());

		try {
			if (json.getString(commonInfo.KEY_SUCCESS) != null)
				commonInfo.updateRestaurantId = json.getJSONObject("addRestaurant").getString("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public JSONObject addPromoiotn(String promotioncontent, String dateline, String startdate){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", promotionAdd_tag)); //送資料過去
		params.add(new BasicNameValuePair("promotioncontent", promotioncontent));
		params.add(new BasicNameValuePair("dateline", dateline));
		params.add(new BasicNameValuePair("startdate", startdate));
		JSONObject json = jsonParser.getJSONFromUrl(restaurantURL, params); //撈資料回來

		//Log.e("JSON", json.toString());
/*
		try {
			if (json.getString(commonInfo.KEY_SUCCESS) != null)
				commonInfo.updateRestaurantId = json.getJSONObject("addPromoiotn").getString("promotionsid");
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		return json;
	}

    public JSONObject addOpinion(String name, String cellphone, String mail, String opinioncontent){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", opinionAdd_tag)); //送資料過去
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("cellphone", cellphone));
        params.add(new BasicNameValuePair("mail", mail));
        params.add(new BasicNameValuePair("opinioncontent", opinioncontent));
        JSONObject json = jsonParser.getJSONFromUrl(restaurantURL, params); //撈資料回來

        //Log.e("JSON", json.toString());
/*
        try {
            if (json.getString(commonInfo.KEY_SUCCESS) != null)
                commonInfo.updateRestaurantId = json.getJSONObject("addOpinion").getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
        return json;
    }

    public JSONObject addPlaceinformation(String placename, String placeaddress, String placetelephone, String distance, String time){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", placeinformationAdd_tag)); //送資料過去
        params.add(new BasicNameValuePair("placename", placename));
        params.add(new BasicNameValuePair("placeaddress", placeaddress));
        params.add(new BasicNameValuePair("placetelephone", placetelephone));
        params.add(new BasicNameValuePair("distance", distance));
        params.add(new BasicNameValuePair("time", time));
        JSONObject json = jsonParser.getJSONFromUrl(restaurantURL, params); //撈資料回來

        //Log.e("JSON", json.toString());
/*
        try {
            if (json.getString(commonInfo.KEY_SUCCESS) != null)
                commonInfo.updateRestaurantId = json.getJSONObject("addRestaurant").getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return json;
    }

    public JSONObject addReply(String reply){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", restaurantaAdd_tag)); //送資料過去
        params.add(new BasicNameValuePair("reply", reply));

        JSONObject json = jsonParser.getJSONFromUrl(restaurantURL, params); //撈資料回來

        //Log.e("JSON", json.toString());

        try {
            if (json.getString(commonInfo.KEY_SUCCESS) != null)
                commonInfo.updateRestaurantId = json.getJSONObject("addRestaurant").getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public JSONObject addMessage(String member_message){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", messageAdd_tag)); //送資料過去
        params.add(new BasicNameValuePair("member_message", member_message));

        JSONObject json = jsonParser.getJSONFromUrl(restaurantURL, params); //撈資料回來

        //Log.e("JSON", json.toString());
/*
        try {
            if (json.getString(commonInfo.KEY_SUCCESS) != null)
                commonInfo.updateRestaurantId = json.getJSONObject("addRestaurant").getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        return json;
    }

	/**
	 * function make Login Request
	 * @param name
	 * @param account
	 * @param password
	 * */
	public JSONObject registerUser(String name, String account, String password, String type){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("type", type));

		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);

		return json;
	}

	public JSONObject registerMember(String name, String nickname, String account, String password, String birthday, String telephone, String email){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("nickname", nickname));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("birthday", birthday));
		params.add(new BasicNameValuePair("telephone", telephone));
		params.add(new BasicNameValuePair("email", email));

		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerMemberURL, params);

		return json;
	}

	public JSONObject registerRestaurant(String restaurantname, String account, String password, String telephone, String area, String address, String category, String email){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("restaurantname", restaurantname));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("telephone", telephone));
		params.add(new BasicNameValuePair("area", area));
		params.add(new BasicNameValuePair("address", address));
		params.add(new BasicNameValuePair("category", category));
		params.add(new BasicNameValuePair("email", email));

		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerRestaurantURL, params);

		return json;
	}

	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0)
			return true;
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}



}
