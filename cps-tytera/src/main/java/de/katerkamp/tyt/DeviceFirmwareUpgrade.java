/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.tyt;

import de.katerkamp.cps.plug.Codeplug;

/**
 * DFU wrapper which contains the codeplug payload.
 * 
 * http://www.usb.org/developers/docs/devclass_docs/DFU_1.1.pdf
 * 
 * @todo fine tune this class
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class DeviceFirmwareUpgrade {
	
	private byte[] dfuHeader; // todo
	private byte[] dfuHeader2; // todo
	private byte[] dfuFooter; // todo
	private Codeplug codeplug;

	public byte[] getDfuHeader() {
		return dfuHeader;
	}

	public void setDfuHeader(byte[] dfuheader) {
		this.dfuHeader = dfuheader;
	}

	public byte[] getDfuFooter() {
		return dfuFooter;
	}

	public void setDfuFooter(byte[] dfufooter) {
		this.dfuFooter = dfufooter;
	}

	public Codeplug getCodeplug() {
		return codeplug;
	}

	public void setCodeplug(Codeplug codeplug) {
		this.codeplug = codeplug;
	}

}
