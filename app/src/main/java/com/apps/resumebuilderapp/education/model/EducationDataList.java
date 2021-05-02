package com.apps.resumebuilderapp.education.model;

import java.util.ArrayList;
import java.util.UUID;

import com.apps.resumebuilderapp.education.dao.EducationDataDao;



import android.content.Context;
import android.util.Log;

public class EducationDataList {
	private ArrayList<EducationData> mEducationDataList;
	private static EducationDataList sEducationDataList;
	private Context mAppContext;
	private EducationDataDao mJobListDAO;
	private static final String TAG = "PendingJobList";
	public static final String FILE = "EducationJobs";


	private EducationDataList(Context c) {
		// mPendingJobs = new ArrayList<PendingJob>();
		mAppContext = c;
		mJobListDAO = new EducationDataDao(mAppContext,FILE);
		try {
			mEducationDataList = mJobListDAO.loadData();
		} catch (Exception e) {
			mEducationDataList = new ArrayList<EducationData>();
			Log.e(TAG, "Error while loading data", e);
		}
	}

	public static EducationDataList getInstance(Context c) {
		if (sEducationDataList == null)
			sEducationDataList = new EducationDataList(
					c.getApplicationContext());
		return sEducationDataList;
	}

	public ArrayList<EducationData> getPendingJobs() {
		return mEducationDataList;
	}

	public EducationData getPendingJob(UUID id) {
		for (EducationData job : mEducationDataList) {
			if (job.getmId().equals(id))
				return job;
		}
		return null;

	}

	public void deletePendingJob(EducationData job) {
		mEducationDataList.remove(job);
	}

	public void addPendingJob(EducationData newJob) {

		mEducationDataList.add(newJob);
	}

	public boolean saveData() {

		try {
			mJobListDAO.saveData(mEducationDataList);
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error while saving data", e);
			return false;
		}
	}
	public void DeleteData(){
		mEducationDataList.removeAll(mEducationDataList);
		saveData();
	}
}
