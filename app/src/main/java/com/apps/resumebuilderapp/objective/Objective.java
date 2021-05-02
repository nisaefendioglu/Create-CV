package com.apps.resumebuilderapp.objective;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.utils.SessionManager;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Objective extends AppCompatActivity {

    private EditText mObjective;
    private LinearLayout lnr_main;
    SharedPreferences sh;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective);

        facebookBannerAdView();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mObjective = (EditText) findViewById(R.id.objective);
        lnr_main = (LinearLayout) findViewById(R.id.lnr_main);

        sessionManager = new SessionManager(Objective.this);
    }

    /* action menu items click handle */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        sh = getSharedPreferences("Objective",
                Context.MODE_PRIVATE);
        sh.edit().putString("message", mObjective.getText().toString().trim()).commit();
//        Toast.makeText(getApplicationContext(), R.string.objectiveSave,
//                Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        SharedPreferences sh = getSharedPreferences("Objective", Context.MODE_PRIVATE);
        mObjective.setText(sh.getString("message", "").trim());
        super.onResume();
        
        setCustomTheme();
    }

    private void setCustomTheme() {
        lnr_main.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        switch (sessionManager.getAppTheme()) {
            
          /*public static String custom_theme1 = "Blue";
            public static String custom_theme2 = "Purple";
            public static String custom_theme3 = "Orange";
            public static String custom_theme4 = "Brown";
            public static String custom_theme5 = "Sky"; // Default theme
            public static String custom_theme6 = "Gray";
            public static String custom_theme7 = "Green";
            public static String custom_theme8 = "Teal";
            public static String custom_theme9 = "Red";
            public static String custom_theme10 = "Indigo";*/

            case "Blue": //bkg1
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg1));
                break;

            case "Purple": //bkg2
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg2));
                break;

            case "Orange": //bkg3
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg3));
                break;

            case "Brown": //bkg4
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg4));
                break;

            case "White":
            case "Default":
                lnr_main.setBackgroundDrawable(null);
                lnr_main.setBackgroundColor(getResources().getColor(R.color.white));
                break;

            case "Gray": //bkg5
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg5));
                break;

            case "Green": //bkg6
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg6));
                break;

            case "Teal": //bkg7
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg7));
                break;

            case "Red": //bkg8
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg8));
                break;

            case "Indigo": //bkg9
                lnr_main.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg9));
                break;
        }
    }

    private void facebookBannerAdView() {
        try {
            adView = new AdView(this, (getString(R.string.banner_facebook)), AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = findViewById(R.id.banner_container);
            adContainer.setVisibility(View.VISIBLE);
            adContainer.addView(adView);
            adView.loadAd();
        } catch (Exception e) {
            Log.d("My AD ERROR", "" + e);
        }
    }

    private AdView adView;
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
