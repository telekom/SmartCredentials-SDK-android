package de.telekom.smartcredentials.eid.commands;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

@SuppressWarnings("unused")
public class SetNewPinCommand extends SmartEidCommand {
    @SerializedName("value")
    @Expose
    private String mValue;

    public SetNewPinCommand(String value) {
        super(EidCommandType.SET_NEW_PIN.getCommandType());
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
