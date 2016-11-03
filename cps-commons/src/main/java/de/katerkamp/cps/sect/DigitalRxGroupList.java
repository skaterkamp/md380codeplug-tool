/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.sect;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public interface DigitalRxGroupList {
	
	@XmlElement
	@NotNull
	@Size(min=1)
	public String getName();

	public void setName(String groupListName);

	@XmlElementWrapper
	@XmlElement(name="digitalContact")
	public List<DigitalContact> getContactMembers();

	public void setContactMembers(List<DigitalContact> contactMembers);

}
