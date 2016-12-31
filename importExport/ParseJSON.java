package com.mind.oceanic.the.synchronicity2.importExport;

import android.util.Log;

import com.mind.oceanic.the.synchronicity2.model.SynchDownload;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

;


/**
 * Created by dave on 4/6/16.
 */
public class ParseJSON {
    public static List<SynchDownload> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            Log.i("dolphin","JSON parsing");
            List<SynchDownload> synchList = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {
                Log.i("dolphin","JSON summary1");
                JSONObject obj = ar.getJSONObject(i);
                Log.i("dolphin", "JSON summary2=");
                SynchDownload synchDownload = new SynchDownload();
                Log.i("dolphin", "JSON summary3=");
                if (obj.getString("synch_id").equals("null")) {
                    Log.i("dolphin", "JSON synchId is null=");
                           synchDownload.setSynchId(0);
                } else {
                            synchDownload.setSynchId(obj.getLong("synch_id"));
                    Log.i("dolphin", "JSON synchId is not null="+synchDownload.getSynchId());
                }
                synchDownload.setSynchDate(obj.getString("synch_date"));
                synchDownload.setSynchSummary(obj.getString("synch_summary"));
                Log.i("dolphin", "JSON summary after load=" + synchDownload.getSynchSummary());
                synchDownload.setSynchAnalysis(obj.getString("synch_analysis"));
                synchDownload.setSynchUser(obj.getString("synch_user"));
                Log.i("dolphin", "JSON summary=4");
                synchDownload.setSynchWebId(obj.getLong("synch_web_id"));
                Log.i("dolphin", "JSON summary=5");
                if (obj.getString("event_id") == "null") {
                    Log.i("dolphin", "JSON summary=5a");
                    Log.i("dolphin", "JSON summary=5A as="+synchDownload.getEventId()+"&&"+synchDownload.getSynchSummary()+"&&"+synchDownload.getEventSummary()+"%%"+synchDownload.getSynchId());
                    synchDownload.setEventId(0);
                } else {
                    synchDownload.setEventId(obj.getLong("event_id"));
                    Log.i("dolphin", "JSON summary=5b as="+synchDownload.getEventId()+"&&"+synchDownload.getSynchSummary()+"&&"+synchDownload.getEventSummary()+"%%"+synchDownload.getSynchId());
                }
                Log.i("dolphin", "JSON summary=6");
                synchDownload.setEventDate(obj.getString("event_date"));
                Log.i("dolphin", "JSON summary=7");
                synchDownload.setEventSummary(obj.getString("event_summary"));
                Log.i("dolphin", "JSON summary=8");
                synchDownload.setEventDetails(obj.getString("event_details"));
                Log.i("dolphin", "JSON summary=9");
                synchDownload.setEventWebId(obj.getLong("event_web_id"));
                Log.i("dolphin", "JSON summary=10   "+synchDownload.getEventWebId());
                if (obj.getString("se_synch_id") != "null") {
                    synchDownload.setSeSynchId(obj.getLong("se_synch_id"));
                }
                Log.i("dolphin", "JSON summary=11");
                if (obj.getString("se_event_id") != "null") {
                    synchDownload.setSeEventId(obj.getLong("se_event_id"));
                }
                Log.i("dolphin", "JSON summary=12");
                synchDownload.setSeWebSynchId(obj.getLong("se_web_synch_id"));
                Log.i("dolphin", "JSON summary=13");
                synchDownload.setSeWebEventId(obj.getLong("se_web_event_id"));
                Log.i("dolphin", "JSON summary14=");
                synchList.add(synchDownload);
                Log.i("dolphin","added synchList");
            }
            Log.i("dolphin","out of JSON loop="+synchList.size());
            return synchList;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("dolphin","JSON exception");
            return null;
        }
    }
}
