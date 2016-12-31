package com.mind.oceanic.the.synchronicity2.synch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.mind.oceanic.the.synchronicity2.importExport.UploadSynchItem;
import com.mind.oceanic.the.synchronicity2.R;
import com.mind.oceanic.the.synchronicity2.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity2.event.EventListActivity;
import com.mind.oceanic.the.synchronicity2.event.UpdateEventActivity;
import com.mind.oceanic.the.synchronicity2.model.Event;
import com.mind.oceanic.the.synchronicity2.model.SynchItem;
import com.mind.oceanic.the.synchronicity2.model.SynchItemEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by dave on 1/17/16.
 */
public class MaintainSynchronicityActivity extends Activity implements View.OnClickListener {

    SynchItem synchItem;
    SynchItem si;
    SynchronicityDataSource datasource;
    protected long synchId;
    protected String synchDate="";
    protected int synchRating;
    protected String synchSummary;
    protected String synchDetails;
    protected String synchUser;
    protected long synchWebId;

    Event event;
    List<Event> events;
    protected long eventId = -1;
    protected long leftEventId = -1;
    protected long rightEventId = -1;
    protected String eventSummary;
    protected String eventDetails;
    protected String eventDate=null;
    private EditText etDate;
    private EditText etSummary;
    private EditText etDetails;
    private ListView lstEvents;
    String entryDate = "";
    protected DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_synch);
        Bundle b = getIntent().getExtras();
