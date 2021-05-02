package com.apps.resumebuilderapp.achievements.dao;

import java.util.ArrayList;

import android.content.Context;
import com.apps.resumebuilderapp.achievements.model.AchievementsData;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class AchievementsDao {
	private Context mContext;
	private String mFileName;

	public AchievementsDao(Context c, String fName) {
		mContext = c;
		mFileName = fName;
	}

	public void saveData(ArrayList<AchievementsData> jobs)
			throws JSONException, IOException {
		JSONArray array = new JSONArray();
		for (AchievementsData job : jobs)
			array.put(job.toJSON());

		FileOutputStream fos = mContext.openFileOutput(mFileName,
				Context.MODE_PRIVATE);

		Writer writer = new PrintWriter(fos);
		writer.write(array.toString());
		writer.flush();
		writer.close();

	}

	public ArrayList<AchievementsData> loadData() throws IOException,
			JSONException {
		ArrayList<AchievementsData> jobList = new ArrayList<AchievementsData>();

		InputStream is = mContext.openFileInput(mFileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		String line = reader.readLine();
		StringBuilder jsonString = new StringBuilder();
		while (line != null) {
			jsonString.append(line);
			line = reader.readLine();
		}

		JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
				.nextValue();
		for (int i = 0; i < array.length(); i++)
			jobList.add(new AchievementsData(array.getJSONObject(i)));
		return jobList;
	}

}
