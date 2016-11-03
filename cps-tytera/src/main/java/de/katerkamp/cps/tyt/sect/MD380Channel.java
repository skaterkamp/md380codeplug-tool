/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.tyt.sect;

import de.katerkamp.cps.ds.channel.ChannelMode;
import de.katerkamp.cps.ds.channel.RefFrequency;
import de.katerkamp.cps.sect.Channel;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.BoundString;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class MD380Channel implements Channel {

	@BoundString(size = "32", encoding = "UTF-16LE")
	private String todo;

	@BoundString(size = "32", encoding = "UTF-16LE")
	private String name;
	

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String key) {
		this.name = key;
	}

	@Override
	public ChannelMode getMode() {
		return ChannelMode.ANALOG;
	}

	@Override
	public void setMode(ChannelMode channelMode) {
	}

	@Override
	public RefFrequency getRxRefFrequency() {
		return RefFrequency.HIGH;
	}

	@Override
	public void setRxRefFrequency(RefFrequency refFrequency) {
	}

	@Override
	public RefFrequency getTxRefFrequency() {
		return RefFrequency.HIGH;
	}

	@Override
	public void setTxRefFrequency(RefFrequency refFrequency) {
	}

	@Override
	public int getContactMember() {
		return 0;
	}

	@Override
	public void setContactMember(int contactMember) {
	}
	
	
}
