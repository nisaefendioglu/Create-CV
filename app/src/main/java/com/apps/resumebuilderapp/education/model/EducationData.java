package com.apps.resumebuilderapp.education.model;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class EducationData {

	private UUID mId;
	private boolean mIsCurrent;
	private String mSchoolName;
	private String mDegreeName;
	private Date mStartDate;
	private Date mEndDate;

	public final static String ID = "com.apps.resumebuilderapp.education.model.model.ID";
	public final static String SCHOOL_NAME = "com.apps.resumebuilderapp.education.model.SCHOOL_NAME";
	public final static String DEGREE = "com.apps.resumebuilderapp.education.model.DEGREE";
	public final static String START_DATE = "com.apps.resumebuilderapp.education.model.START_DATE";
	public final static String END_DATE = "com.apps.resumebuilderapp.education.model.END_DATE";
	public final static String IS_CURRENT = "com.apps.resumebuilderapp.education.model.IS_CURRENT";

	public EducationData(JSONObject obj) {
		// TODO Auto-generated constructor stub
		try {
			mId = UUID.fromString(obj.getString(ID));
			mSchoolName = obj.getString(SCHOOL_NAME);
			mDegreeName = obj.getString(DEGREE);
			mStartDate = new Date(obj.getLong(START_DATE));
			mEndDate = new Date(obj.getLong(END_DATE));
			mIsCurrent = obj.getBoolean(IS_CURRENT);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	public EducationData() {
		mId = UUID.randomUUID();
		mSchoolName = "";
		mDegreeName = "";
		mIsCurrent = false;
		mStartDate = new Date(1800, 1, 1);
		mEndDate = new Date(1800, 1, 1);

	}

	public boolean ismIsCurrent() {
		return mIsCurrent;
	}

	public void setmIsCurrent(boolean mIsCurrent) {
		this.mIsCurrent = mIsCurrent;
	}

	public String getmSchoolName() {
		return mSchoolName;
	}

	public void setmSchoolName(String mSchoolName) {
		this.mSchoolName = mSchoolName;
	}

	public String getmDegreeName() {
		return mDegreeName;
	}

	public void setmDegreeName(String mDegreeName) {
		this.mDegreeName = mDegreeName;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mSchoolName;

	}

	public UUID getmId() {
		return mId;
	}

	public Object toJSON() {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		try {
			json.put(ID, mId.toString());
			json.put(SCHOOL_NAME, mSchoolName);
			json.put(DEGREE, mDegreeName);
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

}
