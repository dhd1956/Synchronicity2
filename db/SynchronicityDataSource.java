package com.mind.oceanic.the.synchronicity2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigInteger;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by dave on 1/19/16.
 */

import com.mind.oceanic.the.synchronicity2.model.Event;
import com.mind.oceanic.the.synchronicity2.model.Ignore;
import com.mind.oceanic.the.synchronicity2.model.Note;
import com.mind.oceanic.the.synchronicity2.model.SynchDownload;
import com.mind.oceanic.the.synchronicity2.model.SynchItem;
import com.mind.oceanic.the.synchronicity2.model.SynchItemEvent;
import com.mind.oceanic.the.synchronicity2.model.Thing;
import com.mind.oceanic.the.synchronicity2.model.Verb;

import java.util.ArrayList;
import java.util.Locale;

/**
     * Created by dave on 8/14/15.
     */
    public class SynchronicityDataSource {
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allSynchItemColumns = {
            SynchronicityDBOpenHelper.COLUMN_SYNCH_ID,
            SynchronicityDBOpenHelper.COLUMN_SYNCH_DATE,
            SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY,
            SynchronicityDBOpenHelper.COLUMN_SYNCH_ANALYSIS,
            SynchronicityDBOpenHelper.COLUMN_SYNCH_USER,
            SynchronicityDBOpenHelper.COLUMN_SYNCH_WEB_ID
    };

    private static final String[] allEventColumns = {
            SynchronicityDBOpenHelper.COLUMN_EVENT_ID,
            SynchronicityDBOpenHelper.COLUMN_EVENT_DATE,
            SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY,
            SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS,
            SynchronicityDBOpenHelper.COLUMN_EVENT_WEB_ID
    };

    private static final String[] allSynchItemEventColumns = {
            SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID,
            SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID,
            SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_WEB_ID,
            SynchronicityDBOpenHelper.COLUMN_SE_EVENT_WEB_ID
    };

    private static final String[] allIgnoresColumns = {
            SynchronicityDBOpenHelper.COLUMN_WORD};

    private static final String[] allPlacesColumns = {
            SynchronicityDBOpenHelper.COLUMN_PLACE_ID,
            SynchronicityDBOpenHelper.COLUMN_PLACE_NAME,
            SynchronicityDBOpenHelper.COLUMN_PLACE_MAIN_PHONE,
            SynchronicityDBOpenHelper.COLUMN_PLACE_EMAIL,
            SynchronicityDBOpenHelper.COLUMN_PLACE_ADDRESS,
            SynchronicityDBOpenHelper.COLUMN_PLACE_CITY,
            SynchronicityDBOpenHelper.COLUMN_PLACE_PROVINCE};

    private static final String[] allPersonsColumns = {
            SynchronicityDBOpenHelper.COLUMN_PERSON_ID,
            SynchronicityDBOpenHelper.COLUMN_PERSON_FIRST_NAME,
            SynchronicityDBOpenHelper.COLUMN_PERSON_LAST_NAME,
            SynchronicityDBOpenHelper.COLUMN_PERSON_MAIN_PHONE,
            SynchronicityDBOpenHelper.COLUMN_PERSON_EMAIL,
            SynchronicityDBOpenHelper.COLUMN_PERSON_ADDRESS,
            SynchronicityDBOpenHelper.COLUMN_PERSON_CITY,
            SynchronicityDBOpenHelper.COLUMN_PERSON_PROVINCE,
            SynchronicityDBOpenHelper.COLUMN_PERSON_COUNTRY};

    private static final String[] allThingsColumns = {
            SynchronicityDBOpenHelper.COLUMN_THING_ID,
            SynchronicityDBOpenHelper.COLUMN_THING_NAME,
            SynchronicityDBOpenHelper.COLUMN_THING_USE};

    private static final String[] allVerbCsolumns = {
            SynchronicityDBOpenHelper.COLUMN_VERB_ID,
            SynchronicityDBOpenHelper.COLUMN_VERB_NAME,
            SynchronicityDBOpenHelper.COLUMN_VERB_APPLIES_TO};

    private static final String[] allTNotesColumns = {
            SynchronicityDBOpenHelper.COLUMN_NOTE_ID,
            SynchronicityDBOpenHelper.COLUMN_FK_EVENT_ID,
            SynchronicityDBOpenHelper.COLUMN_NOTE_PERSON,
            SynchronicityDBOpenHelper.COLUMN_NOTE_INFO};

    public BigInteger getDateId() {
        Date now = new Date();
        String noww = new SimpleDateFormat("yyMMddHHmmssmmm", Locale.ENGLISH).format(now);
        Log.i("dolphin", "text noww=" + noww);
        BigInteger n = new BigInteger(noww);
        return n;
    }

    public BigInteger toBigInteger(String neww) {
        return new BigInteger(neww.getBytes());
    }


    public SynchronicityDataSource(Context context) {
        dbhelper = new SynchronicityDBOpenHelper(context);
    }

    public void open() {
        database = dbhelper.getWritableDatabase();
        Log.i("dolphin", "Database opened");
    }

    public void close() {
        Log.i("dolphin", "Database closed");
        dbhelper.close();
    }

    public SynchItem create(SynchItem synchItem) {
        Log.i("dolphin","creating synchitem");
        if (synchItem.getSynchSummary() != null) {
            Log.i("dolphin", "creating synchitem2  get"+synchItem.getSynchSummary());
            synchItem.setSynchSummary(synchItem.getSynchSummary().replaceAll("'", "\''"));
        }
        if (synchItem.getSynchAnalysis() != null) {
            synchItem.setSynchAnalysis(synchItem.getSynchAnalysis().replaceAll("'", "\''"));
        }
        Log.i("dolphin", "creating synchitem3");
        ContentValues values = new ContentValues();
        values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_DATE, synchItem.getSynchDate());
        values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY, synchItem.getSynchSummary());
        values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_ANALYSIS, synchItem.getSynchAnalysis());
        values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_USER, synchItem.getSynchUser());
        values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_WEB_ID, synchItem.getSynchWebId());
        long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS, null, values);
        synchItem.setSynchId(insertid);
        Log.i("dolphinv", "null synch item  " + synchItem.getSynchId());

        return synchItem;
    }

    public Ignore create(Ignore ignore) {
        Log.i("dolphinv", "TOP OF CREATE IG");
        if (ignore.getWord() == null) {
            Log.i("dolphinv", "null ig item");
        } else {
            String sqlCmd = "Insert into ignores values ('" + ignore.getWord() + "');";

            Log.i("dolphin", "sqlCmd=" + sqlCmd);
//                database.execSQL(sqlCmd);
            database.execSQL(sqlCmd);
            Log.i("dolphiny", "after execSQL");
        }
        return ignore;
    }

    public Event create(Event event) {
        Log.i("dolphinv", "TOP OF CREATE EVENT");
        event.setEventSummary(event.getEventSummary().replaceAll("'", "\''"));
        event.setEventDetails(event.getEventDetails().replaceAll("'", "\''"));

        if (event.getEventDetails() == null) {
            Log.i("dolphinv", "null synch item");
        } else {
            ContentValues values = new ContentValues();
            values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_DATE, event.getEventDate());
            values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY, event.getEventSummary());
            values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS, event.getEventDetails());
            values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_WEB_ID, event.getEventWebId());
            long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_EVENTS, null, values);
            event.setEventId(insertid);
        }
        return event;
    }

    public Verb create(Verb verb) {
        Log.i("dolphinv", "TOP OF CREATE VErB");
        verb.setVerbName(verb.getVerbName().replaceAll("'", "\''"));
        verb.setVerbAppliesTo(verb.getVerbAppliesTo().replaceAll("'", "\''"));

        if (verb.getVerbName() == null) {
            Log.i("dolphinv", "null verb item");
        } else {
            ContentValues values = new ContentValues();
            values.put(SynchronicityDBOpenHelper.COLUMN_VERB_NAME, verb.getVerbName());
            values.put(SynchronicityDBOpenHelper.COLUMN_VERB_APPLIES_TO, verb.getVerbAppliesTo());
            long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_VERBS, null, values);
            verb.setVerbId(insertid);
        }
        return verb;
    }


    public Thing create(Thing thing) {
        Log.i("dolphinv", "TOP OF CREATE VErB");
        thing.setThingName(thing.getThingName().replaceAll("'", "\''"));
        thing.setThingUse(thing.getThingUse().replaceAll("'", "\''"));

        if (thing.getThingName() == null) {
            Log.i("dolphinv", "null verb item");
        } else {
            ContentValues values = new ContentValues();
            values.put(SynchronicityDBOpenHelper.COLUMN_THING_NAME, thing.getThingName());
            values.put(SynchronicityDBOpenHelper.COLUMN_THING_USE, thing.getThingUse());
            long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_THINGS, null, values);
            thing.setThingId(insertid);
        }
        return thing;
    }

    public Note create(Note note) {
        Log.i("dolphinv", "TOP OF CREATE NOTE");

        note.setNotePerson(note.getNotePerson().replaceAll("'", "\''"));
        note.setNoteInfo(note.getNoteInfo().replaceAll("'", "\''"));

        if (note.getNoteInfo() == null) {
            Log.i("dolphinv", "null verb item eventid=" + note.getFkEventId());
        } else {
            ContentValues values = new ContentValues();
            values.put(SynchronicityDBOpenHelper.COLUMN_FK_EVENT_ID, note.getFkEventId());
            values.put(SynchronicityDBOpenHelper.COLUMN_NOTE_PERSON, note.getNotePerson());
            values.put(SynchronicityDBOpenHelper.COLUMN_NOTE_INFO, note.getNoteInfo());
            long insertid = database.insert(SynchronicityDBOpenHelper.TABLE_NOTES, null, values);
            note.setNoteId(insertid);
        }
        return note;
    }

    public boolean update(SynchItem synchItem) {
        synchItem.setSynchSummary(synchItem.getSynchSummary().replaceAll("'", "\''"));
        synchItem.setSynchAnalysis(synchItem.getSynchAnalysis().replaceAll("'", "\''"));

        String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " Set " + SynchronicityDBOpenHelper.COLUMN_SYNCH_DATE + " = '" +
                synchItem.getSynchDate() + "', " + SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY + " = '" +
                synchItem.getSynchSummary() + "', " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ANALYSIS + " = '" +
                synchItem.getSynchAnalysis() +
//                synchItem.getSynchUser() +
//                synchItem.getSynchWebId() +
                "' where " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ID +
                " = " + synchItem.getSynchId() + ";";
        Log.i("dolphiny", "update sql=" + sqlCmd);
        open();
        database.execSQL(sqlCmd);
        Log.i("dolphiny", "after execSQL");
        return (synchItem.getSynchId() != -1);
    }

    public boolean updateFromDownload(SynchItem synchItem) {
        synchItem.setSynchSummary(synchItem.getSynchSummary().replaceAll("'", "\''"));
        synchItem.setSynchAnalysis(synchItem.getSynchAnalysis().replaceAll("'", "\''"));

        String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " Set " + SynchronicityDBOpenHelper.COLUMN_SYNCH_DATE + " = '" +
                synchItem.getSynchDate() + "', " + SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY + " = '" +
                synchItem.getSynchSummary() + "', " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ANALYSIS + " = '" +
                synchItem.getSynchAnalysis() +
//                synchItem.getSynchUser() +
//                synchItem.getSynchWebId() +
                "' where " + SynchronicityDBOpenHelper.COLUMN_SYNCH_WEB_ID +
                " = " + synchItem.getSynchWebId() + ";";
        Log.i("dolphiny", "update sql=" + sqlCmd);
        open();
        database.execSQL(sqlCmd);
        Log.i("dolphiny", "after execSQL");
        return (synchItem.getSynchId() != -1);
    }


    public boolean update(SynchItemEvent synchItemEvent) {

        String sqlCmd = "UPDATE " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS + " SET " +
                SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_WEB_ID  + " = '" +
                synchItemEvent.getSeWebSynchId() +
                SynchronicityDBOpenHelper.COLUMN_SE_EVENT_WEB_ID + " = '" +
                synchItemEvent.getSeWebEventId() + "', " +
                " WHERE " + SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID + " = " + synchItemEvent.getSeSynchId() +
                " AND " + SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID + " + " + synchItemEvent.getSeEventId() +
                ";";
        Log.i("dolphiny", "update sql=" + sqlCmd);
        open();
        database.execSQL(sqlCmd);
        Log.i("dolphiny", "after execSQL");
        return (synchItemEvent.getSeSynchId() != -1);
    }


    public boolean update(Event event) {
        event.setEventSummary(event.getEventSummary().replaceAll("'", "\''"));
        event.setEventDetails(event.getEventDetails().replaceAll("'", "\''"));

        String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_EVENTS + " Set " +
                SynchronicityDBOpenHelper.COLUMN_EVENT_DATE + " = '" +
                event.getEventDate() + "', " +
                SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY + " = '" +
                event.getEventSummary() +
                "', " + SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS + " = '" +
                event.getEventDetails() +
                "', " + SynchronicityDBOpenHelper.COLUMN_EVENT_WEB_ID + " = " +
                event.getEventWebId() +
                " where " + SynchronicityDBOpenHelper.COLUMN_EVENT_ID + " = " + event.getEventId() + ";";
        Log.i("dolphiny", "update event sql=" + sqlCmd);
        open();
        database.execSQL(sqlCmd);
        Log.i("dolphiny", "after execSQL for update event");
        return (event.getEventId() != -1);
    }

    public boolean updateByDownload(Event event) {
        event.setEventSummary(event.getEventSummary().replaceAll("'", "\''"));
        event.setEventDetails(event.getEventDetails().replaceAll("'", "\''"));

        String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_EVENTS + " Set " +
                SynchronicityDBOpenHelper.COLUMN_EVENT_DATE + " = '" +
                event.getEventDate() + "', " +
                SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY + " = '" +
                event.getEventSummary() +
                "', " + SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS + " = '" +
                event.getEventDetails() +
                "' where " + SynchronicityDBOpenHelper.COLUMN_EVENT_WEB_ID + " = " + event.getEventWebId() + ";";
        Log.i("dolphiny", "update event by download sql=" + sqlCmd);
        open();
        database.execSQL(sqlCmd);
        Log.i("dolphiny", "after execSQL for update event");
        return (event.getEventId() != -1);
    }

    public boolean update(Verb verb) {
        verb.setVerbName(verb.getVerbName().replaceAll("'", "\''"));
        verb.setVerbAppliesTo(verb.getVerbAppliesTo().replaceAll("'", "\''"));

        String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_VERBS + " Set " + SynchronicityDBOpenHelper.COLUMN_VERB_NAME + " = '" + verb.getVerbName() + SynchronicityDBOpenHelper.COLUMN_VERB_APPLIES_TO + " = '" + verb.getVerbAppliesTo() + "' where " + SynchronicityDBOpenHelper.COLUMN_VERB_ID + " = " + verb.getVerbId() + ";";
        Log.i("dolphiny", "update sql=" + sqlCmd);
        open();
        database.execSQL(sqlCmd);
        Log.i("dolphiny", "after execSQL");
        return (verb.getVerbId() != -1);
    }

    public boolean update(Thing thing) {
        thing.setThingName(thing.getThingName().replaceAll("'", "\''"));
        thing.setThingUse(thing.getThingUse().replaceAll("'", "\''"));

        String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_THINGS + " Set " + SynchronicityDBOpenHelper.COLUMN_THING_NAME + " = '" + thing.getThingName() + "' where " + SynchronicityDBOpenHelper.COLUMN_THING_ID + " = " + thing.getThingId() + ";";
        Log.i("dolphiny", "update sql=" + sqlCmd);
        open();
        database.execSQL(sqlCmd);
        Log.i("dolphiny", "after execSQL");
        return (thing.getThingId() != -1);
    }

    public boolean update(Note note) {
        note.setNotePerson(note.getNotePerson().replaceAll("'", "\''"));
        note.setNoteInfo(note.getNoteInfo().replaceAll("'", "\''"));

        String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_NOTES + " Set " +
                SynchronicityDBOpenHelper.COLUMN_FK_EVENT_ID + " = '" + note.getFkEventId() + "', " +
                SynchronicityDBOpenHelper.COLUMN_NOTE_PERSON + " = '" + note.getNotePerson() + "', " +
                SynchronicityDBOpenHelper.COLUMN_NOTE_INFO + " = '" + note.getNoteInfo() + "' where " +
                SynchronicityDBOpenHelper.COLUMN_NOTE_ID + " = " + note.getNoteId() + ";";
        Log.i("dolphiny", "update sql=" + sqlCmd);
        open();
        database.execSQL(sqlCmd);
        Log.i("dolphiny", "after execSQL");
        return (note.getNoteId() != -1);
    }


    public SynchItem find(long synchId) {
        Log.i("dolphin", "find  7 sql=before");
        open();
//            String sqlCmd = "select synchDate, synchSummary, SynchAnalysis fromm synchItems where synchId = '" + synchItem.getSynchId() + "'";
        String sqlCmd = "select synchId, synchDate, synchSummary, SynchAnalysis, synchUser, synchWebId from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS +
                " where " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ID + " = '" + synchId + "';";
        Log.i("dolphin", "find 4 sql=" + sqlCmd);
        Cursor cursor = database.rawQuery(sqlCmd, null);
        SynchItem synchItem = new SynchItem();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Log.i("dolphin", "id=");
                synchItem.setSynchId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID)));
                Log.i("dolphin", "Date=");
                synchItem.setSynchDate(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_DATE)));
                Log.i("dolphin", "summ=");
                synchItem.setSynchSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY)));
                Log.i("dolphin", "Ddetail=");
                synchItem.setSynchAnalysis(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_ANALYSIS)));
                Log.i("dolphin", "user=");
                synchItem.setSynchUser(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_USER)));
                Log.i("dolphin", "lala web ="+ synchItem.getSynchUser());
                synchItem.setSynchWebId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_WEB_ID)));
                Log.i("dolphin", "after web=");
            }
        }
        Log.i("dolphin", "after Ddetail=");

        return synchItem;
    }

    public Event findEvent(long eventId) {
        Log.i("dolphin", "find event sql=before");
        open();
//            String sqlCmd = "seldect synchDate, synchSummary, SynchAnalysis fromm synchItems where synchId = '" + synchItem.getSynchId() + "'";
        String sqlCmd = "select eventId, eventDate, eventSummary, eventDetails, eventWebId from " + SynchronicityDBOpenHelper.TABLE_EVENTS + " where " + SynchronicityDBOpenHelper.COLUMN_EVENT_ID + " = '" + eventId + "';";
        Log.i("dolphin", "find 5 sql=" + sqlCmd);
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Event event = new Event();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Log.i("dolphin", "id=");
                event.setEventId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_ID)));
                Log.i("dolphin", "Date=");
                event.setEventDate(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DATE)));
                Log.i("dolphin", "summ=");
                event.setEventSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY)));
                Log.i("dolphin", "Ddetail=");
                event.setEventDetails(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS)));
                event.setEventWebId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_WEB_ID)));
            }
        }
        Log.i("dolphin", "after Ddetail=");

        return event;
    }

    public List<SynchItem> delete(SynchItem synchItem) {
        String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " where " +
                SynchronicityDBOpenHelper.COLUMN_SYNCH_ID + " = '" + synchItem.getSynchId() + "'";
        Log.i("dolphin", "delete sql=" + sqlCmd);
        database.execSQL(sqlCmd);
        List<SynchItem> synchItems = findAllSynchItems();
        return synchItems;
    }

    public List<SynchItem> deleteSynchItem(long synchId) {
        String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " where " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ID + " = '" + synchId + "'";
        Log.i("dolphin", "delete sql=" + sqlCmd);
        database.execSQL(sqlCmd);
        List<SynchItem> synchItems = findAllSynchItems();
        return synchItems;
    }

    public void deleteEvent(long i) {
        String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_EVENTS +
                " where " + SynchronicityDBOpenHelper.COLUMN_EVENT_ID + " = '" + i + "'";
        Log.i("dolphin", "delete sql=" + sqlCmd);
        database.execSQL(sqlCmd);
        List<SynchItem> synchItems = findAllSynchItems();

    }

    public void deleteEventNotes(long eventId) {
        String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_NOTES + " where " +
                SynchronicityDBOpenHelper.COLUMN_FK_EVENT_ID + " = '" + eventId + "'";
        Log.i("dolphin", "delete notes sql=" + sqlCmd);
        database.execSQL(sqlCmd);
    }

    public void deleteSynchEventEventOrphans(long eventId) {
        String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS + " where " +
                SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID + " = '" + eventId + "'";
        Log.i("dolphin", "delete sql=" + sqlCmd);
        database.execSQL(sqlCmd);
    }

    public void deleteSynchEventSynchOrphans(long synchId) {
        String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS + " where " + SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID + " = '" + synchId + "'";
        Log.i("dolphin", "delete sql=" + sqlCmd);
        database.execSQL(sqlCmd);
    }


    public void deleteVerb(long i) {
        String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_VERBS + " where " + SynchronicityDBOpenHelper.COLUMN_VERB_ID + " = '" + i + "'";
        Log.i("dolphin", "delete sql=" + sqlCmd);
        database.execSQL(sqlCmd);
        List<Verb> verbs = findAllVerbs();

    }

    public void deleteThing(long i) {
        String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_THINGS + " where " + SynchronicityDBOpenHelper.COLUMN_THING_ID + " = '" + i + "';";
        Log.i("dolphin", "delete sql=" + sqlCmd);
        database.execSQL(sqlCmd);
        List<Thing> things = findAllThings();

    }

    public void deleteNote(long i) {
        String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_NOTES + " where " + SynchronicityDBOpenHelper.COLUMN_NOTE_ID + " = '" + i + "';";
        Log.i("dolphin", "delete sql=" + sqlCmd);
        database.execSQL(sqlCmd);
        List<Thing> things = findAllThings();

    }

    public void deleteAllIgnores() {
        String sqlCmd = "delete from " + SynchronicityDBOpenHelper.TABLE_IGNORES + ";";
        database.execSQL(sqlCmd);
    }


    public List<SynchItem> findAllSynchItems() {

        String sqlCmd = "SELECT * FROM synchItems ORDER BY synchDate DESC;";
        Cursor cursor = database.rawQuery(sqlCmd, null);
        List<SynchItem> synchItems = synchItemCursorToList(cursor);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
        return synchItems;
    }

    public List<SynchItemEvent> findAllSynchItemEvents() {

//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, allSynchItemColumns,
//                    null, null, null, null, null);
        // dd nov/16
        //String sqlCmd = "SELECT * FROM synchItemEvents ORDER BY synchDate DESC;";
        String sqlCmd = "SELECT * FROM synchItemEvents;";
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " se rows");
        List<SynchItemEvent> synchItemEvents = synchItemEventCursorToList(cursor);
        return synchItemEvents;
    }

    public List<Note> findAllEventNotes(String person) {

//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, allSynchItemColumns,
//                    null, null, null, null, null);
        person = person.replaceAll("'", "\''");
        String sqlCmd = "select noteId, eventDate, notePerson, noteInfo from notes left join events ON eventId  =  fkEventId " +
                "where notePerson = '" + person + "' order by eventDate;";

        Log.i("dolphin", "sql for person notes is=" + sqlCmd);
        open();
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " note rows");
        List<Note> notes = personNoteCursorToList(cursor);
        return notes;
    }

    public List<Note> findAllLinkedNotes(long eventId) {

//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, allSynchItemColumns,
//                    null, null, null, null, null);
        String sqlCmd = "select * from notes where fkEventId = " + eventId + ";";
        Log.i("dolphin", "Dolphiwnate " + eventId);
        open();
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " note rows");
        List<Note> notes = noteCursorToList(cursor);
        return notes;
    }

    public List<Verb> findAllVerbs() {

//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, allSynchItemColumns,
//                    null, null, null, null, null);
        String sqlCmd = "select * from verbs;";
        Log.i("dolphin", "Dolphi");
        open();
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " verb rows");
        List<Verb> verbs = verbCursorToList(cursor);
        return verbs;
    }

    public List<Thing> findAllThings() {

//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS, allSynchItemColumns,
//                    null, null, null, null, null);
        String sqlCmd = "select * from things;";
        Log.i("dolphin", "Dolphinoo");
        open();
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " things rows");
        List<Thing> things = thingCursorToList(cursor);
        return things;
    }

    public List<Event> findAllEvents() {

        Log.i("dolphin", "Dolphin?? ");
        open();
//            Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_EVENTS, allEventColumns,
//                    null, null, null, null, null);

        String sqlCmd = "select * from events order by eventDate DESC;";
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
        List<Event> events = eventAllCursorToList(cursor);
        return events;
    }

    public List<Ignore> findAllIgnores() {

        Log.i("dolphin", "Dolphin?? ");
        open();
        Cursor cursor = database.query(SynchronicityDBOpenHelper.TABLE_IGNORES, allIgnoresColumns,
                null, null, null, null, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
        List<Ignore> ignores = ignoreCursorToList(cursor);
        return ignores;
    }


    public List<Event> findAllLinkedEvents(long synchId) {

        Log.i("dolphin", "Dolphin?? ");
        String sqlCmd = "select synchItemEvents.seEventId, events.eventDate, events.eventSummary, events.eventDetails, events.eventWebId " +
                "from synchItemEvents LEFT JOIN events ON synchItemEvents.seEventId = events.eventId  " +
                "WHERE synchItemEvents.seSynchId = " + synchId +
                " ORDER BY events.eventDate;";

        Log.i("dolphinu", "sqlCmd=" + sqlCmd);
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
        List<Event> events = eventCursorToList(cursor);
        return events;

    }

    public void unlinkSynchEventItem(long synchId, long eventId){

        String sqlCmd = "DELETE FROM " +
                SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS +
                " WHERE " +
                SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID +
                " = " + synchId +
                " AND " +
                SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID +
                " = " + eventId;
        Log.i("dolphinux", "sqlCmd=" + sqlCmd);
        database.execSQL(sqlCmd);
    }

    public boolean findLinkedTogetherEvents(long leftEventId, long rightEventId) {

        boolean matched = false;

        String sqlCmd = "SELECT synchItemEvents.sesynchId FROM synchItemEvents " +
                "WHERE seEventId = " + leftEventId + ";";
        Log.i("dolphin", "sqlCmd left=" + sqlCmd);
        open();
        Cursor leftCursor = database.rawQuery(sqlCmd, null);
        if (leftCursor.getCount() > 0) {
            sqlCmd = "SELECT synchItemEvents.sesynchId FROM synchItemEvents " +
                    "WHERE seEventId = " + rightEventId + ";";

            Log.i("dolphin", "sqlCmd right=" + sqlCmd);
            Cursor rightCursor = database.rawQuery(sqlCmd, null);
            if (rightCursor.getCount() > 0 && leftCursor.getCount() > 0) {
                while (leftCursor.moveToNext()) {
                    while (rightCursor.moveToNext()) {
                        Log.i("dolphin", "sqlCmd rightish=" + sqlCmd);
                        if (leftCursor.getLong(leftCursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID)) == rightCursor.getLong(rightCursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID))) {
                            //Log.i("dolphin", (leftCursor.getLong(0) + "  =  " + rightCursor.getLong(0)));
                            Log.i("dolphin", "while left and right cursor equal=" + leftCursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID) + "  =?  " + rightCursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID));
                            matched = true;
                        }
                    }
                }
            }
        }
        Log.i("dolphin", "after left and right cursor equal=");
        return matched;
    }


    public boolean findLinkedEvent(long synchId, long eventId) {

        Log.i("dolphin", "Dolphin?? ");
        ;

        String sqlCmd = "select eventidId, eventDate, eventSummary,eventDetails from event left join synchItemEvents ON eventId  =  seEventId;";
        Log.i("dolphin", "Dolphin?? ");


        Log.i("dolphinu", "sqlCmd=" + sqlCmd);
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Event> findAllUnlinkedEvents() {
//            TODO-sql to show unlinked events

        Log.i("dolphin", "Dolphin filtered ");


//            String sqlCmd="select * from events "+
//                    "where not exists (select * from synchItemEvents where events.EventId = synchItemEvents.seEventId);";
        String sqlCmd = "SELECT seSynchId, seEventId, eventDate, eventSummary, eventDetails, eventWebId FROM events " +
                "JOIN synchItemEvents ON eventId  =  seEventId WHERE seSynchId = -1;";


        Log.i("dolphinu", "sqlCmd=" + sqlCmd);

        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");

//        while (cursor.moveToNext()){
//           Log.i("dolphinin","cursor seEventId="+cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY))+"  "+cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID))+"  seSynchId="+cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID)));
//            Log.i("dolphinin","cursor seEventId="+cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID)));
//                            }

        List<Event> events = eventCursorToList(cursor);
        return events;
    }

    public void updateSELink(long synchId,long eventId){
        String sqlCmd = "UPDATE synchItemEvents SET seSynchId = " + synchId + " WHERE " + "seSynchId = -1 AND seEventId = " + eventId;
        Log.i("dolphin", "sqlCmd=" + sqlCmd);
        database.execSQL(sqlCmd);
    }

    public SynchItemEvent create(SynchItemEvent seEvent) {
        Log.i("dolphinv", "TOP OF CREATE seEVENT");

        String sqlCmd = " insert into synchItemEvents " +
                "(seSynchId, seEventId) " +
                "values ('" +
                seEvent.getSeSynchId() + "', '" + seEvent.getSeEventId() + "');";
        Log.i("dolphin", "sqlCmd=" + sqlCmd);
        database.execSQL(sqlCmd);
        Log.i("dolphiny", "after execSQL");
        return seEvent;
    }

    private List<SynchItem> synchItemCursorToList(Cursor cursor) {
        List<SynchItem> synchItems = new ArrayList<SynchItem>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                SynchItem synchItem = new SynchItem();
                synchItem.setSynchId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID)));
                synchItem.setSynchSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY)));
                synchItem.setSynchAnalysis(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_ANALYSIS)));
