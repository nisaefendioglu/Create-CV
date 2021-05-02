package com.apps.resumebuilderapp.education;

import java.util.Date;
import java.util.Calendar;
import java.util.UUID;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.education.model.EducationData;
import com.apps.resumebuilderapp.education.model.EducationDataList;
import com.apps.resumebuilderapp.personalInfo.PersonalInfo;
import com.apps.resumebuilderapp.personalInfo.Profile_Info;
import com.apps.resumebuilderapp.utils.Global;
import com.apps.resumebuilderapp.utils.SessionManager;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Education_Ui extends AppCompatActivity {

    private CheckBox mCurrentjob;
    private EditText mSchoolName;
    private Button mStartDate;
    private Button mEndDate;
    private EditText mDegree;
    private Date mStartDateObj;
    private Date mEndDateObj;

    private LinearLayout lnr_main;
    private SessionManager sessionManager;

    public final static int GET_START_DATE_ID = 1;
    public final static int GET_END_DATE_ID = 2;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education__ui);

        facebookBannerAdView();

        sessionManager = new SessionManager(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lnr_main = (LinearLayout) findViewById(R.id.lnr_main);

        mCurrentjob = (CheckBox) findViewById(R.id.isYourCurrentEducationCheckBox);
        mSchoolName = (EditText) findViewById(R.id.schoolName);
        mStartDate = (Button) findViewById(R.id.startDateE);
        mEndDate = (Button) findViewById(R.id.endDateE);
        mDegree = (EditText) findViewById(R.id.degreeEditText);
        lnr_main = (LinearLayout) findViewById(R.id.lnr_main);

        mStartDateObj = new Date(1800, 1, 1);
        mEndDateObj = new Date(1800, 1, 1);

        UUID selectId = (UUID) getIntent().getSerializableExtra(
                Education_fragment.Education_Ui_activity_ID);
        EducationData p = EducationDataList.getInstance(this).getPendingJob(selectId);

        if (p == null)
            return;

        mSchoolName.setText(p.getmSchoolName());

        if (p.ismIsCurrent()) {
            mCurrentjob.setChecked(true);
            mEndDate.setText(R.string.present);
            mEndDate.setEnabled(false);
        } else {
            Date d = p.getmEndDate();
            Global.printLog("d==EndDate=", "==" + d+"");
            if (d.getYear() != 1800) {
                SharedPreferences sh;
                sh = getSharedPreferences(Profile_Info.DATE_FORMATE_FILE,
                        Context.MODE_PRIVATE);
                int a = sh.getInt(Profile_Info.DATE_FORMATE, 1);

                mEndDate.setText(PersonalInfo.getDateOfBirthFormate(a, p
                        .getmEndDate().getDate() + "", (p.getmEndDate()
                        .getMonth()+1) + "", p.getmEndDate().getYear() + ""));
                mEndDateObj = p.getmEndDate();
            }
        }

        Date d = p.getmStartDate();
        Global.printLog("d==StartDate=", "==" + d+"");

        if (d.getYear() != 1800) {
            SharedPreferences sh;
            sh = getSharedPreferences(Profile_Info.DATE_FORMATE_FILE,
                    Context.MODE_PRIVATE);
            int a = sh.getInt(Profile_Info.DATE_FORMATE, 1);

            mStartDate.setText(PersonalInfo.getDateOfBirthFormate(a, p
                    .getmStartDate().getDate() + "", (p.getmStartDate()
                    .getMonth()+1) + "", p.getmStartDate().getYear() + "").trim());
            mStartDateObj = p.getmStartDate();
        }
        mDegree.setText(p.getmDegreeName().trim());

        mCurrentjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (mCurrentjob.isChecked()) {
                    mEndDate.setEnabled(false);
                    mEndDate.setText(R.string.present);
                } else {
                    mEndDate.setEnabled(true);
                    if (mEndDateObj.getYear() != 1800) {
                        SharedPreferences sh = getSharedPreferences(
                                Profile_Info.DOB_FILE, Context.MODE_PRIVATE);
                        sh = getSharedPreferences(
                                Profile_Info.DATE_FORMATE_FILE,
                                Context.MODE_PRIVATE);
                        int a = sh.getInt(Profile_Info.DATE_FORMATE, 1);

                        mEndDate.setText(PersonalInfo.getDateOfBirthFormate(a,
                                mEndDateObj.getDate() + "",
                                (mEndDateObj.getMonth()+1) + "",
                                mEndDateObj.getYear() + ""));
                    } else
                        mEndDate.setText(R.string.endDate);
                }
            }
        });// end of mCurrentJob listner

        mStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(GET_START_DATE_ID);
            }
        });// end of start date listner

        mEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(GET_END_DATE_ID);
            }
        });
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
    protected Dialog onCreateDialog(int id) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        switch (id) {

            case 1:
                // set date picker as current date
                return new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressWarnings("deprecation")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub
                                Global.printLog("1==" + dayOfMonth, "==month==" + monthOfYear + "===year===" + year);


                                mStartDateObj.setDate(dayOfMonth);
                                mStartDateObj.setMonth(monthOfYear);
                                mStartDateObj.setYear(year);


                                SharedPreferences sh = getSharedPreferences(
                                        Profile_Info.DATE_FORMATE_FILE,
                                        Context.MODE_PRIVATE);
                                int a = sh.getInt(Profile_Info.DATE_FORMATE, 1);

                                mStartDate.setText(PersonalInfo
                                        .getDateOfBirthFormate(a,
                                                mStartDateObj.getDate() + "",
                                                mStartDateObj.getMonth() + 1 + "",
                                                mStartDateObj.getYear() + ""));
                            }
                        }, mYear, mMonth, mDay);
            case 2:
                // set date picker as current date
                return new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressWarnings("deprecation")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub

                                Global.printLog("2==" + dayOfMonth, "==month==" + monthOfYear + "===year===" + year);

                                mEndDateObj.setDate(dayOfMonth);
                                mEndDateObj.setMonth(monthOfYear);
                                mEndDateObj.setYear(year);
                                SharedPreferences
                                        sh = getSharedPreferences(
                                        Profile_Info.DATE_FORMATE_FILE,
                                        Context.MODE_PRIVATE);
                                int a = sh.getInt(Profile_Info.DATE_FORMATE, 1);

                                mEndDate.setText(PersonalInfo
                                        .getDateOfBirthFormate(a,
                                                mEndDateObj.getDate() + "",
                                                mEndDateObj.getMonth()+1 + "",
                                                mEndDateObj.getYear() + ""));
                            }
                        }, mYear, mMonth, mDay);
        }
        return null;

    }// end of dilog

    @SuppressWarnings("deprecation")
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        UUID selectId = (UUID) getIntent().getSerializableExtra(
                Education_fragment.Education_Ui_activity_ID);
        EducationData p = EducationDataList.getInstance(this).getPendingJob(
                selectId);
        p.setmSchoolName(mSchoolName.getText().toString().trim());
        p.setmDegreeName(mDegree.getText().toString().trim());
        if (!mCurrentjob.isChecked())
            p.setmEndDate(mEndDateObj);
        else if (mEndDateObj.getYear() != 1800)
            p.setmEndDate(mEndDateObj);

        if (mStartDateObj.getYear() != 1800)
            p.setmStartDate(mStartDateObj);

        p.setmIsCurrent(mCurrentjob.isChecked());
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
