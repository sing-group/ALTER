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
import es.uvigo.ei.sing.alter.types.Lengthable;
import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Nucleotide;
import es.uvigo.ei.sing.alter.types.Protein;
import es.uvigo.ei.sing.alter.types.RNA;
import es.uvigo.ei.sing.alter.types.Sequence;
import es.uvigo.ei.sing.alter.types.Taxable;
import es.uvigo.ei.sing.alter.types.Type;
import es.uvigo.ei.sing.alter.types.Typeable;

/**
 * Implements interface Writer for the NEXUS format.
 * @author Daniel Gomez Blanco
 * @version 1.1
 */

public class NexusWriter implements Writer
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
    public NexusWriter(String os, boolean lowerCase, boolean sequential, boolean match, String logger)
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
     * Writes a MSA in NEXUS format.
     * @param msa Input MSA.
     * @return MSA in NEXUS format.
     */
    public String write(MSA msa)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);
       
        //Copy sequences and find longest ID
        String[] data = new String[msa.getSeqs().size()];
        LinkedHashSet<String> id = new LinkedHashSet<String>(msa.getSeqs().size());
        int longestId = 0;

        for (int i = 0; i < msa.getSeqs().size(); i++)
        {
            Sequence seq = (Sequence)msa.getSeqs().elementAt(i);
            //Copy data
            data[i] = getData(seq, (Sequence) msa.getSeqs().firstElement());
            //Copy ID
            String uid = getId(seq,id);
            id.add(uid);
            //Update longest ID
            if (uid.length() > longestId)
                longestId = uid.length();
        }

        //Intantiate number of sequences, length and type
        int taxa = getTaxa(msa);
        int length = getLength(msa);
        String type = getType(msa);

        //Write header
        outb.append(writeHeader(taxa, length, type));

        if (sequential)
            outb.append(sequential(id, longestId, data));
        else
            outb.append(interleaved(id, longestId, data));

        outb.append(writeFooter());
        
        logger.log(Level.INFO, "MSA successfully converted to NEXUS format!");
        return outb.toString();
    }

    /**
     * Writes sequences of a MSA in sequential NEXUS.
     * @param id Sequences identifiers.
     * @param longestId Longest identifier length (needed to align).
     * @param data Sequences data.
     * @return Sequences in sequential NEXUS.
     */
    private String sequential (LinkedHashSet<String> id, int longestId, String[] data)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);
        //Write sequences
        int i= 0;
        for(String uid:id)
        {
            outb.append(writeSequence(uid, longestId, data[i]));
            i++;
        }
        return outb.toString();
    }

    /**
     * Writes sequences of a MSA in interleaved NEXUS.
     * @param id Sequences identifiers.
     * @param longestId Longest identifier length (needed to align).
     * @param data Sequences data.
     * @return Sequences in interleaved NEXUS.
     */
    private String interleaved (LinkedHashSet<String> id, int longestId, String[] data)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_MSALENGTH);
        //Write sequences
        while (!data[0].isEmpty())
        {
            int i=0;
            for(String uid:id)
            {
                outb.append(writeLine(uid, longestId, data, i));
                i++;
            }
                
            outb.append(nl);
        }
        
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
        //Copiar identificador
        String id = seq.getId();
        //Si contiene espacios
        if (id.contains(" ") || id.contains("\t"))
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                    + "\" contains blanks. Blanks replaced by \"_\"");
            id = id.replaceAll("[\t ]","_");
        }
        //Si contiene caracteres destinados a comentarios
        if (id.contains("[") || id.contains("]"))
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                    + "\" contains \"[\" or \"]\". Characters replaced by \"_\"");
            id = id.replaceAll("\\[\\]","_");
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
     * Returns a string with the MSA type.
     * @param msa MSA to get the type of.
     * @return MSA type.
     */
    protected String getType(MSA msa)
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
            return "PROTEIN";
        else if (type instanceof Nucleotide)
            if (type instanceof DNA)
                return "DNA";
            else if (type instanceof RNA)
                return "RNA";
            else
                return "NUCLEOTIDE";
        else
            return "UNKNOWN";
    }

    /**
     * Writes the NEXUS header.
     * @param taxa Number of sequences.
     * @param length Sequences length.
     * @param type MSA type.
     * @return NEXUS header.
     */
    protected String writeHeader(int taxa, int length, String type)
    {
        String out = "#NEXUS" + nl;
        out += "BEGIN DATA;" + nl;
        out += "dimensions ntax=" + taxa + " nchar=" + length + ";" + nl;
        out += "format missing=?" + nl;
        out += "symbols=\"ABCDEFGHIKLMNOPQRSTUVWXYZ\"" + nl;
        if (!sequential)
            out += "interleave ";
        out += "datatype=" + type + " gap=- match=.;" + nl + nl;
        out += "matrix" + nl;
        return out;
    }

    /**
     * Writes NEXUS footer.
     * @return NEXUS footer.
     */
    protected String writeFooter()
    {
        return ";" + nl + "end;" + nl;
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
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        outb.append(id + WriterUtils.align(id.length(),longestId)
                        + WriterUtils.align(longestId, 10) + "  ");

        outb.append(data + nl);
        return outb.toString();
    }

     /**
     * Writes a line corresponding to a sequence of the MSA.
     * @param id Sequence identifier.
     * @param longestId Longest identifier length (needed to align).
     * @param data Sequences data.
     * @param index Index of the current sequence in the array (needed to
     * update data).
     * @return Line in NEXUS format.
     */
    protected String writeLine(String id, int longestId, String[] data, int index)
    {
        StringBuffer outb = new StringBuffer(STRING_BUFFER_SEQLENGTH);
        outb.append(id + WriterUtils.align(id.length(),longestId)
                        + WriterUtils.align(longestId, 10) + "  ");

        //Escribir una línea si es posible
        if (data[index].length() > 50)
        {
            outb.append(data[index].substring(0, 50));
            data[index] = data[index].substring(50);
        }
        else
        {
            outb.append(data[index]);
            data[index] =  "";
        }
        outb.append(nl);
        return outb.toString();
    }
}
