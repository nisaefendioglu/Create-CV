package com.apps.resumebuilderapp.professionalSummary.model;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfessionalSummary {

	private UUID mId;
	private String mprofessionalSummary;
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	public final static String FILE_NAME = "jobs";

	
	public ProfessionalSummary(){
		mId = UUID.randomUUID();
		mprofessionalSummary="";
	}

	public ProfessionalSummary(JSONObject jsonObject) {
		// TODO Auto-generated constructor stub
		try {
			mId = UUID.fromString(jsonObject.getString(JSON_ID));
			mprofessionalSummary = jsonObject.getString(JSON_TITLE);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mprofessionalSummary;
	}

	public UUID getmId() {
		return mId;
	}

	public void setmId(UUID mId) {
		this.mId = mId;
	}

	public String getmPersonalSummary() {
		return mprofessionalSummary;
	}

	public void setmPersonalSummary(String mprofessionalSummary) {
		this.mprofessionalSummary = mprofessionalSummary;
	}
	
	public JSONObject toJSON() throws JSONException{
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mprofessionalSummary);
		return json;
		
	}
}
