package com.apps.resumebuilderapp.personalInfo;

import java.io.IOException;
import java.util.Calendar;

import org.json.JSONException;

import com.apps.resumebuilderapp.R;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.apps.resumebuilderapp.utils.SessionManager;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

public class Profile_Info extends AppCompatActivity {

    private EditText personName;
    private EditText email;
    private EditText streetNo;
    private EditText city;
    private EditText state;
    private EditText country;
    private EditText pinOrZip;
    private EditText contactNumber;
    private Button dateOfBirth;
    private String day;
    private String Month;
    private String Year;
    private dao d;
    private PersonalInfo profile;
    public final static String DOB_DAY = "DOB_DAY";
    public final static String DOB_MONTH = "DOB_MONTH";
    public final static String DOB_YEAR = "DOB_YEAR";
    public final static String DOB_FILE = "DOB_FILE";
    public final static String DATE_FORMATE_FILE = "DATE_FORMATE_FILE";
    public final static String DATE_FORMATE = "DATE_FORMATE";
    public final static String FILE_NAME = "PersonalInfo";

    private LinearLayout lnr_main;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__info);

        facebookBannerAdView();

        sessionManager = new SessionManager(Profile_Info.this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        personName = (EditText) findViewById(R.id.personName);
        email = (EditText) findViewById(R.id.email);
        streetNo = (EditText) findViewById(R.id.streetNo);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        country = (EditText) findViewById(R.id.country);
        pinOrZip = (EditText) findViewById(R.id.pinOrZip);
        contactNumber = (EditText) findViewById(R.id.contactNumber);
        dateOfBirth = (Button) findViewById(R.id.dateOfBirth);
        lnr_main = (LinearLayout) findViewById(R.id.lnr_main);

        d = new dao(getApplicationContext(), "PersonalInfo");
        profile = new PersonalInfo();
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
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
        try {
            profile.SavePersonalInfo(personName.getText().toString().trim(),
                    email.getText().toString().trim(),
                    streetNo.getText().toString().trim(),
                    city.getText().toString().trim(),
                    state.getText().toString().trim(),
                    country.getText().toString().trim(),
                    pinOrZip.getText().toString().trim(),
                    contactNumber.getText().toString().trim());
            d.saveData(profile);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        Toast.makeText(getApplicationContext(), R.string.profileSave,
//                Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        try {
            SharedPreferences sh = getSharedPreferences(DOB_FILE,
                    Context.MODE_PRIVATE);
            day = sh.getString(DOB_DAY, "");
            Month = sh.getString(DOB_MONTH, "");
            Year = sh.getString(DOB_YEAR, "");
            PersonalInfo ip = d.loadData();
            personName.setText(ip.getmPersonName().trim());
            email.setText(ip.getmEmail().trim());
            streetNo.setText(ip.getmStreetNo().trim());
            city.setText(ip.getmCity().trim());
            state.setText(ip.getmState().trim());
            country.setText(ip.getmCountry().trim());
            pinOrZip.setText(ip.getmPinOrZip().trim());
            contactNumber.setText(ip.getmContactNumber().trim());
            sh = getSharedPreferences(DATE_FORMATE_FILE, Context.MODE_PRIVATE);
            int choice = sh.getInt(DATE_FORMATE, 1);
            if (!day.equals(""))
                dateOfBirth.setText(PersonalInfo.getDateOfBirthFormate(choice,
                        day, Month, Year).trim());
            else {
                dateOfBirth.setText(R.string.dob);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

                                sh = getSharedPreferences(DOB_FILE,
                                        Context.MODE_PRIVATE);
                                sh.edit().putString(DOB_DAY, day).commit();
                                sh.edit().putString(DOB_MONTH, Month).commit();
                                sh.edit().putString(DOB_YEAR, Year).commit();

                                dateOfBirth.setText(PersonalInfo
                                        .getDateOfBirthFormate(choice, dayOfMonth
                                                + "", (monthOfYear + 1) + "", year
                                                + "").trim());
                            }
                        }, mYear, mMonth, mDay);
        }
        return null;
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
