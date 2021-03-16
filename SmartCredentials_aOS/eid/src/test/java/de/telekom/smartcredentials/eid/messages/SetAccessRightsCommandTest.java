/*
 * Copyright (c) 2021 Telekom Deutschland AG
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

package de.telekom.smartcredentials.eid.messages;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collections;

import de.telekom.smartcredentials.eid.commands.SetAccessRightsCommand;
import de.telekom.smartcredentials.eid.messages.AccessRightsMessage;
import de.telekom.smartcredentials.eid.messages.Chat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(PowerMockRunner.class)
public class SetAccessRightsCommandTest {
    @Test
    public void testSetAccessRightsCommand() {
        SetAccessRightsCommand cmd = new SetAccessRightsCommand();
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd), is("{\"chat\":[],\"cmd\":\"SET_ACCESS_RIGHTS\"}"));
        Chat chat = new Chat();
        chat.setRequired(Arrays.asList("Address", "DateOfBirth", "DoctoralDegree", "FamilyName", "GivenNames", "ValidUntil", "DocumentType", "Pseudonym"));
        chat.setOptional(Collections.singletonList("CanAllowed"));
        chat.setEffective(Arrays.asList("Address", "DateOfBirth", "DoctoralDegree", "FamilyName", "GivenNames", "ValidUntil", "DocumentType", "CanAllowed", "Pseudonym"));
        AccessRightsMessage message = new AccessRightsMessage(chat, false);
        String expected = "{\"chat\":{\"effective\":[\"Address\",\"DateOfBirth\",\"DoctoralDegree\",\"FamilyName\",\"GivenNames\",\"ValidUntil\",\"DocumentType\",\"CanAllowed\",\"Pseudonym\"],\"optional\":[\"CanAllowed\"],\"required\":[\"Address\",\"DateOfBirth\",\"DoctoralDegree\",\"FamilyName\",\"GivenNames\",\"ValidUntil\",\"DocumentType\",\"Pseudonym\"]},\"canAllowed\":false}";
        assertThat(gson.toJson(message), is(expected));
    }
}
