/*
 * Copyright (c) 2016 Stefan Katerkamp
 */
package de.katerkamp.cps;

import de.katerkamp.cps.plug.Codeplug;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.xml.bind.JAXBException;

/**
 * Backup and restore service.
 * 
 * The backupDir must exist.
 *
 * File siblings allowed (lowercase only):
 * <ul>
 * <li>.xml</li>
 * <li>.xml.gz</li>
 * <li>.json</li>
 * <li>.json.gz</li>
 * </ul>
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class CPArchiver {

	private static final Logger LOGGER = Logger.getLogger(CPArchiver.class.getName());

	private boolean compressWrite = false;
	private boolean writeXml = true;
	private Path backupDir = null;
	private boolean prettyPrint = true;

	/**
	 * Default backup backup directory.
	 */
	static final String CP_FILE_ID = "-cp";
	static final String JSON_FILE_EXT1 = ".json";
	static final String XML_FILE_EXT1 = ".xml";
	static final String COMPRESSED_FILE_EXT2 = ".gz";

	final static BiPredicate<Path, BasicFileAttributes> PREDICATE_CP = (path, attrs)
		-> attrs.isRegularFile()
		&& path.toString().contains(CP_FILE_ID)
		&& (path.toString().endsWith(JSON_FILE_EXT1 + COMPRESSED_FILE_EXT2)
		|| path.toString().endsWith(JSON_FILE_EXT1)
		|| path.toString().endsWith(XML_FILE_EXT1)
		|| path.toString().endsWith(XML_FILE_EXT1 + COMPRESSED_FILE_EXT2));


	/**
	 * Construct backup service. The BackupDir will be set from the
	 * properties and must exist and be readable. It also must be writable
	 * in case of backup.
	 *
	 * For restore file format gets autodetected. For backup, files will be
	 * created in JSON format and not compressed. They will be nicely
	 * formatted.
	 *
	 * @param backupDir path to existing backup dir
	 */
	public CPArchiver() throws CPException {
		this(
			Paths.get(System.getProperty(CPProperties.CP_BACKUP_DIR, "/var/opt/cps/")),
			Boolean.getBoolean(CPProperties.CP_BACKUP_XML),
			Boolean.getBoolean(CPProperties.CP_BACKUP_GZIP),
			Boolean.getBoolean(CPProperties.CP_BACKUP_PRETTY)
		);
	}

	/**
	 * Construct backup service. BackupDir must exist and be readable. It
	 * also must be writable in case of backup.
	 *
	 * For restore file format gets autodetected. For backup, files will be
	 * created in XML format and not compressed. They will be nicely
	 * formatted.
	 *
	 * @param backupDir path to existing backup dir
	 */
	public CPArchiver(Path backupDir) throws CPException {
		this(backupDir, 
			Boolean.getBoolean(CPProperties.CP_BACKUP_XML),
			Boolean.getBoolean(CPProperties.CP_BACKUP_GZIP),
			Boolean.getBoolean(CPProperties.CP_BACKUP_PRETTY)
		);
	}

	/**
	 * Construct backup service. BackupDir must exist and be readable and
	 * writable.
	 *
	 * @param backupDir path to existing backup dir
	 * @param writeXml if true, write XML, otherwise write JSON (backup only)
	 * @param compressWrite  if true, gzip generated file (backup only)
	 * @param prettyPrint if true, pretty print file (backup only)
	 * @throws CPException
	 */
	public CPArchiver(Path backupDir, boolean writeXml, boolean compressWrite,
		boolean prettyPrint) throws CPException {
		if (backupDir == null) {
			throw new CPException("No backup directory specified");
		}
		if (!Files.isReadable(backupDir)) {
			throw new CPException("Backup directory not readable: " + backupDir.toString());
		}
		this.backupDir = backupDir;
		this.writeXml = writeXml;
		this.prettyPrint = prettyPrint;
		this.compressWrite = compressWrite;
		if (LOGGER.isLoggable(Level.FINEST)) {
			LOGGER.log(Level.FINEST, backupDir.toString() 
			+ " xml:" + writeXml + " pretty:" + prettyPrint + " gz:" + compressWrite);
		}
	}

	/**
	 * Clean up backup sub tree for a single backup. Affects only CP
	 * files. This is useful in case a backup gets backed up again and
	 * has less or different sipURIs since last time. BackupDir must be
	 * writable.
	 *
	 * @param susbcriberId
	 * @return
	 */
	public int cleanup(DmrId dmrId) throws IOException {
		CPFileVisitor visitor = new CPFileVisitor();
		Files.walkFileTree(backupDir.resolve(dmrId.value()), visitor);
		return visitor.count;
	}

	private static final class CPFileVisitor extends SimpleFileVisitor<Path> {

		int count = 0;

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			Files.delete(file);
			count++;
			return FileVisitResult.CONTINUE;
		}
	}

	/**
	 * Restore all MMTels. Restores all MMTels from backup dir.
	 * Autodetects format by file ending. 
	 *
	 * @param dmrId
	 * @return Codeplug or null if not found
	 * @throws JAXBException in case of parsing error
	 */
	public Codeplug restoreCodeplug(DmrId dmrId) throws CPException {

		Codeplug getCopdeplug = null;
		try {
			Stream<Path> stream = Files.find(backupDir.resolve(dmrId.value()), 1, PREDICATE_CP);
			Optional<Path> optional = stream.findFirst();
			if (optional == null) {
				return null;
			}
			Path cpFile = optional.get();
			getCopdeplug = ArchiverLibs.restoreBean(Codeplug.class, cpFile);
		} catch (Exception e) {
			return null;
		}

		return getCopdeplug;
	}


	/**
	 * Backup Codeplug to standard backup dir. It will create directory
	 * tree needed for the backup automatically, also name backup files
	 * according rules. This method writes the Codeplug as
	 * Codeplug.
	 *
	 * @param dmrId
	 * @param mmtelGroup
	 * @throws IOException
	 */
	public void backupCodeplug(DmrId dmrId, Codeplug codeplug) throws IOException {
		StringBuilder fileName = new StringBuilder();
		fileName.append(dmrId.value());
		fileName.append(CP_FILE_ID);
		if (writeXml) {
			fileName.append(XML_FILE_EXT1);
		} else {
			fileName.append(JSON_FILE_EXT1);
		}
		if (compressWrite) {
			fileName.append(COMPRESSED_FILE_EXT2);
		}
		Path mmtelGroupFile = backupDir.resolve(fileName.toString());
		ArchiverLibs.backupBean(mmtelGroupFile, codeplug, Codeplug.class, prettyPrint);
	}

	public Path getBackupDir() {
		return backupDir;
	}

	public boolean isCompressWrite() {
		return compressWrite;
	}

	public boolean isWriteXml() {
		return writeXml;
	}

	public boolean isPrettyPrint() {
		return prettyPrint;
	}
	

}
