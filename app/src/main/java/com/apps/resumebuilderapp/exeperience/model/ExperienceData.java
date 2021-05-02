package com.apps.resumebuilderapp.exeperience.model;


import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class ExperienceData {

	private UUID mId;
	private String mCompanyName;
	private String mDesignation;
	private String mWhatDidYouOverThere;
	private Date mStartDate;
	private Date mEndDate;
	private Boolean mIsCurrent;

	public final static String ID = "com.apps.resumebuilderapp.exeperience.model.ID";
	public final static String COMPANY_NAME = "com.apps.resumebuilderapp.exeperience.model.COMPANY_NAME";
	public final static String DASIGNATION = "com.apps.resumebuilderapp.exeperience.model.DESIGNATION";
	public final static String WHAT_DID_YOU_OVER_THERE = "com.apps.resumebuilderapp.exeperience.model.WHAT_DID_YOU_OVER_THERE";
	public final static String START_DATE = "com.apps.resumebuilderapp.exeperience.model.START_DATE";
	public final static String END_DATE = "com.apps.resumebuilderapp.exeperience.model.END_DATE";
	public final static String IS_CURRENT = "com.apps.resumebuilderapp.exeperience.model.IS_CURRENT";

	public ExperienceData(JSONObject obj) {
		// TODO Auto-generated constructor stub
		try {
			mId = UUID.fromString(obj.getString(ID));
			mCompanyName = obj.getString(COMPANY_NAME);
			mDesignation = obj.getString(DASIGNATION);
			mWhatDidYouOverThere = obj.getString(WHAT_DID_YOU_OVER_THERE);
			mStartDate = new Date(obj.getLong(START_DATE));
			mEndDate = new Date(obj.getLong(END_DATE));
			mIsCurrent = obj.getBoolean(IS_CURRENT);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	public ExperienceData() {
		mId = UUID.randomUUID();
		mCompanyName = "";
		mDesignation = "";
		mWhatDidYouOverThere = "";
		mIsCurrent = false;
		mStartDate = new Date(1800, 1, 1);
		mEndDate = new Date(1800, 1, 1);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mCompanyName;
	}

	public String getmCompanyName() {
		return mCompanyName;
	}

	public void setmCompanyName(String mCompanyName) {
		this.mCompanyName = mCompanyName;
	}

	public String getmDesignation() {
		return mDesignation;
	}

	public void setmDesignation(String mDesignation) {
		this.mDesignation = mDesignation;
	}

	public String getmWhatDidYouOverThere() {
		return mWhatDidYouOverThere;
	}

	public void setmWhatDidYouOverThere(String mWhatDidYouOverThere) {
		this.mWhatDidYouOverThere = mWhatDidYouOverThere;
	}

	public Date getmStartDate() {
		return mStartDate;
	}

	public void setmStartDate(Date mStartDate) {
		this.mStartDate = mStartDate;
	}

	public Date getmEndDate() {
		return mEndDate;
	}

	public void setmEndDate(Date mEndDate) {
		this.mEndDate = mEndDate;
	}

	public UUID getMid() {
		return mId;
	}

	public void setMid(UUID mid) {
		this.mId = mid;
	}

	public Object toJSON() {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		try {
			json.put(ID, mId.toString());
			json.put(COMPANY_NAME, mCompanyName);
			json.put(DASIGNATION, mDesignation);
			json.put(WHAT_DID_YOU_OVER_THERE, mWhatDidYouOverThere);
			json.put(START_DATE, mStartDate.getTime());
			json.put(END_DATE, mEndDate.getTime());
			json.put(IS_CURRENT, mIsCurrent);
			return json;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Boolean getmIsCurrent() {
		return mIsCurrent;
	}

	public void setmIsCurrent(Boolean mIsCurrent) {
		this.mIsCurrent = mIsCurrent;
	}

}
