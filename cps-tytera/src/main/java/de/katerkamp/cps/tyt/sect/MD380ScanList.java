/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.tyt.sect;

import de.katerkamp.cps.sect.Channel;
import de.katerkamp.cps.sect.ScanList;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.preon.annotation.BoundString;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class MD380ScanList implements ScanList{

	@BoundString(size = "32", encoding = "UTF-16LE")
	private String name;

	private List<Channel> members = new ArrayList<>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<Channel> getChannelMembers() {
		return members;
	}

	@Override
	public void setChannelMembers(List<Channel> channelMembers) {
		members = channelMembers;
	}

	
}
