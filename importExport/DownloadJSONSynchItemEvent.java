package com.mind.oceanic.the.synchronicity2.importExport;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.mind.oceanic.the.synchronicity2.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity2.R;
import com.mind.oceanic.the.synchronicity2.model.SynchItemEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by dave on 4/6/16.
 */
public class DownloadJSONSynchItemEvent extends Activity {

    List<MyTask> tasks;
    List<SynchItemEvent> synchItemEvents;
    TextView output;
    SynchronicityDataSource datasource;
    EditText userName;
    Button doImport;
    String entryDate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        Log.i("dolphinp", "before sievent set adapter");
        userName = (EditText) findViewById(R.id.txt_user_name);
        doImport = (Button) findViewById(R.id.btnDoImport);
        output = (TextView) findViewById(R.id.output);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        entryDate = df.format(c.getTime());
        String entryUserName = userName.getText().toString();
        String synchItemEventName = "http://10.0.2.2/www/SynchItemEvent"+entryUserName+entryDate+".json";
        requestData(synchItemEventName);
        datasource = new SynchronicityDataSource(this);
        datasource.open();

    }

    private void requestData(String uri) {

        MyTask task = new MyTask();
        tasks = new ArrayList<MyTask>();
        Log.i("dolphinv", "execution");
        task.execute(uri, "p2", "p3");
        Log.i("dolphinv", "post execution");
    }

    protected void updateDisplay() {
        Log.i("dolphinv", "pissed execution");
        if (synchItemEvents != null) {
            for (SynchItemEvent synchItemEvent : synchItemEvents) {
                output.append(synchItemEvent.getSeSynchId() + "\n");
                datasource.updateFromWeb(synchItemEvent);
            }
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            Log.i("dolphin","onPreExec");
//            if (tasks.size() == 0) {
////                    pb.setVisibility(View.VISIBLE);
//            }
            tasks.add(this);
        }


        @Override
        protected String doInBackground(String... params) {
            String u = params[0];
            Log.i("dolphin","doInBack"+u);
            String result = getData(u);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("dolphin","post execute resut="+result);
            if (result != null) {
                synchItemEvents = ParseJSONSynchItemEvent.parseFeed(result);
                updateDisplay();
            }
            tasks.remove(this);
            if (tasks.size() == 0) {
            }
        }
    }

    public static String getData(String uri) {

        Log.i("dolphinp","in synchItemEvent getData="+uri);
        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                Log.i("dolphin","append "+line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("dolphin","before trace");
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e){
                    Log.i("dolphin","catch close");
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}