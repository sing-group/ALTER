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

/**
 * Implements interface Writer for the MEGA format.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class MegaWriter implements Writer
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
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public MegaWriter(String os, boolean lowerCase, boolean match, String logger)
    {
        if (os.equals("macos"))
            nl = "\r";
        else if (os.equals("linux"))
            nl = "\n";
        else
            nl = "\r\n";
        this.lowerCase = lowerCase;
        this.match = match;
        this.logger = Logger.getLogger(logger);
        this.logger.setLevel(Level.ALL);
    }

    /**
     * Writes a MSA in MEGA format.
     * @param msa Input MSA.
     * @return MSA in MEGA format.
     */
    public String write(MSA msa)
    {
        //Output
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);

        outb.append(writeHeader());

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
        //Write sequences
        while(!data[0].isEmpty())
        {
            int i=0;
            for (String uid:id)
            {
                outb.append(writeLine(uid, longestId, data, i));
                i++;
            }
            outb.append(nl);
        }

        logger.log(Level.INFO, "MSA successfully converted to MEGA format!");
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
     * Writes the MEGA format header.
     * @return MEGA format header.
     */
    protected String writeHeader()
    {
        return "#mega" + nl + "TITLE: MSA converted with ALTER 1.3.3" + nl + nl;
    }

    /**
     * Writes a line corresponding to a sequence of the MSA.
     * @param id Sequence identifier.
     * @param longestId Longest identifier length (needed to align).
     * @param data Sequences data.
     * @param index Index of the current sequence in the array (needed to
     * update data).
     * @return Line in MEGA format.
     */
    protected String writeLine(String id, int longestId, String[] data, int index)
    {
        //Write ID
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        outb.append("#" + id + WriterUtils.align(id.length(), longestId) +
                WriterUtils.align(longestId, 10) + "  ");

        //Write a line of 10 characters blocks
        for (int j = 0; j < 5; j++)
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
            }
            //If there are no more data exit the loop
            if (data[index].isEmpty())
                break;
        }
        outb.append(nl);
        return outb.toString();
    }

}
