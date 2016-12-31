package com.mind.oceanic.the.synchronicity2.importExport;

import android.util.Log;

import com.mind.oceanic.the.synchronicity2.model.SynchItemEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 4/6/16.
 */
public class ParseJSONSynchItemEvent {
    public static List<SynchItemEvent> parseFeed(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<SynchItemEvent> synchItemEventList = new ArrayList<>();
            Log.i("dolphin","after resut");
            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                SynchItemEvent synchItem = new SynchItemEvent();
                synchItem.setSeSynchId(obj.getInt("se_synch_id"));
                synchItem.setSeEventId(obj.getInt("se_event_id"));
                synchItem.setSeWebSynchId(obj.getLong("se_web_synch_id"));
                synchItem.setSeWebEventId(obj.getLong("se_web_event_id"));

                synchItemEventList.add(synchItem);

            }
            return synchItemEventList;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("dolphin","JSON exception");
            return null;
        }
    }
}
