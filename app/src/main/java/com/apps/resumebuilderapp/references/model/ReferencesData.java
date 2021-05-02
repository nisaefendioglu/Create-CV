package com.apps.resumebuilderapp.references.model;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class ReferencesData {
	private UUID mId;
	private String mNameOfCoWorker;
	private String mEmailOfCoWorker;
	private String mContactOfCoWorker;
	private String mCompanyName;
	private String mDasignationofCoWorker;
	private String mHowDidYouWorkWithHim;

	public String getmNameOfCoWorker() {
		return mNameOfCoWorker;
	}

	public void setmNameOfCoWorker(String mNameOfCoWorker) {
		this.mNameOfCoWorker = mNameOfCoWorker;
	}

	public String getmEmailOfCoWorker() {
		return mEmailOfCoWorker;
	}

	public void setmEmailOfCoWorker(String mEmailOfCoWorker) {
		this.mEmailOfCoWorker = mEmailOfCoWorker;
	}

	public String getmCompanyName() {
		return mCompanyName;
	}

	public void setmCompanyName(String mCompanyName) {
		this.mCompanyName = mCompanyName;
	}

	public String getmDasignationofCoWorker() {
		return mDasignationofCoWorker;
	}

	public void setmDasignationofCoWorker(String mDasignationofCoWorker) {
		this.mDasignationofCoWorker = mDasignationofCoWorker;
	}

	public String getmHowDidYouWorkWithHim() {
		return mHowDidYouWorkWithHim;
	}

	public void setmHowDidYouWorkWithHim(String mHowDidYouWorkWithHim) {
		this.mHowDidYouWorkWithHim = mHowDidYouWorkWithHim;
	}

	public String getmContactOfCoWorker() {
		return mContactOfCoWorker;
	}

	public void setmContactOfCoWorker(String mContactOfCoWorker) {
		this.mContactOfCoWorker = mContactOfCoWorker;
	}

	private static final String JSON_ID = "id";
	private static final String JSON_NAME_OF_CO_WORKER = "com.apps.resumebuilderapp.references.model.NAME";
	private static final String JSON_EMAIL_OF_CO_WORKER = "com.apps.resumebuilderapp.references.model.EMAIL";
	private static final String JSON_CONTACT_OF_CO_WORKER = "com.apps.resumebuilderapp.references.model.CONTACT";
	private static final String JSON_COMPANY_NAME = "com.apps.resumebuilderapp.references.model.COMPANYNAME";
	private static final String JSON_DESIGNATION_OF_CO_WORKER = "com.apps.resumebuilderapp.references.model.DESIGNATION";
	private static final String JSON_HOW_DID_YOU_WORK_WITH_HIM = "com.apps.resumebuilderapp.references.model.HOWDIDYOU";

	public final static String FILE_NAME = "References";

	public ReferencesData() {
		mId = UUID.randomUUID();
		mNameOfCoWorker = "";
		mEmailOfCoWorker = "";
		mContactOfCoWorker = "";
		mDasignationofCoWorker = "";
		mCompanyName = "";
		mHowDidYouWorkWithHim = "";
	}

	public ReferencesData(JSONObject jsonObject) {
		// TODO Auto-generated constructor stub
		try {
			mId = UUID.fromString(jsonObject.getString(JSON_ID));
			mNameOfCoWorker = jsonObject.getString(JSON_NAME_OF_CO_WORKER);
			mEmailOfCoWorker = jsonObject.getString(JSON_EMAIL_OF_CO_WORKER);
			mContactOfCoWorker = jsonObject.getString(JSON_CONTACT_OF_CO_WORKER);
			mDasignationofCoWorker = jsonObject.getString(JSON_DESIGNATION_OF_CO_WORKER);
			mCompanyName = jsonObject.getString(JSON_COMPANY_NAME);
			mHowDidYouWorkWithHim = jsonObject.getString(JSON_HOW_DID_YOU_WORK_WITH_HIM);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mCompanyName;
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
		json.put(JSON_NAME_OF_CO_WORKER, mNameOfCoWorker);
		json.put(JSON_EMAIL_OF_CO_WORKER, mEmailOfCoWorker);
		json.put(JSON_CONTACT_OF_CO_WORKER, mContactOfCoWorker);
		json.put(JSON_COMPANY_NAME, mCompanyName);
		json.put(JSON_DESIGNATION_OF_CO_WORKER, mDasignationofCoWorker);
		json.put(JSON_HOW_DID_YOU_WORK_WITH_HIM, mHowDidYouWorkWithHim);
		return json;

	}
}
