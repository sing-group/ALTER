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

import java.util.logging.Level;

import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Nucleotide;
import es.uvigo.ei.sing.alter.types.Type;
import es.uvigo.ei.sing.alter.types.Typeable;

/**
 * Extends class PhylipWriter to adapt the output to ProtTest.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class PhylipProtTestWriter extends PhylipWriter
{
    /**
     * Class constructor. Calls superclass constructor.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param sequential Sequential output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public PhylipProtTestWriter(String os, boolean lowerCase, boolean sequential, boolean match, String logger)
    {
        super(os,lowerCase,sequential, match, logger);
    }

    /**
     * Writes a MSA in PHYLIP format adapted to ProtTest. It checks that it is
     * a proteins MSA and calls the superclass method.
     * @param msa Input MSA.
     * @return MSA in PHYLIP format.
     */
    @Override
    public String write(MSA msa)
    {
        Type type = null;
        if (msa instanceof Typeable)
            type = ((Typeable) msa).getType();
        if (type == null)
        {
            type = WriterUtils.inferType(msa);
        }
        if (type instanceof Nucleotide)
        {
            logger.log(Level.WARNING,"MSA is a nucleotides MSA. " +
                    "It will not be processed by ProtTest (only protein is processed).");
        }
        return super.write(msa);
    }

    /**
     * Writes a complete sequence, adapted to ProtTest. It writes the whole sequence
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
