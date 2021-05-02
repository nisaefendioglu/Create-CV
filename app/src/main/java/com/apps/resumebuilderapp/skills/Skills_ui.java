package com.apps.resumebuilderapp.skills;

import java.util.UUID;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.skills.model.SkillsData;
import com.apps.resumebuilderapp.skills.model.SkillsDataList;
import com.apps.resumebuilderapp.utils.Global;
import com.apps.resumebuilderapp.utils.SessionManager;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Skills_ui extends AppCompatActivity {

    private EditText mSkillsData;
    private LinearLayout skillsContainer;
    private SessionManager sessionManager;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_ui);

        facebookBannerAdView();

        sessionManager = new SessionManager(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        skillsContainer = (LinearLayout) findViewById(R.id.skillsContainer);

        mSkillsData = (EditText) findViewById(R.id.SkillsDataEditText);
        UUID selectId = (UUID) getIntent().getSerializableExtra(
                Skills_fragment.SkillsData_ID);
        SkillsData p = SkillsDataList.getInstance(this)
                .getPendingJob(selectId);

        Global.printLog("skills===p===", "" + p + "====selectedID===" + selectId);
        if (p == null) {
            return;
        } else {
            try {
                if (p != null
                        && !p.toString().trim().equalsIgnoreCase("null")
                        && !p.toString().trim().equals("")
                        && !p.toString().trim().isEmpty()
                        && !p.toString().trim().equals(null)) {
                    mSkillsData.setText(p.toString().trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (!mSkillsData.getText().toString().trim().equals("")) {
            UUID selectId = (UUID) getIntent().getSerializableExtra(
                    Skills_fragment.SkillsData_ID);
            SkillsData p = SkillsDataList.getInstance(this).getPendingJob(selectId);
            p.setmPersonalSummary(mSkillsData.getText().toString().trim());

        } else {
            UUID selectId = (UUID) getIntent().getSerializableExtra(
                    Skills_fragment.SkillsData_ID);
            SkillsData p = SkillsDataList.getInstance(this)
                    .getPendingJob(selectId);
            SkillsDataList.getInstance(this).getPendingJobs()
                    .remove(p);
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

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
