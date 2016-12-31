package com.mind.oceanic.the.synchronicity2.importExport;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import com.mind.oceanic.the.synchronicity2.R;
import com.mind.oceanic.the.synchronicity2.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity2.model.Event;

/**
 * Created by dave on 3/28/16.
 */
public class UploadSynchItem extends Activity {

    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;
    long synchId=-1;
    String synchDate="";
    String synchSummary="";
    String synchAnalysis="";
    long synchWebId=-1;
    String synchUser="";
    String eventDate="";
    String eventSummary="";
    String eventDetails="";
    long eventWebId=-1;
    String strUrl="";
    Event event;
    List<Event> events;
    SynchronicityDataSource datasource;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Bundle b = getIntent().getExtras();
            synchId = b.getLong("SynchId");
            synchDate = b.getString("SynchDate");
            synchSummary = b.getString("SynchSummary");
            synchAnalysis = b.getString("SynchAnalysis");
            synchUser = b.getString("SynchUser");
            synchWebId = b.getLong("SynchWebId");
            datasource = new SynchronicityDataSource(this);
            datasource.open();
            Log.i("dolphinv", "in it");
//		Initialize the TextView for vertical scrolling
//            output = (TextView) findViewById(R.id.textView);
//            output.setMovementMethod(new ScrollingMovementMethod());
//
//            pb = (ProgressBar) findViewById(R.id.progressBar1);
//            pb.setVisibility(View.INVISIBLE);
            Log.i("dolphin","before event datasourcce for");
            events = datasource.findAllLinkedEvents(synchId);
            Log.i("dolphinv", "innn it");
            tasks = new ArrayList<MyTask>();
            uploadSynchItem();
//            requestData("www.oceanicmind.com");


