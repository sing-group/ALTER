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

/**
 * Abstract class representing a MSA.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public abstract class MSA
{
    /**
     * Aligned sequences vector.
     */
    protected Vector seqs;

    /**
     * Class constructor.
     * @param seqs Aligned sequences vector.
     */
    public MSA(Vector seqs)
    {
        this.seqs = seqs;
    }

    /**
     * Returns the aligned sequences.
     * @return Aligned sequences vector.
     */
    public Vector getSeqs()
    {
        return seqs;
    }

    /**
     * Adds a sequence to the sequences vector.
     * @param seq Sequence to be added to the vector.
     */
    public void addSeq(Sequence seq)
    {
        seqs.add(seq);
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
    public abstract MSA collapse(boolean gapsAsMissing, boolean countMissing, int limit, String logger);

    /**
     * Checks if a sequence is unique in the vector till his position in it,
     * according to the given parameters. It compares all previous positions
     * in the vector to the given one, checking for equivalent sequences.
     * @param seq Sequence be checked.
     * @param seqs Sequences vector.
     * @param gapsAsMissing Treat gaps as missing data when collapsing.
     * @param countMissing Count missing data as differences when collapsing.
     * @param limit Connection limit (sequences differing at <= l sites will be collapsed).
     * @return La secuencia a la que es equivalente la secuencia actual. Null en caso de que no exista ninguna secuencia equivalente.
     */
    protected Sequence isUnique(Sequence seq, Vector seqs, boolean gapsAsMissing, boolean countMissing, int limit)
    {
        //Sequence data
        String data = seq.getData();

        //Sequence to compare to (start with the first)
        Sequence uniqueSeq = (Sequence) seqs.firstElement();
        //Counter
        int cont = 0;
        while(cont < seqs.size() && seq != uniqueSeq)
        {
            //Data to compare to
            String unique = uniqueSeq.getData();
            //Number of differences
            int diff = 0;
            int miss = 0;
            int gaps = 0;
            //For each site
            for (int i=0; i<data.length();i++)
            {
                char c1 = data.charAt(i);
                char c2 = unique.charAt(i);
                if(c1 == c2)
                    continue;
                else if (c1 == '?' || c2 == '?')
                    miss++;
                else if (c1 == '-' || c2 == '-')
                    gaps++;
                else
                    diff++;
            }

            if (gapsAsMissing)
                miss += gaps;
            else
                diff += gaps;

            if (countMissing)
                diff += miss;

            if (diff <= limit)
                return uniqueSeq;
            cont++;
            if (cont < seqs.size())
                uniqueSeq = (Sequence) seqs.elementAt(cont);
        }

        return null;
    }
}
