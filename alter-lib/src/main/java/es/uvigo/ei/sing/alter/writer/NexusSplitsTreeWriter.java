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

/**
 * Extends class NexusWriter to adapt the output to SplitsTree.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class NexusSplitsTreeWriter extends NexusWriter
{
    /**
     * Class constructor. Calls the constructor in the superclass.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param sequential Sequential output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public NexusSplitsTreeWriter(String os, boolean lowerCase, boolean sequential, boolean match, String logger)
    {
        super(os, lowerCase, sequential, match, logger);
    }

    /**
     * Writes the NEXUS header adapted to SplitsTree.
     * @param taxa Number of sequences.
     * @param length Sequences length.
     * @param type MSA type.
     * @return NEXUS header.
     */
    @Override
    protected String writeHeader(int taxa, int length, String type)
    {
        String out = "#nexus" + nl;
        out += "begin taxa;" + nl;
        out += "dimensions ntax=" + taxa + ";" + nl;
        out += "taxlabels _detect_;" + nl;
        out += "end;" + nl + nl;
        out += "begin characters;" + nl;
        out += "dimensions nchar=" + length + ";" + nl;
        out += "format missing=?" + nl;
        if (!sequential)
            out += "interleave ";
        out += "datatype=" + type.toLowerCase() + " labels gap=-;" + nl + nl;
        out += "matrix" + nl;
        return out;
    }
}