//                synchItem.setSynchUser(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_USER)));
//                synchItem.setSynchWebId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_WEB_ID)));
                synchItems.add(synchItem);
                Log.i("dolphin", "cursorToList synch item=" + synchItem.getSynchId());
            }
        }
        return synchItems;
    }

    private List<SynchItemEvent> synchItemEventCursorToList(Cursor cursor) {
        List<SynchItemEvent> synchItemEvents = new ArrayList<SynchItemEvent>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                SynchItemEvent synchItemEvent = new SynchItemEvent();
                synchItemEvent.setSeSynchId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID)));
                synchItemEvent.setSeEventId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID)));
                synchItemEvents.add(synchItemEvent);
                Log.i("dolphin", "cursorToList synch item event =" + synchItemEvent.getSeSynchId());
            }
        }
        return synchItemEvents;
    }

    private List<Verb> verbCursorToList(Cursor cursor) {
        List<Verb> verbs = new ArrayList<Verb>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Verb verb = new Verb();
                verb.setVerbId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_VERB_ID)));
                verb.setVerbName(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_VERB_NAME)));
                verb.setVerbAppliesTo(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_VERB_APPLIES_TO)));
                verbs.add(verb);
            }
        }
        return verbs;
    }

    private List<Thing> thingCursorToList(Cursor cursor) {
        List<Thing> things = new ArrayList<Thing>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Thing thing = new Thing();
                thing.setThingId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_THING_ID)));
                thing.setThingName(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_THING_NAME)));
                thing.setThingUse(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_THING_USE)));
                things.add(thing);
            }
        }
        return things;
    }

    private List<Note> noteCursorToList(Cursor cursor) {

        List<Note> notes = new ArrayList<Note>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Note note = new Note();
                note.setNoteId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_NOTE_ID)));
                note.setNotePerson(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_NOTE_PERSON)));
                note.setNoteInfo(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_NOTE_INFO)));
                notes.add(note);
            }
        }
        return notes;
    }

    private List<Note> personNoteCursorToList(Cursor cursor) {
        List<Note> notes = new ArrayList<Note>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Note note = new Note();
                note.setNoteId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_NOTE_ID)));
                note.setNotePerson(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_NOTE_PERSON)));
                note.setNoteInfo(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_NOTE_INFO)));
                notes.add(note);
            }
        }
        Log.i("dolphin", "after personNoteCursor");
        return notes;
    }

    private List<Event> eventCursorToList(Cursor cursor) {
        List<Event> events = new ArrayList<Event>();
        if (cursor.getCount() > 0) {
            Log.i("dolphin", "cursorToList synch item???=");
            while (cursor.moveToNext()) {
                Event event = new Event();
                event.setEventId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID)));
                event.setEventDate(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DATE)));
                event.setEventSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY)));
                event.setEventDetails(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS)));
                event.setEventWebId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_WEB_ID)));
                // dd possibly a problem since it web id was commented out
                events.add(event);
                Log.i("dolphin", "cursorToList synch item=" + event.getEventId());
                Log.i("dolphin", "cursorToList synch item=" + event.getEventSummary());
                Log.i("dolphin", "cursorToList synch item=" + event.getEventDetails());

            }
        }
        return events;
    }

    private List<Ignore> ignoreCursorToList(Cursor cursor) {
        List<Ignore> ignores = new ArrayList<Ignore>();
        if (cursor.getCount() > 0) {
            Log.i("dolphin", "cursorToList synch item???=");
            while (cursor.moveToNext()) {
                Ignore ignore = new Ignore();
                ignore.setWord(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_WORD)));
                ignores.add(ignore);
            }
        }
        return ignores;
    }


    private List<Event> eventAllCursorToList(Cursor cursor) {
        List<Event> events = new ArrayList<Event>();
        if (cursor.getCount() > 0) {
            Log.i("dolphin", "cursorToList synch item???=");
            while (cursor.moveToNext()) {
                Event event = new Event();
                event.setEventId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_ID)));
                event.setEventDate(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DATE)));
                event.setEventSummary(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY)));
                event.setEventDetails(cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_DETAILS)));
