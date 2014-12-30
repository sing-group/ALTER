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

import es.uvigo.ei.sing.alter.types.MSA;

/**
 * Extends class GdeWriter to adapt the output to SeAl.
 * @author Daniel Gomez Blanco
 * @version 1.0
 */

public class GdeSeAlWriter extends GdeWriter
{
    /**
     * Class constructor. Calls the superclass constructor.
     * @param os Output operating system.
     * @param lowerCase Lowercase output.
     * @param match Output match characters.
     * @param logger Logger name.
     */
    public GdeSeAlWriter(String os, boolean lowerCase, boolean match, String logger)
    {
        super(os,lowerCase,match,logger);
    }

    /**
     * Returns the character that must precede the sequence identifier.
     * @param msa Input MSA.
     * @return '#'.
     */
    @Override
    protected char getStart(MSA msa)
    {
        return '#';
    }
}
