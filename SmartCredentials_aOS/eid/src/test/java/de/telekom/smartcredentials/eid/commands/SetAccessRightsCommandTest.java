package de.telekom.smartcredentials.eid.commands;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;

import de.telekom.smartcredentials.eid.messages.Chat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(PowerMockRunner.class)
public class SetAccessRightsCommandTest {
    @Test
    public void testSetAccessRightsCommand() {
        SetAccessRightsCommand cmd = new SetAccessRightsCommand();
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd), is("{\"canAllowed\":false,\"cmd\":\"SET_ACCESS_RIGHTS\"}"));

        // {"aux":{"validityDate":"2021-03-11"},
        // "chat":{
        //   "effective":["Address","DateOfBirth","DoctoralDegree","FamilyName","GivenNames","ValidUntil","DocumentType","CanAllowed","Pseudonym"],
        //   "optional":["CanAllowed"],
        //   "required":["Address","DateOfBirth","DoctoralDegree","FamilyName","GivenNames","ValidUntil","DocumentType","Pseudonym"]},
        //   "msg":"ACCESS_RIGHTS"}

        Chat chat = new Chat();
        chat.setRequired(Arrays.asList("Address","DateOfBirth","DoctoralDegree","FamilyName","GivenNames","ValidUntil","DocumentType","Pseudonym"));
        chat.setOptional(Arrays.asList("CanAllowed"));
        chat.setEffective(Arrays.asList("Address","DateOfBirth","DoctoralDegree","FamilyName","GivenNames","ValidUntil","DocumentType","CanAllowed","Pseudonym"));
        cmd = new SetAccessRightsCommand(chat, false);
        String expected = "{\"canAllowed\":false,\"chat\":{\"effective\":[\"Address\",\"DateOfBirth\",\"DoctoralDegree\",\"FamilyName\",\"GivenNames\",\"ValidUntil\",\"DocumentType\",\"CanAllowed\",\"Pseudonym\"],\"optional\":[\"CanAllowed\"],\"required\":[\"Address\",\"DateOfBirth\",\"DoctoralDegree\",\"FamilyName\",\"GivenNames\",\"ValidUntil\",\"DocumentType\",\"Pseudonym\"]},\"cmd\":\"SET_ACCESS_RIGHTS\"}";
        assertThat(gson.toJson(cmd), is(expected));
    }
}
