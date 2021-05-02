package com.apps.resumebuilderapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.apps.resumebuilderapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SessionManager extends Activity {

    public static final String PIN_LOCK = "pinLock";
    public static final String PASSWORD = "password";
    public static final String CUSTOM_APP_THEME = "appTheme";
    public static final String ORDER_SECTION = "orderSection";
    public static final String SELECTED_FONT = "fontName";
    public static final String NAME_SIZE = "nameSize";
    public static final String TITLE_SIZE = "titleSize";
    public static final String CONTENT_SIZE = "contentSize";
    public static final String NAME_COLOR = "nameColor";
    public static final String TITLE_COLOR = "titleColor";
    public static final String CONTENT_COLOR = "contentColor";

    // Editor for Shared preferences
    public static SharedPreferences.Editor editor;
    // Shared Preferences
    static SharedPreferences pref;
    // Context
    Context context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(context.getString(R.string.app_name), PRIVATE_MODE);
        editor = pref.edit();
    }

    public void savePinLock(boolean value1) {
        editor.putBoolean(PIN_LOCK, value1);
        editor.commit();
    }

    public boolean getPinLock() {
        return pref.getBoolean(PIN_LOCK, false);
    }

    public void savePassword(String value1) {
        editor.putString(PASSWORD, value1);
        editor.commit();
    }

    public String getPassword() {
        return pref.getString(PASSWORD, "");
    }

    public void saveAppTheme(String value1) {
        editor.putString(CUSTOM_APP_THEME, value1);
        editor.commit();
    }

    public String getAppTheme() {
        return pref.getString(CUSTOM_APP_THEME, "White");
    }

    public static void save_orderSection(ArrayList<String> searchHistoryList) {
        Gson gson = new Gson();
        String json = gson.toJson(searchHistoryList);
        editor.putString(ORDER_SECTION, json);
        editor.commit();
    }

    public static ArrayList<String> get_orderSection() {

        Gson gson = new Gson();
        String json = pref.getString(ORDER_SECTION, "");
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();

        ArrayList<String> searchHistoryList = gson.fromJson(json, type);

        Global.printLog("get_orderSection===", "==searchHistoryList==" + searchHistoryList);
        if (searchHistoryList != null)
            return searchHistoryList;
        else {
            ArrayList<String> mDatas = new ArrayList<>();

            mDatas.add("Hedef");
            mDatas.add("Profeyonel Özet");
            mDatas.add("Eğitim");
            mDatas.add("Deneyim");
            mDatas.add("Beceriler");
            mDatas.add("Başarılar");
            mDatas.add("Referanslar");

            return mDatas;
        }
    }

    public String getStringPref(String Key) {
        return pref.getString(Key, "");
    }

    public void setStringPref(String Key, String value) {
        editor.putString(Key, value);
        editor.commit();
    }
}
