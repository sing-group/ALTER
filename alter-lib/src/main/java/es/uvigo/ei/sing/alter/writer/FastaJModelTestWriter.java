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
import es.uvigo.ei.sing.alter.types.Protein;
import es.uvigo.ei.sing.alter.types.Type;
import es.uvigo.ei.sing.alter.types.Typeable;

/**
 * Extends class FastaWriter to adapt the output to jModelTest.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class FastaJModelTestWriter extends FastaWriter
{
    /**
     * Class constructor. Calls the superclass constructor.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public FastaJModelTestWriter(String os, boolean lowerCase, boolean match, String logger)
    {
        super(os,lowerCase,match,logger);
    }

    /**
     * Writes a MSA in FASTA format, adapted for jModelTest. It checks
     * that the MSA is not a protein MSA and that it contains 4 or more sequences.
     * It then calls the method in the superclass.
     * @param msa Input MSA.
     * @return String with the MSA in FASTA format.
     */
    @Override
    public String write(MSA msa)
    {
        if (msa.getSeqs().size() < 4)
            logger.log(Level.WARNING, "MSA contains less than 4 sequences. " +
                    "It will not be processed by jModelTest.");
        Type type = null;
        if (msa instanceof Typeable)
            type = ((Typeable) msa).getType();
        if (type == null)
        {
            type = WriterUtils.inferType(msa);
        }
        if (type instanceof Protein)
        {
            logger.log(Level.WARNING,"MSA is an amino acids MSA. " +
                    "It will not be processed by jModelTest (only DNA is processed).");
        }
        return super.write(msa);
    }
}
