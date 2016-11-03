/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.tyt;

import de.katerkamp.cps.plug.Codeplug;
import de.katerkamp.cps.plug.CodeplugType;
import de.katerkamp.cps.sect.Channel;
import de.katerkamp.cps.sect.DigitalContact;
import de.katerkamp.cps.sect.DigitalRxGroupList;
import de.katerkamp.cps.sect.ScanList;
import de.katerkamp.cps.sect.TextMessage;
import de.katerkamp.cps.tyt.sect.MD380DigitalContact;
import de.katerkamp.cps.tyt.sect.MD380DigitalRxGroupList;
import de.katerkamp.cps.tyt.sect.MD380GeneralSettings;
import de.katerkamp.cps.tyt.sect.MD380TextMessage;
import de.katerkamp.cps.tyt.sect.MD380Zone;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.HexDump;
import org.codehaus.preon.Codec;
import org.codehaus.preon.Codecs;
import org.codehaus.preon.DecodingException;
import de.katerkamp.cps.sect.Zone;
import de.katerkamp.cps.tyt.sect.MD380Channel;
import de.katerkamp.cps.tyt.sect.MD380ScanList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Codeplug Factory.
 *
 * == Records
 *
 * Data inside the codeplug is divided in records. Every record has its own
 * format. Some records are available in single instance (for example, the
 * record
 * describing the “General Settings”), while other records are repeated (for
 * example, records describing the “Channel Information” have one record for
 * each
 * channel). Multiple records are always allocated in sequence within the
 * codeplug file. Records are always octet aligned. For each set of records, the
 * description below will give
 *
 * the offset in octets where the first record starts
 * the length, in octets, of each record
 * the number of records available in the file
 *
 * == Fields
 *
 * The records are divided in fields. Each field represent an edit box, a check
 * box or other field in the editor card. Fields can be as small as one bit or
 * as
 * long as 16 octets. The field table will give the offset in bits of the field
 * within the record and the length in bits of the field. So, bit #0 will be the
 * MSB of the first octet of the record; bit #7 will be the LSB of the first
 * octet, while bit #8 will be the MSB of the second octet and so on. Fields are
 * encoded according an encoding type
 *
 * == Empty records
 *
 * Tables supporting multiple records (for example Channel Information, Scan
 * List,
 * Zone Information, etc) have always all records allocated. So, if Channel
 * Information supports a maximum of 1000 entries, in the codeplug file there
 * will
 * always be 1000 entries. The unused entries are marked with one or more octets
 * that are set to a given value when the record is not used. When setting the a
 * record to empty, the safest strategy is to set it to its “initial condition”.
 * The “initial condition” is the configuraton used by the “CPS RT3” editor
 * program when creates a new “default1.rdt” empty codeplug. Some types of
 * records are initialized to zero, while other are initialized to 0xFF. In
 * every
 * case, the Unicode and Ascii strings are to be initialized to zero. The
 * “Default zero value” in the table below specifies this detail. *
 * == Deletion markers
 *
 * When a record is deleted, the CPS RT3 editor does not clear its contents to
 * its
 * initial state but simply sets one or bytes to a prefixed value. Therefore,
 * the
 * decoder must check these octets only to determine whether a record is active
 * or
 * deleted. The bits to be checked are described in the “deletion markers” table
 * of every record descriptor below.
 *
 *
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class CodeplugFactory {

	private Codeplug codeplug;

	private final int RDTOFF = 549; // from rdt start: 0x2265 = 8805, 8256 = 8805 - 549

	private final int GENERAL_SETTINGS_AREA_OFFSET = 8256;
	private final int GENERAL_SETTINGS_BLOCK_LENGTH = 144;
	private final int GENERAL_SETTINGS_BLOCK_COUNT = 1;

	private final int TEXT_MESSAGES_AREA_OFFSET = 0x23A5 - RDTOFF;
	private final int TEXT_MESSAGE_BLOCK_LENGTH = 288;
	private final int TEXT_MESSAGE_BLOCK_COUNT = 50;

	private final int CHANNEL_AREA_OFFSET = 0x1F025 - RDTOFF;
	private final int CHANNEL_LENGTH = 64;
	private final int CHANNEL_COUNT = 1000;

	private final int DIGITAL_CONTACT_AREA_OFFSET = 0x61a5 - RDTOFF;
	private final int DIGITAL_CONTACT_LENGTH = 36;
	private final int DIGITAL_CONTACT_COUNT = 1000;

	private final int DIGITAL_RX_GROUPLIST_AREA_OFFSET = 0xEE45 - RDTOFF;
	private final int DIGITAL_RX_GROUPLIST_LENGTH = 96;
	private final int DIGITAL_RX_GROUPLIST_COUNT = 250;

	private final int ZONE_INFORMATION_AREA_OFFSET = 0x14C05 - RDTOFF;
	private final int ZONE_INFORMATION_LENGTH = 64;
	private final int ZONE_INFORMATION_COUNT = 250;

	private final int SCAN_LIST_AREA_OFFSET = 0x18A85 - RDTOFF;
	private final int SCAN_LIST_LENGTH = 104;
	private final int SCAN_LIST_COUNT = 250;

	private static final Logger LOGGER = Logger.getLogger(CodeplugFactory.class.getName());

	CodeplugFactory(CodeplugType codeplugType) {
		switch (codeplugType) {
			case MD380:
				codeplug = new MD380Codeplug();
				break;
		}
	}

	/**
	 * Covert from binary to Java bean object tree.
	 * 
	 * @param codeplugByteArray
	 * @return
	 * @todo create method for redundant code in here
	 * @throws IOException 
	 */
	public Codeplug unmarshal(byte[] codeplugByteArray) throws IOException {

		byte[] block;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		block = getBlock(codeplugByteArray,
				GENERAL_SETTINGS_AREA_OFFSET, GENERAL_SETTINGS_BLOCK_LENGTH, 0);
		codeplug.setGeneralSettings(unmarshal(MD380GeneralSettings.class, block));

		LOGGER.log(Level.CONFIG, "Reading byte array into Codeplug object tree...");

		List<TextMessage> textMessages = new ArrayList<>();
		codeplug.setTextMessages(textMessages);
		for (int i = 0; i < TEXT_MESSAGE_BLOCK_COUNT; i++) {
			block = getBlock(codeplugByteArray,
					TEXT_MESSAGES_AREA_OFFSET, TEXT_MESSAGE_BLOCK_LENGTH, i);
			TextMessage textMessage = unmarshal(MD380TextMessage.class, block);

			Set<ConstraintViolation<TextMessage>> constraintViolations = validator.validate(textMessage);
			if (constraintViolations.isEmpty()) {
				LOGGER.fine("Added text message");
				textMessages.add(textMessage);
			}
		}

		List<Zone> zones = new ArrayList<>();
		codeplug.setZones(zones);
		for (int i = 0; i < ZONE_INFORMATION_COUNT; i++) {
			block = getBlock(codeplugByteArray,
					ZONE_INFORMATION_AREA_OFFSET, ZONE_INFORMATION_LENGTH, i);
			Zone zone = unmarshal(MD380Zone.class, block);

			Set<ConstraintViolation<Zone>> constraintViolations = validator.validate(zone);
			if (constraintViolations.isEmpty()) {
				zones.add(zone);
			}
		}

		List<DigitalContact> digitalContacts = new ArrayList<>();
		codeplug.setDigitalContacts(digitalContacts);
		for (int i = 0; i < DIGITAL_CONTACT_COUNT; i++) {
			block = getBlock(codeplugByteArray,
					DIGITAL_CONTACT_AREA_OFFSET, DIGITAL_CONTACT_LENGTH, i);
			DigitalContact digitalContact = unmarshal(MD380DigitalContact.class, block);
			digitalContacts.add(digitalContact);
		}

		List<DigitalRxGroupList> digitalRxGroupLists = new ArrayList<>();
		codeplug.setDigitalRxGroupLists(digitalRxGroupLists);
		for (int i = 0; i < DIGITAL_RX_GROUPLIST_COUNT; i++) {
			block = getBlock(codeplugByteArray, 
					DIGITAL_RX_GROUPLIST_AREA_OFFSET, DIGITAL_RX_GROUPLIST_LENGTH, i);
			DigitalRxGroupList digitalRxGroupList = unmarshal(MD380DigitalRxGroupList.class, block);
			Set<ConstraintViolation<DigitalRxGroupList>> constraintViolations = validator.validate(digitalRxGroupList);
			if (constraintViolations.isEmpty()) {
				digitalRxGroupLists.add(digitalRxGroupList);
			}
		}

		List<ScanList> scanLists = new ArrayList<>();
		codeplug.setScanLists(scanLists);
		for (int i = 0; i < SCAN_LIST_COUNT; i++) {
			block = getBlock(codeplugByteArray, 
					SCAN_LIST_AREA_OFFSET, SCAN_LIST_LENGTH, i);
			ScanList scanList = unmarshal(MD380ScanList.class, block);
			Set<ConstraintViolation<ScanList>> constraintViolations = validator.validate(scanList);
			if (constraintViolations.isEmpty()) {
				scanLists.add(scanList);
			}
		}

		List<Channel> channels = new ArrayList<>();
		codeplug.setChannels(channels);
		for (int i = 0; i < CHANNEL_COUNT; i++) {
			block = getBlock(codeplugByteArray, 
					CHANNEL_AREA_OFFSET, CHANNEL_LENGTH, i);
			Channel channel = unmarshal(MD380Channel.class, block);
			Set<ConstraintViolation<Channel>> constraintViolations = validator.validate(channel);
			if (constraintViolations.isEmpty()) {
				channels.add(channel);
			}
		}

		return codeplug;
	}

	private byte[] getBlock(byte[] fullImage, int areaOffset, int blockLength, int blockPos) {
		return Arrays.copyOfRange(fullImage, areaOffset + (blockPos * blockLength), areaOffset + ((blockPos + 1) * blockLength));
	}

	private static <T> T unmarshal(Class<T> clazz, byte[] in) throws IOException {

		if (LOGGER.isLoggable(Level.FINEST)) {
			System.out.println(clazz.toString() + ";");
			HexDump.dump(in, 0, System.out, 0);
		}

		Codec<T> codec = Codecs.create(clazz);
		T gs = null;
		byte[] readback = null;
		try {
			gs = Codecs.decode(codec, in);
		} catch (DecodingException ex) {
			ex.printStackTrace();
		}
		//try {
		//	readback = Codecs.encode(gs, codec);
		//	HexDump.dump(readback, 0, System.out, 0);
		//} catch (Exception ex) {
		//	ex.printStackTrace();
		//}

		return gs;
	}

}
