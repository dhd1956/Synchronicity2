package com.mind.oceanic.the.synchronicity2.importExport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mind.oceanic.the.synchronicity2.R;
import com.mind.oceanic.the.synchronicity2.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity2.model.SynchDownload;
import com.mind.oceanic.the.synchronicity2.synch.HelpSynchListActivity;

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
public class DownloadJSON extends Activity implements View.OnClickListener {

    List<MyTask> tasks;
    List<SynchDownload> synchDownloads;
    TextView output;
    EditText userName;
    Button doImport;
    Button doReturn;
    String entryDate="";
    String synchItemName;
    String entryUserName="";
    SynchronicityDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        Log.i("dolphin", "before si set adapter");

        userName = (EditText) findViewById(R.id.txt_user_name);
        doImport = (Button) findViewById(R.id.btnDoImport);
        doReturn = (Button) findViewById(R.id.btnReturn);
        output = (TextView) findViewById(R.id.output);
        doImport.setOnClickListener(this);
        doReturn.setOnClickListener(this);
        datasource = new SynchronicityDataSource(this);
        datasource.open();
    }

    @Override
    public void onClick(View view) {
        if (view == doImport) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            entryDate = df.format(c.getTime());
            entryUserName = userName.getText().toString();
            //synchItemName = "http://10.0.2.2/www/Synch"+entryUserName+entryDate+".json";
            synchItemName = "http://theoceanicmind.com/info/download"+entryUserName+entryDate+".json";
            requestData(synchItemName);
            Log.i("dolphinp", "synchItemName =" + synchItemName);
        }
        if (view == doReturn) {
            finish();
        }
    }

    private void requestData(String uri) {

        MyTask task = new MyTask();
        tasks = new ArrayList<MyTask>();
        Log.i("dolphinv", "execution" + uri);
        task.execute(uri, "p2", "p3");
        Log.i("dolphinv", "post execution");
    }

    protected void updateDisplay() {
        Log.i("dolphinv", "pissed updateDisplay execution");

        if (synchDownloads != null) {
            for (SynchDownload synchDownload : synchDownloads) {
                output.append(synchDownload.getSynchSummary() + "\n");
                Log.i("dolphin", "here in requestData loop");
                datasource.updateFromWeb(synchDownload);
                datasource.setToUser(entryUserName);
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
            Log.i("dolphin", "post execute resut=" + result);
            if (result == null) {
                Log.i("dolphin", "after result");
                okEventAlert();
            } else {
                synchDownloads = ParseJSON.parseFeed(result);
                Log.i("dolphin", "back fromJSON and synchItems size=");//+synchItems.size());
                Log.i("dolphin", "after result before updatedisplay" + synchDownloads.size());
                updateDisplay();
            }
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
            line = reader.readLine();
            Log.i("dolphin","first line append "+line);
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
                Log.i("dolphin","append "+line);
            }
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//                Log.i("dolphin","append "+line);
//            }
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
    protected void okEventAlert(){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Log.i("dolphin", "Download");
    builder.setMessage("No Downloads found for this User")
       .setCancelable(false)
       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
           }
       });
        Log.i("dolphin", "alert2");
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_download, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = i.getItemId();

        switch (i.getItemId()) {

            case R.id.menu_return:
                finish();
                break;

            case R.id.menu_help:
                Intent intent = new Intent(this, HelpDownloadJSONActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }
}