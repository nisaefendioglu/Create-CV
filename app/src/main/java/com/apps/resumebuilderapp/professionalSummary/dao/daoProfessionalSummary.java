package com.apps.resumebuilderapp.professionalSummary.dao;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import com.apps.resumebuilderapp.professionalSummary.model.ProfessionalSummary;


import android.content.Context;


public class daoProfessionalSummary {
    private Context mContext;
    private String mFileName;

    public daoProfessionalSummary(Context c, String fName) {
        mContext = c;
        mFileName = fName;
    }

    public void saveData(ArrayList<ProfessionalSummary> jobs) throws JSONException,
            IOException {
        JSONArray array = new JSONArray();
        for (ProfessionalSummary job : jobs)
            array.put(job.toJSON());

        FileOutputStream fos = mContext.openFileOutput(mFileName,
                Context.MODE_PRIVATE);

        Writer writer = new PrintWriter(fos);
        writer.write(array.toString());
        writer.flush();
        writer.close();
    }

    public ArrayList<ProfessionalSummary> loadData() throws IOException, JSONException {
        ArrayList<ProfessionalSummary> jobList = new ArrayList<ProfessionalSummary>();

        InputStream is = mContext.openFileInput(mFileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = reader.readLine();
        StringBuilder jsonString = new StringBuilder();
        while (line != null) {
            jsonString.append(line);
            line = reader.readLine();
        }

        JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
        for (int i = 0; i < array.length(); i++)
            jobList.add(new ProfessionalSummary(array.getJSONObject(i)));
        return jobList;
    }

}

