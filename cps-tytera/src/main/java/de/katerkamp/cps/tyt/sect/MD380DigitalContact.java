/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.tyt.sect;

import de.katerkamp.cps.ds.CallType;
import de.katerkamp.cps.sect.DigitalContact;
import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.BoundString;

/**
 * Digital Contact.
 *
 * ----
 * 00000000 F6 0D 28 C3 4F 00 5A 00 37 00 4F 00 4B 00 4D 00 ..(.O.Z.7.O.K.M.
 * 00000010 20 00 4B 00 6C 00 61 00 75 00 73 00 00 00 00 00 .K.l.a.u.s.....
 * 00000020 00 00 00 00 ....
 * ----
 *
 * ----
 * record DigitalContact [cont]
 *   offset = 0x61A5
 *   length = 36
 *   count = 1000
 *
 *   deletionMarker 8 bit, 0 value = 0xFF
 *   deletionMarker 8 bit, 8 value = 0xFF
 *   deletionMarker 8 bit, 16 value = 0xFF
 *   CallId key 24 bit, 0 binary #: (min=0, max=16776415 0xFFFCDF) CallIds 
 *     must be unique
 *   fixed 2 bit, 24 value=0b11
 *   CallReceiveTone 1 bit, 26 binary enum {0="off" 1="on"}
 *   CallType 2 bit, 30 binary enum {0b01="group" 0b10="private" 0b11="all"} #
 *   ALL=(CallType always 16776415)
 *   Name 256 bit, 32 unicode
 * end
 * ----
 *
 * MD380DigitalContact{callIdKey=2625014, callReceiveToneEnabled=false,
 * callType=3, name=OZ7OKM Klaus
 * 
 * @todo Check: In Tytera Windows GUI it says Private Call Type
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class MD380DigitalContact implements DigitalContact {

	@BoundNumber(size = "24")  // 0x280df6
	int callIdKey;

	// 0x03  value 0xC3 = 11000011
	@Bound
	private boolean fixed1;  // 0
	@Bound
	private boolean fixed2;  // 1
	@Bound
	private boolean callReceiveToneEnabled; // 2
	@BoundNumber(size = "3")
	private int nn27; // 3 - 5
	@BoundNumber(size = "2") // 6 - 7
	private int callType;

	// 0x04
	@BoundString(size = "32", encoding = "UTF-16LE")
	private String name;

	@Override
	public int getCallId() {
		return callIdKey;
	}

	@Override
	public void setCallId(int callIdKey) {
		this.callIdKey = callIdKey;
	}

	@Override
	public boolean isCallReceiveToneEnabled() {
		return callReceiveToneEnabled;
	}

	@Override
	public void setCallReceiveToneEnabled(boolean callReceiveToneEnabled) {
		this.callReceiveToneEnabled = callReceiveToneEnabled;
	}

	@Override
	public CallType getCallType() {
		switch (callType) {
			case 1:
				return CallType.GROUP;
			case 2:
				return CallType.PRIVATE;
			case 3:
				return CallType.ALL;
		}
		return CallType.UNDEF;
	}

	@Override
	public void setCallType(CallType callType) {
		this.callType = callType.ordinal();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		if (name == null) {
			return;
		}
		this.name = name.trim();
	}

	@Override
	public String toString() {
		return "MD380DigitalContact{" + "callIdKey=" + callIdKey + ", callReceiveToneEnabled=" + callReceiveToneEnabled + ", callType=" + callType + ", name=" + name + '}';
	}

}
