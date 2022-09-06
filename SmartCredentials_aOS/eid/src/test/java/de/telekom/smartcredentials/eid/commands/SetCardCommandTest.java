package de.telekom.smartcredentials.eid.commands;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import android.util.Log;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Locale;

import de.persosim.simulator.tlv.Asn1;
import de.persosim.simulator.tlv.Asn1DateWrapper;
import de.persosim.simulator.tlv.Asn1DocumentType;
import de.persosim.simulator.tlv.Asn1PrintableStringWrapper;
import de.persosim.simulator.tlv.Asn1Utf8StringWrapper;
import de.persosim.simulator.tlv.ConstructedTlvDataObject;
import de.persosim.simulator.tlv.PrimitiveTlvDataObject;
import de.persosim.simulator.tlv.TlvConstants;
import de.persosim.simulator.tlv.TlvDataObject;
import de.persosim.simulator.tlv.TlvDataObjectFactory;
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
    public void testSetCardCommandFirstnames() {
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setFirstNames("ERIKA");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"0104\",\"shortFileId\":\"04\",\"content\":\"64070c054552494b41\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSetCardCommandFamilyNames() {
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setFamilyNames("MUSTERMANN");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"0105\",\"shortFileId\":\"05\",\"content\":\"650c0c0a4d55535445524d414e4e\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSetCardCommandBirthName() {
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setBirthName("GABLER");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"010d\",\"shortFileId\":\"0d\",\"content\":\"6d080c064741424c4552\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSetCardCommandDocumentType() {
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setDocumentType("ID");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"0101\",\"shortFileId\":\"01\",\"content\":\"610413024944\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSetCardCommandNomDePlume() {
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setNomDePlume("");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"0106\",\"shortFileId\":\"06\",\"content\":\"66020c00\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSetCardCommandAcademicTitle() {
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setAcademicTitle("");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"0107\",\"shortFileId\":\"07\",\"content\":\"67020c00\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSetCardCommandBirthDate() {
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setBirthDate("19640812");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"0108\",\"shortFileId\":\"08\",\"content\":\"680a12083139363430383132\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSetCardCommandDateOfExpiry() {
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setDateOfExpiry("20291031");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"0103\",\"shortFileId\":\"03\",\"content\":\"630a12083230323931303331\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSetCardCommandDateOfIssuance() {
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setDateOfIssuance("20191101");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"010F\",\"shortFileId\":\"0F\",\"content\":\"6f0a12083230313931313031\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSetCardCommandMunicipality() {
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setMunicipality("02760503150000");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"0112\",\"shortFileId\":\"12\",\"content\":\"7209040702760503150000\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
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
        {
            ConstructedTlvDataObject received = Asn1Utf8StringWrapper.getInstance().encode(new TlvTag((byte) 0x64), "ERIKA");
            ConstructedTlvDataObject expected = new ConstructedTlvDataObject(HexString.toByteArray("64070C054552494B41"));

            assertThat(expected.toByteArray(), is(received.toByteArray()));

            assertThat(HexString.encode(received.toByteArray()), is("64070C054552494B41"));
        }
        {
            ConstructedTlvDataObject received = Asn1Utf8StringWrapper.getInstance().encode(new TlvTag((byte) 0x66), "");
            ConstructedTlvDataObject expected = new ConstructedTlvDataObject(HexString.toByteArray("66020C00"));

            assertThat(expected.toByteArray(), is(received.toByteArray()));

            assertThat(HexString.encode(received.toByteArray()), is("66020C00"));
        }
    }

    /**
     * Positive test: test generation of wrapped ASN.1 data structure "UTF8String".
     */
    @Test
    public void testAsn1DateWrapperEncode() {
        ConstructedTlvDataObject received = Asn1DateWrapper.getInstance().encode(new TlvTag((byte) 0x68), "19640812");
        ConstructedTlvDataObject expected = new ConstructedTlvDataObject(HexString.toByteArray("680a12083139363430383132"));

        assertThat(expected.toByteArray(),is(received.toByteArray()));

        assertThat(HexString.encode(received.toByteArray()), is("680a12083139363430383132".toUpperCase(Locale.ROOT)));
    }

    @Test
    public void testOctetString() {
        byte[] decoded = HexString.toByteArray("02760503150000");
        // byte[] bytes = new byte[]{2, 118, 5, 3, 21, 0, 0};
        PrimitiveTlvDataObject primitiveTlvDataObject =
                new PrimitiveTlvDataObject(new TlvTag(Asn1.UNIVERSAL_OCTET_STRING), decoded);
        ConstructedTlvDataObject constructedTlvDataObject = new ConstructedTlvDataObject(new TlvTag((byte) 0x72));
        constructedTlvDataObject.addTlvDataObject(primitiveTlvDataObject);

        ConstructedTlvDataObject expected = new ConstructedTlvDataObject(HexString.toByteArray("7209040702760503150000"));

        assertThat(expected.toByteArray(),is(constructedTlvDataObject.toByteArray()));

        assertThat(HexString.encode(constructedTlvDataObject.toByteArray()), is("7209040702760503150000".toUpperCase(Locale.ROOT)));
    }

    @Test
    public void testGeneralPlace() {
        // PlaceOfResidence ::= [APPLICATION 17] CHOICE {
        //  residence GeneralPlace
        //  multResidence SET OF GeneralPlace
        //}

        // 71|2D|(
        // [30|2B|
        //      (
        //       [AA|12|([0C|10|484549444553545241E1BA9E45203137])]
        //       [AB|07|([0C|05|4BC3964C4E])]
        //       [AD|03|([13|01|44])]
        //       [AE|07|([13|05|3531313437])]
        //      )
        // ])
        SetCardCommand cmd = new SetCardCommand("reader name");
        cmd.setPlaceOfResidence(
                "HEIDESTRAẞE 17",
                "KÖLN",
                "51147",
                "D");
        Gson gson = new Gson();
        assertThat(gson.toJson(cmd),
                is("{\"simulator\":{\"files\":[{\"fileId\":\"0112\",\"shortFileId\":\"12\",\"content\":\"712d302baa120c10484549444553545241e1ba9e45203137ab070c054bc3964c4ead03130144ae0713053531313437\"}]},\"name\":\"reader name\",\"cmd\":\"SET_CARD\"}"));
    }

    @Test
    public void testSimulatorDefaults() {
        // DocumentType
        TlvDataObject tlvDataObject0101 = TlvDataObjectFactory.createTLVDataObject("610413024944");
        assertThat(tlvDataObject0101.getTlvTag(), is(new TlvTag((byte) 0x61)));

        ConstructedTlvDataObject asn1DocumentType = Asn1DocumentType.getInstance().encode(new TlvTag((byte) 0x61), "ID");
        assertThat(asn1DocumentType.toByteArray(), is(HexString.toByteArray("610413024944")));

        TlvDataObject tlvDataObject0102 = TlvDataObjectFactory.createTLVDataObject("6203130144");

/*
        SetCardCommand.SimulatorFile[] simulatorFiles = new SetCardCommand.SimulatorFile[]{
                new SetCardCommand.SimulatorFile("2f00", "1e", "61324f0fe828bd080fa000000167455349474e500f434941207a752044462e655369676e5100730c4f0aa000000167455349474e61094f07a0000002471001610b4f09e80704007f00070302610c4f0aa000000167455349474e"),
                new SetCardCommand.SimulatorFile("011c", "1c", "3181c13012060a04007f0007020204020202010202010d300d060804007f00070202020201023012060a04007f00070202030202020102020129301c060904007f000702020302300c060704007f0007010202010d020129303e060804007f000702020831323012060a04007f0007020203020202010202012d301c060904007f000702020302300c060704007f0007010202010d02012d302a060804007f0007020206161e687474703a2f2f6273692e62756e642e64652f6369662f6e70612e786d6c"),
                new SetCardCommand.SimulatorFile("011d", "1d", "308209f606092a864886f70d010702a08209e7308209e3020103310f300d06096086480165030402030500308203e0060804007f0007030201a08203d2048203ce318203ca3012060a04007f0007020204020202010202010d300d060804007f00070202020201023017060a04007f0007020205020330090201010201010101003021060904007f000702020502301406072a8648ce3d020106092b24030302080101073017060a04007f0007020205020330090201010201020101ff3012060a04007f00070202030202020102020129301c060904007f000702020302300c060704007f0007010202010d0201293062060904007f0007020201023052300c060704007f0007010202010d0342000419d4b7447788b0e1993db35500999627e739a4e5e35f02d8fb07d6122e76567f17758d7a3aa6943ef23e5e2909b3e8b31bfaa4544c2cbf1fb487f31ff239c8f80201293081a3060804007f00070202083181963012060a04007f0007020203020202010202012d301c060904007f000702020302300c060704007f0007010202010d02012d3062060904007f0007020201023052300c060704007f0007010202010d034200041ac6cae884a6c2b8461404150f54cd1150b21e862a4e5f21ce34290c741104bd1bf31ed91e085d7c630e8b4d10a8ae22bbb2898b44b52ea0f4cdadcf57cfba2502012d302a060804007f0007020206161e687474703a2f2f6273692e62756e642e64652f6369662f6e70612e786d6c308201e6060804007f0007020207308201d8300b0609608648016503040204308201c73021020101041c2ff0247f59dd3c646e314f03abb33ee91a586577ebdf48d3864ec34d3021020102041c37823963b71af0bf5698d1fdc30da2b7f9ece57cfa4959bee9d6d9943021020103041ce8b2a171dc1290a765f124aafe33061c08c918a1069dff5caf4c62b53021020104041cad81d20dbd4f5687fdb05e5037ec267609fde28c6036fdbdf2c8b4333021020105041ca90f28eb7a0fa0de83abf3293d14e0838b9c85fc7277cbb97737a32b3021020106041c712b8550e49a13c64dced4457e9a0f5a85dc26cd6a321596723005d63021020107041c42a8fa36b60887ed022cd3b6ecc255220fbe8cb3f607e416601fcaa63021020108041c6446e0a909967462b5c1117634f8a1b557ef74be3f606c1e94efae433021020109041c635d1017f4abc656b9fdddd7e0fbb1e992b7686e89485e6ab51b638b302102010d041c04db93544a64bc1245b10aab266386f08f8e89f72e1db178c172624d3021020111041caadee20557d41ab9969e962282caf25904475148d329d2f6b2f43e343021020112041c57ce396ca707b96fa37c580f693230e4d4aebb97293f0909489d95cb302102010a041c1880a259cdb497c15a7fdd1c9ac9490d7dc0d18743378603d43d1d4fa082049f3082049b308203fea003020102020204d5300a06082a8648ce3d0403043046310b3009060355040613024445310d300b060355040a0c0462756e64310c300a060355040b0c03627369311a301806035504030c115445535420637363612d6765726d616e79301e170d3230303132313036333630345a170d3330303832313233353935395a305c310b3009060355040613024445310c300a060355040a0c03425349310d300b06035504051304303039393130302e06035504030c275445535420446f63756d656e74205369676e6572204964656e7469747920446f63756d656e7473308201b53082014d06072a8648ce3d020130820140020101303c06072a8648ce3d01010231008cb91e82a3386d280f5d6f7e50e641df152f7109ed5456b412b1da197fb71123acd3a729901d1a71874700133107ec53306404307bc382c63d8c150c3c72080ace05afa0c2bea28e4fb22787139165efba91f90f8aa5814a503ad4eb04a8c7dd22ce2826043004a8c7dd22ce28268b39b55416f0447c2fb77de107dcd2a62e880ea53eeb62d57cb4390295dbc9943ab78696fa504c110461041d1c64f068cf45ffa2a63a81b7c13f6b8847a3e77ef14fe3db7fcafe0cbd10e8e826e03436d646aaef87b2e247d4af1e8abe1d7520f9c2a45cb1eb8e95cfd55262b70b29feec5864e19c054ff99129280e4646217791811142820341263c53150231008cb91e82a3386d280f5d6f7e50e641df152f7109ed5456b31f166e6cac0425a7cf3ab6af6b7fc3103b883202e90465650201010362000401b434b9555974f51934687c520dae338032f5046999e1595d85b89a4cbdb90888b8dcab2d6588cf73e8e43db78ab40a0fdb710d971f1c0205b9243e1f769a9e0681c01d1b298c4d7de7f3f7e6ce9f16657907b79328bec8166f5fc035e26ee3a382016630820162301f0603551d23041830168014539db1872aac9193d76392ee80d9e5996cf99b3b301d0603551d0e0416041472571e58fc52ead9641412875c615e8090508cfa300e0603551d0f0101ff040403020780302b0603551d1004243022800f32303230303132313036333630345a810f32303230303832313233353935395a30160603551d20040f300d300b060904007f00070301010130260603551d11041f301d820b6273692e62756e642e6465a40e300c310a300806035504070c014430510603551d12044a30488118637363612d6765726d616e79406273692e62756e642e6465861c68747470733a2f2f7777772e6273692e62756e642e64652f63736361a40e300c310a300806035504070c01443019060767810801010602040e300c02010031071301411302494430350603551d1f042e302c302aa028a0268624687474703a2f2f7777772e6273692e62756e642e64652f746573745f637363615f63726c300a06082a8648ce3d04030403818a00308186024100a348c5e7948535c9ecb5043d62fa1f56f16886af76c434c870d988d345175fd51e60a89c0e9d06a94d35078853397d7c8403e32053df6bdfc16cc1b3a5e7d1cb0241008506dc6aca4f202b4bdf7957263010886d38d4991d101374f6a7b8f4bc1ce51cb278e9f8851951f6af0aba7d4773f42762fd8f840a01f2d526cc80682dca08103182014430820140020101304c3046310b3009060355040613024445310d300b060355040a0c0462756e64310c300a060355040b0c03627369311a301806035504030c115445535420637363612d6765726d616e79020204d5300d06096086480165030402030500a06a301706092a864886f70d010903310a060804007f0007030201304f06092a864886f70d0109043142044066927654d73a84cccd931e2c44a9b34ef3b848ee85b7f4a92699ea7bf5262fe73b101f31f580180c96ea642569e5e6db8469a4c7e4cb47dfe9c5d95b0939125e300a06082a8648ce3d040304046630640230582364c74d9c694d3c8f99acbf82a7a847141248b015aed8bee3c395e82788426f032978d196303a6b81d9fa8b8dbc8e02305bf169de97b344a4b03e862c48a76226f044c6da1ea78e380c2c6479b79526415735345764d7b6e738ee83931aabe840"),
                new SetCardCommand.SimulatorFile("0101", "01", "610413024944"),
                new SetCardCommand.SimulatorFile("0102", "02", "6203130144"),
                new SetCardCommand.SimulatorFile("0103", "03", "630a12083230323931303331"),
                new SetCardCommand.SimulatorFile("0104", "04", "64070c054552494b41"),
                new SetCardCommand.SimulatorFile("0105", "05", "650c0c0a4d55535445524d414e4e"),
                new SetCardCommand.SimulatorFile("0106", "06", "66020c00"),
                new SetCardCommand.SimulatorFile("0107", "07", "67020c00"),
                new SetCardCommand.SimulatorFile("0108", "08", "680a12083139363430383132"),
                new SetCardCommand.SimulatorFile("0109", "09", "690aa1080c064245524c494e"),
                new SetCardCommand.SimulatorFile("010a", "0a", "6a03130144"),
                new SetCardCommand.SimulatorFile("010b", "0b", "6b03130146"),
                new SetCardCommand.SimulatorFile("010c", "0c", "6c30312e302c06072a8648ce3d0101022100a9fb57dba1eea9bc3e660a909d838d726e3bf623d52620282013481d1f6e5377"),
                new SetCardCommand.SimulatorFile("010d", "0d", "6d080c064741424c4552"),
                new SetCardCommand.SimulatorFile("010f", "0f", "6f0a12083230313931313031"),
                new SetCardCommand.SimulatorFile("0111", "11", "712d302baa120c10484549444553545241e1ba9e45203137ab070c054bc3964c4ead03130144ae0713053531313437"),
                new SetCardCommand.SimulatorFile("0112", "12", "7209040702760503150000"),
                new SetCardCommand.SimulatorFile("0113", "13", "7316a1140c125245534944454e4345205045524d49542031"),
                new SetCardCommand.SimulatorFile("0114", "14", "7416a1140c125245534944454e4345205045524d49542032"),
                new SetCardCommand.SimulatorFile("0115", "15", "7515131374656c3a2b34392d3033302d31323334353637"),
                new SetCardCommand.SimulatorFile("0116", "16", "761516136572696b61406d75737465726d616e6e2e6465")
        };
        for (SetCardCommand.SimulatorFile simulatorFile : simulatorFiles) {
            String hex = simulatorFile.getContent();
            TlvDataObject tlvDataObject = TlvDataObjectFactory.createTLVDataObject(hex);
            assertThat("", is(tlvDataObject.toString()));
        }
*/
    }
}
