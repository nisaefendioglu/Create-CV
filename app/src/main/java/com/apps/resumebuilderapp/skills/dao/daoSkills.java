package com.apps.resumebuilderapp.skills.dao;

import java.util.ArrayList;

import android.content.Context;

import com.apps.resumebuilderapp.skills.model.SkillsData;
import com.apps.resumebuilderapp.utils.Global;

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

public class daoSkills {
    private Context mContext;
    private String mFileName;

    public daoSkills(Context c, String fName) {
        mContext = c;
        mFileName = fName;
    }

    public void saveData(ArrayList<SkillsData> jobs) throws JSONException,
            IOException {
        JSONArray array = new JSONArray();
        for (SkillsData job : jobs)
            array.put(job.toJSON());

        FileOutputStream fos = mContext.openFileOutput(mFileName,
                Context.MODE_PRIVATE);

        Writer writer = new PrintWriter(fos);
        writer.write(array.toString());
        writer.flush();
        writer.close();

    }

    public ArrayList<SkillsData> loadData() throws IOException, JSONException {
        ArrayList<SkillsData> jobList = new ArrayList<SkillsData>();

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

        Global.printLog("daoSkills===array-==", array + "");
        for (int i = 0; i < array.length(); i++) {
            if (array.getJSONObject(i).has("title"))
                jobList.add(new SkillsData(array.getJSONObject(i)));
        }
        return jobList;
    }

}
