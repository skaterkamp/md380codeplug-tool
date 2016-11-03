/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.tyt;

import de.katerkamp.cps.ArchiverLibs;
import de.katerkamp.cps.plug.Codeplug;
import de.katerkamp.cps.sect.Channel;
import de.katerkamp.cps.sect.DigitalContact;
import de.katerkamp.cps.sect.DigitalRxGroupList;
import de.katerkamp.cps.tyt.sect.MD380Channel;
import de.katerkamp.cps.tyt.sect.MD380DigitalContact;
import de.katerkamp.cps.tyt.sect.MD380DigitalRxGroupList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test MD380 codeplug instance.
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class CodeplugTest {
	
	public CodeplugTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
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
	 * Test of marshal to xml method for Codeplug instance.
	 */
	@Test
	public void writeXML() throws Exception {
		Codeplug cp = new MD380Codeplug();

		Channel channel = new MD380Channel();
		cp.getChannels().add(channel);

		DigitalContact digitalContact = new MD380DigitalContact();
		cp.getDigitalContacts().add(digitalContact);

		DigitalRxGroupList drgl = new MD380DigitalRxGroupList();
		cp.getDigitalRxGroupLists().add(drgl);
		
		
		// todo
		//ArchiverLibs.writeBeanXML(System.out, cp, Codeplug.class, true);
	}
	
}
