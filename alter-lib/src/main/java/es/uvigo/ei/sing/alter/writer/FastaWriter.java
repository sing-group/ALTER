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
import es.uvigo.ei.sing.alter.types.Sequence;

/**
 * Implements interface Writer for the FASTA format.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */

public class FastaWriter implements Writer
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
    public FastaWriter(String os, boolean lowerCase, boolean match, String logger)
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
     * Writes a MSA in FASTA format.
     * @param msa Input MSA.
     * @return MSA in FASTA format.
     */
    public String write(MSA msa)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);

        LinkedHashSet<String> ids = new LinkedHashSet<String>(msa.getSeqs().size());
        //Write sequences
        for(int i=0;i<msa.getSeqs().size();i++)
        {
            Sequence seq = (Sequence)msa.getSeqs().elementAt(i);
            String id = getId(seq, ids);
            ids.add(id);
            String data = getData(seq, (Sequence) msa.getSeqs().firstElement());
            outb.append(writeSequence(id, data));
        }

        logger.log(Level.INFO, "MSA successfully converted to FASTA format!");
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

        return WriterUtils.getUniqueId(ids,id,logger.getName());
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
     * Writes a complete sequence.
     * @param id Sequence identifier.
     * @param data Sequence data.
     * @return Formatted sequence.
     */
    protected String writeSequence(String id, String data)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        //Escribir ID y descripcion
        outb.append(">" + id);
        outb.append(nl);

        //Mientras queden caracteres por escribir
        while(!data.isEmpty())
        {
            //Si hay caracteres para hacer una línea
            if (data.length() > 60)
            {            
                outb.append(data.substring(0, 60));
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