            Log.i("dolphin","before event for");
            for (int i=0;i<events.size();i++){
                uploadEvent(events.get(i));
            }
            finish();
        }

    protected void uploadSynchItem(){
        try {
            synchDate = "'" + synchDate + "'";
            synchSummary = "'" + synchSummary + "'";
            synchAnalysis = "'" + synchAnalysis + "'";
            synchUser = "'" + synchUser + "'";
            Log.i("dolpin","SYNCHsynchUser="+synchUser);
            strUrl = "?type=SYNCH";
            strUrl = strUrl + "&synchId=" + synchId;
            strUrl = strUrl + "&synchDate=" + URLEncoder.encode(synchDate, "UTF-8");
            strUrl = strUrl + "&synchSummary=" + URLEncoder.encode(synchSummary, "UTF-8");
            strUrl = strUrl + "&synchAnalysis=" + URLEncoder.encode(synchAnalysis, "UTF-8");
            strUrl = strUrl + "&synchUser=" + URLEncoder.encode(synchUser, "UTF-8");
            strUrl = strUrl + "&synchWebId=" + synchWebId;
            strUrl = strUrl + "&eventId=" + "";
            strUrl = strUrl + "&eventDate=" + "";
            strUrl = strUrl + "&eventSummary=" + "";
            strUrl = strUrl + "&eventDetails=" + "";
            strUrl = strUrl + "&eventWebId" + eventWebId;
        } catch (Exception e){
            Log.i("dolphin","exception in upload="+strUrl);
        }
//        strUrl = "http://10.0.2.2/upload.php"+strUrl;
        strUrl = "http://theoceanicmind.com/upload.php"+strUrl;
        requestData(strUrl);
    }

    protected void uploadEvent(Event event){
        try{
            String eventId = String.valueOf(event.getEventId());
            eventDate = event.getEventDate();
            eventSummary = event.getEventSummary();
            eventDetails = event.getEventDetails();
            eventWebId = event.getEventWebId();
            eventDate = "'" + eventDate + "'";
            eventSummary = "'" + eventSummary + "'";
            eventDetails = "'" + eventDetails + "'";
            Log.i("dolphin","db quote eventSummary="+eventSummary);
            strUrl = "?type=EVENT";
            strUrl = strUrl + "&synchId=" + synchId;
            strUrl = strUrl + "&synchDate=" + "";
            strUrl = strUrl + "&synchSummary=" + "";
            strUrl = strUrl + "&synchAnalysis=" + "";
            strUrl = strUrl + "&synchUser=" + "";
            strUrl = strUrl + "&synchWebId=" + synchWebId;
            strUrl = strUrl + "&eventId=" + URLEncoder.encode(eventId, "UTF-8");
            strUrl = strUrl + "&eventDate=" + URLEncoder.encode(eventDate, "UTF-8");
            strUrl = strUrl + "&eventSummary=" + URLEncoder.encode(eventSummary, "UTF-8");
            strUrl = strUrl + "&eventDetails=" + URLEncoder.encode(eventDetails, "UTF-8");
            strUrl = strUrl + "&eventWebId=" + eventWebId;
            Log.i("dolphin","eventWebId==="+eventWebId+"  "+strUrl);
        } catch (Exception e){
            Log.i("dolphin","exception in event upload="+strUrl);

        }
 //       strUrl = "http://10.0.2.2/upload.php"+strUrl;
        strUrl = "http://theoceanicmind.com/upload.php"+strUrl;
        Log.i("dolphin","ebefore xception in event upload="+strUrl);

        requestData(strUrl);
    }
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            getMenuInflater().inflate(R.menu.menu_main, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            if (item.getItemId() == R.id.action_get_data) {
//
//                if (isOnline()) {
//                    Log.i("dolphinv", "get data");
//                    requestData("ooo");
//
//                } else {
//                    Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
//                }
//            }
//            return false;
//        }

        private void requestData(String uri) {
//		RequestPackage p = new RequestPackage();
////		p.setMethod("POST");
////		p.setMethod("GET");
//		p.setMethod("PUT");
//		p.setUri(uri);
//		p.setParam("name", "Rosa");
//		p.setParam("price", "13.95");

            MyTask task = new MyTask();
            Log.i("dolphinv","execution");
            task.execute(uri,"p2","p3");
            Log.i("dolphinv", "post execution");

        }

        protected void updateDisplay(String result) {
            output.append(result + "\n");
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

            public void Mytask(String url,String ur2, String ur3){

            }

            @Override
            protected void onPreExecute() {
                if (tasks.size() == 0) {
//                    pb.setVisibility(View.VISIBLE);
                }
                tasks.add(this);
            }


            @Override
            protected String doInBackground(String... params) {

                String u = params[0];
                try
                {

                    HttpClient httpclient = new DefaultHttpClient();

                    HttpPost method = new HttpPost(u);
                    Log.i("dolphin","method strUrl="+u);

                    HttpResponse response = httpclient.execute(method);
                    Log.i("dolphin","after method");

                    HttpEntity entity = response.getEntity();
                    Log.i("dolphin","after entity set");
                    if(entity != null){
                        Log.i("dolphin","entity not null");
//                        return EntityUtils.toString(entity);
                        return "string";
                    }
                    else{
                        Log.i("dolphin","entity null");
                        return "No string.";
                    }
                }
                catch(Exception e) {
                    Log.i("dolphin","exception hit");
                    e.printStackTrace();
                    return "Network problem";
                }
            }


//            @Override
//            protected String doInBackground(String... params) {
//                try
//                {
//                String id = "56";
//                String sdate = "'2018-02-19'";
//                String summary = "'this is the summary'";
//                String analysis = "'this is the analysis on one line but longer than the better half summary'";
//                String theStr = "";
////                        HttpPost method = new HttpPost(//"http://www.theoceanicmind.bitnamiapp.com/file.php?value="+output);
////						String theStr="";
//
////						HttpPost method = new HttpPost("http://www.theoceanicmind.com/file.php?value="+output);
//                sdate = URLEncoder.encode(sdate, "UTF-8");
//                summary = "&synchSummary="+URLEncoder.encode(summary,"UTF-8");
//                analysis = URLEncoder.encode(analysis,"UTF-8");
//
//                    Uri.Builder builder = new Uri.Builder();
//                    builder.scheme("http")
//                            .authority("10.0.2.2")
//                            .appendPath("www")
//                            .appendPath("synchId.php")
//                            .appendQueryParameter("synchId", "56")
//                            .appendQueryParameter("synchDate", sdate)
//                            .appendQueryParameter("synchSummary", summary)
//                            .appendQueryParameter("synchAnalysis", analysis);
//                    String myUrl = builder.build().toString();
//                String urlParameters  = myUrl;
//
//                    Log.i("dolphin","myUrl="+myUrl);
//                byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
//                    int    postDataLength = postData.length;
//                    String request = "http://10.0.2.2/www/synch.php";
//                    URL    url            = new URL(request);
//                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
//                    conn.setDoOutput( true );
//                conn.setInstanceFollowRedirects( false );
//                conn.setRequestMethod( "POST" );
//                conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
//                conn.setRequestProperty( "charset", "utf-8");
//                conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
//                conn.setUseCaches( false );
//                    try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
//                    wr.write( postData );
//                }
////                    try{
////                DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
////
////                        wr.write(postData);
////                    }
//                    catch (Exception e) {
//                        Log.i("dolphin","in exception on post");
//                        e.printStackTrace();
//                    }
//
//                    Log.i("dolphin","after entity set");
//
//                }
//                catch(Exception e) {
//                    Log.i("dolphin", "exception hit");
//                    e.printStackTrace();
//                    return "Network problem";
//                }
//                return "OK";
//            }

            @Override
            protected void onPostExecute(String result) {

                tasks.remove(this);
                if (tasks.size() == 0) {
                }


            }

        }



    }
