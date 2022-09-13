package de.persosim.simulator.tlv;

/**
 * This class provides support for encoding the wrapped ASN.1 data structure "ICAOString"
 */
public class Asn1IcaoStringWrapper extends Asn1ConstructedApplicationWrapper implements Asn1 {
	
	private static Asn1IcaoStringWrapper instance = null;
	
	private Asn1IcaoStringWrapper() {
		super(Asn1IcaoString.getInstance());
	}
	
	public static Asn1IcaoStringWrapper getInstance() {
		if(instance == null) {
			instance = new Asn1IcaoStringWrapper();
		}
		
		return instance;
	}
	
}