//        synchItem = b.getParcelable(".model.SynchItem");
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        synchId = b.getLong("Id");
        Log.i("dolphin", "   staying alive");
        //synchDate = b.getString("Date");
        // dd nov 2016
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        synchDate = df.format(c.getTime());

        Log.i("dolphin", "   the datesynch is " + synchDate);
        synchSummary = b.getString("Summary");
        synchDetails = b.getString("Details");
        synchUser = b.getString("User");
        synchWebId = b.getLong("SynchWebId");
        String flag = b.getString("Flag");
        datasource = new SynchronicityDataSource(this);
        datasource.open();

        if (flag.equals("set")) {
            //check if synch events are already linked
            SynchItem synchItem = new SynchItem();
            // dd nov 2016
            //synchItem.setSynchId(synchId);
            synchItem.setSynchDate(synchDate);
            synchItem.setSynchSummary(synchSummary);
            synchItem.setSynchAnalysis(synchDetails);
            synchItem.setSynchUser(synchUser);
            synchItem.setSynchWebId(synchWebId);
            Log.i("dolphin", "eventId from start of mainaintit=" + flag + "  synchid=" + synchId);
            // dd nov 2016

            synchItem = datasource.create(synchItem);
            synchId = synchItem.getSynchId();
            Log.i("dolphin", "maintina start=" + synchSummary + "  AND " + synchDetails + "  and " + synchId);
            leftEventId = b.getLong("leftEventId");
            event = datasource.findEvent(leftEventId);
            linkEvent(synchId, leftEventId);
            showEvent(synchId, leftEventId);
            rightEventId = b.getLong("rightEventId");
            event = datasource.findEvent(rightEventId);
            linkEvent(synchId, rightEventId);
            showEvent(synchId, rightEventId);
        }
        etDate = (EditText) findViewById(R.id.txt_synch_date);
        etSummary = (EditText) findViewById(R.id.txt_synch_summary);
        etDetails = (EditText) findViewById(R.id.txt_synch_details);
        lstEvents = (ListView) findViewById(R.id.lst_events);
        Log.i("dolphin", "before detail is coming into maintain=");//+ synchDetails);
        if (synchId != -1) {
            Log.i("dolphin", "detail is coming into maintain=");//+ synchDetails);
            setEventList();
            setSynchInfo();
        }
        Log.i("dolphin", "after detail is coming into maintain=");//+ synchDetails);
        setDateTimeField();
    }
    private void getEvents() {
        if (eventId == -1) {
            eventDate = "";
            eventSummary = "";
            eventDetails = "";

        }
        Log.i("dolphin","sychid before getEvent intent is "+synchId);
        Intent intent2 = new Intent(MaintainSynchronicityActivity.this,UpdateEventActivity.class);
        intent2.putExtra("SynchId", synchId);
        intent2.putExtra("EventId", eventId);
        intent2.putExtra("EventSummary", eventSummary);
        intent2.putExtra("EventDetails",eventDetails);
        intent2.putExtra("EventDate", eventDate);
        intent2.putExtra("EventSource","UpdateEventActivity");
        startActivityForResult(intent2,2);

    }
    private void linkEvents() {
        if (eventId == -1) {
            eventDate = "";
            eventSummary = "";
            eventDetails = "";
        }

        Intent intent2 = new Intent(MaintainSynchronicityActivity.this,EventListActivity.class);
        intent2.putExtra("EventSource","Synch");
        intent2.putExtra("SynchId", synchId);
        intent2.putExtra("EventId", eventId);
        intent2.putExtra("EventSummary", eventSummary);
        intent2.putExtra("EventDetails",eventDetails);
        intent2.putExtra("EventDate", eventDate);
        startActivityForResult(intent2,5);

    }
    private void setDateTimeField() {
        Log.i("dolphin", "setdatetimefiled");

        etDate = (EditText) findViewById(R.id.txt_synch_date);
        Log.i("dolphinoo","synchDate is "+synchDate);
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();
        etDate.setOnClickListener(this);
        etDate.setText(synchDate);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        entryDate = df.format(c.getTime());
        Log.i("dolphin", "entryDate is before in date setting as " + entryDate);
        Log.i("dolphin", "s dt is before in date setting as " + synchDate);
        if (synchDate != null && synchDate.length()>3) {
            Log.i("dolphin","synchDate is in date setting as "+synchDate);
            etDate.setText(synchDate);
        } else {
            Log.i("dolphin","entryDate is in date setting as "+entryDate);
            etDate.setText(entryDate);
        }

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                etDate.setText(dateFormatter.format(newDate.getTime()));
//                synchDate = etDate.getText().toString();dd
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    public void setSynchInfo() {
        Log.i("dolphin", "setting text id=" + synchId);

        synchItem = datasource.find(synchId);
        etDate.setText(synchItem.getSynchDate());
        synchDate = etDate.getText().toString();
        Log.i("dolphin", "in setsynchinfo synchDate = " + synchDate);

        etSummary.setText(synchItem.getSynchSummary());
        etDetails.setText(synchItem.getSynchAnalysis());
    }

    protected void setSEInfo() {
 //       datasource.updateSELink(synchId);
    }

    @Override
    public void onClick(View view) {
        if(view == etDate) {

            datePickerDialog.show();
        }
        Log.i("dolphinp", "after new show=");
    }

    public void setEventList() {

        events = datasource.findAllLinkedEvents(synchId);
//        final ListView lst1 = (ListView) findViewById(R.id.lst_events);
        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(MaintainSynchronicityActivity.this,
                android.R.layout.simple_list_item_1, events);
//        lst1.setAdapter(adapter);
        Log.i("dolphino", "before seteventlist "+synchId);
        lstEvents.setAdapter(adapter);
        Log.i("dolphino", "seteventlist "+synchId);
        lstEvents.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        Log.i("dolphin", "after summary");

                        event = events.get(position);
                        eventId = event.getEventId();
                        Log.i("dolphin", "before summaryyyyyyy=" + eventId + "   " + position+"  synchId="+synchId);
                        if (event.getEventSummary() != null) {
                            eventSummary = event.getEventSummary();
                        }
                        showEvent(synchId, eventId);
                    }
                }
        );
        lstEvents.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = new Event();
                event = events.get(position);
                eventId = event.getEventId();
//                Log.i("dolphin", "deleting=" + synchId + synchItem.getVerbName());
                if (okCancelUnlinkEventAlert()) {
                }
                return true;
            }
        });
    }
    protected boolean okCancelUnlinkEventAlert(){


        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MaintainSynchronicityActivity.this);
        myAlertDialog.setTitle("Unlink");
        myAlertDialog.setMessage("Press Ok to Unlink this Event");
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                Log.i("dolphin", "unlinking");
                datasource.unlinkSynchEventItem(synchId, eventId);
