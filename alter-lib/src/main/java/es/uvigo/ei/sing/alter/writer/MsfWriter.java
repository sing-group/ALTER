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

import es.uvigo.ei.sing.alter.types.Lengthable;
import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Nucleotide;
import es.uvigo.ei.sing.alter.types.Protein;
import es.uvigo.ei.sing.alter.types.Sequence;
import es.uvigo.ei.sing.alter.types.Type;
import es.uvigo.ei.sing.alter.types.Typeable;
import es.uvigo.ei.sing.alter.types.Weightable;

/**
 * Implements interface Writer for the MSF format.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */

public class MsfWriter implements Writer
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
     * Logger to register information messages.
     */
    Logger logger;

    /**
     * Class constructor.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param logger Logger name.
     */
    public MsfWriter(String os, boolean lowerCase, String logger)
    {
        if (os.equals("macos"))
            nl = "\r";
        else if (os.equals("linux"))
            nl = "\n";
        else
            nl = "\r\n";
        this.lowerCase = lowerCase;
        this.logger = Logger.getLogger(logger);
    }

    /**
     * Writes a MSA in MSF format.
     * @param msa Input MSA.
     * @return MSA in MSF format.
     */
    public String write(MSA msa)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);

        //Copy sequence data and find longest strings
        String[] data = new String[msa.getSeqs().size()];
        LinkedHashSet<String> id = new LinkedHashSet<String>(msa.getSeqs().size());
        int[] length = new int[msa.getSeqs().size()];
        int[] check = new int[msa.getSeqs().size()];
        float[] weight = new float[msa.getSeqs().size()];
        int longestId = 0;
        int longestLength = 0;
        int longestCheck = 0;
        int longestWeight = 0;
        for (int i = 0; i < msa.getSeqs().size(); i++)
        {
            Sequence seq = (Sequence)msa.getSeqs().elementAt(i);
            data[i] = getData(seq);
            String uid = getId(seq,id);
            id.add(uid);
            length[i] = getLength(seq);
            check[i] = getChecksum(data[i]);
            weight[i] = getWeight(seq);

            if (uid.length() > longestId)
                longestId = uid.length();
            if (String.valueOf(length[i]).length() > longestLength)
                longestLength = String.valueOf(length[i]).length();
            if ((String.valueOf(check[i])).length() > longestCheck)
                longestCheck = (String.valueOf(check[i])).length();
            if (String.valueOf(weight[i]).length() > longestWeight)
                longestWeight = String.valueOf(weight[i]).length();
        }

        //Write header
        int msaLength = getLength(msa);
        int msaCheck = getChecksum(check);
        char msaType = getType(msa);
        outb.append(writeHeader(msaLength, msaType, msaCheck));

        //Write sequences information
        int i=0;
        for (String uid:id)
        {
            outb.append(writeInfo(uid, longestId, length[i], longestLength,
                    check[i], longestCheck, weight[i], longestWeight));
            i++;
        }

        outb.append(nl + "//" + nl + nl + nl + nl);

        while (!data[0].isEmpty())
        {
            i = 0;
            for (String uid:id)
            {
                outb.append(writeLine(uid, longestId, data, i));
                i++;
            }
                
            outb.append(nl);
        }
        logger.log(Level.INFO, "MSA successfully converted to MSF format!");
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
        //If ID contains spaces
        if (id.length() > 10 && (id.contains(" ") || id.contains("\t")))
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                    + "\" is longer than 10 characters and contains blanks. Blanks replaced by \"_\".");
            id = id.replaceAll("[\t ]","_");
        }
        
        return WriterUtils.getUniqueId(ids, id, logger.getName());
    }

    /**
     * Returns the sequence data, using lowercase characters if necessary.
     * @param seq Sequence containing the data.
     * @param first First sequence in the MSA.
     * @return Sequence data.
     */
    protected String getData(Sequence seq)
    {
        if (lowerCase)
            return seq.getData().replace('-','.').toLowerCase();
        else
            return seq.getData().replace('-', '.');
    }

    /**
     * Returns the length of the sequence.
     * @param seq Sequence to get the length of.
     * @return Sequence length.
     */
    protected int getLength(Sequence seq)
    {
        if (seq instanceof Lengthable)
            return ((Lengthable) seq).getLength();
        else
            return seq.getData().length();
    }

    /**
     * Returns the length of the sequences in the MSA.
     * @param msa MSA to get the length of.
     * @return Length of the sequences in the MSA.
     */
    protected int getLength(MSA msa)
    {
        if (msa instanceof Lengthable)
            return ((Lengthable) msa).getLength();
        else
            return ((Sequence) msa.getSeqs().firstElement()).getData().length();
    }

    /**
     * Returns the sequence weight.
     * @param seq Sequence to get the weight of.
     * @return Sequence weight.
     */
    protected float getWeight(Sequence seq)
    {
        if (seq instanceof Weightable)
            return ((Weightable) seq).getWeight();
        else
            return 1.0f;
    }

    /**
     * Calculates the MSA checksum using the checksums of the sequences in the MSA.
     * @param check Checksums of the sequences in the MSA.
     * @return MSA checksum.
     */
    protected int getChecksum (int[] check)
    {
        int checksum = 0;
        for (int i=0;i<check.length;i++)
        {
            checksum += check[i];
            checksum %= 10000;
        }
        return checksum;
    }

    /**
     * Calculates the checksum of a sequence.
     * @param s Sequence to calculate the checksum of.
     * @return Sequence checksum.
     */
    protected int getChecksum (String s)
    {
        int index = 0;
        int checksum = 0;
        for (int i=0;i<s.length();i++)
        {
            char c = s.charAt(i);
            index++;
            checksum += index * (int)c;
            if (index == 57)
                index = 0;

        }
        return checksum % 10000;
    }

    /**
     * Returns a string with the MSA type.
     * @param msa MSA to get the type of.
     * @return MSA type.
     */
    protected char getType(MSA msa)
    {
        Type type = null;
        if (msa instanceof Typeable)
            type = ((Typeable) msa).getType();
        if (type == null)
        {
            type = WriterUtils.inferType(msa);
            if (type instanceof Nucleotide)
                logger.log(Level.INFO,"Nucleotide MSA type inferred.");
            else
                logger.log(Level.INFO, "Protein MSA type inferred.");
        }

        if (type instanceof Protein)
            return 'P';
        else if (type instanceof Nucleotide)
            return 'N';
        else
            return 'X';
    }

    /**
     * Writes the MSF header.
     * @param checksum MSA checksum.
     * @param length Sequences length.
     * @param type MSA type.
     * @return MSF header.
     */
    protected String writeHeader(int length, char type, int checksum)
    {
        String out = "";
        out += "PileUp" + nl + nl + nl + nl;
        out += "   MSF:  " + length + "  Type: " + type + "    Check:  "
                + checksum + "   .. " + nl + nl;
        return out;
    }

    /**
     * Writes information of a sequence, to be placed in the information block
     * after the MSF header.
     * @param id Sequence identifier.
     * @param longestId Longest identifier length.
     * @param length Sequence length.
     * @param longestLength Longest length number length.
     * @param check Sequence checksum.
     * @param longestCheck Longest checksum length.
     * @param weight Sequence weight.
     * @param longestWeight Longest weight length.
     * @return Sequence information.
     */
    protected String writeInfo(String id, int longestId, int length, int longestLength,
                                int check, int longestCheck, float weight, int longestWeight)
    {
        return " Name: " + id + " oo" + WriterUtils.align(id.length(),longestId)
            + "  Len:  " + WriterUtils.align(String.valueOf(length).length(),longestLength) + length
            + "  Check:  " + WriterUtils.align((String.valueOf(check)).length(),longestCheck) + check
            + "  Weight:  " + WriterUtils.align(String.valueOf(weight).length(),longestWeight) + weight
            + nl;
    }

    /**
     * Writes a line corresponding to a sequence of the MSA.
     * @param id Sequence identifier.
     * @param longestId Longest identifier length (needed to align).
     * @param data Sequences data.
     * @param index Index of the current sequence in the array (needed to
     * update data).
     * @return Line in MSF format.
     */
    protected String writeLine(String id, int longestId, String[] data, int index)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        outb.append(id + WriterUtils.align(id.length(),longestId));
        outb.append(WriterUtils.align(longestId, 10) + "      ");

        //Write a row
        for (int j = 0; j < 6; j++)
        {
            //If there are enough characters to make a 10 characters block
            if (data[index].length() > 10)
            {
                outb.append(data[index].substring(0, 10) + " ");
                data[index] = data[index].substring(10);
            }
            else
            {
                outb.append(data[index]);
                data[index] = "";
                break;
            }
        }
        outb.append(nl);
        return outb.toString();
    }
}
