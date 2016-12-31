package com.mind.oceanic.the.synchronicity2.importExport;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mind.oceanic.the.synchronicity2.R;
import com.mind.oceanic.the.synchronicity2.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity2.model.Event;
import com.mind.oceanic.the.synchronicity2.model.SynchItem;
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
public class DownloadJSONold extends Activity implements View.OnClickListener {

    List<MyTask> tasks;
    List<SynchItem> synchItems;
    List<Event> events;
    List<SynchItemEvent> synchItemEvents;

    TextView output;
    EditText userName;
    Button doImport;
    String entryDate="";
    String nameSI;
    String nameE;
    String nameSE;
    SynchronicityDataSource datasource;
    String dbType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        Log.i("dolphin", "before si set adapter");

        userName = (EditText) findViewById(R.id.txt_user_name);
        doImport = (Button) findViewById(R.id.btnDoImport);
        output = (TextView) findViewById(R.id.output);
        doImport.setOnClickListener(this);
        datasource = new SynchronicityDataSource(this);
        datasource.open();
    }

    @Override
    public void onClick(View view) {
        String dbType="";
        if (view == doImport) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            entryDate = df.format(c.getTime());
            String entryUserName = userName.getText().toString();
            nameSI = "http://10.0.2.2/www/SynchItem"+entryUserName+entryDate+".json";
            nameE = "http://10.0.2.2/www/Event"+entryUserName+entryDate+".json";
            nameSE = "http://10.0.2.2/www/SynchItemEvent"+entryUserName+entryDate+".json";
        }
        Log.i("dolphinp", "synchItemName ="+nameSI);
        dbType = "SI";
        requestData(nameSI,dbType);
        dbType = "E";
        requestData(nameE,dbType);
        dbType = "SE";
        requestData(nameSE,dbType);

    }

    private void requestData(String uri,String dbType) {

        MyTask task = new MyTask();
        tasks = new ArrayList<MyTask>();
        Log.i("dolphinv", "execution");
        task.execute(uri, dbType, "p3");
        Log.i("dolphinv", "post execution");
    }

    protected void updateDisplay() {
        Log.i("dolphinv", "pissed updateDisplay execution");
        if (dbType.equals("SI")) {
            if (synchItems != null) {
                for (SynchItem synchItem : synchItems) {
                    output.append(synchItem.getSynchSummary() + "\n");
                    Log.i("dolphin", "here in requestData loop");
                    datasource.updateFromWeb(synchItem);
                }

            }
        } else if (dbType.equals("E")) {
            if (events != null) {
                for (Event event : events) {
                    output.append(event.getEventSummary() + "\n");
                    Log.i("dolphin", "here in requestData loop");
                    datasource.updateFromWeb(event);
                }
            } else {
                if (synchItemEvents != null) {
                    for (SynchItemEvent synchItemEvent : synchItemEvents) {
                        output.append(synchItemEvent.getSeEventId() + "\n");
                        Log.i("dolphin", "here in requestData loop");
                        datasource.updateFromWeb(synchItemEvent);
                    }
                }
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
            String dbType = params[1];
            Log.i("dolphin","doInBack"+u);
            String result = getData(u);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("dolphin","post execute resut="+result);
            Log.i("dolphin","after result");
            if (dbType.equals("SI")) {
                Log.i("dolphin","before json call");
                synchItems = ParseJSONSynchItem.parseFeed(result);
                Log.i("dolphin","after result before updatedisplay"+synchItems.size());
            } else if (dbType.equals("E")) {
                events = ParseJSONEvent.parseFeed(result);
            } else {
                Log.i("dolphin","after result defaut"+dbType);
                synchItemEvents = ParseJSONSynchItemEvent.parseFeed(result);
            }
//            switch (dbType) {
//                case R.id.dbTypeSI:
//                    Log.i("dolphin","before json call");
//                    synchItems = ParseJSONSynchItem.parseFeed(result);
//                    Log.i("dolphin","after result before updatedisplay"+synchItems.size());
//                    break;
//
//                case "E":
//                    events = ParseJSONEvent.parseFeed(result);
//                    break;
//
//                case "SE":
//                    synchItemEvents = ParseJSONSynchItemEvent.parseFeed(result);
//                    break;
//            }
            updateDisplay();

            tasks.remove(this);
            if (tasks.size() == 0) {
            }
        }
    }

    public static String getData(String uri) {

        Log.i("dolphin","in getData="+uri);
        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            Log.i("dolphin","in getData=after url");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Log.i("dolphin","in getData=after http");
            StringBuilder sb = new StringBuilder();
            Log.i("dolphin","in getData=aftersb");
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Log.i("dolphin","in getData=after reader");
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                Log.i("dolphin","append "+line);
            }
            return sb.toString();
        } catch (Exception e) {
            Log.i("dolphin","before trace");
            e.printStackTrace();
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