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
 * Extends class PhylipWriter to adapt the output to BioEdit.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class PhylipBioEditWriter extends PhylipWriter
{
    /**
     * Class constructor. Calls superclass constructor.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param sequential Sequential output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public PhylipBioEditWriter(String os, boolean lowerCase, boolean sequential, boolean match, String logger)
    {
        super(os,lowerCase,sequential,match, logger);
    }

    /**
     * Writes the PHYLIP header, adapted to BioEdit.
     * @param taxa Number of sequences.
     * @param length Sequences length.
     * @return PHYLIP header.
     */
    @Override
    protected String writeHeader(int taxa, int length)
    {
        return " " + taxa + " " + length + nl;
    }
}
