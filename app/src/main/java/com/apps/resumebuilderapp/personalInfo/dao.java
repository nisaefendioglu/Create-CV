package com.apps.resumebuilderapp.personalInfo;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import android.content.Context;

import com.apps.resumebuilderapp.utils.Global;


public class dao {

    private Context mContext;
    private String mFileName;

    public dao(Context c, String fName) {
        mContext = c;
        mFileName = fName;
    }

    public void saveData(PersonalInfo info) throws JSONException,
            IOException {
        FileOutputStream fos = mContext.openFileOutput(mFileName,
                Context.MODE_PRIVATE);

        Writer writer = new PrintWriter(fos);
        writer.write(info.toJSON().toString());
        writer.flush();
        writer.close();

    }

    public PersonalInfo loadData() throws IOException, JSONException {

        InputStream is = mContext.openFileInput(mFileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = reader.readLine();
        StringBuilder jsonString = new StringBuilder();
        while (line != null) {
            jsonString.append(line);
            line = reader.readLine();
        }

        JSONObject obj = (JSONObject) new JSONTokener(jsonString.toString()).nextValue();
        Global.printLog("dao===", obj + "");
        return new PersonalInfo(obj);

    }

}
