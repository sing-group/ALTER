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
import es.uvigo.ei.sing.alter.types.Sequence;

/** Implements interface Writer for the ALN format.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */
public class AlnWriter implements Writer
{
    /**
     * New line characters according to the given output OS.
     */
    String nl;
    /**
     * Lowercase output.
     */
    boolean lowerCase;
    /**
     * Output residue numbers.
     */
    boolean resNumbers;
    /**
     * Output match characters.
     */
    boolean match;
    /**
     * Logger to register information messages.
     */
    Logger logger;

    /**
     * Class constructor.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param resNumbers Output residue numbers.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public AlnWriter(String os, boolean lowerCase, boolean resNumbers, boolean match, String logger)
    {
        if (os.equals("macos"))
            nl = "\r";
        else if (os.equals("linux"))
            nl = "\n";
        else
            nl = "\r\n";
        this.lowerCase = lowerCase;
        this.resNumbers = resNumbers;
        this.match = match;
        this.logger = Logger.getLogger(logger);
    }

    /**
     * Writes a MSA in ALN format.
     * @param msa Input MSA.
     * @return MSA in ALN format.
     */
    public String write(MSA msa)
    {
        //Output
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);

        //Copy sequences and calculate longest ID in order to align
        String[] data = new String[msa.getSeqs().size()];
        LinkedHashSet<String> id = new LinkedHashSet<String>(msa.getSeqs().size());
        int[] residues = new int[msa.getSeqs().size()];
        int longestId = 0;
        for (int i = 0; i < msa.getSeqs().size(); i++)
        {
            //Copy data
            Sequence seq = (Sequence) msa.getSeqs().elementAt(i);
            data[i] = getData(seq, (Sequence) msa.getSeqs().firstElement());
            String uid = getId(seq, id);
            id.add(uid);
            //Update longest ID
            if (uid.length() > longestId)
                longestId = uid.length();
            residues[i] = 0;
        }

        //Write header
        outb.append(writeHeader());

