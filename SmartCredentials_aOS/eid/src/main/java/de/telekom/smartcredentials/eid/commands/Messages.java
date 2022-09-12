package de.telekom.smartcredentials.eid.commands;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Messages {

    @SerializedName("sessionStarted")
    @Expose
    private String mSessionStarted;
    @SerializedName("sessionFailed")
    @Expose
    private String mSessionFailed;
    @SerializedName("sessionSucceeded")
    @Expose
    private String mSessionSucceeded;
    @SerializedName("sessionInProgress")
    @Expose
    private String mSessionInProgress;

    public Messages(String sessionStarted, String sessionFailed, String sessionSucceeded, String sessionInProgress) {
        this.mSessionStarted = sessionStarted;
        this.mSessionFailed = sessionFailed;
        this.mSessionSucceeded = sessionSucceeded;
        this.mSessionInProgress = sessionInProgress;
    }

    public String getSessionStarted() {
        return mSessionStarted;
    }

    public String getSessionFailed() {
        return mSessionFailed;
    }

    public String getSessionSucceeded() {
        return mSessionSucceeded;
    }

    public String getSessionInProgress() {
        return mSessionInProgress;
    }

    @NonNull
    @Override
    public String toString() {
        return "Messages{" +
                "mSessionStarted='" + mSessionStarted + '\'' +
                ", mSessionFailed='" + mSessionFailed + '\'' +
                ", mSessionSucceeded='" + mSessionSucceeded + '\'' +
                ", mSessionInProgress='" + mSessionInProgress + '\'' +
                '}';
    }
}
