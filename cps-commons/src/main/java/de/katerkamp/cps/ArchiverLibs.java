/*
 * Copyright (c) 2016 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps;

import static de.katerkamp.cps.CPArchiver.COMPRESSED_FILE_EXT2;
import static de.katerkamp.cps.CPArchiver.JSON_FILE_EXT1;
import static de.katerkamp.cps.CPArchiver.XML_FILE_EXT1;
import de.katerkamp.cps.plug.Codeplug;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class ArchiverLibs {

	private static final Logger LOGGER = Logger.getLogger(ArchiverLibs.class.getName());

	final static Set<PosixFilePermission> DIR_PERMISSIONS = EnumSet.of(
			PosixFilePermission.OWNER_READ,
			PosixFilePermission.OWNER_WRITE,
			PosixFilePermission.OWNER_EXECUTE,
			PosixFilePermission.GROUP_READ,
			PosixFilePermission.GROUP_WRITE,
			PosixFilePermission.GROUP_EXECUTE,
			PosixFilePermission.OTHERS_READ,
			PosixFilePermission.OTHERS_EXECUTE
	);
	final static Set<PosixFilePermission> FILE_PERMISSIONS = EnumSet.of(
			PosixFilePermission.OWNER_READ,
			PosixFilePermission.OWNER_WRITE,
			PosixFilePermission.GROUP_READ,
			PosixFilePermission.GROUP_WRITE,
			PosixFilePermission.OTHERS_READ
	);

	final static PathMatcher COMPRESSED_PATHMATCHER = FileSystems.getDefault().getPathMatcher("glob:**{" + COMPRESSED_FILE_EXT2 + "}");

	final static PathMatcher JSON_PATHMATCHER = FileSystems.getDefault().getPathMatcher("glob:**{" + JSON_FILE_EXT1 + "," + JSON_FILE_EXT1 + COMPRESSED_FILE_EXT2 + "}");

	final static PathMatcher XML_PATHMATCHER = FileSystems.getDefault().getPathMatcher("glob:**{" + XML_FILE_EXT1 + "," + XML_FILE_EXT1 + COMPRESSED_FILE_EXT2 + "}");

	/**
	 * Write Bean.
	 * Writes Java Bean to file. Understands whether it should use JSON
	 * or XML format by the pathname extension, optionally gzipped.
	 *
	 * @param outFile
	 * @param instance
	 * @param clazz
	 * @throws IOException
	 */
	final static void backupBean(Path outFile, Object instance, Class clazz, boolean prettyPrint) throws IOException {
		LOGGER.log(Level.FINE, "Backing up to " + outFile + " class " + clazz.getName());
		// bootstrap MOXy context to prevent JAXB can't handle interface error
		System.getProperties().setProperty(JAXBContext.JAXB_CONTEXT_FACTORY,
				org.eclipse.persistence.jaxb.JAXBContextFactory.class.getCanonicalName());

		OutputStream os = Files.newOutputStream(outFile);
		if (COMPRESSED_PATHMATCHER.matches(outFile)) {
			os = new GZIPOutputStream(os);
		}

		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Marshaller marshaller = context.createMarshaller();
			if (prettyPrint) {
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			}

			if (XML_PATHMATCHER.matches(outFile)) {
				LOGGER.log(Level.CONFIG, "Writing XML to " + outFile);
				marshaller.setProperty(org.eclipse.persistence.jaxb.JAXBContextProperties.MEDIA_TYPE, MediaType.APPLICATION_XML);
			} else if (JSON_PATHMATCHER.matches(outFile)) {
				LOGGER.log(Level.CONFIG, "Writing JSON to " + outFile);
				marshaller.setProperty(org.eclipse.persistence.jaxb.JAXBContextProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
				marshaller.setProperty(org.eclipse.persistence.jaxb.JAXBContextProperties.JSON_INCLUDE_ROOT, true);
			} else {
				throw new IOException("File format not supported: " + outFile.toString());
			}

			marshaller.marshal(instance, os);
		} catch (JAXBException ex) {
			LOGGER.log(Level.WARNING, ex.getMessage());
		} finally {
			os.flush();
			os.close();
			updatePermissions(outFile);
		}
	}

	public final static void writeBeanXML(OutputStream os, Object instance, Class clazz, boolean prettyPrint) throws CPException {
		// bootstrap MOXy context to prevent JAXB can't handle interface error
		System.getProperties().setProperty(JAXBContext.JAXB_CONTEXT_FACTORY,
				org.eclipse.persistence.jaxb.JAXBContextFactory.class.getCanonicalName());
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(org.eclipse.persistence.jaxb.JAXBContextProperties.MEDIA_TYPE, MediaType.APPLICATION_XML);
			if (prettyPrint) {
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			}
			marshaller.marshal(instance, os);
		} catch (JAXBException ex) {
			throw new CPException(ex);
		}
	}

	/**
	 * Read Java Bean from File. Understands whether it should use JSON
	 * or XML format by the pathname extension, optionally gzipped.
	 *
	 * @param <T>
	 * @param clazz
	 * @param beanFile
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	final static <T> T restoreBean(Class<T> clazz, Path beanFile) throws IOException, JAXBException {

		// bootstrap MOXy context to prevent JAXB can't handle interface error
		System.getProperties().setProperty(JAXBContext.JAXB_CONTEXT_FACTORY,
				org.eclipse.persistence.jaxb.JAXBContextFactory.class.getCanonicalName());

		InputStream is = Files.newInputStream(beanFile);
		if (COMPRESSED_PATHMATCHER.matches(beanFile)) {
			is = new GZIPInputStream(is);
		}
		T instance = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			if (JSON_PATHMATCHER.matches(beanFile)) {
				LOGGER.log(Level.CONFIG, "Reading JSON from " + beanFile);
				unmarshaller.setProperty(org.eclipse.persistence.jaxb.JAXBContextProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
				unmarshaller.setProperty(org.eclipse.persistence.jaxb.JAXBContextProperties.JSON_INCLUDE_ROOT, true);
			} else if (XML_PATHMATCHER.matches(beanFile)) {
				LOGGER.log(Level.CONFIG, "Reading XML from " + beanFile);
				unmarshaller.setProperty(org.eclipse.persistence.jaxb.JAXBContextProperties.MEDIA_TYPE, MediaType.APPLICATION_XML);
			} else {
				throw new IOException("File format not supported: " + beanFile.toString());
			}
			Source source = new StreamSource(is);

			Object o;
			if (clazz.equals(Codeplug.class)) {
				JAXBElement<T> jaxbElement = unmarshaller.unmarshal(source, clazz);
				o = jaxbElement.getValue();
			} else {
				o = unmarshaller.unmarshal(source);
			}

		} finally {
			is.close();
		}

		return instance;
	}

	static final void updatePermissions(Path fileOrDir) throws IOException {
		PosixFileAttributeView posixView = Files.getFileAttributeView(fileOrDir, PosixFileAttributeView.class);
		if (posixView == null) {
			LOGGER.log(Level.INFO, "Cannot set file permissions.");
			return;
		}
		if (Files.isDirectory(fileOrDir)) {
			posixView.setPermissions(DIR_PERMISSIONS);
		} else {
			posixView.setPermissions(FILE_PERMISSIONS);
		}
	}
}
