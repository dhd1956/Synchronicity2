package com.mind.oceanic.the.synchronicity2.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mind.oceanic.the.synchronicity2.R;
import com.mind.oceanic.the.synchronicity2.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity2.model.Event;
import com.mind.oceanic.the.synchronicity2.model.SynchItemEvent;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by dave on 1/31/16.
 */

    public class EventListActivity extends Activity  implements View.OnClickListener  {
    Event event;
    SynchronicityDataSource datasource;

    String eventSource="";
    List<Event> events;
    protected long synchId=-1;
    protected long eventId;
    protected long verbId;
    protected String eventSummary;
    protected String eventDetails;
    protected String eventDate;
    protected long eventWebId;

    String entryDate = "";
    protected DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_summary);
        Log.i("dolphin","top of eventlistactivity");

        Bundle b = getIntent().getExtras();
        eventSource = b.getString("EventSource");
        synchId = b.getLong("SynchId");
        datasource = new SynchronicityDataSource(this);
        datasource.open();
        events = datasource.findAllEvents();
        // dd dec/16
        if (events.size() < 1) {
            datasource.populateSample();
        }
        setList();
    }

    @Override
    public void onClick(View view) {

    }

    protected void setList() {

        final ListView lst1 = (ListView) findViewById(R.id.lst_events);

        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(EventListActivity.this,
                android.R.layout.simple_list_item_1, events);
        lst1.setAdapter(adapter);

        Log.i("dolphin", "bad move");
        lst1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        Log.i("dolphin", "there is a house in new " + eventId);
                        event = events.get(position);
                        eventId = event.getEventId();
                        eventDate = event.getEventDate();
                        eventWebId = event.getEventWebId();
                        Log.i("dolphin", "before summary");
                        if (event.getEventSummary() != null) {
                            eventSummary = event.getEventSummary().toString();
                        }
                        Log.i("dolphin", "after summary eventID=" + eventId + "evemtspirce=" + eventSource);
                        if (eventSource.equals("EventList")) {

                            Intent intent2 = new Intent(EventListActivity.this, UpdateEventActivity.class);
//                        intent2.putExtra("Id", synchItem.getSynchId());
                            intent2.putExtra("EventId", eventId);
                            intent2.putExtra("EventDate", eventDate);
                            intent2.putExtra("EventSummary", event.getEventSummary());
                            intent2.putExtra("EventDetails", event.getEventDetails());
                            intent2.putExtra("EventWebId", event.getEventWebId());
                            long eventId = -1;
                            intent2.putExtra("Flag", "Reset");
                            intent2.putExtra("EventSource", "EventList");
                            startActivityForResult(intent2, 2);
                        } else {
                            // link
                            Log.i("dolphin", "sink id=" + synchId + "  left=");

                            SynchItemEvent seEvent = new SynchItemEvent();
                            seEvent.setSeSynchId(synchId);
                            Log.i("dolphin", "in click of list=" + synchId + " eventid=" + eventId);
                            seEvent.setSeEventId(eventId);
                            try {
                                // dd Oct 2016 - removed create and add updateSELink
                                //datasource.create(seEvent);
                                datasource.updateSELink(seEvent.getSeSynchId(),seEvent.getSeEventId());
                                // dd Oct 2016 - remove create and add updateSELink

                                finish();
                            } catch (Exception e) {
                                Context context = getApplicationContext();
                                CharSequence text = "Already selected";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                    }
                }
        );
        lst1.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = new Event();
                event = events.get(position);
                eventId = event.getEventId();
//                Log.i("dolphin", "deleting=" + synchId + synchItem.getVerbName());
                if (okCancelEventAlert()) {
                }
                return true;
            }
        });
    }

    protected void recordEvent() {
        Intent intent2 = new Intent(EventListActivity.this, UpdateEventActivity.class);
        eventId = -1;
        eventSummary = null;
        eventDetails = null;
        eventDate = null;
        eventWebId = -1;
        intent2.putExtra("EventId", eventId);
        intent2.putExtra("EventSummary", eventSummary);
        intent2.putExtra("EventDetails", eventDetails);
        intent2.putExtra("EventDate", eventDate);
        intent2.putExtra("EventWebId",eventWebId);
        intent2.putExtra("EventSource", "EventList");
        startActivityForResult(intent2, 2);
    }


    @Override
    protected void onResume() {
        super.onResume();
        events = datasource.findAllEvents();
        setList();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        Log.i("dolphin", "onactivityresulttop" + requestCode);
        if(requestCode==1)
        {
            verbId=data.getLongExtra("VerbId",2);
            Log.i("dolphin","verbid from above"+verbId);
        }
    }
    protected boolean okCancelEventAlert(){


        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EventListActivity.this);
        myAlertDialog.setTitle("Delete");
        myAlertDialog.setMessage("Press Ok to delete");
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the OK button is clicked
                datasource.deleteEvent(eventId);
                datasource.deleteEventNotes(eventId);
                datasource.deleteSynchEventEventOrphans(eventId);
                events = datasource.findAllEvents();
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


    public void openRecordEvent(View view){
        recordEvent();
    }

    protected void findAllUnlinkedEvents() {
        events = datasource.findAllUnlinkedEvents();
        setList();
    }

    protected void findAllEvents() {
        events = datasource.findAllEvents();
        setList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_list, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = i.getItemId();

        switch (i.getItemId()) {

            case R.id.menu_record_event:
                recordEvent();
                break;

            case R.id.menu_show_all:
                findAllEvents();
                break;

            case R.id.menu_show_unlinked_only:
                findAllUnlinkedEvents();
//                datasource.repairAllUnlinkedEvents();
                break;

            case R.id.menu_return:
            finish();
            break;


            case R.id.menu_help:
                Intent intent = new Intent(this, HelpEventListActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }
}