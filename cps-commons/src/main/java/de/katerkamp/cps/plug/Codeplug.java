/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 */
package de.katerkamp.cps.plug;

import de.katerkamp.cps.sect.DigitalContact;
import de.katerkamp.cps.sect.DigitalRxGroupList;
import de.katerkamp.cps.sect.GeneralSettings;
import de.katerkamp.cps.sect.ScanList;
import de.katerkamp.cps.sect.TextMessage;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import de.katerkamp.cps.sect.Channel;
import de.katerkamp.cps.sect.Zone;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * The codeplug. 
 * 
 * Used an interface here for the schema instead of a class, as the 
 * class is occupied by Preon annotations. This way there is no need 
 * to copy fields from one class to another. However, this only works
 * with MOXy, not vanilla JAXB implementation.
 * @see http://blog.bdoughan.com/2010/07/moxy-jaxb-map-interfaces-to-xml.html
 * 
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
@XmlRootElement
public interface Codeplug {

	/**
	 * Schema version.
	 */
	@XmlAttribute
	public static final String version = "0.1";

	@XmlElement
	public GeneralSettings getGeneralSettings();

	public void setGeneralSettings(GeneralSettings generalSettings);

	@XmlElement
	public List<TextMessage> getTextMessages();

	public void setTextMessages(List<TextMessage> textMessages);

	@XmlElementWrapper
	@XmlElement(name="channel")
	public List<Channel> getChannels();

	public void setChannels(List<Channel> channelInformation);

	@XmlElementWrapper
	@XmlElement(name="digitalContact")
	public List<DigitalContact> getDigitalContacts();

	public void setDigitalContacts(List<DigitalContact> digitalContacts);

	@XmlElementWrapper
	@XmlElement(name="digitalRxGroupList")
	public List<DigitalRxGroupList> getDigitalRxGroupLists();

	public void setDigitalRxGroupLists(List<DigitalRxGroupList> digitalRxGroupList);

	@XmlElementWrapper
	@XmlElement(name="zone")
	public List<Zone> getZones();

	public void setZones(List<Zone> zones);

	@XmlElement
	public List<ScanList> getScanLists();

	public void setScanLists(List<ScanList> scanLists);
}
