/* 
 * Copyright (c) 2016 Stefan Katerkamp
 */

package de.katerkamp.cps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DMR ID.
 * 
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public final class DmrId implements Serializable, Comparable<DmrId> {

	// todo maybe reuse instances
	//private final static DmrId INSTANCE = new DmrId();
	//private Set<String> ids = null;
	//private DmrId() {
	//	ids = new HashSet<>();	
	//}
	// public static void setId(String subscriberID) {
	//     INSTANCE.ids.add(value);
	// }
	

	/**
	 * The value of the {@code DmrId}.
	 *
	 * @serial
	 */
	private final String value;

	private static final long serialVersionUID = -6692810390022352324L;
	
	/**
	 * Constructs a subscriber ID.
	 *
	 * @param subscriberID
	 */
	public DmrId(long subscriberID) {
		value = Long.toString(subscriberID);
	}

	/**
	 * Constructs a subscriber ID.
	 *
	 * @param subscriberID
	 */
	public DmrId(String subscriberID) {
		if (subscriberID == null) {
			throw new NumberFormatException("Input must not be null.");
		}
		value = subscriberID.trim();
	}

	/**
	 * Returns the value of this DmrId as a long value.
	 *
	 * @return
	 */
	public String value() {
		return value;
	}

	/**
	 * Returns a hash code for this DmrId.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return value.hashCode();
	}

	/**
	 * Compares this object to the specified object. The result is {@code true}
	 * if and only if the argument is not {@code null} and is a
	 * {@code SubscriberId} object that contains the same {@code long} value as
	 * this object.
	 *
	 * @param obj the object to compare with.
	 * @return {@code true} if the objects are the same; {@code false}
	 * otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return value.equals(((DmrId) obj).value());
	}

	/**
	 * Compares two DmrId numerically.
	 *
	 * @param anotherDmrId
	 * @return
	 */
	@Override
	public int compareTo(DmrId anotherDmrId) {
		return value.compareTo(((DmrId) anotherDmrId).value());
	}

	/**
	 * Return subscriber ID.
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return value;
	}

}
