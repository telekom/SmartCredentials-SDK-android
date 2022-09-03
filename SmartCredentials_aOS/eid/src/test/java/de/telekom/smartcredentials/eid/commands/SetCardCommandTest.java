package de.telekom.smartcredentials.eid.commands;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import de.persosim.simulator.tlv.Asn1Utf8StringWrapper;
import de.persosim.simulator.tlv.ConstructedTlvDataObject;
import de.persosim.simulator.tlv.TlvTag;
import de.persosim.simulator.utils.HexString;

@RunWith(PowerMockRunner.class)
public class SetCardCommandTest {
    @Test
    public void testSetCardCommand() {
        SetCardCommand cmd = new SetCardCommand("reader name", new SetCardCommand.SimulatorFile[]{});
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd), is("{\"simulator\":{\"files\":[]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSetCardCommandWithFiles() {
        SetCardCommand.SimulatorFile[] files = new SetCardCommand.SimulatorFile[]{
                new SetCardCommand.SimulatorFile("0101", "01", "610413024944")
        };
        SetCardCommand cmd = new SetCardCommand("reader name", files);
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"0101\",\"shortFileId\":\"01\",\"content\":\"610413024944\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    /**
     * Positive test: test generation of wrapped ASN.1 data structure "UTF8String".
     */
    @Test
    public void testAsn1Utf8StringWrapperEncode() {
        ConstructedTlvDataObject received = Asn1Utf8StringWrapper.getInstance().encode(new TlvTag((byte) 0x64), "ERIKA");
        ConstructedTlvDataObject expected = new ConstructedTlvDataObject(HexString.toByteArray("64070C054552494B41"));

        assertThat(expected.toByteArray(),is(received.toByteArray()));
    }
}
