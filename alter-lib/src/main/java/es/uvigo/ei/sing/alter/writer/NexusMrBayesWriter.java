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

import es.uvigo.ei.sing.alter.types.DNA;
import es.uvigo.ei.sing.alter.types.MSA;
import es.uvigo.ei.sing.alter.types.Nucleotide;
import es.uvigo.ei.sing.alter.types.Protein;
import es.uvigo.ei.sing.alter.types.RNA;
import es.uvigo.ei.sing.alter.types.Sequence;
import es.uvigo.ei.sing.alter.types.Type;
import es.uvigo.ei.sing.alter.types.Typeable;

/**
 * Extends class NexusWriter to adapt the output to MrBayes.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class NexusMrBayesWriter extends NexusWriter
{
    /**
     * Class constructor. Calls the constructor in the superclass.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param sequential Sequential output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public NexusMrBayesWriter(String os, boolean lowerCase, boolean sequential, boolean match, String logger)
    {
        super(os, lowerCase, sequential, match, logger);
    }

    /**
     * Returns the sequence identifier, considering MrBayes limitations. In case
     * the current identifier is repeated, it will be renamed.
     * @param seq Sequence containing the identifier.
     * @param ids Sequences copied so far.
     * @return Unique sequence identifier.
     */
    @Override
    protected String getId(Sequence seq, LinkedHashSet<String> ids)
    {
         //Copy ID
        String id = seq.getId();
        //If it contains spaces
        if (id.contains(" ") || id.contains("\t"))
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                    + "\" contains blanks. Blanks replaced by \"_\"");
            id = id.replaceAll("[\t ]","_");
        }
        //If it contains characters used indicate comments
        if (id.contains("[") || id.contains("]"))
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                    + "\" contains \"[\" or \"]\". Characters replaced by \"_\"");
            id = id.replaceAll("\\[\\]","_");
        }

        if (id.replaceAll("[_.0-9a-zA-Z]", "").length() != 0)
        {
            logger.log(Level.WARNING,"ID for sequence \"" + id
                    + "\" contains invalid characters. Characters replaced by \"_\"");
            id = id.replaceAll("[^_.0-9a-zA-Z]","_");
        }

        return WriterUtils.getUniqueId(ids, id, logger.getName());
    }

    /**
     * Writes the NEXUS header adapted to MrBayes.
     * @param taxa Number of sequences.
     * @param length Sequences length.
     * @param type MSA type.
     * @return NEXUS header.
     */
    @Override
    protected String writeHeader(int taxa, int length, String type)
    {
        String out = "#NEXUS" + nl;
        out += "BEGIN DATA;" + nl;
        out += "dimensions ntax=" + taxa + " nchar=" + length + ";" + nl;
        out += "format missing=?" + nl;
        if (sequential)
            out += "interleave=no ";
        else
            out += "interleave=yes ";
        out += "datatype=" + type + " gap=- match=.;" + nl + nl;
        out += "matrix" + nl;
        return out;
    }

    /**
     * Returns a string with the MSA type. MrBayes does not accept NUCLEOTIDE
     * type so it is changed to DNA.
     * @param msa MSA to get the type of.
     * @return MSA type.
     */
    @Override
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
                return "DNA";
        else
            return "UNKNOWN";
    }
}
