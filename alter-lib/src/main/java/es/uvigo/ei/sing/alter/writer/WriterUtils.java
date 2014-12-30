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
import java.util.logging.Logger;

import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Nucleotide;
import es.uvigo.ei.sing.alter.types.Protein;
import es.uvigo.ei.sing.alter.types.Sequence;
import es.uvigo.ei.sing.alter.types.Type;

/**
 * Class with static methods commonly used by all writers.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */

public class WriterUtils
{
    /**
     * Returns a string with the number of spaces to align two strings.
     * @param cur Current string length.
     * @param longest Longest string length.
     * @return String with loges - cur spaces.
     */
    public static String align (int cur, int longest)
    {
        String toret = "";
        for(int i=cur;i<longest;i++)
        {
            toret = toret.concat(" ");
        }
        return toret;
    }

    /**
     * Infers the MSA type from the characters contained in its sequences.
     * If the sequences are made up of characters A, C, G, T or U
     * in an 85% or more, the MSA is considered to be nucleotide. This algorithm
     * has been tested to be correct in 97,3% of the cases.
     * @param msa Input MSA.
     * @return MSA type (Nucleotide or Protein).
     */
    public static Type inferType(MSA msa)
    {
        //Counters for total read and and nucleotide read
        int total = 0;
        int n = 0;

        //Para cada secuencia
        for (int i=0;i<msa.getSeqs().size();i++)
        {
            String seq = ((Sequence) msa.getSeqs().elementAt(i)).getData();
            for(int j=0;j<seq.length();j++)
            {
                if (seq.charAt(j) != '-' && seq.charAt(j) != '?')
                {
                    total++;
                    if (seq.charAt(j) == 'A' || seq.charAt(j) == 'C'
                            || seq.charAt(j) == 'G' || seq.charAt(j) == 'T'
                            || seq.charAt(j) == 'U')
                        n++;
                }
            }
        }

        //Return adequate type
        if ((100 * n) / total >= 85)
            return new Nucleotide();
        else
            return new Protein();
    }

    /**
     * Indicates if a MSA contains ambiguous IUPAC characters or not.
     * @param msa Input MSA.
     * @param type Input MSA type.
     * @return Boolean indicating if the MSA contains ambiguous characters.
     */
    public static boolean isIUPAC(MSA msa, Type type)
    {
        boolean toret = false;

        //If it is a protein
        if (type instanceof Protein)
            for(int i=0; i<msa.getSeqs().size();i++)
            {
                String data = ((Sequence) msa.getSeqs().elementAt(i)).getData();

                for(int j=0; j<data.length();j++)
                    if (data.charAt(j) == 'B' || data.charAt(j) == 'Z'
                            || data.charAt(j) == 'X')
                        toret = true;
            }
        //If it is a nucleotide
        else if (type instanceof Nucleotide)
            for(int i=0; i<msa.getSeqs().size();i++)
            {
                String data = ((Sequence) msa.getSeqs().elementAt(i)).getData();

                for(int j=0; j<data.length();j++)
                    if (data.charAt(j) == 'R' || data.charAt(j) == 'Y'
                        || data.charAt(j) == 'M' || data.charAt(j) == 'K'
                        || data.charAt(j) == 'W' || data.charAt(j) == 'S'
                        || data.charAt(j) == 'B' || data.charAt(j) == 'D'
                        || data.charAt(j) == 'H' || data.charAt(j) == 'V'
                        || data.charAt(j) == 'N')
                        toret = true;
            }
        
        return toret;
    }

    /**
     * Writes match characters in the current sequence where the character
     * in the same position of the first sequence is equal.
     * @param seq Current sequence.
     * @param firstSeq First sequence.
     * @return Sequence data with match characters.
     */
    public static String writeMatch(Sequence seq, Sequence firstSeq)
    {
        if(seq != firstSeq)
        {
            StringBuffer toret = new StringBuffer();
            String data = seq.getData();
            String first = firstSeq.getData();
            for (int i=0;i<data.length();i++)
                if (first.charAt(i) != '-' && first.charAt(i) != '?'
                        && data.charAt(i) == first.charAt(i))
                    toret.append(".");
                else
                    toret.append(data.charAt(i));
            return toret.toString();
        }
        else
            return seq.getData();
    }

    /**
     * Returns a unique sequence identifier, checking that it is not repeated
     * in the hash set given as a parameter, and renaming it if necessary.
     * @param ids Sequences identifiers.
     * @param id Current sequence identifier.
     * @param logger Name of the logger.
     * @return
     */
    public static String getUniqueId(LinkedHashSet<String> ids, String id, String logger)
    {
        String oldId = id;
        int cont = 0;
        while(ids.contains(id))
        {
            int contChars = String.valueOf(cont).length();
            id = id.substring(0, id.length() - contChars - 1) + "_" + cont;
            cont++;
        }
        if(!oldId.equals(id))
            Logger.getLogger(logger).log(Level.WARNING, "ID for sequence \"" + oldId + "\" renamed to \"" + id + "\".");
        return id;
    }
}
