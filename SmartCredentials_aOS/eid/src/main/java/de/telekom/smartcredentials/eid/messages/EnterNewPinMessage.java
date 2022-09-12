package de.telekom.smartcredentials.eid.messages;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnterNewPinMessage extends SmartEidMessage {

    @SerializedName("error")
    @Expose
    private String mError;
    @SerializedName("reader")
    @Expose
    private Reader mReader;

    public Reader getReader() {
        return mReader;
    }

    public void setReader(Reader reader) {
        this.mReader = reader;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        this.mError = error;
    }

    @NonNull
    @Override
    public String toString() {
        return "EnterNewPinMessage{" +
                "mError='" + mError + '\'' +
                ", mReader=" + mReader +
                '}';
    }
}