package com.mind.oceanic.the.synchronicity2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dave on 1/18/16.
 */
public class SynchDownload implements  Parcelable {
    private long synchId;
    private String synchDate;
    private String synchSummary;
    private String synchAnalysis;
    private String synchUser;
    private long synchWebId;
    private long eventId;
    private String eventDate;
    private String eventSummary;
    private String eventDetails;
    private long eventWebId;
    private long seSynchId;
    private long seEventId;
    private long seWebSynchId;
    private long seWebEventId;

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

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventSummary() {
        return eventSummary;
    }

    public void setEventSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public long getEventWebId() {
        return eventWebId;
    }

    public void setEventWebId(long eventWebId) { this.eventWebId = eventWebId; }

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

    public SynchDownload() {

    }

    @Override
    public int describeContents() {
        return 0;
    }


    public SynchDownload(Parcel in) {
        synchId = in.readLong();
        synchDate = in.readString();
        synchSummary = in.readString();
        synchAnalysis = in.readString();
        synchUser = in.readString();
        synchWebId = in.readLong();
        eventId = in.readLong();
        eventDate = in.readString();
        eventSummary = in.readString();
        eventDetails = in.readString();
        eventWebId = in.readLong();
        seSynchId = in.readLong();
        seEventId = in.readLong();
        seWebSynchId = in.readLong();
        seWebEventId = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(synchId);
        dest.writeString(synchDate);
        dest.writeString(synchSummary);
        dest.writeString(synchAnalysis);
        dest.writeString(synchUser);
        dest.writeLong(synchWebId);
        dest.writeLong(eventId);
        dest.writeString(eventDate);
        dest.writeString(eventSummary);
        dest.writeString(eventDetails);
        dest.writeLong(eventWebId);
        dest.writeLong(seSynchId);
        dest.writeLong(seEventId);
        dest.writeLong(seWebSynchId);
        dest.writeLong(seWebEventId);
    }

    public static final Parcelable.Creator<SynchDownload> CREATOR =
            new Parcelable.Creator<SynchDownload>() {

                @Override
                public SynchDownload createFromParcel(Parcel source) {
                    return new SynchDownload(source);
                }

                @Override
                public SynchDownload[] newArray(int size) {
                    return new SynchDownload[size];
                }

            };
}
