package com.apps.resumebuilderapp.achievements.model;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class AchievementsData {
    private UUID mId;
    private String mNameOfAward;
    private Date mDateOfAward = null;
    private String mWhatDidYouDoToAchieveIt;
    private static final String JSON_ID = "com.apps.resumebuilderapp.achievements.ID";
    private static final String JSON_NAME = "com.apps.resumebuilderapp.achievements.NAME";
    private static final String JSON_DATE = "com.apps.resumebuilderapp.achievements.DATE";
    private static final String JSON_JOB = "com.apps.resumebuilderapp.achievements.JOB";

    public AchievementsData() {
        // TODO Auto-generated constructor stub
        mId = UUID.randomUUID();
        mNameOfAward = "";
//		mDateOfAward =  new Date(1800, 1, 1);
        mWhatDidYouDoToAchieveIt = "";
    }

    public AchievementsData(JSONObject jsonObject) {
        // TODO Auto-generated constructor stub
        try {
            mId = UUID.fromString(jsonObject.getString(JSON_ID));
            mNameOfAward = jsonObject.getString(JSON_NAME);
            if (jsonObject.has(JSON_DATE))
                mDateOfAward = new Date(jsonObject.getLong(JSON_DATE));
            mWhatDidYouDoToAchieveIt = jsonObject.getString(JSON_JOB);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return mNameOfAward;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_NAME, mNameOfAward);
        if (mDateOfAward != null) {
            json.put(JSON_DATE, mDateOfAward.getTime());
        } else
            json.put(JSON_DATE, null);
        json.put(JSON_JOB, mWhatDidYouDoToAchieveIt);
        return json;

    }

    public String getmNameOfAward() {
        return mNameOfAward;
    }

    public void setmNameOfAward(String mNameOfAward) {
        this.mNameOfAward = mNameOfAward;
    }

    public Date getmDateOfAward() {
        return mDateOfAward;
    }

    public void setmDateOfAward(Date mDateOfAward) {
        this.mDateOfAward = mDateOfAward;
    }

    public String getmWhatDidYouDoToAchieveIt() {
        return mWhatDidYouDoToAchieveIt;
    }

    public void setmWhatDidYouDoToAchieveIt(String mWhatDidYouDoToAchieveIt) {
        this.mWhatDidYouDoToAchieveIt = mWhatDidYouDoToAchieveIt;
    }
}
