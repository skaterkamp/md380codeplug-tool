/*
 * Copyright (c) 2014 Stefan Katerkamp. All rights reserved.
 * 
 * Original Author: Stefan Katerkamp <info@katerkamp.de>
 */
package de.katerkamp.cps;

import de.katerkamp.cps.plug.Codeplug;
import de.katerkamp.tyt.RDTFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Reads a codeplug file and dumps its contents in human readable form.
 *
 * @dtodo check for file extension and call proper codec
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class PrintCodeplugFile {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage:  PrintCodeplugFile <file.rdt>");
			System.exit(-1);
		}

		Path file = Paths.get(args[0]);

		// MD380 only for now
		RDTFactory rdtFactory = new RDTFactory();
		Codeplug codeplug = rdtFactory.unmarshal(file);
		System.out.println(codeplug.getGeneralSettings().toString().replaceAll(", ", ",\n"));

	}
}
