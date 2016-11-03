/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class CPConverterTest {

	final static Level loggingLevel = Level.FINEST;

	public CPConverterTest() {
	}

	@BeforeClass
	public static void setUpClass() {
		for (Handler h : Logger.getLogger("").getHandlers()) {
			if (h instanceof ConsoleHandler) {
				h.setLevel(loggingLevel);
			}
		}
		Logger.getLogger("de.katerkamp").setLevel(loggingLevel);
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of main method, of class PrintCodeplugFile.
	 */
	@Test
	public void testMain() throws Exception {
		// avoid binary files for git
		Path inFile = Paths.get(CPConverterTest.class.getResource("/samples/TYT-MD380-codeplug.rdt.base64").toURI());
		Path p = inFile.getParent();
		Path rdtFile = p.resolve("TYT-MD380-codeplug.rdt");
		Path outFile = p.resolve("TYT-MD380-codeplug.xml");

		byte[] input = Files.readAllBytes(inFile);
		byte[] rdt = DatatypeConverter.parseBase64Binary(new String(input));
		Files.write(rdtFile, rdt);
		
		System.out.println("Converting: " + rdtFile);

		String[] args = {rdtFile.toString(), outFile.toString()};
		CPConverter.main(args);

		System.out.println("\n\nConverted:");
		System.out.println(rdtFile);
		System.out.println(" to ");
		System.out.println(outFile + "\n\n");
	}

}
