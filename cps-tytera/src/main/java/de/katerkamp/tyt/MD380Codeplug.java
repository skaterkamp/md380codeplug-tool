/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.tyt;

import de.katerkamp.cps.plug.Codeplug;
import de.katerkamp.cps.sect.DigitalContact;
import de.katerkamp.cps.sect.DigitalRxGroupList;
import de.katerkamp.cps.sect.GeneralSettings;
import de.katerkamp.cps.sect.ScanList;
import de.katerkamp.cps.sect.TextMessage;
import java.util.ArrayList;
import java.util.List;
import de.katerkamp.cps.sect.Channel;
import de.katerkamp.cps.sect.Zone;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class MD380Codeplug implements Codeplug {

	private GeneralSettings generalSettings = null;
	private List<TextMessage> textMessages = new ArrayList<>();
	private List<Channel> channelInformation = new ArrayList<>();
	private List<DigitalContact> digitalContact = new ArrayList<>();
	private List<DigitalRxGroupList> digitalRxGroupList = new ArrayList<>();
	private List<Zone> zoneInformation = new ArrayList<>();
	private List<ScanList> scanList = new ArrayList<>();

	@Override
	public GeneralSettings getGeneralSettings() {
		return generalSettings;
	}

	@Override
	public void setGeneralSettings(GeneralSettings generalSettings) {
		this.generalSettings = generalSettings;
	}

	@Override
	public List<TextMessage> getTextMessages() {
		return textMessages;
	}

	@Override
	public void setTextMessages(List<TextMessage> textMessages) {
		this.textMessages = textMessages;
	}

	@Override
	public List<Channel> getChannels() {
		return channelInformation;
	}

	@Override
	public void setChannels(List<Channel> channelInformation) {
		this.channelInformation = channelInformation;
	}

	@Override
	public List<DigitalContact> getDigitalContacts() {
		return digitalContact;
	}

	@Override
	public void setDigitalContacts(List<DigitalContact> digitalContact) {
		this.digitalContact = digitalContact;
	}

	@Override
	public List<DigitalRxGroupList> getDigitalRxGroupLists() {
		return digitalRxGroupList;
	}

	@Override
	public void setDigitalRxGroupLists(List<DigitalRxGroupList> digitalRxGroupList) {
		this.digitalRxGroupList = digitalRxGroupList;
	}

	@Override
	public List<Zone> getZones() {
		return zoneInformation;
	}

	@Override
	public void setZones(List<Zone> zoneInformation) {
		this.zoneInformation = zoneInformation;
	}

	@Override
	public List<ScanList> getScanLists() {
		return scanList;
	}

	@Override
	public void setScanLists(List<ScanList> scanList) {
		this.scanList = scanList;
	}

	
}