//                events = datasource.findAllLinkedEvents(synchId);
                setEventList();
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


    protected void linkEvent(long synchId, long eventId) {
        Log.i("dolphin", "showevent");
        SynchItemEvent synchItemEvent = new SynchItemEvent();
        synchItemEvent.setSeSynchId(synchId);
        synchItemEvent.setSeEventId(eventId);
        Log.i("dolphinv", "Item crated synchId=" + synchId + "  eventid=" + eventId);
        datasource.create(synchItemEvent);
        eventDetails = event.getEventDetails();
    }

    protected void showEvent(long synchId, long eventId) {
        Intent intent2 = new Intent(MaintainSynchronicityActivity.this, UpdateEventActivity.class);
        intent2.putExtra("SynchId", synchId);
        intent2.putExtra("EventId", event.getEventId());
        intent2.putExtra("EventSummary", event.getEventSummary());
        intent2.putExtra("EventDetails", event.getEventDetails());
        intent2.putExtra("EventDate", event.getEventDate());
        intent2.putExtra("EventSource", "MaintainSynchronicityActivity");
        Log.i("dolphin", "in click of list=" + event.getEventDetails());
        startActivityForResult(intent2, 2);
    }


    protected void deleteBlank() {
        // when Cancelling, if a blank record exists, kill it
        if (synchId > 0 && synchSummary == null && synchDetails == null) {
            datasource.deleteSynchItem(synchId);
        }
    }

    protected void save(){
        synchDate = etDate.getText().toString();
        Log.i("dolphin", "  saving with this etdate " + synchId);
        SynchItem synchItem = new SynchItem();
        synchItem.setSynchId(synchId);
        synchItem.setSynchDate(etDate.getText().toString());
        synchItem.setSynchSummary(etSummary.getText().toString());
        synchItem.setSynchAnalysis(etDetails.getText().toString());
        if (synchId == -1) {
            Log.i("dolphinv","saving new");
            SaveNew(synchItem);
            synchId = synchItem.getSynchId();
            Log.i("dolphinv","saved new aith synchID="+synchId);
        } else {
            Log.i("dolphinv","saving existing");
            SaveExisting(synchItem);
        }
        datasource.updateUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (synchId != -1) {
            Log.i("dolphin", "onresume in maintainsynch  "+synchId);
            setEventList();
            setSynchInfo();
        }
        setDateTimeField();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        Log.i("dolphin", "onactivityresulttop" + requestCode);
        if(requestCode==1)
        {
            synchId=data.getLongExtra("SynchId",2);
            Log.i("dolphin","synchid from above"+synchId);
        } else if (requestCode == 5) {

        }
    }

    protected boolean SaveNew(SynchItem synchItem) {
        Log.i("dolphinv", "Item added");
        if (datasource.add(synchItem)) {
            synchId = synchItem.getSynchId();
            Log.i("dolphinv", "Item added-synchid="+synchId);
            return true;
        } else {
            Log.i("dolphinv", "ID not added");
            return false;
        }
    }

    protected boolean SaveExisting(SynchItem synchItem) {
        Log.i("dolphinu","saveexisting");
        if (datasource.update(synchItem)) {
            return true;
        } else {
            return false;
        }
    }

    protected void doUpload() {
        Log.i("dolphin", "doUpload");
        //datasource.updateUser();
        Intent intent7 = new Intent(MaintainSynchronicityActivity.this, UploadSynchItem.class);
        intent7.putExtra("SynchId", synchId);
        Log.i("dolphin", "etDate is " + etDate.getText());
        intent7.putExtra("SynchDate", etDate.getText().toString());
        intent7.putExtra("SynchSummary",etSummary.getText().toString());
        intent7.putExtra("SynchAnalysis",etDetails.getText().toString());
        Log.i("dolphin", "synchsummary is " + synchItem.getSynchSummary());
        intent7.putExtra("SynchUser",synchItem.getSynchUser().toString());
        Log.i("dolphin", "synchUser is " + synchItem.getSynchUser());
        intent7.putExtra("SynchWebId",synchItem.getSynchWebId());
        Log.i("dolphin", "synchwebid is " + synchItem.getSynchWebId());
        startActivityForResult(intent7,7);
    }

    public void openNewEvent(View view){
        save();
        eventId = -1;
        getEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maintain_synch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = i.getItemId();

        switch (i.getItemId()) {

//            case R.id.action_settings:
//                Intent intent = new Intent(this, HttpMainActivity.class);
//                startActivity(intent);
//                break;
            case R.id.menu_cancel:
                deleteBlank();
                finish();
                break;

            case R.id.menu_link_event:
                save();
                eventId = -1;
                linkEvents();
                break;

            case R.id.menu_new_event:
                save();
                eventId = -1;
                getEvents();
                break;

            case R.id.menu_export:
                save();
                eventId = -1;
                doUpload();
                break;

            case R.id.menu_save:
                save();
                finish();
                break;

            case R.id.menu_synch_list_help:
                Intent intent = new Intent(this, HelpMaintainSynchronicityActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }

}
