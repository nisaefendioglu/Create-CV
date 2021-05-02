package com.apps.resumebuilderapp.references.model;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;
import com.apps.resumebuilderapp.references.dao.ReferencesDao;

public class ReferencesDataList {

	private ArrayList<ReferencesData> mReferenceList;
	private static ReferencesDataList sReferenceList;
	private Context mAppContext;
	private ReferencesDao mJobListDAO;
	private static final String TAG = "PendingJobList";
	public static final String FILE= "SKILLS";

	private ReferencesDataList(Context c){
		//mPendingJobs = new ArrayList<PendingJob>();
		mAppContext = c;
		mJobListDAO = new ReferencesDao(mAppContext, FILE);
		try{
			mReferenceList = mJobListDAO.loadData();
		}catch(Exception e){
			mReferenceList = new ArrayList<ReferencesData>();
			Log.e(TAG, "Error while loading data", e);
		}
	}
	public static ReferencesDataList getInstance(Context c) {
		if (sReferenceList == null)
			sReferenceList = new ReferencesDataList(c.getApplicationContext());
		return sReferenceList;
	}

	public ArrayList<ReferencesData> getPendingJobs() {
		return mReferenceList;
	}

	public ReferencesData getPendingJob(UUID id) {
		for (ReferencesData job : mReferenceList) {
			if (job.getmId().equals(id))
				return job;
		}
		return null;
	}

	public void deletePendingJob(ReferencesData job){
		mReferenceList.remove(job);
	}

	public void addPendingJob(ReferencesData newJob) {

		mReferenceList.add(newJob);
	}
	
	public boolean saveData(){

		try{
			mJobListDAO.saveData(mReferenceList);
			return true;
		}catch(Exception e){
			Log.e(TAG, "Error while saving data", e);
			return false;
		}
	}

	public void DeleteData(){
		mReferenceList.removeAll(mReferenceList);
		saveData();
	}
}
