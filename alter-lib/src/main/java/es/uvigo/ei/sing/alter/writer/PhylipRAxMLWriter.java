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

import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Sequence;

/**
 * Extends class PhylipWriter to adapt the output to RAxML.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class PhylipRAxMLWriter extends PhylipWriter
{
    /**
     * Class constructor. Calls superclass constructor.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param sequential Sequential output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public PhylipRAxMLWriter(String os, boolean lowerCase, boolean sequential, boolean match, String logger)
    {
        super(os,lowerCase,sequential,match,logger);
    }

    /**
     * Writes a MSA in PHYLIP format, adapted for RAxML. It checks
     * that the MSA contains 4 or more sequences.
     * It then calls the method in the superclass.
     * @param msa Input MSA.
     * @return String with the MSA in PHYLIP format.
     */
    @Override
    public String write(MSA msa)
    {
        if (msa.getSeqs().size() < 4)
            logger.log(Level.WARNING, "MSA contains less than 4 sequences. " +
                    "It will not be processed by RAxML.");
        return super.write(msa);
    }

    /**
     * Returns the sequence identifier, considering RAxML limitations. In case
     * the current identifier is repeated, it will be renamed.
     * @param seq Sequence containing the identifier.
     * @param ids Sequences copied so far.
     * @return Unique sequence identifier.
     */
    @Override
    protected String getId(Sequence seq, LinkedHashSet<String> ids)
    {
        String id = seq.getId();
        //If ID contains "(),:;[]"
        if (id.contains("(") || id.contains(")")
                || id.contains(",") || id.contains(":")
                || id.contains(";") || id.contains("[")
                || id.contains("]"))
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                + "\" contains one of \"()[],:;\". Characters removed.");
            id = id.replaceAll("[,:;()\\[\\]]","");
        }
        //If ID contains spaces
        if (id.contains(" ") || id.contains("\t"))
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                + "\" contains blanks. Blanks replaced by \"_\".");
            id = id.replaceAll("[\t ]","_");
        }
        //If ID is longer than 256
        if (id.length() > 256)
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                    + "\" is longer than 50 characters. ID truncated to \""
                    + id.substring(0,256) + "\"");
            id = id.substring(0,256);
        }
        
        return WriterUtils.getUniqueId(ids, id, logger.getName());
    }

    /**
     * Writes a complete sequence, adapted to RAxML. It writes the whole sequence
     * in a single line.
     * @param id Sequence identifier.
     * @param longestId Longest identifier length.
     * @param data Sequence data.
     * @return Formatted sequence.
     */
    @Override
    protected String writeSequence(String id, int longestId, String data)
    {
        //Write ID
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        outb.append(id + WriterUtils.align(id.length(), longestId)
                            + WriterUtils.align(longestId, 10) + "  " + data + nl);
        return outb.toString();
    }
}
