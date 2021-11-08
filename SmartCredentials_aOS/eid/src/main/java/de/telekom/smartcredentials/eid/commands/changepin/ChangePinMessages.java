package de.telekom.smartcredentials.eid.commands.changepin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePinMessages {

    @SerializedName("sessionStarted")
    @Expose
    private String sessionStarted;
    @SerializedName("sessionFailed")
    @Expose
    private String sessionFailed;
    @SerializedName("sessionSucceeded")
    @Expose
    private String sessionSucceeded;
    @SerializedName("sessionInProgress")
    @Expose
    private String sessionInProgress;

    public String getSessionStarted() {
        return sessionStarted;
    }

    public void setSessionStarted(String sessionStarted) {
        this.sessionStarted = sessionStarted;
    }

    public String getSessionFailed() {
        return sessionFailed;
    }

    public void setSessionFailed(String sessionFailed) {
        this.sessionFailed = sessionFailed;
    }

    public String getSessionSucceeded() {
        return sessionSucceeded;
    }

    public void setSessionSucceeded(String sessionSucceeded) {
        this.sessionSucceeded = sessionSucceeded;
    }

    public String getSessionInProgress() {
        return sessionInProgress;
    }

    public void setSessionInProgress(String sessionInProgress) {
        this.sessionInProgress = sessionInProgress;
    }

}