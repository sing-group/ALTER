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

package es.uvigo.ei.sing.alter.writer;

import java.util.LinkedHashSet;
import java.util.logging.Level;

import es.uvigo.ei.sing.alter.types.Sequence;

/**
 * Extends class PhylipWriter to adapt the output to PAML.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class PhylipPAMLWriter extends PhylipWriter
{
    /**
     * Class constructor. Calls superclass constructor.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param sequential Sequential output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public PhylipPAMLWriter(String os, boolean lowerCase, boolean sequential, boolean match, String logger)
    {
        super(os,lowerCase,sequential,match,logger);
    }

    /**
     * Writes the PHYLIP header, adapted to PAML. It writes an "I" in case
     * it is interleaved.
     * @param taxa Number of sequences.
     * @param length Sequences length.
     * @return PHYLIP header.
     */
    @Override
    protected String writeHeader(int taxa, int length)
    {
        if (sequential)
            return taxa + " " + length + nl;
        else
            return taxa + " " + length + " I" + nl;
    }

    /**
     * Returns the sequence identifier, considering PAML limitations. In case
     * the current identifier is repeated, it will be renamed.
     * @param seq Sequence containing the identifier.
     * @param ids Sequences copied so far.
     * @return Unique sequence identifier.
     */
    @Override
    protected String getId(Sequence seq, LinkedHashSet<String> ids)
    {
        String id = seq.getId();
        //If ID contains ",:#()$="
        if (id.contains("(") || id.contains(")")
                || id.contains(",") || id.contains(":")
                || id.contains("#") || id.contains("$")
                || id.contains("="))
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                + "\" contains one of \",:#()$=\". Characters removed.");
            id = id.replaceAll("[,:=()#$]","");
        }
        //If ID is longer than 30
        if (id.length() > 30)
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                    + "\" is longer than 30 characters. ID truncated to \""
                    + id.substring(0,30) + "\"");
            id = id.substring(0,30);
        }

        return WriterUtils.getUniqueId(ids, id, logger.getName());
    }
}
