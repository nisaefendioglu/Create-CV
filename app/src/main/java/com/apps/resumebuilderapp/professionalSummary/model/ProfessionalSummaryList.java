package com.apps.resumebuilderapp.professionalSummary.model;

import java.util.ArrayList;
import java.util.UUID;

import com.apps.resumebuilderapp.professionalSummary.dao.daoProfessionalSummary;


import android.content.Context;
import android.util.Log;

public class ProfessionalSummaryList {

		private ArrayList<ProfessionalSummary> mProfessionalSummaryLists;
		private static ProfessionalSummaryList sProfesionalSummaryList;
		private Context mAppContext;
		private daoProfessionalSummary mJobListDAO;
		private static final String TAG = "PendingJobList";
		public static final String FILE = "ProfessionalSummary";

		private ProfessionalSummaryList(Context c){
			//mPendingJobs = new ArrayList<PendingJob>();
			mAppContext = c;
			mJobListDAO = new daoProfessionalSummary(mAppContext, FILE);
			try{
				mProfessionalSummaryLists = mJobListDAO.loadData();
			}catch(Exception e){
				mProfessionalSummaryLists = new ArrayList<ProfessionalSummary>();
				Log.e(TAG, "Error while loading data", e);
			}
		}
		public static ProfessionalSummaryList getInstance(Context c) {
			if (sProfesionalSummaryList == null)
				sProfesionalSummaryList = new ProfessionalSummaryList(c.getApplicationContext());
			return sProfesionalSummaryList;
		}

		public ArrayList<ProfessionalSummary> getPendingJobs() {
			return mProfessionalSummaryLists;
		}

		public ProfessionalSummary getPendingJob(UUID id) {
			for (ProfessionalSummary job : mProfessionalSummaryLists) {
				if (job.getmId().equals(id))
					return job;
			}
			return null;

		}
		public void deletePendingJob(ProfessionalSummary job){
			mProfessionalSummaryLists.remove(job);
		}

		public void addPendingJob(ProfessionalSummary newJob) {
			mProfessionalSummaryLists.add(newJob);
		}
		
		public boolean saveData(){
			try{
				mJobListDAO.saveData(mProfessionalSummaryLists);
				return true;
			}catch(Exception e){
				Log.e(TAG, "Error while saving data", e);
				return false;
			}
		}
		public void DeleteData(){
			mProfessionalSummaryLists.removeAll(mProfessionalSummaryLists);
			saveData();
		}

	}
