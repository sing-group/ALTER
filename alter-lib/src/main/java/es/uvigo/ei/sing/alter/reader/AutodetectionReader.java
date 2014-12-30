/*
 *  This file is part of ALTER.
 *
 *  ALTER is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ALTER is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ALTER.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.uvigo.ei.sing.alter.reader;

import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.ei.sing.alter.parser.*;
import es.uvigo.ei.sing.alter.types.MSA;

/**
 * Autodetection reader
 * @author Daniel Gomez Blanco
 * @version 1.1
 */

public class AutodetectionReader implements Reader
{
    /**
     * Logger to register information messages.
     */
    Logger logger;

     /**
     * Class contructor, it initializes the logger.
     * @param logger Name of the logger to be instatiated.
     */
    public AutodetectionReader(String logger)
    {
        this.logger = Logger.getLogger(logger);
    }

    /**
     * Parses an input string and returns an MSA object.
     * @param in Input string.
     * @return MSA object parsed from the input string.
     * @throws ParseException If an error occurs while parsing.
     */
    public MSA read(String in) throws ParseException
    {
        //Detect format
        String inF = AutodetectionParser.detectFormat(in);

        logger.log(Level.INFO, inF.toUpperCase() + " format detected.");

        //Parse input in the detected format
        Reader reader;
        if (inF.equals("aln"))
            reader = new AlnReader(logger.getName());
        else if (inF.equals("fasta"))
            reader = new FastaReader(logger.getName());
        else if (inF.equals("gde"))
            reader = new GdeReader(logger.getName());
        else if (inF.equals("msf"))
            reader = new MsfReader(logger.getName());
        else if (inF.equals("nexus"))
            reader = new NexusReader(logger.getName());
        else if (inF.equals("phylip"))
            reader = new PhylipReader(logger.getName());
        else if (inF.equals("pir"))
            reader = new PirReader(logger.getName());
        else
            throw new UnsupportedOperationException("Input format not supported.");
        return reader.read(in);
    }
}
