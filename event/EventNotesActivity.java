package com.mind.oceanic.the.synchronicity2.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.mind.oceanic.the.synchronicity2.R;
import com.mind.oceanic.the.synchronicity2.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity2.model.Note;
import com.mind.oceanic.the.synchronicity2.model.Thing;
import com.mind.oceanic.the.synchronicity2.note.ContactsActivity;
import com.mind.oceanic.the.synchronicity2.note.ThingActivity;
import com.mind.oceanic.the.synchronicity2.note.VerbActivity;
import com.mind.oceanic.the.synchronicity2.model.Event;
import com.mind.oceanic.the.synchronicity2.model.Verb;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by dave on 2/15/16.
 */
public class EventNotesActivity extends Activity {

    protected List<Verb> verbs;
    protected List<Thing> things;
    protected List<Note> notes;

    SynchronicityDataSource datasource;

    long eventId=-1;
    long noteId=-1;
    long verbId=-1;
    long thingId=-1;
    String person="";
    String noteInfo="";
    String verbName;
    String thingName;

//    Button btnContacts;
//    Button btnCancel;
//    Button btnVerb;
//    Button btnThing;
//    Button btnSave;
//    Button btnAddToCalendar;
    ListView lstVerb;
    ListView lstThing;
    EditText txtNote;
    EditText txtPerson;

    boolean ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        datasource = new SynchronicityDataSource(this);
        datasource.open();

        setContentView(R.layout.event_notes);
        final Note note = new Note();

        Bundle b = getIntent().getExtras();
        eventId = b.getLong("EventId");
        noteId = b.getLong("NoteId");
        person = b.getString("NotePerson");
        noteInfo = b.getString("NoteInfo");
        txtPerson = (EditText) findViewById(R.id.txt_person);
        txtNote = (EditText) findViewById(R.id.txt_note);

        //Log.i("dolphin","from bundle event id="+eventId);

