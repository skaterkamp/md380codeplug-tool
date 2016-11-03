/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.tyt.sect;

import de.katerkamp.cps.sect.TextMessage;
import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundString;

/**
 * Text Message Block. It starts mit a deletion marker.
 *
 * @todo check alignment 
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class MD380TextMessage implements TextMessage {

	private byte deletionMarker = 0x00;

	@BoundString(size = "288", encoding = "UTF-16LE")
	String message;


	@Override
	public String getText() {
		return message;
	}

	@Override
	public void setText(String textMessage) {
		if (textMessage == null) {
			return;
		}
		this.message = textMessage.trim();

	}

	@Override
	public String toString() {
		return "MD380TextMessage{" + "textMessage=" + message + '}';
	}

	
}
