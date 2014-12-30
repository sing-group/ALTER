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

import es.uvigo.ei.sing.alter.parser.MsfParser;
import es.uvigo.ei.sing.alter.parser.ParseException;
import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Msf;
import es.uvigo.ei.sing.alter.types.MsfSequence;
import es.uvigo.ei.sing.alter.types.Sequence;

/**
 * MSF format reader.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */

public class MsfReader implements Reader
{
    /**
     * Logger to register information messages.
     */
    Logger logger;

     /**
     * Class contructor, it initializes the logger.
     * @param logger Name of the logger to be instatiated.
     */
    public MsfReader(String logger)
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
        //Parse input
        Msf msa = MsfParser.parseMSA(in);

        //Check if MSA is correctly constructed
        for(int i=0;i<msa.getSeqs().size();i++)
        {
            Sequence seq = (Sequence)msa.getSeqs().elementAt(i);
            //Check if the sequences lengths are equal
            if (seq.getData().length() != msa.getLength()
                    || seq.getData().length() != ((MsfSequence)seq).getLength())
                throw new ParseException("Sequence lengths are not equal to the given length.");
        }

        logger.log(Level.INFO, "MSA read in MSF format (" +
                "Taxa = " + msa.getSeqs().size() + ", " +
                "Length =  " + ((Sequence)msa.getSeqs().elementAt(0)).getData().length() +
                ").");
        
        return msa;
    }
}