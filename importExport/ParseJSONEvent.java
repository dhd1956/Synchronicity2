package com.mind.oceanic.the.synchronicity2.importExport;

import android.util.Log;

import com.mind.oceanic.the.synchronicity2.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 4/6/16.
 */
public class ParseJSONEvent {
    public static List<Event> parseFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<Event> eventList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Event event = new Event();
                event.setEventId(obj.getInt("event_id"));
                event.setEventDate(obj.getString("event_date"));
                event.setEventSummary(obj.getString("event_summary"));
                event.setEventDetails(obj.getString("event_details"));
                event.setEventWebId(obj.getLong("event_web_id"));
                Log.i("dolphin","JSON summary="+event.getEventSummary());

                eventList.add(event);

            }
            return eventList;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("dolphin","JSON exception");
            return null;
        }
    }
}