//                event.setEventWebId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_WEB_ID)));
                events.add(event);
                Log.i("dolphin", "cursorToList synch item??????=");

            }
        }
        return events;
    }

    public boolean add(SynchItem synchItem) {
        Log.i("dolphino", "top of addProduct= ");
        open();
        ContentValues values = new ContentValues();
//            values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID, synchItem.getSynchId());
        open();
        synchItem = create(synchItem);
        String thisItem = synchItem.getSynchAnalysis();
        Log.i("dolphino", "top of addProduct inside synchItem " + thisItem);
//        long result = database.insert(SynchronicityDBOpenHelper.TABLE_PRODUCTS, null, values);
//        close();

        return (synchItem.getSynchId() != -1);
    }

    public long add(Event event) {
        Log.i("dolphino", "top of addProduct add(Event)= ");
        open();
        ContentValues values = new ContentValues();
        values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_ID, event.getEventId());
        open();
        event = create(event);
        long thisItem = event.getEventId();
        Log.i("dolphino", "inside add(Event) " + thisItem);
        return (event.getEventId());
    }

    public long add(Verb verb) {
        Log.i("dolphino", "top of addProduct nameverb= ");

        verb.setVerbName(verb.getVerbName().replaceAll("'", "\''"));

        open();
        ContentValues values = new ContentValues();
        values.put(SynchronicityDBOpenHelper.COLUMN_EVENT_ID, verb.getVerbId());
        open();
        verb = create(verb);
        long thisItem = verb.getVerbId();
        Log.i("dolphino", "top of addProduct id= " + thisItem);
        return (verb.getVerbId());
    }

    public String add(Ignore ignore) {
        Log.i("dolphino", "top of add Ignore ");
        open();
        ContentValues values = new ContentValues();
        values.put(SynchronicityDBOpenHelper.COLUMN_WORD, ignore.getWord());
        open();
        ignore = create(ignore);
        String thisItem = ignore.getWord();
        close();
        open();
        Log.i("dolphino", "top of addProduct id= " + thisItem);
        return (ignore.getWord());
    }

    public void populateSample() {
        SynchItem synchItem = new SynchItem();
        synchItem.setSynchUser("2016-01-01");
        synchItem.setSynchSummary("Carl Jung's Example - the golden scarab");
        synchItem.setSynchAnalysis("The example concerns a young woman who, in spite of efforts made on both sides, proved to be psychologically inaccessible. The difficulty lay in the fact that she always knew better about everything. Her excellent education had provided her with a weapon ideally suited to this purpose. It took an unlikely coincidence to break through to her.");
        synchItem = create(synchItem);

        Event event = new Event();
        event.setEventDate("1944-03-10");
        event.setEventSummary("The Dream");
        event.setEventDetails("Carl Jung was sitting opposite the woman with his back to the window. He listened to her flow of rhetoric. She had an impressive dream the night before in which someone had given her a gold scarab - a costly piece of jewelry");
        event = create(event);
        SynchItemEvent synchItemEvent = new SynchItemEvent();
        synchItemEvent.setSeSynchId(synchItem.getSynchId());
        synchItemEvent.setSeEventId(event.getEventId());
        synchItemEvent = create(synchItemEvent);

        event.setEventDate("1944-03-11");
        event.setEventSummary("The Gold Scarab");
        event.setEventDetails("While telling about the dream, Carl heard something behind him, gently tapping on the window. He turned around and saw that it was a fairly large insect that was knocking on the window pane from outside in an abvious effort to get into the dark room. This seemed strange. He opened the window and cauught the insect in the air. It was a scarabeid beetle whose colour most nearly resembles that of a gold scarab.");
        create(event);

        synchItemEvent.setSeSynchId(synchItem.getSynchId());
        synchItemEvent.setSeEventId(event.getEventId());
        synchItemEvent = create(synchItemEvent);

        event.setEventDate("1944-03-12");
        event.setEventSummary("Spell broken");
        event.setEventDetails("Carl handed the beetle he caught to his patient with the words: 'Here is your scarab'. The experience punctured the desired old in her rationalism and broke the ice of her intellectual resistance. The treatment continued with satisfactory resutlts.");
        create(event);

        synchItemEvent.setSeSynchId(synchItem.getSynchId());
        synchItemEvent.setSeEventId(event.getEventId());
        synchItemEvent = create(synchItemEvent);
    }

    public void populateVerbs() {
        Verb verb = new Verb();
        verb.setVerbName("Borrow");
        verb.setVerbAppliesTo("");
        create(verb);
        verb.setVerbName("Return");
        verb.setVerbAppliesTo("Borrow");
        create(verb);
        verb.setVerbName("Lend");
        verb.setVerbAppliesTo("");
        create(verb);
        verb.setVerbName("Receive");
        verb.setVerbAppliesTo("Lend");
        create(verb);
        verb.setVerbName("Sell");
        verb.setVerbAppliesTo("");
        create(verb);
        verb.setVerbName("Buy");
        verb.setVerbAppliesTo("");
        create(verb);
    }

    public void populateIgnores() {
        Ignore ignore = new Ignore();
        ignore.setWord("I");
        create(ignore);
        ignore.setWord("The");
        create(ignore);
        ignore.setWord("and");
        create(ignore);
        ignore.setWord("the");
        create(ignore);
        ignore.setWord("looking");
        create(ignore);
        ignore.setWord("at");
        create(ignore);
        ignore.setWord("is");
        create(ignore);
        ignore.setWord("In");
        create(ignore);
        ignore.setWord("when");
        create(ignore);
        ignore.setWord("very");
        create(ignore);
        ignore.setWord("get");
        create(ignore);
        ignore.setWord("got");
        create(ignore);
        ignore.setWord("how");
        create(ignore);
        ignore.setWord("that");
        create(ignore);
        ignore.setWord("a");
        create(ignore);
        ignore.setWord("some");
        create(ignore);
        ignore.setWord("to");
        create(ignore);
        ignore.setWord("it");
        create(ignore);
        ignore.setWord("in");
        create(ignore);
        ignore.setWord("same");
        create(ignore);
        ignore.setWord("was");
        create(ignore);
        ignore.setWord("about");
        create(ignore);
        ignore.setWord("took");
        create(ignore);
        ignore.setWord("said");
        create(ignore);
        ignore.setWord("his");
        create(ignore);
        ignore.setWord("her");
        create(ignore);
    }

    public List<Event> repairAllUnlinkedEvents() {
//            TODO-sql to show unlinked events

        Log.i("dolphin", "Dolphin filtered ");

        String sqlCmd = "select eventId, eventDate, eventSummary, synchItemEvents.seSynchId, synchItems.synchSummary " +
                " from synchItemEvents " +
                "left join Events ON eventId  =  seEventId " +
                "left join synchItems ON synchId = seSynchId;";


        Log.i("dolphinu", "sqlCmd=" + sqlCmd);

        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "Dolphin " + cursor.getCount() + " rows");

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                Log.i("dolphing", "event=  " + cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_ID)) +
                        cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID)) +
                        "  event = " +
                        cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_SUMMARY)) +
                        "  synchSummary= " +
                        cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY)));
            }
        }

        List<Event> events = eventCursorToList(cursor);
        return events;
    }

    public void updateFromWeb(SynchItem synchItem) {
        Log.i("dolphino", "top of import sink updatefromweb synchItem ");
        open();
        ContentValues values = new ContentValues();
        //values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID, synchItem.getSynchId());
        if (findFromWeb(synchItem)) {
            Log.i("dolphin","updating sychitem:"+synchItem.getSynchId()+"  "+synchItem.getSynchWebId()+synchItem.getSynchSummary()+"  "+synchItem.getSynchAnalysis());
            updateFromDownload(synchItem);
        } else {
            Log.i("dolphin","creating sychitem:"+synchItem.getSynchId()+"  "+synchItem.getSynchWebId()+synchItem.getSynchSummary());
            synchItem = create(synchItem);
        }
    }

    public boolean findFromWeb(SynchItem synchItem) {
        String sqlCmd;

        if (synchItem.getSynchId() < 1) {
            sqlCmd = "SELECT synchId, synchDate, synchSummary, synchAnalysis from " +
                    SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS +
                    " WHERE " + SynchronicityDBOpenHelper.COLUMN_SYNCH_WEB_ID + " = " + synchItem.getSynchWebId() + ";";
        } else {
            sqlCmd = "SELECT synchId, synchDate, synchSummary, synchAnalysis from " +
                    SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS +
                    " WHERE " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ID + " = " + synchItem.getSynchId() + ";";
        }

//                " WHERE " + SynchronicityDBOpenHelper.COLUMN_SYNCH_DATE + " = '" + synchItem.getSynchDate()  +
//                "' AND " + SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY + " = '" + synchItem.getSynchSummary() +
//                "' AND " + SynchronicityDBOpenHelper.COLUMN_SYNCH_ANALYSIS + " = '" + synchItem.getSynchAnalysis() +
//                "';";
        Log.i("dolphin", "find 6 sql=" + sqlCmd);
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "find afterr sql");
         if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void updateFromWeb(SynchItemEvent synchItemEvent) {
        Log.i("dolphino", "top of import sinkeve synchItemE>vent ");
        open();

        SynchItemEvent se = getKeysSEFromWeb(synchItemEvent);
        // null means that web id(s) are not found
        if (se != null) {
           if (findSEForWeb(se)) {
                //update(se);
            } else {
               create(se);
           }
        }
    }

    public boolean findSEForWeb(SynchItemEvent se) {
        // if missing keys then see if web ids works else see if se ids work
        String sqlCmd;
        //Log.i("dolphin","sesynchid="+synchItemEvent.getSeSynchId()+" and seeventid="+synchItemEvent.getSeEventId());
        if (se.getSeSynchId() < 1 || se.getSeEventId() < 1) {
            Log.i("dolphin", "found less than 1");
            sqlCmd = "SELECT * FROM " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS +
                    " WHERE " + SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_WEB_ID + " = " + se.getSeWebSynchId() +
                    " AND " + SynchronicityDBOpenHelper.COLUMN_SE_EVENT_WEB_ID + " = " + se.getSeWebEventId() +
                    ";";
            Log.i("dolphin", "less than 1 sql="+sqlCmd);
        } else {
            Log.i("dolphin", "not found less than 1");
            sqlCmd = "SELECT * FROM " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEM_EVENTS +
                " WHERE " + SynchronicityDBOpenHelper.COLUMN_SE_SYNCH_ID + " = " + se.getSeSynchId() +
                " AND " + SynchronicityDBOpenHelper.COLUMN_SE_EVENT_ID + " = " + se.getSeEventId() +
                ";";
            Log.i("dolphin", "not less than 1 sql="+sqlCmd);
        }
        Log.i("dolphin", "find 1 sql=" + sqlCmd);
        Cursor cursor = database.rawQuery(sqlCmd, null);
        Log.i("dolphin", "after findSEForWeb se="+"cursor");
        if (cursor.getCount() > 0) {

            Log.i("dolphin", "TRUE");
            return true;
        } else {
            Log.i("dolphin", "FALSE");
            return false;
        }
    }

    public SynchItemEvent getKeysSEFromWeb(SynchItemEvent se) {
// see if web ids exist in db if so return synchId and eventID else null
//          Log.i("dolphin","se is "+se.getSeSynchId()+ " "+se.getSeEventId() + " " + se.getSeWebSynchId()+" "+se.getSeWebEventId());

        String sqlCmd = "SELECT * FROM " +
//                SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS;
                // dd nov 1916 - why was this commented out and wide open?
                SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS +
                " WHERE " + SynchronicityDBOpenHelper.COLUMN_SYNCH_WEB_ID + " = " + se.getSeWebSynchId();
        Log.i("dolphin", "top of get keys sql=" + sqlCmd);
        Cursor cursor = database.rawQuery(sqlCmd,null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                se.setSeSynchId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID)));
                Log.i("dolphin", "after setSe synchId="+cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_SUMMARY)));
                Log.i("dolphin", "after setSe synchId="+cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID))+"  "+cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_WEB_ID)));
            }
            // dd nov 2016 - added else
        } else {
            return null;
        }
        sqlCmd = "SELECT eventId FROM " +
                SynchronicityDBOpenHelper.TABLE_EVENTS +
                " WHERE " + SynchronicityDBOpenHelper.COLUMN_EVENT_WEB_ID  + " = " + se.getSeWebEventId() + ";";
        Log.i("dolphin", "find 2 sql=" + sqlCmd);
        cursor = database.rawQuery(sqlCmd, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                se.setSeEventId(cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_ID)));
                Log.i("dolphin","event cursor="+cursor.getLong(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_EVENT_ID)));
            }
            return se;
        } else {
            return null;
        }
    }

    public void updateFromWeb(Event event) {
        Log.i("dolphino", "top of import sink event ");
        open();
        ContentValues values = new ContentValues();
        values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID, event.getEventId());
        if (findEventForWeb(event)) {
            Log.i("dolphin"," updatebydownload from web"+event.getEventWebId());
            updateByDownload(event);
        } else {
            event = create(event);
            Log.i("dolphin"," create updatebydownload from web"+event.getEventWebId());
        }
    }

    public boolean findEventForWeb(Event event) {
        String sqlCmd;

        if (event.getEventId() < 1) {
            sqlCmd = "SELECT * FROM " +
                    SynchronicityDBOpenHelper.TABLE_EVENTS +
                    " WHERE " + SynchronicityDBOpenHelper.COLUMN_EVENT_WEB_ID + " = " + event.getEventWebId() + ";";
        } else {
            sqlCmd = "SELECT * FROM " +
                    SynchronicityDBOpenHelper.TABLE_EVENTS +
                    " WHERE " + SynchronicityDBOpenHelper.COLUMN_EVENT_ID + " = " + event.getEventId();
        }
        Log.i("dolphin", "find 3 sql=" + sqlCmd);
        Cursor cursor = database.rawQuery(sqlCmd, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void updateFromWeb(SynchDownload synchDownload) {
        Log.i("dolphino", "top of import sink synchDownload top ");
        open();
        ContentValues values = new ContentValues();
        //values.put(SynchronicityDBOpenHelper.COLUMN_SYNCH_ID, synchItem.getSynchId());
        if (synchDownload.getSeWebEventId() > 0) {
            SynchItemEvent synchItemEvent = new SynchItemEvent();
            Log.i("dolphino", "top of import sink item event= ");
            synchItemEvent.setSeWebSynchId(synchDownload.getSeWebSynchId());
            synchItemEvent.setSeWebEventId(synchDownload.getSeWebEventId());
            synchItemEvent.setSeSynchId(synchDownload.getSeSynchId());
            synchItemEvent.setSeEventId(synchDownload.getSeEventId());
            updateFromWeb(synchItemEvent);
        } else if (synchDownload.getEventWebId() > 0 ) {
            Event event = new Event();
            Log.i("dolphino", "top of import sink synchTypeevent ");
            event.setEventId(synchDownload.getEventId());
            event.setEventDate(synchDownload.getEventDate());
            event.setEventSummary(synchDownload.getEventSummary());
            event.setEventDetails(synchDownload.getEventDetails());
            event.setEventWebId(synchDownload.getEventWebId());
            Log.i("dolphin","top eventwebid=="+event.getEventWebId());
            updateFromWeb(event);
        } else {
            SynchItem synchItem = new SynchItem();
            Log.i("dolphinz", "top of import sink synchItem getsi=" + synchDownload.getSynchId());
            synchItem.setSynchId(synchDownload.getSynchId());
            synchItem.setSynchDate(synchDownload.getSynchDate());
            synchItem.setSynchSummary(synchDownload.getSynchSummary());
            synchItem.setSynchAnalysis(synchDownload.getSynchAnalysis());
            Log.i("dolphincg","download getsynchAnalysis="+synchItem.getSynchAnalysis());

            synchItem.setSynchUser(synchDownload.getSynchUser());
            Log.i("dolphinz","download getsynchwebid="+synchDownload.getSynchWebId());
            synchItem.setSynchWebId(synchDownload.getSynchWebId());
            updateFromWeb(synchItem);
        }
    }
    public void setToUser(String user) {

        //user = "dave";
        String sqlCmd = "update " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS + " Set " +
                SynchronicityDBOpenHelper.COLUMN_SYNCH_USER+ " = '" +
                user + "'";
        Log.i("dolphiny", "update user sql=" + sqlCmd);
        open();
        database.execSQL(sqlCmd);
    }
    public void updateUser() {

        String sqlCmd = "SELECT DISTINCT "+SynchronicityDBOpenHelper.COLUMN_SYNCH_USER +
                " FROM " + SynchronicityDBOpenHelper.TABLE_SYNCH_ITEMS;
        Log.i("dolphiny", "update user sql=" + sqlCmd);
        open();
        Cursor cursor = database.rawQuery(sqlCmd, null);
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            String user = cursor.getString(cursor.getColumnIndex(SynchronicityDBOpenHelper.COLUMN_SYNCH_USER));
            Log.i("dolphin","user is "+user);
            setToUser(user);
        }
    }
}