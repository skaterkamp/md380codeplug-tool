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
import java.util.logging.Logger;

/**
 * Reads a codeplug file and writes json file.
 *
 * @dtodo check for file extension and call proper codec
 * @author Stefan Katerkamp <stefan@katerkamp.de>
 */
public class CPConverter {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage:  CPConverter <file.rdt> <file.json>");
			System.exit(-1);
		}

		Path rdtFile = Paths.get(args[0]);
		Path jsonFile = Paths.get(args[1]);

		// MD380 only for now
		RDTFactory rdtFactory = new RDTFactory();
		Codeplug codeplug = rdtFactory.unmarshal(rdtFile);

		codeplug.setGeneralSettings(null);


		try {
			//System.out.println(codeplug.getGeneralSettings().toString().replaceAll(", ", ",\n"));
			ArchiverLibs.backupBean(jsonFile, codeplug, Codeplug.class, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
