package de.telekom.smartcredentials.eid.commands;

import de.telekom.smartcredentials.eid.commands.builder.CommandBuilder;
import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

@SuppressWarnings("unused")
public class GetStatusCommand extends SmartEidCommand {

    private GetStatusCommand() {
        super(EidCommandType.GET_STATUS.getCommandType());
    }

    public static class Builder implements CommandBuilder<GetStatusCommand> {

        @Override
        public GetStatusCommand build() {
            return new GetStatusCommand();
        }
    }
}
