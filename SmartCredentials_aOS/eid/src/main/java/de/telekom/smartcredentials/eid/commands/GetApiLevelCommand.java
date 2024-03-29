/*
 * Copyright (c) 2020 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.eid.commands;

import de.telekom.smartcredentials.eid.commands.builder.CommandBuilder;
import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

/**
 * Created by Alex.Graur@endava.com at 4/8/2020
 */
@SuppressWarnings("unused")
public class GetApiLevelCommand extends SmartEidCommand {

    private GetApiLevelCommand() {
        super(EidCommandType.GET_API_LEVEL.getCommandType());
    }

    public static class Builder implements CommandBuilder<GetApiLevelCommand> {

        @Override
        public GetApiLevelCommand build() {
            return new GetApiLevelCommand();
        }
    }
}
