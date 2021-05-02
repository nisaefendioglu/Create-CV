package com.apps.resumebuilderapp.achievements.model;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;
import com.apps.resumebuilderapp.achievements.dao.AchievementsDao;

public class AchievementsDataList {
	private ArrayList<AchievementsData> mAchievementsList;
	private static AchievementsDataList sAchievementsList;
	private Context mAppContext;
	private AchievementsDao mJobListDAO;
	private static final String TAG = "PendingJobList";
	public static final String FILE = "Achievements";

	private AchievementsDataList(Context c) {
		// mPendingJobs = new ArrayList<PendingJob>();
		mAppContext = c;
		mJobListDAO = new AchievementsDao(mAppContext, FILE);
		try {
			mAchievementsList = mJobListDAO.loadData();
		} catch (Exception e) {
			mAchievementsList = new ArrayList<AchievementsData>();
			Log.e(TAG, "Error while loading data", e);
		}
	}

	public static AchievementsDataList getInstance(Context c) {
		if (sAchievementsList == null)
			sAchievementsList = new AchievementsDataList(
					c.getApplicationContext());
		return sAchievementsList;
	}

	public ArrayList<AchievementsData> getPendingJobs() {
		return mAchievementsList;
	}

	public AchievementsData getPendingJob(UUID id) {
		for (AchievementsData job : mAchievementsList) {
			if (job.getmId().equals(id))
				return job;
		}
		return null;

	}

	public void deletePendingJob(AchievementsData job) {
		mAchievementsList.remove(job);
	}

	public void addPendingJob(AchievementsData newJob) {

		mAchievementsList.add(newJob);
	}

	public boolean saveData() {

		try {
			mJobListDAO.saveData(mAchievementsList);
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error while saving data", e);
			return false;
		}
	}

	public void DeleteData() {
		mAchievementsList.removeAll(mAchievementsList);
		saveData();
	}
}