        verbs = datasource.findAllVerbs();
        if (verbs.size() < 1) {
            datasource.populateVerbs();
        }

//        btnContacts = (Button) findViewById(R.id.btn_contacts);
//        btnCancel = (Button) findViewById(R.id.btn_cancel);
//        btnVerb = (Button) findViewById(R.id.btn_verb);
//        btnThing = (Button) findViewById(R.id.btn_thing);
//        btnSave = (Button) findViewById(R.id.btn_save);
//        btnAddToCalendar = (Button) findViewById(R.id.btn_add_to_calendar);
        lstVerb = (ListView) findViewById(R.id.lst_verb);
        lstThing = (ListView) findViewById(R.id.lst_thing);
//        btnSave.setOnClickListener(this);
        txtPerson.setText(person);
        txtNote.setText(noteInfo);
    }


    public void setToSave() {
        Note note = new Note();
        note.setNoteId(noteId);
        note.setFkEventId(eventId);
        note.setNotePerson(txtPerson.getText().toString());
        note.setNoteInfo(txtNote.getText().toString());
        if (noteId == -1) {
            //Log.i("dolphin", "savenew eventId=" + eventId+"  and "+note.getFkEventId());
            saveNew(note);
        } else {
            //Log.i("dolphin", "save Existing eventId=" + eventId+"  and "+note.getFkEventId());
            saveExisting(note);
        }
    }

    public void setVerbList() {
        //Log.i("dolphin", "in setVerblist");
        verbs = datasource.findAllVerbs();
        final ListView lstVerb = (ListView) findViewById(R.id.lst_verb);
        ArrayAdapter<Verb> adapter = new ArrayAdapter<Verb>(this,
                android.R.layout.simple_list_item_1, verbs);
        lstVerb.setAdapter(adapter);

        lstVerb.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        Verb verb = new Verb();
                        verb = verbs.get(position);
                        verbId = verb.getVerbId();

                        //Log.i("dolphin", "before summary verbid=" + verbId + "  name=" + verb.getVerbName().toString());
                        if (verb.getVerbName() != null) {
                            verbName = verb.getVerbName().toString();
                        }
                        //Log.i("dolphin", "after summary");
                        setVerbText();
                    }
                });
                lstVerb.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Verb verb = new Verb();
                        verb = verbs.get(position);
                        verbId = verb.getVerbId();
                        //Log.i("dolphin", "deleting=" + verbId + verb.getVerbName());
                        if (okCancelVerbAlert()) {
//                            datasource.deleteVerb(verbId);
                        }
                        return true;
                    }
                });
        }

    public void setThingList() {
        //Log.i("dolphin", "in setThinglist");
        things = datasource.findAllThings();
        final ListView lstThing = (ListView) findViewById(R.id.lst_thing);
        ArrayAdapter<Thing> adapter = new ArrayAdapter<Thing>(this,
                android.R.layout.simple_list_item_1, things);
        lstThing.setAdapter(adapter);

        lstThing.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {

                        Thing thing = new Thing();
                        thing = things.get(position);
                        thingId = thing.getThingId();

                        //Log.i("dolphin", "before summary");
                        if (thing.getThingName() != null) {
                            thingName = thing.getThingName();
                        }
                        //Log.i("dolphin", "after summary");
                        setThingText();
                    }
                });
        lstThing.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Thing thing = new Thing();
                thing = things.get(position);
                thingId = thing.getThingId();
                //Log.i("dolphin", "deleting=" + thingId + thing.getThingName());
                if (okCancelThingAlert()) {
//                    datasource.deleteThing(thingId);
                }
                return true;
            }
        });
    }

    protected void setVerbText() {
        if (verbId > -1) {
            for (int i=0;i<verbs.size();i++) {
                if (verbs.get(i).getVerbId() == verbId) {
                    //Log.i("dolphin","setting text verbs.get="+verbs.get(i).getVerbName()+"  i="+i+"  verbid="+verbId);
                    txtNote.setText(updateVerbText(txtNote.getText().toString(), verbs.get(i).getVerbName().toString()));
                }
            }
        }
        verbId=-1;
    }

    protected void setThingText() {
        if (thingId > -1) {
            for (int i=0;i<things.size();i++) {
                if (things.get(i).getThingId() == thingId) {
                    //Log.i("dolphin","setting text things.get="+things.get(i).getThingName()+"  i="+i+"  thingnId="+thingId);
                    txtNote.setText(updateThingText(txtNote.getText().toString(), things.get(i).getThingName().toString()));
                }
            }
        }
        thingId=-1;
    }

    protected String updateVerbText(String str1,String str2){
        //Log.i("dolphinpo","str1="+str1+"  str2"+str2);
        if (str1.length()>0) {
            str1 = str1 + "\n" + str2;
        } else {
            str1 = str2;
        }
        return str1;
    }

    protected String updateThingText(String str1,String str2){
        if (str1.length()>0) {
            str1 = str1 + " " + str2;
        } else {
            str1 = str2;
        }
        return str1;
    }
    protected void getContacts() {

        Intent intent5 = new Intent(EventNotesActivity.this, ContactsActivity.class);
        verbId = -1;
        intent5.putExtra("VerbId", verbId);
        //Log.i("dolphin", "btnContacts click");
        startActivityForResult(intent5, 5);
    }

    protected void addToCalendar() {
        noteInfo = txtNote.getText().toString();
        Event event = new Event();
        event = datasource.findEvent(eventId);
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, event.getEventSummary());
//        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, //txtPlaceName.getText().toString());
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, noteInfo);

        GregorianCalendar calDate = new GregorianCalendar(2012, 7, 15);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calDate.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                calDate.getTimeInMillis());

        startActivity(calIntent);

    }

    protected void setAction(){
        Intent intent1 = new Intent(EventNotesActivity.this, VerbActivity.class);
        verbId = -1;
        intent1.putExtra("VerbId", verbId);
        //Log.i("dolphin", "btnVerb click");
        startActivityForResult(intent1, 3);
    }

    protected void setItem(){
        Intent intent4 = new Intent(EventNotesActivity.this, ThingActivity.class);
        thingId = -1;
        intent4.putExtra("ThingId", thingId);
        intent4.putExtra("ThingName", thingName);
        //Log.i("dolphin","btnThing click");
        startActivityForResult(intent4, 4);
    }

    protected void save() {
//        if (!person.equals("")) {
            setToSave();
//            finish();
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        noteId = -1;
        Log.i("dolphin", "resume eventnotesactitivy");
        if (verbId > -1) {
            setVerbText();
        }
        if (thingId > -1) {
            setThingText();
        }
        setVerbList();
        setThingList();
    }

    protected boolean okCancelVerbAlert(){


        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EventNotesActivity.this);
        myAlertDialog.setTitle("Delete");
        myAlertDialog.setMessage("Press Ok to delete");
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the OK button is clicked
                ok = true;
                datasource.deleteVerb(verbId);
                setVerbList();
            }
        });
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // do something when the Cancel button is clicked
                ok = false;
            }
        });
        myAlertDialog.show();
        return ok;
    }

    protected boolean okCancelThingAlert(){


    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EventNotesActivity.this);
    myAlertDialog.setTitle("Delete");
    myAlertDialog.setMessage("Press Ok to delete");
    myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface arg0, int arg1) {
            // do something when the OK button is clicked
            ok = true;
            datasource.deleteThing(thingId);
            setThingList();
        }
    });
    myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface arg0, int arg1) {
            // do something when the Cancel button is clicked
            ok = false;
        }
    });
    myAlertDialog.show();
    return ok;
}

    protected void saveNew(Note note) {
        //Log.i("dolphinv", "Item added  saveNew fk=" + note.getFkEventId());
        note = datasource.create(note);
        noteId = note.getNoteId();
    }

    protected boolean saveExisting(Note note) {
        //Log.i("dolphinu", "saveexisting");
        if (datasource.update(note)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        //Log.i("dolphin", "onactivityresulttop" + requestCode);
//        if(requestCode==3)
//        {
//            verbId=data.getLongExtra("VerbId",2);
//        } else if (requestCode==2) {
//            thingId=data.getLongExtra("ThingId",2);
        if (requestCode == 5) {
            person = data.getStringExtra("ContactName");
            //Log.i("dolphin", "person here is " + person);

            txtPerson.setText(person);

        }
    }


    protected void checkForActionRequired(String person) {
        String thisNote = "";
        int actionItemCount = 0;
        int altActionItemCount = 0;
        String thisThing = "";
        String thisAction = "";
        String thisAltAction = "";
        String thisAppliedAction = "";
        String thisAltThing = "";
        String thisAltNote = "";
        String thisAltSentence = "";

        List<Note> personNotes;
        List<Thing> things;
        List<Verb> actions;
        List<Verb> altActions;
        List<Thing> altThings;
        List<Note> altNotes;

        actions = datasource.findAllVerbs();
        things = datasource.findAllThings();
        person = txtPerson.getText().toString();
        //Log.i("dolphinc","person="+person);
        personNotes = datasource.findAllEventNotes(person);
        altActions = datasource.findAllVerbs();
        altThings = datasource.findAllThings();
        altNotes = datasource.findAllEventNotes(person);

        for(int i=0;i<actions.size();i++) {
            //Log.i("dolphind","action="+actions.get(i).getVerbName());
            //Log.i("dolphind","applies to="+actions.get(i).getVerbAppliesTo());
        }

        for (int i=0;i<personNotes.size();i++) {
            //Log.i("dolphinc","Note:" +personNotes.get(i).getNoteId());
            //Log.i("dolphinc","fkevent: "+personNotes.get(i).getFkEventId());
            //Log.i("dolphinc", "note: " + personNotes.get(i).getNoteInfo());
//            datasource.deleteNote(personNotes.get(i).getNoteId());
        }

        for (int a = 0; a < actions.size(); a++) {
            //Log.i("dolphinx", "topper of action item thisAction=" + actions.get(a).getVerbName()+"  a="+a);
            if (actions.get(a).getVerbAppliesTo().equals("")) {
                thisAction = actions.get(a).getVerbName();
                for (int t = 0; t < things.size(); t++) {
                    thisThing = things.get(t).getThingName();
                    for (int i = 0; i < personNotes.size(); i++) {
                        thisNote = personNotes.get(i).getNoteInfo();
                        //Log.i("dolphindoo", "before add for sentence=" + thisAction+" "+thisThing + "  thisnote=" + thisNote+"  ActionItemCount=: "+actionItemCount+"  alt="+altActionItemCount);

                    }
                    for (int i = 0; i < personNotes.size(); i++) {
                        // inner note count for multiples
                        thisNote = personNotes.get(i).getNoteInfo();
                        //Log.i("dolphinx", "inner note count for multipples  thisAction=" + thisAction + " and item=" + thisThing + "  thisnote=" + thisNote+"  ActionItemCount=: "+actionItemCount+"  alt="+altActionItemCount);
                        String thisSentence = thisAction + " " + thisThing;
//                        actionItemCount = actionItemCount + findAnActionItem(thisAction, thisThing, thisNote);
                        //Log.i("dolphindoo", "before add for sentence=" + thisSentence + " thisnote=" + thisNote+"  ActionItemCount=: "+actionItemCount+"  alt="+altActionItemCount);
                        if (inStr(1,thisNote,thisSentence)) {
                            //Log.i("dolphindoo", "add one for sentence=" + thisSentence + " thisnote=" + thisNote+"  ActionItemCount=: "+actionItemCount+"  alt="+altActionItemCount);

                            actionItemCount++;
                        }

                        // on this note
                        for (int alti = 0; alti < altActions.size(); alti++) {
                            thisAppliedAction = altActions.get(alti).getVerbAppliesTo();
                            //Log.i("dolphindoo","thisAppliedAction="+thisAppliedAction);
                            if (thisAppliedAction.equals(thisAction)) {
                                thisAltAction = altActions.get(alti).getVerbName();
                                //Log.i("dolphindoo","thisAction="+thisAction+"  thisAltAction="+thisAltAction);
                                for (int altj=0; altj < altThings.size();altj++) {
                                    thisAltThing = altThings.get(altj).getThingName();
                                    //Log.i("dolphindoo","thisAltThing="+thisAltThing+"  thisThing="+thisThing);
                                    if (thisAltThing.equals(thisThing)) {
                                        for (int altk=0; altk < altNotes.size();altk++) {
                                            thisAltNote = altNotes.get(altk).getNoteInfo();
                                            thisAltSentence = thisAltAction + " " + thisThing;
                                            //Log.i("dolphindoo","thisAltNote="+thisAltNote+"  thisAltSentence="+thisAltSentence);
                                            if (inStr(1,thisAltNote,thisAltSentence)) { altActionItemCount++; }
                                            //Log.i("dolphindoo","altActionItemCount="+altActionItemCount);
                                        }
                                    }
                                }
                            }
                        }
//                        //Log.i("dolphinx", "top of action item thisAction=" + thisAction + " and item=" + thisThing + "  thisnote=" + thisNote+"  ActionItemCount=: "+actionItemCount+"  alt="+altActionItemCount);
                        // dd Oct 2016
                    }
                    if (actionItemCount - altActionItemCount > 0) {
                        //Log.i("dolphinx", "prior to post action=" + thisAltAction + "  item=" + thisThing+" actionItemCount="+actionItemCount+" altActionItemCount="+altActionItemCount);
                        postActionItem(thisAltAction, thisThing);
                        //Log.i("dolphinx","post action="+thisAltAction+"  item="+thisThing);
                    }
                    actionItemCount = 0;
                    altActionItemCount = 0;
                }
            }
        }
    }

    protected boolean inStr(int startPos, String thisNote, String sentence) {
        int i=-0;
        int j=0;
        int matchPos=0;
        int startNotePos=0;
        boolean found=false;
        boolean done=false;

        i = startPos-1;
        //Log.i("dolphin","instr top thisNote="+thisNote+"  sentence="+sentence);
        while ((i < thisNote.length()-1) && !done) {
//            //Log.i("dolphinx","instr note="+thisNote+"  sentence="+sentence+"  notechar1="+thisNote.charAt(i)+"  i="+i+"  j="+j+"  sentencech="+sentence.charAt(j));
            startNotePos = i;

            while ((thisNote.charAt(i) == sentence.charAt(j)) && !done) {
//Log.i("dolphin","thisNote.charAt(i)="+thisNote.charAt(i)+"  sentencesame="+sentence.charAt(j));
//                //Log.i("dolphinaei","instr while thisnote =="+thisNote+"  aye="+i+" sentence="+sentence+"  and jay="+j+"  and sent len="+sentence.length());
                i++;
                j++;

                //Log.i("dolphinx","post this note="+thisNote+"  sentence="+sentence+"  sentence.len="+sentence.length()+"i="+i+"  j="+j);
//dd Oct 2016   if (j >= sentence.length()-1) {
                if (j >= sentence.length()-1) {
                    done = true;
                    found = true;
                    //Log.i("dolphinx","instr found one while i =="+i+"  j="+j+  "note="+thisNote+"  sentence="+sentence.toString());
                } else {
                    if (i >= thisNote.length()-1) {
                        done = true;
                        //Log.i("dolphinx","done but not foundi =="+i+"  j="+j+  "note="+thisNote+"  sentence="+sentence.toString());
                    }
                }
            }
            if (!found) {
                i = startNotePos + 1;
                j = 0;
            }
            //Log.i("dolphinx","ifound="+found);
        }
        //Log.i("dolphinx","oofound="+found+"  matchPos="+matchPos);
        return found;
    }

    protected void postActionItem(String action,String item){
        //Log.i("dolphindo","action="+action+"  item="+item);
       txtNote.setText(updateVerbText(txtNote.getText().toString(), action));
       txtNote.setText(updateThingText(txtNote.getText().toString(), item));
    }

    public void openContacts(View view){
        getContacts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
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
                finish();
                break;

            case R.id.menu_contacts:
                getContacts();
                break;

            case R.id.menu_create_reminders:
                //save();
                checkForActionRequired(person);
                break;

            case R.id.menu_calendar:
                addToCalendar();
                break;

            case R.id.menu_action:
                setAction();
                break;

            case R.id.menu_item:
                setItem();
                break;

            case R.id.menu_save:
                save();
                finish();
                break;

            case R.id.menu_help:
                Intent intent = new Intent(this, HelpEventNotesActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }

}




