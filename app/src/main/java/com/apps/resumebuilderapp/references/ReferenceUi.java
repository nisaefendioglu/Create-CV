package com.apps.resumebuilderapp.references;

import java.util.UUID;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.references.model.ReferencesData;
import com.apps.resumebuilderapp.references.model.ReferencesDataList;
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

public class ReferenceUi extends AppCompatActivity {

    private EditText mNameCW;
    private EditText mEmailCW, contactOfCw;
    private EditText mCompanyName;
    private EditText mDesignationCW;
    private EditText mHowDidYouWorkWithHim;

    private LinearLayout name;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_ui);

        facebookBannerAdView();

        sessionManager = new SessionManager(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (LinearLayout) findViewById(R.id.Name);

        mNameCW = (EditText) findViewById(R.id.nameCoWorker);

        mEmailCW = (EditText) findViewById(R.id.emailCoWorker);
        contactOfCw = (EditText) findViewById(R.id.contactOfCw);

        mCompanyName = (EditText) findViewById(R.id.companynameOfCw);

        mDesignationCW = (EditText) findViewById(R.id.designationCoWorker);

        mHowDidYouWorkWithHim = (EditText) findViewById(R.id.howDidYouWorkWithHim);

        UUID selectId = (UUID) getIntent().getSerializableExtra(
                ReferencesFragment.ReferencesData_ID);
        ReferencesData p = ReferencesDataList.getInstance(this).getPendingJob(
                selectId);

        if (p != null) {
            mNameCW.setText(p.getmNameOfCoWorker().trim());
            mEmailCW.setText(p.getmEmailOfCoWorker().trim());
            contactOfCw.setText(p.getmContactOfCoWorker().trim());
            mCompanyName.setText(p.getmCompanyName().trim());
            mDesignationCW.setText(p.getmDasignationofCoWorker().trim());
            mHowDidYouWorkWithHim.setText(p.getmHowDidYouWorkWithHim().trim());
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
        if (mNameCW.getText().toString().equals("")
                && mEmailCW.getText().toString().equals("")
                && contactOfCw.getText().toString().equals("")
                && mCompanyName.getText().toString().equals("")
                && mDesignationCW.getText().toString().equals("")
                && mHowDidYouWorkWithHim.getText().toString().equals("")
                ) {
            UUID selectId = (UUID) getIntent().getSerializableExtra(
                    ReferencesFragment.ReferencesData_ID);
            ReferencesData p = ReferencesDataList.getInstance(this)
                    .getPendingJob(selectId);
            ReferencesDataList.getInstance(this).getPendingJobs().remove(p);

        } else {

            UUID selectId = (UUID) getIntent().getSerializableExtra(
                    ReferencesFragment.ReferencesData_ID);
            ReferencesData p = ReferencesDataList.getInstance(this)
                    .getPendingJob(selectId);
            p.setmNameOfCoWorker(mNameCW.getText().toString().trim());
            p.setmEmailOfCoWorker(mEmailCW.getText().toString().trim());
            p.setmContactOfCoWorker(contactOfCw.getText().toString().trim());
            p.setmCompanyName(mCompanyName.getText().toString().trim());
            p.setmDasignationofCoWorker(mDesignationCW.getText().toString().trim());
            p.setmHowDidYouWorkWithHim(mHowDidYouWorkWithHim.getText().toString().trim());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setCustomTheme();
    }

    private void setCustomTheme() {
        name.setBackgroundColor(getResources().getColor(android.R.color.transparent));

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
                name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg1));
                break;

            case "Purple": //bkg2
                name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg2));
                break;

            case "Orange": //bkg3
                name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg3));
                break;

            case "Brown": //bkg4
                name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg4));
                break;

            case "White":
            case "Default":
                name.setBackgroundDrawable(null);
                name.setBackgroundColor(getResources().getColor(R.color.white));
                break;

            case "Gray": //bkg5
                name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg5));
                break;

            case "Green": //bkg6
                name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg6));
                break;

            case "Teal": //bkg7
                name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg7));
                break;

            case "Red": //bkg8
                name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg8));
                break;

            case "Indigo": //bkg9
                name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg9));
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
