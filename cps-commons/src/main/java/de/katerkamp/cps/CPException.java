/*
 * Copyright (c) 2016 Stefan Katerkamp
 */
package de.katerkamp.cps;

/**
 * Signals that a CPS error has occurred.
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class CPException extends Exception {
	
	private static final long serialVersionUID = -4824951860603651169L;

	public CPException() {
		super();
	}

	public CPException(final String message) {
		super(message);
	}

	public CPException(final Exception ex) {
		super(ex);
	}
}
