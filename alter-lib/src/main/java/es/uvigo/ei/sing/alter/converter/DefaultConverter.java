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

package es.uvigo.ei.sing.alter.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.ei.sing.alter.parser.ParseException;
import es.uvigo.ei.sing.alter.reader.Reader;
import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.writer.Writer;

/**
 * Implements the default converter.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */
public class DefaultConverter implements Converter
{
    /**
     * Collapse sequences to haplotypes.
     */
    private boolean collapse;
    /**
     * Treat gaps as missing characters when collapsing.
     */
    private boolean gapsAsMissing;
    /**
     * Count missing characters as differences when collapsing.
     */
    private boolean countMissing;
    /**
     * Connection limit (sequences differing at <= l sites will be collapsed) (default is l=0).
     */
    private int limit;
    /**
     * Logger to register information messages.
     */
    private Logger logger;
    /**
     * Reader to parse the input MSA.
     */
    private Reader reader;
    /**
     * Writer to output the converted MSA.
     */
    private Writer writer;

    /**
     * Class constructor
     * @param reader Reader to parse the input MSA.
     * @param writer Writer to output the converted MSA.
     * @param collapse Collapse sequences to haplotypes.
     * @param gapsAsMissing Treat gaps as missing characters when collapsing.
     * @param countMissing Count missing characters as differences when collapsing.
     * @param limit Connection limit (sequences differing at <= l sites will be collapsed) (default is l=0).
     * @param logger Name of the logger to instantiate.
     */
    public DefaultConverter(Reader reader, Writer writer, boolean collapse, boolean gapsAsMissing, boolean countMissing, int limit, String logger)
    {
        this.reader = reader;
        this.writer = writer;
        this.collapse = collapse;
        this.gapsAsMissing = gapsAsMissing;
        this.countMissing = countMissing;
        this.limit = limit;
        this.logger = Logger.getLogger(logger);
    }

    /**
     * Takes as input a string and returns a converted string.
     * @param in Input string.
     * @return Converted string.
     * @throws ParseException If an error occurs while parsing the input string.
     */
    public String convert(String in) throws ParseException
    {
        MSA msa = reader.read(in);
        if (collapse)
        {
            logger.log(Level.INFO,"*** haplotype collapse begin ***");
            msa = msa.collapse(gapsAsMissing, countMissing, limit, logger.getName());
            logger.log(Level.INFO,"*** haplotype collapse end ***");
        }
        return writer.write(msa);
    }
}
