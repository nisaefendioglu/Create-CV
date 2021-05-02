package com.apps.resumebuilderapp.exeperience.model;

import java.util.ArrayList;
import java.util.UUID;

import com.apps.resumebuilderapp.exeperience.dao.ExperienceDataDao;
import com.apps.resumebuilderapp.utils.Global;

import android.content.Context;
import android.util.Log;

public class ExperienceDataList {
    private ArrayList<ExperienceData> mExperienceDataList;
    private static ExperienceDataList sExperienceDataList;
    private Context mAppContext;
    private ExperienceDataDao mJobListDAO;
    private static final String TAG = "PendingJobList";
    public static final String FILE = "ExperienceJobs";

    private ExperienceDataList(Context c) {
        // mPendingJobs = new ArrayList<PendingJob>();
        mAppContext = c;
        mJobListDAO = new ExperienceDataDao(mAppContext, FILE);
        try {
            mExperienceDataList = mJobListDAO.loadData();
        } catch (Exception e) {
            mExperienceDataList = new ArrayList<ExperienceData>();
            Log.e(TAG, "Error while loading data", e);
        }
    }

    public static ExperienceDataList getInstance(Context c) {
        if (sExperienceDataList == null)
            sExperienceDataList = new ExperienceDataList(c.getApplicationContext());
        return sExperienceDataList;
    }

    public ArrayList<ExperienceData> getPendingJobs() {
        return mExperienceDataList;
    }

    public ExperienceData getPendingJob(UUID id) {
        for (ExperienceData job : mExperienceDataList) {
            if (job.getMid().equals(id))
                return job;
        }
        return null;

    }

    public void deletePendingJob(ExperienceData job) {
        mExperienceDataList.remove(job);
    }

    public void addPendingJob(ExperienceData newJob) {

        mExperienceDataList.add(newJob);
    }

    public boolean saveData() {

        try {

            mJobListDAO.saveData(mExperienceDataList);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error while saving data", e);
            return false;
        }
    }

    public void DeleteData() {
        mExperienceDataList.removeAll(mExperienceDataList);
        saveData();
    }
}
