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
 * Extends class NexusWriter to adapt the output to Mesquite.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class NexusMesquiteWriter extends NexusWriter
{
    /**
     * Class constructor. Calls the constructor in the superclass.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param sequential Sequential output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public NexusMesquiteWriter(String os, boolean lowerCase, boolean sequential, boolean match, String logger)
    {
        super(os, lowerCase, sequential, match, logger);
    }

    /**
     * Writes the NEXUS header adapted to Mesquite.
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
        out += "symbols=\"ABCDEFGHIKLMNOPQRSTUVWXYZ\"" + nl;
        if (!sequential)
            out += "interleave ";
        out += "datatype=" + type + " gap=-;" + nl + nl;
        out += "matrix" + nl;
        return out;
    }
}
