package de.telekom.smartcredentials.eid.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.eid.messages.types.EidMessageType;

public class ChangePinMessage extends SmartEidMessage{

    @SerializedName("success")
    @Expose
    private boolean mState;

    public ChangePinMessage(boolean state) {
        super(EidMessageType.CHANGE_PIN.getMessageType());
        mState = state;
    }

    public boolean getState() {
        return mState;
    }

}
