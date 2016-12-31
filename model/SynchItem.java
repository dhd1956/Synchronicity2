package com.mind.oceanic.the.synchronicity2.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by dave on 1/18/16.
 */
public class SynchItem implements  Parcelable {
    private long synchId;
    private String synchDate;
    private String synchSummary;
    private String synchAnalysis;
    private String synchUser;
    private long synchWebId;


    public long getSynchId() {
        return synchId;
    }

    public void setSynchId(long synchId) {
        this.synchId = synchId;
    }

    public String getSynchDate() {
        return synchDate;
    }

    public void setSynchDate(String synchDate) {
        this.synchDate = synchDate;
    }

    public String getSynchSummary() {
        return synchSummary;
    }

    public void setSynchSummary(String summary) {
        this.synchSummary = summary;
    }

    public String getSynchAnalysis() {
        return synchAnalysis;
    }

    public void setSynchAnalysis(String analysis) {
        Log.i("dolphin", "in setsynchanal");
        this.synchAnalysis = analysis;
    }

    public String getSynchUser() {
        return synchUser;
    }

    public void setSynchUser(String user) {
        this.synchUser = user;
    }

    public long getSynchWebId() {
        return synchWebId;
    }

    public void setSynchWebId(long synchWebId) {
        this.synchWebId = synchWebId;
    }


    public long getWebId() {
        return synchWebId;
    }

    public void setWebId(String user) {
        this.synchWebId = synchWebId;
    }

    @Override
    public String toString() {
        return synchSummary;
    }

    public SynchItem() {

    }

    public SynchItem(Parcel in) {
        synchId = in.readLong();
        synchDate = in.readString();
        synchSummary = in.readString();
        synchAnalysis = in.readString();
        synchUser = in.readString();
        synchWebId = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(synchId);
        dest.writeString(synchDate);
        dest.writeString(synchSummary);
        dest.writeString(synchAnalysis);
        dest.writeString(synchUser);
        dest.writeLong(synchWebId);
    }

    public static final Parcelable.Creator<SynchItem> CREATOR =
            new Parcelable.Creator<SynchItem>() {

                @Override
                public SynchItem createFromParcel(Parcel source) {
                    return new SynchItem(source);
                }

                @Override
                public SynchItem[] newArray(int size) {
                    return new SynchItem[size];
                }

            };
}
