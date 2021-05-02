package com.apps.resumebuilderapp.skills.model;

import java.util.ArrayList;
import java.util.UUID;

import com.apps.resumebuilderapp.skills.dao.daoSkills;
import com.apps.resumebuilderapp.utils.Global;

import android.content.Context;
import android.util.Log;

public class SkillsDataList {


    private ArrayList<SkillsData> mSkillList;
    private static SkillsDataList sSkillList;
    private Context mAppContext;
    private daoSkills mJobListDAO;
    private static final String TAG = "PendingJobList";
    public static final String FILE = "SKILLS";


    private SkillsDataList(Context c) {
        //mPendingJobs = new ArrayList<PendingJob>();
        mAppContext = c;
        mJobListDAO = new daoSkills(mAppContext, FILE);
        try {
            mSkillList = mJobListDAO.loadData();
        } catch (Exception e) {
            mSkillList = new ArrayList<SkillsData>();
            Log.e(TAG, "Error while loading data", e);
        }
    }

    public static SkillsDataList getInstance(Context c) {
        if (sSkillList == null)
            sSkillList = new SkillsDataList(c.getApplicationContext());
        return sSkillList;
    }

    public ArrayList<SkillsData> getPendingJobs() {
        return mSkillList;
    }

    public SkillsData getPendingJob(UUID id) {

        Global.printLog("SkillsDataList===", "==mSkillList===" + mSkillList
        );
        for (SkillsData job : mSkillList) {
            if (job.getmId().equals(id)) {
                return job;
            }
        }
        return null;

    }

    public void deletePendingJob(SkillsData job) {
        mSkillList.remove(job);
    }

    public void addPendingJob(SkillsData newJob) {
        mSkillList.add(newJob);
    }

    public boolean saveData() {
        try {
            mJobListDAO.saveData(mSkillList);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Veri Kaydedilirken Hata Oluştu.", e);
            return false;
        }
    }

    public void DeleteData() {
        mSkillList.removeAll(mSkillList);
        saveData();
    }
}
