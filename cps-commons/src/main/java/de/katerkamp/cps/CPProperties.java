/*
 * Copyright (c) 2016 Stefan Katerkamp
 */
package de.katerkamp.cps;

/**
 * CPS Configuration Properties.
 * 
 * @author Stefan Katerkamp (stefan.katerkamp@katerkamp.de)
 */
public class CPProperties {

	public static final String  CP_PREFIX = "cps.";

	/**
	 * String value which defines the backup directory in the local file system
	 * where subscriber data will be stored in ldif and xml format. There will
	 * be a per subscriber subdirectory based on the subscriberID inside.
	 *
	 * The default value is null. Meaning, no backup will be created.
	 *
	 * The name of the configuration property is +{@value}+.
	 */
	public static final String CP_BACKUP_DIR = CP_PREFIX + "backup.dir";

	/**
	 * A boolean value that defines if backup files will be gzipped or not.
	 *
	 * If this property is missing or set to false, files in the backup are
	 * not gzipped.
	 * 
	 * The name of the configuration property is +{@value}+.
	 */
	public static final String CP_BACKUP_GZIP = CP_PREFIX + "backup.gzip";

	/**
	 * A boolean value that defines that format should be XML rather than
	 * JSON if possible.
	 *
	 * If this property is missing or set to false, files in the backup are
	 * written in Json format.
	 * 
	 * The name of the configuration property is +{@value}+.
	 */
	public static final String CP_BACKUP_XML = "backup.xml";

	/**
	 * A boolean value that defines that the directory will be cleaned up before
	 * anything get newly written into it.
	 *
	 * If this property is missing or set to false, files in the backup are
	 * simply added or overwritten.
	 * 
	 * The name of the configuration property is +{@value}+.
	 */
	public static final String CP_BACKUP_CLEANUP = CP_PREFIX + "backup.cleanup";

	/**
	 * A boolean value that defines that JAXB output will be nicely formatted.
	 *
	 * If this property is missing or set to false, the compact format will
	 * be used.
	 * 
	 * The name of the configuration property is +{@value}+.
	 */
	public static final String CP_BACKUP_PRETTY = CP_PREFIX + "backup.pretty";

}
