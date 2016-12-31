package com.mind.oceanic.the.synchronicity2.synch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mind.oceanic.the.synchronicity2.HelpActivity;
import com.mind.oceanic.the.synchronicity2.R;
import com.mind.oceanic.the.synchronicity2.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity2.model.SynchItem;

import java.math.BigInteger;
import java.util.List;

public class SynchListActivity extends Activity implements View.OnClickListener {

    List<SynchItem> synchItems;
    SynchronicityDataSource datasource;
    SynchItem synchItem;
    long synchId = -1;
    String synchDate = null;
    String synchSummary = null;
    String synchDetails = null;
    String synchUser = null;
    long synchWebId=-1;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synch_list);

        datasource = new SynchronicityDataSource(this);
//        Log.i("dolphin", "before start");
        datasource.open();
        synchItems = datasource.findAllSynchItems();
        //dd dec/16
        if (synchItems.size() < 1) {
            datasource.populateSample();
        }
        BigInteger getDate = datasource.getDateId();
//        Log.i("dolphin","date as of now is "+getDate);
        setList();
    }

    protected void createNewSynch() {
        Intent intent = new Intent(SynchListActivity.this, MaintainSynchronicityActivity.class);
        synchId = -1;  //added monday mar 14/16
        synchDate = null;
        synchSummary = null;
        synchDetails = null;
        synchUser = null;
        synchWebId = -1;
        intent.putExtra("Id", synchId);
        intent.putExtra("Date",synchDate);
        intent.putExtra("Summary", synchSummary);
        intent.putExtra("Detail", synchDetails);
        intent.putExtra("User", synchUser);
        intent.putExtra("SynchWebId", synchWebId);
        intent.putExtra("Flag","Reset");
        Log.i("dolphin", "still alive");
        startActivityForResult(intent, 1);
    }


    public void openCreateNewSynch(View view){
        createNewSynch();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_synch_list, menu);
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

            case R.id.menu_new_synch:
                createNewSynch();
                break;

            case R.id.menu_help:
                Intent intent = new Intent(this, HelpSynchListActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }


    @Override
    public void onClick(View view) {
//        if (view == shoppingDate) {
//
//        }
    }

    public void setList() {

//        synchItems = datasource.findAllSynchItems();
        final ListView lst1 = (ListView) findViewById(R.id.lst_synch_summary);
        ArrayAdapter<SynchItem> adapter = new ArrayAdapter<SynchItem>(this,
                android.R.layout.simple_list_item_1, synchItems);
        lst1.setAdapter(adapter);

        lst1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        synchItem = synchItems.get(position);
                        synchId = synchItem.getSynchId();
                        Log.i("dolphin", "there is a house in new " + synchId);
                        Log.i("dolphin", "before summary");
                        if (synchItem.getSynchSummary() != null) {
                            synchSummary = synchItem.getSynchSummary().toString();
                        }
                        Log.i("dolphin", "after summary");
                        synchDate = synchItem.getSynchDate();
                        synchDetails = synchItem.getSynchAnalysis().toString();
                        Intent intent2 = new Intent(SynchListActivity.this, MaintainSynchronicityActivity.class);
//                        intent2.putExtra("Id", synchItem.getSynchId());
                        intent2.putExtra("Id", synchId);
                        intent2.putExtra("Date", synchDate);
                        intent2.putExtra("Summary", synchItem.getSynchSummary());
                        intent2.putExtra("Details", synchItem.getSynchAnalysis());
                        intent2.putExtra("User", synchItem.getSynchUser());
                        intent2.putExtra("SynchWebId", synchItem.getSynchWebId());
                        long eventId = -1;
                        intent2.putExtra("Flag", "Reset");
                        Log.i("dolphin", "in click of list=" + synchItem.getSynchAnalysis());
                        startActivityForResult(intent2, 2);
                    }
                }
        );
        lst1.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SynchItem synchItem = new SynchItem();
                synchItem = synchItems.get(position);
                synchId = synchItem.getSynchId();
//                Log.i("dolphin", "deleting=" + synchId + synchItem.getVerbName());
                if (okCancelSynchAlert()) {
                }
                return true;
            }
        });
    }
    protected boolean okCancelSynchAlert(){


        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(SynchListActivity.this);
        myAlertDialog.setTitle("Delete");
        myAlertDialog.setMessage("Press Ok to delete");
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the OK button is clicked
                datasource.deleteSynchItem(synchId);
                datasource.deleteSynchEventSynchOrphans(synchId);
                synchItems = datasource.findAllSynchItems();
                setList();
            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the Cancel button is clicked
            }
        });
        myAlertDialog.show();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        synchId = -1; comment out monday night March 14/16
        synchItems = datasource.findAllSynchItems();
        setList();
    }
}
