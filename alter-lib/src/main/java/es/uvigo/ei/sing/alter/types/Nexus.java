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
 * MSA in NEXUS format.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class Nexus extends MSA implements Lengthable, Typeable, Taxable
{
    /**
     * Number of sequences in the MSA.
     */
    private int taxa;
    /**
     * Length of the sequences in the MSA.
     */
    private int length;
    /**
     * MSA type.
     */
    private Type type;

    /**
     * Class constructor.
     * @param taxa Number of sequences in the MSA.
     * @param length Length of the sequences in the MSA.
     * @param type Type of MSA.
     * @param seqs Sequences vector.
     */
    public Nexus(int taxa, int length, String type, Vector<NexusSequence> seqs)
    {
        super(seqs);
        this.taxa = taxa;
        this.length = length;

        if (type.equals("PROTEIN"))
            this.type = new Protein();
        else if (type.equals("NUCLEOTIDE"))
            this.type = new Nucleotide();
        else if (type.equals("DNA"))
            this.type = new DNA();
        else if (type.equals("RNA"))
            this.type = new RNA();
        else
            this.type = null;
    }

    /**
     * Class constructor. Gets as the type an object of class Type.
     * @param taxa Number of sequences in the MSA.
     * @param length Length of the sequences in the MSA.
     * @param type Type of MSA.
     * @param seqs Sequences vector.
     */
    public Nexus(int taxa, int length, Type type, Vector<NexusSequence> seqs)
    {
        super(seqs);
        this.taxa = taxa;
        this.length = length;
        this.type = type;
    }

    /**
     * Returns the number of sequences in the MSA.
     * @return Number of sequences.
     */
    public int getTaxa()
    {
        return taxa;
    }

    /**
     * Returns the MSA type.
     * @return MSA type.
     */
    public Type getType()
    {
        return type;
    }

    /**
     * Returns the length of the sequences in the MSA.
     * @return Length of the sequences in the MSA.
     */
    public int getLength()
    {
        return length;
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
        Vector<NexusSequence> newSeqs = new Vector<NexusSequence>();
        newSeqs.add((NexusSequence) seqs.firstElement());

        for(int i=1;i<seqs.size();i++)
        {
            NexusSequence seq = (NexusSequence) seqs.elementAt(i);
            Sequence unique = isUnique(seq, newSeqs, gapsAsMissing, countMissing, limit);
            if (unique != null)
                logger.log(Level.INFO, "Sequence \"" + seq.getId() + "\" is equal" +
                        " to sequence \"" + unique.getId() + "\". Sequence removed.");
            else
                newSeqs.add(seq);
        }

        return new Nexus(newSeqs.size(), length, type, newSeqs);
    }
}
