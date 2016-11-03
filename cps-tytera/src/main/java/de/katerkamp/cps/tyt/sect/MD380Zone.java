/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.tyt.sect;

import de.katerkamp.cps.sect.Channel;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.List;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.BoundNumber;
import org.codehaus.preon.annotation.BoundString;
import de.katerkamp.cps.sect.Zone;
import java.util.ArrayList;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class MD380Zone implements Zone {

	private byte deletionMarker = 0x00;

	@BoundString(size = "32", encoding = "UTF-16LE")
	String key;

	@BoundList(type=MD380Channel.class,size = "16")
	List<MD380Channel> channelMembers;
	List<Channel> cms = new ArrayList<>();

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
	public List<Channel> getMembers() {
		return cms;
	}

	@Override
	public void setMembers(List<Channel> channels) {
		this.cms = channels;
	}


	
	
}
