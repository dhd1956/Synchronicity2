package com.mind.oceanic.the.synchronicity2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dave on 2/4/16.
 */
public class SynchItemEvent implements Parcelable {
    private long seSynchId;
    private long seEventId;
    private long seWebSynchId;
    private long seWebEventId;

    public long getSeSynchId() {
        return seSynchId;
    }

    public void setSeSynchId(long seSynchId) {
        this.seSynchId = seSynchId;
    }

    public long getSeEventId() {
        return seEventId;
    }

    public void setSeEventId(long seEventId) {
        this.seEventId = seEventId;
    }

    public long getSeWebSynchId() {
        return seWebSynchId;
    }

    public void setSeWebSynchId(long seWebSynchId) {
        this.seWebSynchId = seWebSynchId;
    }

    public long getSeWebEventId() {
        return seWebEventId;
    }

    public void setSeWebEventId(long seWebEventId) {
        this.seWebEventId = seWebEventId;
    }
    @Override
    public String toString() {
        return "help";
    }

    public SynchItemEvent() {

    }

    public SynchItemEvent(Parcel in) {
        seSynchId = in.readLong();
        seEventId = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(seSynchId);
        dest.writeLong(seEventId);
    }

    public static final Parcelable.Creator<SynchItemEvent> CREATOR =
            new Parcelable.Creator<SynchItemEvent>() {

                @Override
                public SynchItemEvent createFromParcel(Parcel source) {
                    return new SynchItemEvent(source);
                }

                @Override
                public SynchItemEvent[] newArray(int size) {
                    return new SynchItemEvent[size];
                }

            };
}
