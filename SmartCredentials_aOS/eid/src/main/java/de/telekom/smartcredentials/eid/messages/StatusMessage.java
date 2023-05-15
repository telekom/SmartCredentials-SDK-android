package de.telekom.smartcredentials.eid.messages;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusMessage extends SmartEidMessage {

    @SerializedName("workflow")
    @Expose
    private String mWorkflow;
    @SerializedName("progress")
    @Expose
    private int mProgress;
    @SerializedName("state")
    @Expose
    private String mState;

    public String getWorkflow() {
        return mWorkflow;
    }

    public int getProgress() {
        return mProgress;
    }

    public String getState() {
        return mState;
    }

    public void setWorkflow(String workflow) {
        this.mWorkflow = workflow;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }

    public void setState(String state) {
        this.mState = state;
    }

    @NonNull
    @Override
    public String toString() {
        return "StatusMessage{" +
                "mWorkflow='" + mWorkflow + '\'' +
                ", mProgress='" + mProgress + '\'' +
                ", mState='" + mState + '\'' +
                '}';
    }
}
