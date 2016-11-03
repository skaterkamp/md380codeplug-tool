/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 */
package de.katerkamp.cps.sect;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public interface TextMessage {
	
	@Size(min=1)
	@NotNull
	public String getText();

	public void setText(String textMessage);

}
