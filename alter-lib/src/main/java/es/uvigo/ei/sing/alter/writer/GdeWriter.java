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

import es.uvigo.ei.sing.alter.types.Describable;
import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Nucleotide;
import es.uvigo.ei.sing.alter.types.Sequence;
import es.uvigo.ei.sing.alter.types.Type;
import es.uvigo.ei.sing.alter.types.Typeable;

/**
 * Implements interface Writer for the GDE format.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */

public class GdeWriter implements Writer
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
    public GdeWriter(String os, boolean lowerCase, boolean match, String logger)
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
    }

    /**
     * Writes a MSA in GDE format.
     * @param msa Input MSA.
     * @return MSA in GDE format.
     */
    public String write(MSA msa)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);

        //Start character
        char start = getStart(msa);
        LinkedHashSet<String> ids = new LinkedHashSet<String>(msa.getSeqs().size());
        //Write sequences
        for(int i=0;i<msa.getSeqs().size();i++)
        {
            Sequence seq = (Sequence)msa.getSeqs().elementAt(i);
            String id = getId(seq,ids);
            ids.add(id);
            String data = getData(seq, (Sequence)msa.getSeqs().firstElement());
            outb.append(writeSequence(id, data, start));
        }
        logger.log(Level.INFO, "MSA successfully converted to GDE format!");
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
        if (seq instanceof Describable)
            id += " " + ((Describable) seq).getDesc();

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
     * Returns the character that must precede the sequence identifier.
     * @param msa Input MSA.
     * @return '#' for nucleotides and '%' in othe case.
     */
    protected char getStart(MSA msa)
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

        if (type instanceof Nucleotide)
            return '#';
        else
            return '%';
    }

    /**
     * Writes a complete sequence.
     * @param id Sequence identifier.
     * @param data Sequence data.
     * @param start Start character.
     * @return Formatted sequence.
     */
    protected String writeSequence(String id, String data, char start)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        //Write ID
        outb.append(start + id + nl);

        //While there are characters to be written
        while(!data.isEmpty())
        {
            //If there are enough characters to make a line
            if (data.length() > 60)
            {
                outb.append(data.substring(0,60));
                data = data.substring(60);
            }
            else
            {
                outb.append(data);
                data = "";
            }
            outb.append(nl);
        }

        return outb.toString();
    }
}
