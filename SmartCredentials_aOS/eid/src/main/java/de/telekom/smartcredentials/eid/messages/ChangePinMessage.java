package de.telekom.smartcredentials.eid.messages;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePinMessage extends SmartEidMessage {

    @SerializedName("success")
    @Expose
    private boolean mSuccess;

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }

    @NonNull
    @Override
    public String toString() {
        return "ChangePinMessage{" +
                "mSuccess=" + mSuccess +
                '}';
    }
}
