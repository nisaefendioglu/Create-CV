package com.apps.resumebuilderapp.professionalSummary;

import java.util.UUID;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.professionalSummary.model.ProfessionalSummary;
import com.apps.resumebuilderapp.professionalSummary.model.ProfessionalSummaryList;
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

public class Professional_Summary_Ui_Activity extends AppCompatActivity {

    private EditText mProfessionalSummary;
    private LinearLayout lnr_main;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professional_summary_ui);

        facebookBannerAdView();

        sessionManager = new SessionManager(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProfessionalSummary = (EditText) findViewById(R.id.ProfessionalSummaryEditText);
        lnr_main = (LinearLayout) findViewById(R.id.lnr_main);

        UUID selectId = (UUID) getIntent().getSerializableExtra(
                Professional_summary_fragment.ProfeesionalSummary_ID);
        ProfessionalSummary p = ProfessionalSummaryList.getInstance(this)
                .getPendingJob(selectId);
        if (!p.getmPersonalSummary().equals(""))
            mProfessionalSummary.setText(p.getmPersonalSummary().trim());
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
        if (!mProfessionalSummary.getText().toString().equals("")) {
            UUID selectId = (UUID) getIntent().getSerializableExtra(
                    Professional_summary_fragment.ProfeesionalSummary_ID);
            ProfessionalSummary p = ProfessionalSummaryList.getInstance(this)
                    .getPendingJob(selectId);
            p.setmPersonalSummary(mProfessionalSummary.getText().toString().trim());
        } else {
            UUID selectId = (UUID) getIntent().getSerializableExtra(
                    Professional_summary_fragment.ProfeesionalSummary_ID);
            ProfessionalSummary p = ProfessionalSummaryList.getInstance(this)
                    .getPendingJob(selectId);
            ProfessionalSummaryList.getInstance(this).getPendingJobs()
                    .remove(p);

        }
    }

    @Override
    protected void onResume() {
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
            Log.d("My AD ERROR", "" + e);
        }
    }
}
