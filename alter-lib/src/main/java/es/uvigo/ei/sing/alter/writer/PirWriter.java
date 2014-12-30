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

import es.uvigo.ei.sing.alter.types.DNA;
import es.uvigo.ei.sing.alter.types.DNACircular;
import es.uvigo.ei.sing.alter.types.DNALinear;
import es.uvigo.ei.sing.alter.types.Describable;
import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Nucleotide;
import es.uvigo.ei.sing.alter.types.Protein;
import es.uvigo.ei.sing.alter.types.ProteinComplete;
import es.uvigo.ei.sing.alter.types.ProteinFragment;
import es.uvigo.ei.sing.alter.types.RNA;
import es.uvigo.ei.sing.alter.types.RNACircular;
import es.uvigo.ei.sing.alter.types.RNALinear;
import es.uvigo.ei.sing.alter.types.RNAOther;
import es.uvigo.ei.sing.alter.types.RNAt;
import es.uvigo.ei.sing.alter.types.Sequence;
import es.uvigo.ei.sing.alter.types.Type;
import es.uvigo.ei.sing.alter.types.Typeable;

/**
 * Implements interface Writer for the PIR format.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */
public class PirWriter implements Writer
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
    public PirWriter(String os, boolean lowerCase, boolean match, String logger)
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
     * Writes a MSA in PIR format.
     * @param msa Input MSA.
     * @return MSA in PIR format.
     */
    public String write(MSA msa)
    {
        //Output
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);

        String type = getType(msa);
        LinkedHashSet<String> ids = new LinkedHashSet<String>(msa.getSeqs().size());
        //Write sequences
        for (int i = 0; i < msa.getSeqs().size(); i++)
        {
            //Get sequence
            Sequence seq = (Sequence) msa.getSeqs().elementAt(i);
            //Instantiate attributes
            String id = getId(seq, ids);
            ids.add(id);
            String data = getData(seq, (Sequence) msa.getSeqs().firstElement());
            String desc = getDesc(seq);
            //Write sequence
            outb.append(writeSequence(type, id, desc, data));
        }
        
        logger.log(Level.INFO, "MSA successfully converted to PIR format!");
        return outb.toString();
    }

    /**
     * Devuelve una cadena con el identificador de la secuencia. En caso de
     * que el identificador de secuencia esté repetido se renombra.
     * @param seq Secuencia de la cual se desea obtener el identificador.
     * @param ids Secuencias copiadas hasta el momento.
     * @return Identificador de secuencia único.
     */
    protected String getId(Sequence seq, LinkedHashSet<String> ids)
    {
        String id = seq.getId();

        return WriterUtils.getUniqueId(ids, id, logger.getName());
    }

    /**
     * Returns the sequence identifier. In case the current identifier is
     * repeated, it will be renamed.
     * @param seq Sequence containing the identifier.
     * @param ids Sequences copied so far.
     * @return Unique sequence identifier.
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
     * Returns a sequence description.
     * @param seq Sequence to get the description of..
     * @return Sequence description.
     */
    protected String getDesc(Sequence seq)
    {
        if (seq instanceof Describable)
            return ((Describable) seq).getDesc();
        else
            return "";
    }

    /**
     * Returns a string with the type of MSA.
     * @param msa MSA to get the type of.
     * @return MSA type.
     */
    protected String getType(MSA msa)
    {
        //Infer type if the instance is not Typeable
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
            if (type instanceof DNA)
                if (type instanceof DNALinear)
                    return "DL";
                else if (type instanceof DNACircular)
                    return "DC";
                else
                    return "DL";
            else if (type instanceof RNA)
                if (type instanceof RNALinear)
                    return "RL";
                else if (type instanceof RNACircular)
                    return "RC";
                else if (type instanceof RNAt)
                    return "N3";
                else if (type instanceof RNAOther)
                    return "N1";
                else
                    return "RL";
            else
                return "DL";
        else if (type instanceof Protein)
            if (type instanceof ProteinComplete)
                return "P1";
            else if (type instanceof ProteinFragment)
                return "F1";
            else
                return "P1";
        else
            return "XX";
    }

    /**
     * Writes a complete sequence.
     * @param type Sequence type.
     * @param id Sequence identifier.
     * @param desc Sequence description.
     * @param data Sequence data.
     * @return Formatted sequence.
     */
    protected String writeSequence(String type, String id, String desc, String data)
    {
        String out = "";
        //Write type, ID and description.
        out += ">" + type + ";" + id + nl;
        out += desc + nl;

        //While there are characters to write
        while (data.length() > 0)
        {
            //If there are enough characters to make a line
            if (data.length() > 60)
            {
                out += data.substring(0, 60);
                data = data.substring(60);
            }
            else
            {
                out += data;
                data = "";
            }
            out += nl;
        }
        out += "*" + nl;

        return out;
    }
}
