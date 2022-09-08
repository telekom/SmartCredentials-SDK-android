package de.telekom.smartcredentials.eid.commands;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.eid.commands.builder.CommandBuilder;
import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

@SuppressWarnings("unused")
public class SetNewPinCommand extends SmartEidCommand {

    @SerializedName("value")
    @Expose
    private String mValue;

    public SetNewPinCommand(Builder builder) {
        super(EidCommandType.SET_NEW_PIN.getCommandType());
        mValue = builder.mValue;
    }

    public String getValue() {
        return mValue;
    }

    @NonNull
    @Override
    public String toString() {
        return "SetNewPinCommand{" +
                "mValue='" + mValue + '\'' +
                '}';
    }

    public static class Builder implements CommandBuilder<SetNewPinCommand> {

        private final String mValue;

        public Builder(String value) {
            mValue = value;
        }

        @Override
        public SetNewPinCommand build() {
            return new SetNewPinCommand(this);
        }
    }
}
