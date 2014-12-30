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
 * Extends class NexusWriter to adapt the output to TCS.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class NexusTCSWriter extends NexusWriter
{
    /**
     * Class constructor. Calls the constructor in the superclass.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param sequential Sequential output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public NexusTCSWriter(String os, boolean lowerCase, boolean sequential, boolean match, String logger)
    {
        super(os, lowerCase, sequential, match, logger);
    }

    /**
     * Writes a MSA in NEXUS format, adapted to TCS. It checks that the MSA is
     * not a proteins MSA and that it not contains ambiguous caracters. It then
     * calls the superclass method.
     * @param msa Input MSA.
     * @return MSA in NEXUS format.
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
        if (type instanceof Protein)
        {
            logger.log(Level.WARNING,"MSA is a protein MSA. " +
                    "It will not be processed by TCS (only DNA is processed).");
        }
        if (WriterUtils.isIUPAC(msa,type))
        {
            logger.log(Level.WARNING,"MSA contains IUPAC ambiguous characters. " +
                    "They will be treated as missing data by TCS.");
        }
        return super.write(msa);
    }
}
