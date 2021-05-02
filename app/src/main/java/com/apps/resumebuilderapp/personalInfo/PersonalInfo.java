package com.apps.resumebuilderapp.personalInfo;

import com.apps.resumebuilderapp.utils.Global;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonalInfo {

    private String mPersonName;
    private String mEmail;
    private String mStreetNo;
    private String mCity;
    private String mState;
    private String mCountry;
    private String mPinOrZip;
    private String mContactNumber;


    public PersonalInfo(JSONObject obj) {
        // TODO Auto-generated constructor stub
        try {
            mPersonName = obj.getString("NAME");
            mEmail = obj.getString("EMAIL");
            mStreetNo = obj.getString("STREET");
            mCity = obj.getString("CITY");
            mState = obj.getString("STATE");
            mCountry = obj.getString("COUNTRY");
            mPinOrZip = obj.getString("PIN_OR_ZIP");
            mContactNumber = obj.getString("CONTACT_NUMBER");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public PersonalInfo() {
        this.mPersonName = "";
        this.mEmail = "";
        this.mStreetNo = "";
        this.mCity = "";
        this.mState = "";
        this.mCountry = "";
        this.mPinOrZip = "";
        this.mContactNumber = "";
    }

    public void SavePersonalInfo(String mPersonName, String mEmail, String mStreetNo,
                                 String mCity, String mState, String mCountry, String mPinOrZip,
                                 String mContactNumber) {
        this.mPersonName = mPersonName;
        this.mEmail = mEmail;
        this.mStreetNo = mStreetNo;
        this.mCity = mCity;
        this.mState = mState;
        this.mCountry = mCountry;
        this.mPinOrZip = mPinOrZip;
        this.mContactNumber = mContactNumber;
    }

    public String getmPersonName() {
        return mPersonName;
    }

    public void setmPersonName(String mPersonName) {
        this.mPersonName = mPersonName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmStreetNo() {
        return mStreetNo;
    }

    public void setmStreetNo(String mStreetNo) {
        this.mStreetNo = mStreetNo;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmPinOrZip() {
        return mPinOrZip;
    }

    public void setmPinOrZip(String mPinOrZip) {
        this.mPinOrZip = mPinOrZip;
    }

    public String getmContactNumber() {
        return mContactNumber;
    }

    public void setmContactNumber(String mContactNumber) {
        this.mContactNumber = mContactNumber;
    }

    public static String getDateOfBirthFormate(int a, String day, String month, String year) {

        Global.printLog("choice==" + a + "==day==" + day, "==month==" + month + "===year===" + year);
        switch (a) {
            case 1:
                return month + "/" + day + "/" + year;
            case 2:
                return day + "/" + month + "/" + year;
            case 3:
                return year + "/" + month + "/" + day;
        }
        return "";
    }

    public static String getDateOfBirthFormate4Pdf(int a, String day, String month, String year) {

        Global.printLog("day==" + day, "==month==" + month + "===year===" + year);

        int mnth = Integer.parseInt(month) + 1;
        switch (a) {
            case 1:
                return mnth + "/" + day + "/" + year;
            case 2:
                return day + "/" + mnth + "/" + year;
            case 3:
                return year + "/" + mnth + "/" + day;
        }
        return "";
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject JSONObject = new JSONObject();
        JSONObject.put("NAME", mPersonName);
        JSONObject.put("EMAIL", mEmail);
        JSONObject.put("STREET", mStreetNo);
        JSONObject.put("CITY", mCity);
        JSONObject.put("STATE", mState);
        JSONObject.put("COUNTRY", mCountry);
        JSONObject.put("PIN_OR_ZIP", mPinOrZip);
        JSONObject.put("CONTACT_NUMBER", mContactNumber);
        return JSONObject;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        //recorrect it
        return "Name : " + mPersonName + "\n" + "Email : " + mEmail + "\n" + "Address : " + mStreetNo + " " + mCity + " " + mState + " " + mCountry + "\n" + "Zip Or Pin : " + mPinOrZip + "\n" + "Contact # " + mContactNumber + "\n";
    }

}
