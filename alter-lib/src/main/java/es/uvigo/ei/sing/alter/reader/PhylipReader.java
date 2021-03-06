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

import es.uvigo.ei.sing.alter.parser.ParseException;
import es.uvigo.ei.sing.alter.parser.PhylipParser;
import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Phylip;
import es.uvigo.ei.sing.alter.types.Sequence;

/**
 * PHYLIP format reader.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */

public class PhylipReader implements Reader
{
    /**
     * Logger to register information messages.
     */
    Logger logger;

     /**
     * Class contructor, it initializes the logger.
     * @param logger Name of the logger to be instatiated.
     */
    public PhylipReader(String logger)
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
        Phylip msa = PhylipParser.parseMSA(in);

        //Check if the number of sequences is equal to the given one
        if (msa.getSeqs().size() != msa.getTaxa())
            throw new ParseException("Number of sequences is not equal to the given number.");

        //Check if MSA is correctly constructed
        Sequence first = (Sequence)msa.getSeqs().elementAt(0);
        for(int i=0;i<msa.getSeqs().size();i++)
        {
            Sequence seq = (Sequence)msa.getSeqs().elementAt(i);
            //Check if the sequences lengths are equal
            if (seq.getData().length() != msa.getLength())
                throw new ParseException("Sequence lengths are not equal to the given length.");
            //Replace match characters
            ReaderUtils.replaceMatch(seq, first);
        }


        logger.log(Level.INFO, "MSA read in PHYLIP format (" +
                "Taxa = " + msa.getSeqs().size() + ", " +
                "Length =  " + ((Sequence)msa.getSeqs().elementAt(0)).getData().length() +
                ").");

        return msa;
    }
}
