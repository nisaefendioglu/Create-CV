package com.apps.resumebuilderapp.achievements;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.achievements.model.AchievementsData;
import com.apps.resumebuilderapp.achievements.model.AchievementsDataList;
import com.apps.resumebuilderapp.personalInfo.PersonalInfo;
import com.apps.resumebuilderapp.personalInfo.Profile_Info;
import com.apps.resumebuilderapp.utils.Global;
import com.apps.resumebuilderapp.utils.SessionManager;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.apps.resumebuilderapp.personalInfo.Profile_Info.DATE_FORMATE;
import static com.apps.resumebuilderapp.personalInfo.Profile_Info.DATE_FORMATE_FILE;

public class Achievements_Ui_activity extends AppCompatActivity {

    private EditText mAwardName;
    //    private EditText mDateAward;
    private TextView tv_mDateAward;
    private EditText mWhatDidYouToAchieveIt;

    private LinearLayout lnr_name;
    private SessionManager sessionManager;

    private Date mStartDateObj;

    private String day;
    private String Month;
    private String Year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements__ui);

        facebookBannerAdView();

        sessionManager = new SessionManager(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lnr_name = (LinearLayout) findViewById(R.id.lnr_main);

        mAwardName = (EditText) findViewById(R.id.awardName);
//        mDateAward = (EditText) findViewById(R.id.dateeOfAward);
        tv_mDateAward = (TextView) findViewById(R.id.tv_dateOfAward);

        mWhatDidYouToAchieveIt = (EditText) findViewById(R.id.didForAchieve);

        mStartDateObj = new Date(1800, 1, 1);

        UUID selectId = (UUID) getIntent().getSerializableExtra(
                Achievements_fragment.AchievementsData_ID);
        AchievementsData p = AchievementsDataList.getInstance(this)
                .getPendingJob(selectId);

        if (p == null)
            return;

        if (!p.getmNameOfAward().equals(""))
            mAwardName.setText(p.getmNameOfAward().trim());
        if (!p.getmWhatDidYouDoToAchieveIt().equals(""))
            mWhatDidYouToAchieveIt.setText(p.getmWhatDidYouDoToAchieveIt().trim());

        Date d = p.getmDateOfAward();

        Global.printLog("Achivement===date==", d + "");
        if (d != null && d.getYear() != 1800) {
            SharedPreferences sh = getSharedPreferences(Profile_Info.DATE_FORMATE_FILE,
                    Context.MODE_PRIVATE);
            int a = sh.getInt(Profile_Info.DATE_FORMATE, 1);
            mStartDateObj = p.getmDateOfAward();

            tv_mDateAward.setText(PersonalInfo.getDateOfBirthFormate(a, mStartDateObj.getDate() + "", (mStartDateObj
                    .getMonth() + 1) + "", mStartDateObj.getYear() + "").trim());
        }

        tv_mDateAward.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(0);
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
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mAwardName.getText().toString().trim().equals("")
                && tv_mDateAward.getText().toString().trim().equals("")
                && mWhatDidYouToAchieveIt.getText().toString().trim().equals("")) {
            UUID selectId = (UUID) getIntent().getSerializableExtra(
                    Achievements_fragment.AchievementsData_ID);
            AchievementsData p = AchievementsDataList.getInstance(this)
                    .getPendingJob(selectId);
            AchievementsDataList.getInstance(this).getPendingJobs().remove(p);
        } else {
            UUID selectId = (UUID) getIntent().getSerializableExtra(
                    Achievements_fragment.AchievementsData_ID);
            AchievementsData p = AchievementsDataList.getInstance(this)
                    .getPendingJob(selectId);
            p.setmNameOfAward(mAwardName.getText().toString().trim());
            if (mStartDateObj.getYear() != 1800)
                p.setmDateOfAward(mStartDateObj);

            p.setmWhatDidYouDoToAchieveIt(mWhatDidYouToAchieveIt.getText()
                    .toString().trim());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCustomTheme();
    }

    private void setCustomTheme() {
        Global.setCustomTheme(Achievements_Ui_activity.this, lnr_name);

	/*	lnr_name.setBackgroundColor(getResources().getColor(android.R.color.transparent));

		switch (sessionManager.getAppTheme()) {

          *//*public static String custom_theme1 = "Blue";
            public static String custom_theme2 = "Purple";
            public static String custom_theme3 = "Orange";
            public static String custom_theme4 = "Brown";
            public static String custom_theme5 = "Sky"; // Default theme
            public static String custom_theme6 = "Gray";
            public static String custom_theme7 = "Green";
            public static String custom_theme8 = "Teal";
            public static String custom_theme9 = "Red";
            public static String custom_theme10 = "Indigo";*//*

			case "Blue": //bkg1
				lnr_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg1));
				break;

			case "Purple": //bkg2
				lnr_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg2));
				break;

			case "Orange": //bkg3
				lnr_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg3));
				break;

			case "Brown": //bkg4
				lnr_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg4));
				break;

			case "Default":
				lnr_name.setBackgroundDrawable(null);
				lnr_name.setBackgroundColor(getResources().getColor(R.color.white));
				break;

			case "Gray": //bkg5
				lnr_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg5));
				break;

			case "Green": //bkg6
				lnr_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg6));
				break;

			case "Teal": //bkg7
				lnr_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg7));
				break;

			case "Red": //bkg8
				lnr_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg8));
				break;

			case "Indigo": //bkg9
				lnr_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg9));
				break;
		}*/
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        switch (id) {

            case 0:
                // set date picker as current date
                return new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub
                                day = dayOfMonth + "";
                                Month = (monthOfYear + 1) + "";
                                Year = year + "";
                                SharedPreferences sh = getSharedPreferences(
                                        DATE_FORMATE_FILE, Context.MODE_PRIVATE);
                                int choice = sh.getInt(DATE_FORMATE, 1);

                                tv_mDateAward.setText(PersonalInfo
                                        .getDateOfBirthFormate(choice, dayOfMonth
                                                + "", (monthOfYear + 1) + "", year
                                                + "").trim());

                                mStartDateObj.setDate(dayOfMonth);
                                mStartDateObj.setMonth(monthOfYear);
                                mStartDateObj.setYear(year);

                            }
                        }, mYear, mMonth, mDay);
        }
        return null;
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