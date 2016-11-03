/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.tyt;

import de.katerkamp.cps.plug.Codeplug;
import de.katerkamp.cps.plug.CodeplugType;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 *
 * RDT file factory.
 *
 * TYT's official editor for the MD380 issues its codeplugs as .RDT files,
 * which are just a thin DFU header applied to the binary codeplug.
 * Extracting a .img from a .rdt is straightforward:
 *
 * * .rdt files are 565 bytes longer.
 * * There is 549 bytes at the beginning that
 * * identifies the radio/version of CPS software.
 * ** the bytes that indicate md-380 vs. cs700 start around 0x115.
 * * There is an extra 16 bytes at the end of the .rdt.
 *
 * @see
 * http://www.iz2uuf.net/wp/index.php/2016/06/04/tytera-dm380-codeplug-binary-format/[]
 * @see https://github.com/travisgoodspeed/md380tools/issues/2[]
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class RDTFactory {

	private static final Logger LOGGER = Logger.getLogger(RDTFactory.class.getName());

	private final static int RDT_FILE_SIZE = 262709;
	private final static int RDT_PAYLOAD_STARTPOS = 549; //0x2265;
	private final static int RDT_PAYLOAD_ENDPOS = RDT_FILE_SIZE - 16;

	/**
	 * Read RDT file.
	 *
	 * @param file
	 * @return
	 */
	public Codeplug unmarshal(Path file) throws IOException {

		if (Files.size(file) != RDT_FILE_SIZE) {
			throw new IOException("File is corrupted, size not equal to 262709: " + Files.size(file));
		}

		ByteChannel byteChannel = Files.newByteChannel(file);
		ByteBuffer byteBuffer = ByteBuffer.allocate(RDT_FILE_SIZE);
		if (RDT_FILE_SIZE != byteChannel.read(byteBuffer)) {
			throw new IOException("File not properly read.");
		}
		byte[] rdtArray = byteBuffer.array();
		byteChannel.close();

		byte[] header = Arrays.copyOfRange(rdtArray, 0, RDT_PAYLOAD_STARTPOS);
		byte[] codeplugBytes = Arrays.copyOfRange(rdtArray, RDT_PAYLOAD_STARTPOS, RDT_PAYLOAD_ENDPOS);
		byte[] footer = Arrays.copyOfRange(rdtArray, RDT_PAYLOAD_ENDPOS, RDT_FILE_SIZE);

		DeviceFirmwareUpgrade dfu = new DeviceFirmwareUpgrade();
		dfu.setDfuHeader(header);
		dfu.setDfuFooter(footer);

		CodeplugFactory codeplugFactory = new CodeplugFactory(CodeplugType.MD380);
		Codeplug codeplug = codeplugFactory.unmarshal(codeplugBytes);
		dfu.setCodeplug(codeplug);

		return codeplug;
	}

	/**
	 * Write RDT file.
	 */
	public void marshal(DeviceFirmwareUpgrade dfu, Path file) {

	}
}
