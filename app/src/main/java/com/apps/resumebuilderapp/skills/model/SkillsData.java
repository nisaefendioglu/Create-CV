package com.apps.resumebuilderapp.skills.model;

import com.apps.resumebuilderapp.utils.Global;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class SkillsData {
    private UUID mId;
    private String mSkills;
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";

    public SkillsData() {
        // TODO Auto-generated constructor stub
        mId = UUID.randomUUID();
        mSkills = "";
    }

    public SkillsData(JSONObject jsonObject) {

        Global.printLog("SkillsData===jsonObject==", jsonObject + "");
        // TODO Auto-generated constructor stub
        try {
            mId = UUID.fromString(jsonObject.getString(JSON_ID));

            if (jsonObject.has(JSON_TITLE))
                mSkills = jsonObject.getString(JSON_TITLE);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return mSkills;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public void setmPersonalSummary(String mprofessionalSummary) {
        this.mSkills = mprofessionalSummary;
    }

    public String getmPersonalSummary() {
        return mSkills;
    }

    public void setmSkills(String mSkills) {
        this.mSkills = mSkills;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mSkills);
        return json;

    }
}
