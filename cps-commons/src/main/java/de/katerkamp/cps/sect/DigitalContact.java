/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.sect;

import de.katerkamp.cps.ds.CallType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public interface DigitalContact {

	@XmlElement
	public int getCallId(); // key

	public void setCallId(int callIdKey);

	@XmlElement
	public boolean isCallReceiveToneEnabled();

	public void setCallReceiveToneEnabled(boolean callReceiveToneEnabled);

	@XmlElement
	public CallType getCallType() ;

	public void setCallType(CallType callType);

	@XmlElement
	public String getName() ;

	public void setName(String name);
}
