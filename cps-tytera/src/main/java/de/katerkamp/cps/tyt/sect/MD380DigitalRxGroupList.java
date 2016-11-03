/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.tyt.sect;

import de.katerkamp.cps.sect.DigitalContact;
import de.katerkamp.cps.sect.DigitalRxGroupList;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.BoundString;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class MD380DigitalRxGroupList implements DigitalRxGroupList{

	@BoundString(size = "32", encoding = "UTF-16LE")
	String key;

	// todo bind object instead of index
	//DigitalContact
	@BoundNumber(size = "16")
	int contactInformation;

	private List<DigitalContact> contactMembers = new ArrayList<>();

	@Override
	public String getName() {
		return key;
	}

	@Override
	public void setName(String key) {
		if (key == null) {
			return;
		}
		this.key = key.trim();
	}

	@Override
	public List<DigitalContact> getContactMembers() {
		return contactMembers;
	}

	@Override
	public void setContactMembers(List<DigitalContact> contactMembers) {
		this.contactMembers = contactMembers;
	}


	
}