        //Write sequences
        while (!data[0].isEmpty())
        {
            String[] forConsensus = new String[data.length];

            int i = 0;
            for (String uid:id)
            {
                outb.append(writeLine(uid, longestId, data, forConsensus, residues, i));
                i++;
            }
            //Write consensus line
            outb.append(consensusLine(forConsensus, longestId));
            outb.append(nl);
        }
        logger.log(Level.INFO, "MSA successfully converted to ALN format!");
        return outb.toString();
    }

    /**
     * Returns the sequence identifier. In case the current identifier is
     * repeated, it will be renamed.
     * @param seq Sequence containing the identifier.
     * @param ids Sequences copied so far.
     * @return Unique sequence identifier.
     */
    protected String getId(Sequence seq, LinkedHashSet<String> ids)
    {
        String id = seq.getId();
        if (id.contains(" ") || id.contains("\t"))
        {
            logger.log(Level.WARNING, "ID for sequence \"" + id + "\" contains blanks. Blanks replaced by \"_\".");
            id = id.replaceAll("[\t ]", "_");
        }

        return WriterUtils.getUniqueId(ids, id, logger.getName());
    }

    /**
     * Returns the sequence data, using lowercase or match characters if
     * necessary.
     * @param seq Sequence containing the data.
     * @param first First sequence in the MSA.
     * @return Sequence data.
     */
    protected String getData(Sequence seq, Sequence first)
    {
        String data;
        if (match)
            data = WriterUtils.writeMatch(seq, first);
        else
            data = seq.getData();
        if (lowerCase)
            return data.toLowerCase();
        else
            return data;
    }

    /**
     * Write the ALN header.
     * @return ALN header.
     */
    protected String writeHeader()
    {
        return "CLUSTAL W (1.8) multiple sequence alignment (ALTER 1.3.3)" + nl + nl + nl;
    }

    /**
     * Writes a line corresponding to a sequence of the MSA.
     * @param id Sequence identifier.
     * @param longestId Longest identifier length (needed to align).
     * @param data Sequences data.
     * @param forConsensus Data to calculate consensus line.
     * @param Residues written so far for every sequence.
     * @param index Index of the current sequence in the array (needed to
     * update data).
     * @return Line in ALN format.
     */
    protected String writeLine(String id, int longestId, String data[],
                            String forConsensus[], int residues[], int index)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        outb.append(id + WriterUtils.align(id.length(), longestId)
                        + WriterUtils.align(longestId, 10) + "      ");

        //Write a line if possible
        if (data[index].length() > 60)
        {
            forConsensus[index] = data[index].substring(0, 60);
            outb.append(forConsensus[index]);
            if (resNumbers)
            {
                int res = countResidues(forConsensus[index]);
                if (res != 0)
                {
                    residues[index] += res;
                    outb.append(" " + residues[index]);
                }
            }
            data[index] = data[index].substring(60);
        }
        else
        {
            forConsensus[index] = data[index];
            outb.append(forConsensus[index]);
            if (resNumbers)
            {
                int res = countResidues(forConsensus[index]);
                if (res != 0)
                {
                    residues[index] += res;
                    outb.append(" " + residues[index]);
                }
            }
            data[index] = "";
        }
        outb.append(nl);
        return outb.toString();
    }

    /**
     * Counts residues (characters that are not "?" or "-") in the input string.
     * @param s Input string.
     * @return Number of residues in the string.
     */
    protected int countResidues(String s)
    {
        int toret = 0;
        for(int i=0;i<s.length();i++)
            if ( s.charAt(i) != '-' && s.charAt(i) != '?')
                toret++;
        return toret;
    }

     /**
     * Calculates the consesus line of a block of sequences.
     * @param data Block of sequences.
     * @return Consensus line for the given sequences.
     */
    protected String consensusLine(String[] data, int longestId)
    {
        //String to return
        String out = "";

        out += WriterUtils.align(0, longestId) + WriterUtils.align(longestId, 10) + "      ";

        //For each character
        for (int i = 0; i < data[0].length(); i++)
        {
            boolean fully = true;
            boolean nores = false;
            char residue = data[0].charAt(i);
            //Build string with the characters in a column
            String col = "";
            for (int j = 0; j < data.length; j++)
            {
                char c = data[j].charAt(i);
                if (c == '.')
                    c = residue;
                //If there is no residue break the loop
                if (c == '-' || c == '?')
                {
                    nores = true;
                    break;
                }
                //If the current residue is different from the first one flag it
                if (c != residue)
                    fully = false;
                col += c;
            }

            //If there is no residue
            if (nores)
                out += ' ';
            //If the residue is fully preserved
            else if (fully)
                out += '*';
            //Check strong groups
            else if (col.replaceAll("[STA]", "").isEmpty())
                out += ':';
            else if (col.replaceAll("[NEQK]", "").isEmpty())
                out += ':';
            else if (col.replaceAll("[NHQK]", "").isEmpty())
                out += ':';
            else if (col.replaceAll("[NDEQ]", "").isEmpty())
                out += ':';
            else if (col.replaceAll("[QHRK]", "").isEmpty())
                out += ':';
            else if (col.replaceAll("[MILV]", "").isEmpty())
                out += ':';
            else if (col.replaceAll("[MILF]", "").isEmpty())
                out += ':';
            else if (col.replaceAll("[HY]", "").isEmpty())
                out += ':';
            else if (col.replaceAll("[FYW]", "").isEmpty())
                out += ':';
            //Check weak groups
            else if (col.replaceAll("[CSA]", "").isEmpty())
                out += '.';
            else if (col.replaceAll("[ATV]", "").isEmpty())
                out += '.';
            else if (col.replaceAll("[SAG]", "").isEmpty())
                out += '.';
            else if (col.replaceAll("[STNK]", "").isEmpty())
                out += '.';
            else if (col.replaceAll("[STPA]", "").isEmpty())
                out += '.';
            else if (col.replaceAll("[SGND]", "").isEmpty())
                out += '.';
            else if (col.replaceAll("[SNDEQK]", "").isEmpty())
                out += '.';
            else if (col.replaceAll("[NDEQHK]", "").isEmpty())
                out += '.';
            else if (col.replaceAll("[NEQHRK]", "").isEmpty())
                out += '.';
            else if (col.replaceAll("[FVLIM]", "").isEmpty())
                out += '.';
            else if (col.replaceAll("[HFY]", "").isEmpty())
                out += '.';
            //If none is preserved
            else
                out += ' ';
        }
        out += nl;

        return out;
    }
}
