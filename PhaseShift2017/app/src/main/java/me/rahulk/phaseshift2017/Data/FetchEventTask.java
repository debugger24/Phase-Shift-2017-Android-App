package me.rahulk.phaseshift2017.Data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Vector;

import static me.rahulk.phaseshift2017.AppConfig.URL_EVENTS;

/**
 * Created by debugger24 on 12/08/17.
 */

public class FetchEventTask extends AsyncTask<Void, Void, Void> {

    Context mContext;

    public FetchEventTask(FragmentActivity activity) {
        mContext = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        URL url = createUrl(URL_EVENTS);

        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        extractEventsFromJson(jsonResponse);
        return null;
    }

    private void extractEventsFromJson(String jsonResponse) {

        Vector<ContentValues> cVVector;
        final String OWM_ID = "ID";
        final String OWM_Title = "Title";
        final String OWM_Icon = "Icon";
        final String OWM_Type = "Type";
        final String OWM_Category = "Category";
        final String OWM_Department = "Department";
        final String OWM_BMSCE = "BMSCE";
        final String OWM_Full = "Full";
        final String OWM_Flagship = "Flagship";
        final String OWM_Participation = "Participation";
        final String OWM_Prize1 = "Prize1";
        final String OWM_Prize2 = "Prize2";
        final String OWM_Prize3 = "Prize3";
        final String OWM_Name = "Name";
        final String OWM_Number = "Number";
        final String OWM_RegFees = "RegFees";
        final String OWM_Date = "Date";
        final String OWM_Time = "Time";
        final String OWM_Description = "Description";
        final String OWM_Rules = "Rules";
        final String OWM_Venue = "Venue";

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray eventList = jsonObject.getJSONArray("events");

            cVVector = new Vector<ContentValues>(eventList.length());

            JSONObject event;
            for (int i = 0; i < eventList.length(); i++) {
                event = eventList.getJSONObject(i);
                ContentValues eventValues = new ContentValues();
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_TITLE, event.getString(OWM_Title));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_ICON, event.getString(OWM_Icon));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_TYPE, event.getString(OWM_Type));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_CATEGORY, event.getString(OWM_Category));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_DEPARTMENT, event.getString(OWM_Department));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_BMSCE, event.getString(OWM_BMSCE));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_FULL, event.getString(OWM_Full));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_FLAGSHIP, event.getString(OWM_Flagship));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_PARTICIPATION, event.getString(OWM_Participation));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_PRIZE1, event.getString(OWM_Prize1));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_PRIZE2, event.getString(OWM_Prize2));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_PRIZE3, event.getString(OWM_Prize3));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_PERSON, event.getString(OWM_Name));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_PERSON_NUMBER, event.getString(OWM_Number));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_FEES, event.getString(OWM_RegFees));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_DATE, event.getString(OWM_Date));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_TIME, event.getString(OWM_Time));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_DESCRIPTION, event.getString(OWM_Description));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_RULES, event.getString(OWM_Rules));
                eventValues.put(PhaseShiftContract.EventEntry.COLUMNS_EVENT_VENUE, event.getString(OWM_Venue));
                cVVector.add(eventValues);
            }

            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];

                cVVector.toArray(cvArray);

                mContext.getContentResolver().bulkInsert(PhaseShiftContract.EventEntry.CONTENT_URI, cvArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("CUSTOM ERROR : ", "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.v("JSON RESPONSE", jsonResponse);
            }
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
