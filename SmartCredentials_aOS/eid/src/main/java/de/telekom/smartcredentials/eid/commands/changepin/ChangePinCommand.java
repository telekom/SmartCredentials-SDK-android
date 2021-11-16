package de.telekom.smartcredentials.eid.commands.changepin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.eid.commands.SmartEidCommand;
import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

@SuppressWarnings("unused")
public class ChangePinCommand extends SmartEidCommand {
    @SerializedName("handleInterrupt")
    @Expose
    private boolean mHandleInterrupt;

    @SerializedName("messages")
    @Expose
    private ChangePinMessages mMessages;

    public ChangePinCommand(ChangePinMessages messages) {
        super(EidCommandType.RUN_CHANGE_PIN.getCommandType());
        mHandleInterrupt = true;
        mMessages = messages;
    }
}

