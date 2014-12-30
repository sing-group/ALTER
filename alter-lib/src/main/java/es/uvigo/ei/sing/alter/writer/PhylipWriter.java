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
import es.uvigo.ei.sing.alter.types.Sequence;
import es.uvigo.ei.sing.alter.types.Taxable;

/**
 * Implements interface Writer for the PHYLIP format.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */

public class PhylipWriter implements Writer
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
     * Sequential output.
     */
    boolean sequential;
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
     * @param sequential Sequential output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public PhylipWriter(String os, boolean lowerCase, boolean sequential, boolean match, String logger)
    {
        if (os.equals("macos"))
            nl = "\r";
        else if (os.equals("linux"))
            nl = "\n";
        else
            nl = "\r\n";
        this.lowerCase = lowerCase;
        this.sequential = sequential;
        this.match = match;
        this.logger = Logger.getLogger(logger);
    }

    /**
     * Writes a MSA in PHYLIP format.
     * @param msa Input MSA.
     * @return MSA in PHYLIP format.
     */
    public String write(MSA msa)
    {
        //Output
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);
        
        //Instantiate taxa and length
        int taxa = getTaxa(msa);
        int length = getLength(msa);

        outb.append(writeHeader(taxa, length));

        //Copy sequences
        String[] data = new String[msa.getSeqs().size()];
        LinkedHashSet<String> id = new LinkedHashSet<String>(msa.getSeqs().size());
        int longestId = 0;
        for (int i = 0; i < msa.getSeqs().size(); i++)
        {
            Sequence seq = (Sequence) msa.getSeqs().elementAt(i);
            //Copy data
            data[i] = getData(seq, (Sequence) msa.getSeqs().firstElement());
            String uid = getId(seq,id);
            id.add(uid);
            //Update longest ID
            if(uid.length() > longestId)
                longestId = uid.length();
        }
        if (sequential)
            outb.append(sequential(id, longestId, data));
        else
            outb.append(interleaved(id, longestId, data));

        logger.log(Level.INFO, "MSA successfully converted to PHYLIP format!");
        return outb.toString();
    }

    /**
     * Writes sequences of a MSA in interleaved PHYLIP.
     * @param id Sequences identifiers.
     * @param longestId Longest identifier length (needed to align).
     * @param data Sequences data.
     * @return Sequences in interleaved PHYLIP.
     */
    private String interleaved(LinkedHashSet<String> id, int longestId, String[] data)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);

        //Write first sequences with identifier
        int i = 0;
        for (String uid:id)
        {
            outb.append(writeLine(uid, longestId, data, i));
            i++;
        }

        outb.append(nl);

        //Write the rest of the sequences
        while (!data[0].isEmpty())
        {
            i = 0;
            for (String uid:id)
            {
                outb.append(writeLine(longestId, data, i));
                i++;
            }
            outb.append(nl);
        }
        return outb.toString();
    }

    /**
     * Writes sequences of a MSA in sequential PHYLIP.
     * @param id Sequences identifiers.
     * @param longestId Longest identifier length (needed to align).
     * @param data Sequences data.
     * @return Sequences in sequential PHYLIP.
     */
    private String sequential(LinkedHashSet<String> id, int longestId, String[] data)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);

        int i = 0;
        for (String uid:id)
        {
            outb.append(writeSequence(uid, longestId, data[i]));
            i++;
        }
        return outb.toString();
    }

    /**
     * Returns the number of sequences in the MSA.
     * @param msa MSA to get the number of sequences of.
     * @return Number of sequences.
     */
    protected int getTaxa(MSA msa)
    {
        if (msa instanceof Taxable)
            return ((Taxable) msa).getTaxa();
        else
            return msa.getSeqs().size();
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
     * Returns the sequence identifier. In case the current identifier is
     * repeated, it will be renamed.
     * @param seq Sequence containing the identifier.
     * @param ids Sequences copied so far.
     * @return Unique sequence identifier.
     */
    protected String getId(Sequence seq, LinkedHashSet<String> ids)
    {
        String id = seq.getId();
        if (id.length() > 10)
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                    + "\" is longer than 10 characters. ID truncated to \""
                    + id.substring(0,10) + "\"");
            id = id.substring(0,10);
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
     * Writes the PHYLIP header.
     * @param taxa Number of sequences.
     * @param length Sequences length.
     * @return PHYLIP header.
     */
    protected String writeHeader(int taxa, int length)
    {
        return taxa + " " + length + nl;
    }

    /**
     * Writes a line corresponding to a sequence of the MSA.
     * @param id Sequence identifier.
     * @param longestId Longest identifier length (needed to align).
     * @param data Sequences data.
     * @param index Index of the current sequence in the array (needed to
     * update data).
     * @return Line in PHYLIP format.
     */
    protected String writeLine(String id, int longestId, String[] data, int index)
    {
        //Write ID
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        outb.append(id + WriterUtils.align(id.length(), longestId) +
                WriterUtils.align(longestId, 10) + "  ");

        //Write a row of 10 character blocks
        for (int j = 0; j < 5; j++)
        {
            //If there are enough characters to make a 10 character block
            if (data[index].length() > 10)
            {

                outb.append(data[index].substring(0, 10) + " ");
                data[index] = data[index].substring(10);
            }
            else
            {
                outb.append(data[index]);
                data[index] = "";
            }
            //If there are no more characters to write exit the loop
            if (data[index].isEmpty())
                break;
        }
        outb.append(nl);
        return outb.toString();
    }

    /**
     * Writes a line corresponding to a sequence of the MSA. This method is
     * used in interleaved PHYLIP when the identifier is omitted before the data.
     * @param longestId Longest identifier length (needed to align).
     * @param data Sequences data.
     * @param index Index of the current sequence in the array (needed to
     * update data).
     * @return Line in PHYLIP format.
     */
    protected String writeLine(int longestId, String[] data, int index)
    {
        //Write spaces to align
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        outb.append(WriterUtils.align(0, longestId)
                        + WriterUtils.align(longestId, 10) + "  ");
        //Write a row of 10 character blocks
        for (int j = 0; j < 5; j++)
        {
            //If there are enough chracters to make a 10 character block
            if (data[index].length() > 10)
            {
                outb.append(data[index].substring(0, 10) + " ");
                data[index] = data[index].substring(10);
            }
            else
            {
                outb.append(data[index]);
                data[index] = "";
            }

            //If there are no more characters to write, exit the loop.
            if (data[index].isEmpty())
                break;
        }
        outb.append(nl);
        return outb.toString();
    }

    /**
     * Writes a complete sequence.
     * @param id Sequence identifier.
     * @param longestId Longest identifier length.
     * @param data Sequence data.
     * @return Formatted sequence.
     */
    protected String writeSequence(String id, int longestId, String data)
    {
        //Write ID
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        outb.append(id + WriterUtils.align(id.length(), longestId)
                            + WriterUtils.align(longestId, 10) + "  ");

        while(!data.isEmpty())
        {
            //Write a row with 10 character blocks
            for (int j = 0; j < 5; j++)
            {
                //If there are enough characters to make a 10 character block
                if (data.length() > 10)
                {
                    outb.append(data.substring(0, 10) + " ");
                    data = data.substring(10);
                }
                else
                {
                    outb.append(data);
                    data = "";
                }
                //If there are no more characters to write, exit the loop
                if (data.isEmpty())
                    break;
            }
            outb.append(nl);
            if (!data.isEmpty())
            {
                outb.append(WriterUtils.align(0, longestId)
                        + WriterUtils.align(longestId, 10) + "  ");
            }
        }
        outb.append(nl);
        return outb.toString();
    }
}
