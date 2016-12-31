package com.mind.oceanic.the.synchronicity2.importExport;

import android.util.Log;

import com.mind.oceanic.the.synchronicity2.model.SynchItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
;


/**
 * Created by dave on 4/6/16.
 */
public class ParseJSONSynchItem {
    public static List<SynchItem> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<SynchItem> synchList = new ArrayList<>();
            Log.i("dolphin","JSON parsing");
            for (int i = 0; i < ar.length(); i++) {
                Log.i("dolphin","JSON summary1");
                JSONObject obj = ar.getJSONObject(i);
                Log.i("dolphin","JSON summary2=");
                SynchItem synchItem = new SynchItem();
                Log.i("dolphin", "JSON summary3=");
                if (obj.getString("synch_id") == "null"){
                    Log.i("dolphin", "JSON synchId is null=");
//                            synchItem.setSynchId(-2);
                } else {
                    Log.i("dolphin", "JSON synchId is not null=");
                            synchItem.setSynchId(obj.getLong("synch_id"));
                }
                synchItem.setSynchDate(obj.getString("synch_date"));
                synchItem.setSynchSummary(obj.getString("synch_summary"));
                Log.i("dolphin", "JSON summary after load=" + synchItem.getSynchSummary());
                synchItem.setSynchAnalysis(obj.getString("synch_analysis"));
                synchItem.setSynchUser(obj.getString("synch_user"));
                Log.i("dolphin", "JSON summary=4");
                synchItem.setSynchWebId(obj.getLong("synch_web_id"));
                Log.i("dolphin", "JSON summary5=");
                synchList.add(synchItem);
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
