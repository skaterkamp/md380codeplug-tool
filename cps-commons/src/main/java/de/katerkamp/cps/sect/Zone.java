/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps.sect;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public interface Zone {

	@Size(min=1)
	@NotNull
	public String getName();
	public void setName(String zoneName);
	public List<Channel> getMembers();
	public void setMembers(List<Channel> channels);
}
