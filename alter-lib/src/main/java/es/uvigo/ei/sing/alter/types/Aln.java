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


package es.uvigo.ei.sing.alter.types;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MSA in ALN format.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class Aln extends MSA
{
    /**
     * Class constructor.
     * @param seqs Vector containing aligned sequences.
     */
    public Aln(Vector<AlnSequence> seqs)
    {
        super(seqs);
    }

    /**
     * Collapse sequences to haplotypes. It creates a new MSA object with the
     * result sequences and then returns it.
     * @param gapsAsMissing Treat gaps as missing data when collapsing.
     * @param countMissing Count missing data as differences when collapsing.
     * @param limit Connection limit (sequences differing at <= l sites will be collapsed).
     * @param log Name of the logger to be instantiated.
     * @return New MSA containing the result sequences.
     */
    @Override
    public MSA collapse(boolean gapsAsMissing, boolean countMissing, int limit, String log)
    {
        Logger logger = Logger.getLogger(log);
        Vector<AlnSequence> newSeqs = new Vector<AlnSequence>();
        newSeqs.add((AlnSequence) seqs.firstElement());

        for(int i=1;i<seqs.size();i++)
        {
            AlnSequence seq = (AlnSequence) seqs.elementAt(i);
            Sequence unique = isUnique(seq, newSeqs, gapsAsMissing, countMissing, limit);
            if (unique != null)
                logger.log(Level.INFO, "Sequence \"" + seq.getId() + "\" is equal" +
                        " to sequence \"" + unique.getId() + "\". Sequence removed.");
            else
                newSeqs.add(seq);
        }

        return new Aln(newSeqs);
    }
}
