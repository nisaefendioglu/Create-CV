package com.apps.resumebuilderapp.skills;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.utils.SessionManager;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class Skills_activity extends AppCompatActivity {

    private LinearLayout skillsContainer;
    private SessionManager sessionManager;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_activity);

        facebookBannerAdView();

        sessionManager = new SessionManager(Skills_activity.this);

        skillsContainer = (LinearLayout) findViewById(R.id.skillsContainer);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.skillsContainer);
        if (fragment == null) {
            fragment = new Skills_fragment();
            fm.beginTransaction().add(R.id.skillsContainer, fragment).commit();
        }
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
    protected void onResume() {
        super.onResume();
        setCustomTheme();
    }

    private void setCustomTheme() {
        skillsContainer.setBackgroundColor(getResources().getColor(android.R.color.transparent));

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

            case "Mavi": //bkg1
                skillsContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg1));
                break;

            case "Mor": //bkg2
                skillsContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg2));
                break;

            case "Turuncu": //bkg3
                skillsContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg3));
                break;

            case "Kahverengi": //bkg4
                skillsContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg4));
                break;

            case "Beyaz":
            case "Default":
                skillsContainer.setBackgroundDrawable(null);
                skillsContainer.setBackgroundColor(getResources().getColor(R.color.white));
                break;

            case "Gri": //bkg5
                skillsContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg5));
                break;

            case "Yeşil": //bkg6
                skillsContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg6));
                break;

            case "Teal": //bkg7
                skillsContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg7));
                break;

            case "Kırmızı": //bkg8
                skillsContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg8));
                break;

            case "Indigo": //bkg9
                skillsContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg9));
                break;
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

    private void facebookBannerAdView() {
        try {
            adView = new AdView(this, (getString(R.string.banner_facebook)), AdSize.BANNER_HEIGHT_50);
            LinearLayout adContainer = findViewById(R.id.banner_container);
            adContainer.setVisibility(View.VISIBLE);
            adContainer.addView(adView);
            adView.loadAd();
        } catch (Exception e) {
            Log.d("Reklam Hatası", "" + e);
        }
    }
}
